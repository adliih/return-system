package com.adli.returnmanagementservice.controller;

import com.adli.returnmanagementservice.entity.order.Items;
import com.adli.returnmanagementservice.entity.order.Orders;
import com.adli.returnmanagementservice.entity.returns.ReturnItems;
import com.adli.returnmanagementservice.entity.returns.ReturnToken;
import com.adli.returnmanagementservice.entity.returns.Returns;
import com.adli.returnmanagementservice.repository.order.ItemsRepository;
import com.adli.returnmanagementservice.repository.returns.ReturnItemsRepository;
import com.adli.returnmanagementservice.repository.returns.ReturnTokenRepository;
import com.adli.returnmanagementservice.repository.returns.ReturnsRepository;
import com.adli.returnmanagementservice.request.returns.CreateReturnItemRequest;
import com.adli.returnmanagementservice.request.returns.CreateReturnRequest;
import com.adli.returnmanagementservice.response.returns.ReturnsResponse;
import com.adli.returnmanagementservice.service.returns.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ReturnsController {

    @Autowired
    ReturnsRepository returnsRepository;

    @Autowired
    ReturnTokenRepository returnTokenRepository;

    @Autowired
    ReturnItemsRepository returnItemsRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    ReturnService returnService;

    @GetMapping("/returns/{id}")
    @Transactional
    public ResponseEntity<ReturnsResponse> getReturnDetail(@PathVariable int id) {
        Returns returns = returnsRepository.findById(id).orElseThrow();

        ReturnsResponse response = ReturnsResponse.of(returns).build();

        return ResponseEntity.of(Optional.of(response));
    }

    @PostMapping("/returns")
    public ResponseEntity<ReturnsResponse> createReturn(@RequestBody CreateReturnRequest request) {
        // validation check token valid or not (no invalid token)
        ReturnToken returnToken = returnTokenRepository.findByToken(request.getToken()).orElseThrow();

        // token should not be used previously
        if (null != returnToken.getReturns()) {
            // token already used, bad request.
            return ResponseEntity.badRequest().build();
        }

        Orders order = returnToken.getOrder();

        // since the request use SKU but in data level we use order item id,
        // we need to convert the sku -> item order id
        // the service itself will also validate the sku value (the order should contain sku requested)
        Map<String, Items> orderItemMap = returnService.convertsSkuToOrderItemOfOrder(
                order,
                request.getItems().stream()
                        .map(CreateReturnItemRequest::getSku)
                        .collect(Collectors.toSet())
        );

        if (request.getItems().size() != orderItemMap.size()) {
            // some of sku is not found
            return ResponseEntity.unprocessableEntity().build();
        }

        // Validation to check quantity is not exceeding the ordered quantity
        boolean isInvalidQuantityRequested = request.getItems().stream()
                .anyMatch(createReturnItemRequest -> {
                    int requestedQuantity = createReturnItemRequest.getQuantity();
                    int orderedQuantity = orderItemMap.get(createReturnItemRequest.getSku()).getQuantity();
                    return requestedQuantity > orderedQuantity;
                });

        if (isInvalidQuantityRequested) {
            return ResponseEntity.badRequest().build();
        }

        // validation check orderId-sku already returned or not (no double return)
        List<ReturnItems> existingReturnedOrderItems = returnItemsRepository.findAllByOrderItemId(
                orderItemMap.values().stream()
                .map(Items::getId)
                .toList()
        );
        if (!existingReturnedOrderItems.isEmpty()) {
            // there are already returned order item
            // reject the request
            return ResponseEntity.badRequest().build();
        }

        // construct the return items request
        Set<ReturnItems> returnItems = request.getItems().stream()
                .map(createReturnItemRequest -> {
                    Items orderItem = orderItemMap.get(createReturnItemRequest.getSku());
                    return ReturnItems.builder()
                            .quantity(createReturnItemRequest.getQuantity())
                            .orderItem(orderItem)
                            .build();
                }).collect(Collectors.toSet());


        Returns returns = returnService.createReturn(returnToken, returnItems);

        ReturnsResponse response = ReturnsResponse.of(returns)
                // Use estimated refund amount when initial creating returns data
                .refundAmount(returnService.calculateEstimatedRefundAmount(returns))
                .build();

        return ResponseEntity.of(Optional.of(response));
    }
}
