package com.adli.returnmanagementservice.response.returns;

import com.adli.returnmanagementservice.entity.returns.ReturnStatus;
import com.adli.returnmanagementservice.entity.returns.Returns;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public class ReturnsResponse {

    private int id;
    private ReturnStatus status;
    private double refundAmount;
    @Singular
    private List<ReturnItemResponse> items;

    public static ReturnsResponseBuilder of(Returns returns) {
        return ReturnsResponse.builder()
                .id(returns.getId())
                .status(returns.getStatus())
                .refundAmount(returns.getRefundAmount())
                .items(returns.getItems().stream().map((item) -> {
                    return ReturnItemResponse.builder()
                            .id(item.getId())
                            .sku(item.getOrderItem().getSku())
                            .quantity(item.getQuantity())
                            .build();
                }).toList());
    }
}
