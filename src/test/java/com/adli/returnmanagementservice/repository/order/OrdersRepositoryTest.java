package com.adli.returnmanagementservice.repository.order;

import com.adli.returnmanagementservice.entity.order.Orders;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class OrdersRepositoryTest {

    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    void testFindByOrderIdAndEmailFromDataset() {
        String orderId = "RK-478";
        String email = "john@example.com";
        Orders order = ordersRepository.findByIdAndEmail(orderId, email).orElse(null);

        Assert.notNull(order, "order from csv should not null");

        Assert.notEmpty(order.getItems(), "items of the order should not be empty");
    }

    @Test
    void testFindByOrderIdAndEmailNotFromDataset() {
        String orderId = "RK-000";
        String email = "john@example.com";
        Orders order = ordersRepository.findByIdAndEmail(orderId, email).orElse(null);

        Assert.isNull(order, "order from csv should be null");
    }
}