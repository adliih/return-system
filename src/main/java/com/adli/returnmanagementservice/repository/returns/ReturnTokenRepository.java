package com.adli.returnmanagementservice.repository.returns;

import com.adli.returnmanagementservice.entity.returns.ReturnToken;
import com.adli.returnmanagementservice.entity.returns.Returns;
import org.springframework.data.repository.CrudRepository;

public interface ReturnTokenRepository extends CrudRepository<ReturnToken, Integer> {
}
