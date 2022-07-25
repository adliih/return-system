package com.adli.returnmanagementservice.service.returns;

import com.adli.returnmanagementservice.entity.order.Items;
import com.adli.returnmanagementservice.entity.order.Orders;
import com.adli.returnmanagementservice.entity.returns.*;
import com.adli.returnmanagementservice.repository.returns.ReturnItemsRepository;
import com.adli.returnmanagementservice.repository.returns.ReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReturnService {

    @Autowired
    ReturnsRepository returnsRepository;

    @Autowired
    ReturnItemsRepository returnItemsRepository;

    public Map<String, Items> convertsSkuToOrderItemOfOrder(Orders order, Set<String> listOfSku) {
        Map<String, Items> orderItemMap = order.getItems().stream()
                .filter(items -> listOfSku.contains(items.getSku()))
                .collect(Collectors.toMap(Items::getSku, items -> items));

        return orderItemMap;
    }

    public Returns createReturn(ReturnToken returnToken, Set<ReturnItems> items) {

        Returns returns = returnsRepository.save(Returns.builder()
                .token(returnToken)
                .build());

        items.forEach(returnItems -> returnItems.setReturns(returns));

        returnItemsRepository.saveAll(items);

        returns.setItems(items);

        return returns;
    }

    public double calculateEstimatedRefundAmount(Returns returns) {
        return returns.getItems().stream()
                // calculate the refund amount for all item regardless it's accepted or not
                .mapToDouble((item) -> item.getOrderItem().getPrice() * item.getQuantity())
                .sum();
    }

    public void recalculateRefundAmount(Returns returns) {
        double refundAmount = returns.getItems().stream()
                // Only calculate the item with approved status
                .filter((item) -> item.getStatus() == ReturnItemsQcStatus.APPROVED)
                .mapToDouble((item) -> item.getOrderItem().getPrice() * item.getQuantity())
                .sum();

        returns.setRefundAmount(refundAmount);
    }

    public void recalculateStatus(Returns returns) {
        boolean isComplete = returns.getItems().stream()
                // remove the approved item, if it's all gone, then all the items are not waiting anymore
                .noneMatch((item) -> item.getStatus() == ReturnItemsQcStatus.WAITING);

        if (!isComplete) {
            return;
        }
        returns.setStatus(ReturnStatus.COMPLETE);
    }
}
