package com.adli.returnmanagementservice.response.returns;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PendingReturnResponse {
    private String token;
    private String orderId;
    private String email;
}
