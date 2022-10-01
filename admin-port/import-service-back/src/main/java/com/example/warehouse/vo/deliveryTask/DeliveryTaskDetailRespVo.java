package com.example.warehouse.vo.deliveryTask;

import com.example.warehouse.entity.deliveryTask.DeliveryTaskDetail;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryTaskDetailRespVo extends DeliveryTaskDetail {

    private String parentOrder;

}
