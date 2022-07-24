package com.adli.returnmanagementservice.repository.returns;

import com.adli.returnmanagementservice.entity.returns.ReturnToken;
import com.adli.returnmanagementservice.entity.returns.Returns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReturnTokenRepository extends JpaRepository<ReturnToken, Integer> {
    public Optional<ReturnToken> findByToken(String token);
}
