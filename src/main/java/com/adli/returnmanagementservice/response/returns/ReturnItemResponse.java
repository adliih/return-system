package com.adli.returnmanagementservice.response.returns;

import com.adli.returnmanagementservice.entity.returns.ReturnItems;
import com.adli.returnmanagementservice.entity.returns.ReturnItemsQcStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReturnItemResponse {
    private int id;
    private String sku;
    private int quantity;
    private ReturnItemsQcStatus status;

    public static ReturnItemResponseBuilder of(ReturnItems item) {
        return ReturnItemResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .quantity(item.getQuantity())
                .sku(item.getOrderItem().getSku());
    }
}
