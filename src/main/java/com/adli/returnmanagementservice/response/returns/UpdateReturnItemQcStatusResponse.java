package com.adli.returnmanagementservice.response.returns;

import com.adli.returnmanagementservice.entity.returns.ReturnItemsQcStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateReturnItemQcStatusResponse {
    private int returnId;
    private int itemId;
    private ReturnItemsQcStatus status;

}
