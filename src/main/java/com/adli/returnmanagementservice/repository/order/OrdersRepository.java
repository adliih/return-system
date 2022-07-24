package com.adli.returnmanagementservice.repository.order;

import com.adli.returnmanagementservice.entity.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, String> {

    Optional<Orders> findByIdAndEmail(String orderId, String email);
}
