package com.adli.returnmanagementservice.service.returns;

import com.adli.returnmanagementservice.entity.order.Orders;
import com.adli.returnmanagementservice.repository.order.OrdersRepository;
import com.adli.returnmanagementservice.repository.returns.ReturnsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReturnServiceTest {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ReturnsRepository returnsRepository;

    @Test
    void calculateEstimatedRefundAmountFull() {
        Orders order = ordersRepository.findAll().stream().findFirst().orElseThrow();



    }

    @Test
    void recalculateRefundAmount() {
    }

    @Test
    void recalculateStatus() {
    }
}