package com.adli.returnmanagementservice.entity.returns;

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
public class ReturnItems {

    @Id
    @GeneratedValue
    private int id;

    private int returnId;

    private int orderItemId;

    private int quantity;

    @Builder.Default()
    private ReturnItemsQcStatus status = ReturnItemsQcStatus.WAITING;
}
