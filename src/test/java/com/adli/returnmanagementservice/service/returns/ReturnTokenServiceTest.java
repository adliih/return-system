package com.adli.returnmanagementservice.service.returns;

import com.adli.returnmanagementservice.entity.order.Orders;
import com.adli.returnmanagementservice.entity.returns.ReturnToken;
import com.adli.returnmanagementservice.repository.order.OrdersRepository;
import com.adli.returnmanagementservice.repository.returns.ReturnTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReturnTokenServiceTest {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ReturnTokenService service;

    @Autowired
    ReturnTokenRepository returnTokenRepository;

    @Test
    void issueTokenForOrder() {
        Orders order = ordersRepository.findAll().stream().findFirst().orElseThrow();

        ReturnToken token = service.issueTokenForOrder(order);

        // try to re-fetch by token
        token = returnTokenRepository.findByToken(token.getToken()).orElseThrow();

        assertNotNull(token);
        assertNotNull(token.getOrder());
        assertNull(token.getReturns());
    }
}