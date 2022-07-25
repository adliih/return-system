package com.adli.returnmanagementservice.controller;

import com.adli.returnmanagementservice.entity.returns.ReturnItems;
import com.adli.returnmanagementservice.entity.returns.ReturnItemsQcStatus;
import com.adli.returnmanagementservice.entity.returns.Returns;
import com.adli.returnmanagementservice.repository.returns.ReturnItemsRepository;
import com.adli.returnmanagementservice.repository.returns.ReturnsRepository;
import com.adli.returnmanagementservice.request.returns.UpdateReturnItemQcStatusRequest;
import com.adli.returnmanagementservice.response.returns.ReturnItemResponse;
import com.adli.returnmanagementservice.response.returns.UpdateReturnItemQcStatusResponse;
import com.adli.returnmanagementservice.service.returns.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class ReturnItemController {

    @Autowired
    ReturnsRepository returnsRepository;

    @Autowired
    ReturnItemsRepository returnItemsRepository;

    @Autowired
    ReturnService returnService;

    @PutMapping("/returns/{id}/items/{itemId}/qc/status")
    public ResponseEntity<ReturnItemResponse> updateItemStatus(
            @PathVariable("id") int id,
            @PathVariable("itemId") int itemId,
            @RequestBody UpdateReturnItemQcStatusRequest request
    ) {
        Returns returns = returnsRepository.findById(id).orElseThrow();
        ReturnItems returnItem = returns.getItems().stream()
                .filter(returnItems -> returnItems.getId() == itemId)
                .findFirst().orElseThrow();

        if (returnItem.getStatus() != ReturnItemsQcStatus.WAITING) {
            return ResponseEntity.unprocessableEntity().build();
        }

        returnItem.setStatus(request.getStatus());
        returnItem = returnItemsRepository.save(returnItem);

        returnService.recalculateStatus(returns);
        returnService.recalculateRefundAmount(returns);
        returnsRepository.save(returns);

        return ResponseEntity.of(Optional.of(ReturnItemResponse.of(returnItem).build()));
    }
}
