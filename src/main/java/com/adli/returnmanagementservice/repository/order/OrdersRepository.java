package com.adli.returnmanagementservice.repository.order;

import com.adli.returnmanagementservice.entity.order.Orders;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface OrdersRepository extends CrudRepository<Orders, String> {

    public Optional<Orders> findByIdAndEmail(String orderId, String email);
}
