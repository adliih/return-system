package com.adli.returnmanagementservice.service.returns;

import com.adli.returnmanagementservice.entity.order.Orders;
import com.adli.returnmanagementservice.entity.returns.ReturnToken;
import com.adli.returnmanagementservice.repository.returns.ReturnTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class ReturnTokenService {

    @Autowired
    ReturnTokenRepository repository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public ReturnToken issueTokenForOrder(Orders order) {
        String token = this.generateUniqueToken(order.getId());
        return repository.save(ReturnToken.builder()
                .token(token)
                .orderId(order.getId())
                .build());
    }

    private String generateUniqueToken(String orderId) {
        // simple 'unique' hashing to create token
        String hash = encoder.encode(System.currentTimeMillis() + "-" + orderId);

        return Base64.getEncoder().encodeToString(hash.getBytes());
    }
}
