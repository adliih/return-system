package com.adli.returnmanagementservice.response.returns;

import com.adli.returnmanagementservice.entity.returns.ReturnStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
public class ReturnsResponse {

    private int id;
    private ReturnStatus status;
    private double refundAmount;
    @Singular
    private List<ReturnItemResponse> items;
}
