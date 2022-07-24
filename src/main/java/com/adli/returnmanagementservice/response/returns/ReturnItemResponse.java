package com.adli.returnmanagementservice.response.returns;

import lombok.Builder;

@Builder
public class ReturnItemResponse {
    private int id;
    private String sku;
    private int quantity;

}
