package com.adli.returnmanagementservice.entity.order;

import lombok.*;

import javax.persistence.*;

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

    private String sku;
    private int quantity;
    private double price; // in $USD

    @ManyToOne(targetEntity = Orders.class, optional = false)
    private Orders order;
}
