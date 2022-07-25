package com.adli.returnmanagementservice.entity.returns;

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
public class Returns {

    @Id
    @GeneratedValue
    private int id;

//    @Column(name = "token_id")
//    private int tokenId;

    @Builder.Default()
//    @Column(name = "refund_amount")
    private double refundAmount = 0;

    @Builder.Default()
    private ReturnStatus status = ReturnStatus.AWAITING_APPROVAL;

    // relationships

    @OneToMany(targetEntity = ReturnItems.class, mappedBy = "returns")
    private Set<ReturnItems> items;

    @OneToOne(targetEntity = ReturnToken.class)
    private ReturnToken token;

    // ./relationships
}
