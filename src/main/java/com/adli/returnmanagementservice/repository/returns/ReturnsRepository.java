package com.adli.returnmanagementservice.repository.returns;

import com.adli.returnmanagementservice.entity.product.Products;
import com.adli.returnmanagementservice.entity.returns.Returns;
import org.springframework.data.repository.CrudRepository;

public interface ReturnsRepository extends CrudRepository<Returns, Integer> {
}
