package com.adli.returnmanagementservice.repository.order;

import com.adli.returnmanagementservice.entity.order.Items;
import com.adli.returnmanagementservice.entity.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepository extends JpaRepository<Items, Integer> {
}
