package com.adli.returnmanagementservice.entity.product;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Products {
    @Id
    private String sku;
    private String name;
    private float price; // in $USD
}
