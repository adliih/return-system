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
public class Returns {

    @Id
    @GeneratedValue
    private int id;

    private int tokenId;

    @Builder.Default()
    private long refundAmount = 0;

    @Builder.Default()
    private ReturnStatus status = ReturnStatus.AWAITING_APPROVAL;

}
