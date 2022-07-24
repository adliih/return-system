package com.adli.returnmanagementservice.request.returns;

import lombok.Getter;

@Getter
public class PendingReturnCreationRequest {
    private String orderId;
    private String email;
}
