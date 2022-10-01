package com.example.warehouse.service.deliveryTask;

import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.deliveryTask.DeliveryTaskReqVo;
import com.example.warehouse.vo.deliveryTask.DeliveryTaskSaveReqVo;
import org.springframework.http.ResponseEntity;

public interface DeliveryTaskService {

    ResponseEntity<PageResultModel> getDeliveryTask(DeliveryTaskReqVo deliveryTaskReqVo);

    ResponseEntity<ResultModel> getDeliveryTaskAll(DeliveryTaskReqVo deliveryTaskReqVo);

    ResponseEntity<ResultModel> getDeliveryTaskById(Integer id);

    ResponseEntity<ResultModel> padGetDeliveryTaskById(Integer id);

    ResponseEntity<ResultModel> saveDeliveryTask(DeliveryTaskSaveReqVo saveReqVo);

    ResponseEntity<ResultModel> deleteDeliveryTask(Integer id);

    ResponseEntity<ResultModel> orderDelivery(String orderNumber);

    ResponseEntity<ResultModel> overTask(Integer taskId);
}
