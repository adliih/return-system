package com.adli.returnmanagementservice.request.returns;

import lombok.Getter;

@Getter
public class CreateReturnItemRequest {
    private String sku;
    private int quantity;
}
