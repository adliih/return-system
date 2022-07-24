package com.adli.returnmanagementservice.controller;

import com.adli.returnmanagementservice.entity.order.Orders;
import com.adli.returnmanagementservice.entity.returns.ReturnToken;
import com.adli.returnmanagementservice.repository.order.OrdersRepository;
import com.adli.returnmanagementservice.request.returns.PendingReturnCreationRequest;
import com.adli.returnmanagementservice.response.returns.PendingReturnResponse;
import com.adli.returnmanagementservice.service.returns.ReturnTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class PendingReturnsController {
    @Autowired OrdersRepository ordersRepository;
    @Autowired ReturnTokenService returnTokenService;

    @PostMapping("/pending/returns")
    public PendingReturnResponse postPendingRequest(
            PendingReturnCreationRequest request
    ) {

        Orders order = ordersRepository
                .findByIdAndEmail(request.getOrderId(), request.getEmail())
                .orElseThrow();

        ReturnToken token = returnTokenService.issueTokenForOrder(order);

        return PendingReturnResponse.builder()
                .token(token.getToken())
                .orderId(request.getOrderId())
                .email(request.getEmail())
                .build();
    }
}
