package com.example.warehouse.vo.deliveryTask;

import com.example.warehouse.entity.deliveryTask.DeliveryTask;
import lombok.Data;

@Data
public class DeliveryTaskSaveReqVo extends DeliveryTask {

    private String deliveryTaskDetails;

}
