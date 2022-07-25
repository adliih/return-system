package com.adli.returnmanagementservice.request.returns;

import com.adli.returnmanagementservice.entity.returns.ReturnItemsQcStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReturnItemQcStatusRequest {
    private ReturnItemsQcStatus status;
}
