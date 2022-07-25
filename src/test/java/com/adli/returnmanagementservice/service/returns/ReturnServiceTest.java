package com.adli.returnmanagementservice.service.returns;

import com.adli.returnmanagementservice.entity.order.Items;
import com.adli.returnmanagementservice.entity.order.Orders;
import com.adli.returnmanagementservice.entity.returns.*;
import com.adli.returnmanagementservice.repository.order.OrdersRepository;
import com.adli.returnmanagementservice.repository.returns.ReturnTokenRepository;
import com.adli.returnmanagementservice.repository.returns.ReturnsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReturnServiceTest {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ReturnsRepository returnsRepository;

    @Autowired
    ReturnTokenRepository returnTokenRepository;

    @Autowired
    ReturnService returnService;

    @Autowired
    ReturnTokenService returnTokenService;

    @Test
    void calculateEstimatedRefundAmountFull() {
        Returns returns = Returns.builder()
                .item(ReturnItems.builder().quantity(1).orderItem(Items.builder().price(10.0).build()).build())
                .item(ReturnItems.builder().quantity(2).orderItem(Items.builder().price(10.0).build()).build())
                .build();
        double refundAmount = returnService.calculateEstimatedRefundAmount(returns);

        assertEquals(30.0, refundAmount);
    }

    @Test
    void recalculateRefundAmount() {
        Returns returns = Returns.builder()
                .item(ReturnItems.builder()
                        .quantity(3)
                        .status(ReturnItemsQcStatus.APPROVED)
                        .orderItem(Items.builder().price(15.0).build())
                        .build())
                .item(ReturnItems.builder()
                        .quantity(1)
                        .status(ReturnItemsQcStatus.WAITING)
                        .orderItem(Items.builder().price(10.0).build())
                        .build())
                .build();
        returnService.recalculateRefundAmount(returns);

        assertEquals(45.0, returns.getRefundAmount());
    }

    @Test
    void recalculateStatusPartialApproved() {
        Returns returns = Returns.builder()
                .item(ReturnItems.builder()
                        .status(ReturnItemsQcStatus.APPROVED)
                        .build())
                .item(ReturnItems.builder()
                        .status(ReturnItemsQcStatus.WAITING)
                        .build())
                .build();
        returnService.recalculateStatus(returns);

        assertEquals(ReturnStatus.AWAITING_APPROVAL, returns.getStatus());
    }

    @Test
    void recalculateStatusFullyApproved() {
        Returns returns = Returns.builder()
                .item(ReturnItems.builder()
                        .status(ReturnItemsQcStatus.APPROVED)
                        .build())
                .item(ReturnItems.builder()
                        .status(ReturnItemsQcStatus.APPROVED)
                        .build())
                .build();
        returnService.recalculateStatus(returns);

        assertEquals(ReturnStatus.COMPLETE, returns.getStatus());
    }

    @Test
    void convertsSkuToOrderItemIdOfOrder() {
        Orders order = Orders.builder()
                .item(Items.builder().sku("SKU-1").id(1).build())
                .item(Items.builder().sku("SKU-2").id(2).build())
                .item(Items.builder().sku("SKU-3").id(3).build())
                .build();
        Set<String> listOfSku = new HashSet<>();
        listOfSku.add("SKU-1");
        listOfSku.add("SKU-3");
        Map<String, Integer> orderItemMap = returnService.convertsSkuToOrderItemIdOfOrder(order, listOfSku);

        assertNull(orderItemMap.get("SKU-2"));
        assertEquals(1, orderItemMap.get("SKU-1"));
        assertEquals(3, orderItemMap.get("SKU-3"));
    }

    @Test
    void createReturn() {
        ReturnToken returnToken = ReturnToken.builder().build();
        Orders order = Orders.builder().id("ID").build();

        returnTokenRepository.save(returnToken);
        ordersRepository.save(order);

        Set<ReturnItems> returnItems = new HashSet<>();
        Returns returns = returnService.createReturn(returnToken, order, returnItems);

        assertNotNull(returns);
    }
}