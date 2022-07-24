package com.adli.returnmanagementservice.entity.returns;

import com.adli.returnmanagementservice.entity.order.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnToken {
    @Id
    @GeneratedValue
    private int id;

    private String token;

//    @Column(name = "order_id")
//    private String orderId;

    // relationships

    @ManyToOne(targetEntity = Orders.class)
    private Orders order;

    @OneToOne(targetEntity = Returns.class)
    private Returns returns;

    // ./relationships
}
