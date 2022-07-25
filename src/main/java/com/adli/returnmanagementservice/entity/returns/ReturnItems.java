package com.adli.returnmanagementservice.entity.returns;

import com.adli.returnmanagementservice.entity.order.Items;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnItems {

    @Id
    @GeneratedValue
    private int id;

//    @Column(name = "return_id")
//    private int returnId;

    private int quantity;

    @Builder.Default()
    private ReturnItemsQcStatus status = ReturnItemsQcStatus.WAITING;

    // relationships

    @ManyToOne(targetEntity = Returns.class, optional = false)
    private Returns returns;

    @ManyToOne(targetEntity = Items.class, optional = false)
    private Items orderItem;

    // ./relationships
}
