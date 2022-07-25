package com.adli.returnmanagementservice.request.returns;

import lombok.Getter;
import lombok.Singular;

import java.util.List;
import java.util.Set;

@Getter
public class CreateReturnRequest {
    private String token;
    @Singular
    private Set<CreateReturnItemRequest> items;
}
