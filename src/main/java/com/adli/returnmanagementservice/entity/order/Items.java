package com.adli.returnmanagementservice.entity.order;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Items {

    @Id
    @GeneratedValue
    private int id;
    private String orderId;
    private String sku;
    private int quantity;
    private float price; // in $USD
}
