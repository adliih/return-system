package com.adli.returnmanagementservice.controller;

import com.adli.returnmanagementservice.entity.returns.Returns;
import com.adli.returnmanagementservice.repository.returns.ReturnsRepository;
import com.adli.returnmanagementservice.request.returns.CreateReturnRequest;
import com.adli.returnmanagementservice.response.returns.ReturnsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReturnsController {

    @Autowired
    ReturnsRepository returnsRepository;

    @GetMapping("/returns/{id}")
    public ReturnsResponse getReturnDetail(@PathVariable int id) {
        Returns returns = returnsRepository.findById(id).orElseThrow();

        return ReturnsResponse.builder()
                .id(returns.getId())
                .status(returns.getStatus())
                .refundAmount(returns.getRefundAmount())
                .build();
    }
}
