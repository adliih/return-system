package com.adli.returnmanagementservice.repository.product;

import com.adli.returnmanagementservice.entity.order.Items;
import com.adli.returnmanagementservice.entity.product.Products;
import org.springframework.data.repository.CrudRepository;

public interface ProductsRepository extends CrudRepository<Products, String> {
}
