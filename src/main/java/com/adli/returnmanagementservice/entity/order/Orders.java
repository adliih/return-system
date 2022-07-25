package com.adli.returnmanagementservice.entity.order;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    private String id;
    private String email;

    // relationships

    @OneToMany(targetEntity = Items.class, cascade = CascadeType.ALL, mappedBy = "order")
    private Set<Items> items;

    // ./relationships
}
