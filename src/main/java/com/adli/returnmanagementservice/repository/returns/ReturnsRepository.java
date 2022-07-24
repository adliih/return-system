package com.adli.returnmanagementservice.repository.returns;

import com.adli.returnmanagementservice.entity.returns.Returns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ReturnsRepository extends JpaRepository<Returns, Integer> {
}
