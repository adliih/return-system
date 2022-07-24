package com.adli.returnmanagementservice.repository.order;

import com.adli.returnmanagementservice.entity.order.Orders;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class OrdersRepositoryTest {

    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    void testFindByOrderIdAndEmailFromDataset() {
        String orderId = "RK-478";
        String email = "john@example.com";
        Orders order = ordersRepository.findByIdAndEmail(orderId, email).orElse(null);

        Assert.notNull(order, "order from csv should not null");
    }

    @Test
    void testFindByOrderIdAndEmailNotFromDataset() {
        String orderId = "RK-000";
        String email = "john@example.com";
        Orders order = ordersRepository.findByIdAndEmail(orderId, email).orElse(null);

        Assert.isNull(order, "order from csv should be null");
    }
}