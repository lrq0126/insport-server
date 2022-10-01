package com.example.warehouse.vo.deliveryTask;

import com.example.warehouse.entity.deliveryTask.DeliveryTask;
import com.example.warehouse.entity.deliveryTask.DeliveryTaskDetail;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryTaskRespVo extends DeliveryTask {

    private String createName;

    private List<DeliveryTaskDetailRespVo> deliveryTaskDetails;


}
