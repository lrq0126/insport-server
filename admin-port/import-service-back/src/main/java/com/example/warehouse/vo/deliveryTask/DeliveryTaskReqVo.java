package com.example.warehouse.vo.deliveryTask;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class DeliveryTaskReqVo extends PageVo {

    private Integer taskStatus;

    private String taskName;

    private String taskOrder;

    private String orderNumber;

}
