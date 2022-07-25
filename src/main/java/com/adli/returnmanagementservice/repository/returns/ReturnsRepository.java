package com.adli.returnmanagementservice.repository.returns;

import com.adli.returnmanagementservice.entity.returns.Returns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnsRepository extends JpaRepository<Returns, Integer> {
}
