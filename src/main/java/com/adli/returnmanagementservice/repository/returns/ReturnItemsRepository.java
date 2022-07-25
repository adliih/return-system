package com.adli.returnmanagementservice.repository.returns;

import com.adli.returnmanagementservice.entity.returns.ReturnItems;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReturnItemsRepository extends JpaRepository<ReturnItems, Integer> {

    @Query("FROM ReturnItems ri where ri.orderItem.id in (:orderItemIds)")
    List<ReturnItems> findAllByOrderItemId(@Param("orderItemIds") Iterable<Integer> orderItemIds);
}
