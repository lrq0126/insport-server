package com.example.warehouse.controller;

import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.deliveryTask.DeliveryTaskService;
import com.example.warehouse.vo.deliveryTask.DeliveryTaskReqVo;
import com.example.warehouse.vo.deliveryTask.DeliveryTaskSaveReqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveryTask")
@Slf4j
public class DeliveryTaskController {

    @Autowired
    private DeliveryTaskService deliveryTaskService;

    @PostMapping("getDeliveryTask")
    private ResponseEntity<PageResultModel> getDeliveryTask(DeliveryTaskReqVo deliveryTaskReqVo){
        return deliveryTaskService.getDeliveryTask(deliveryTaskReqVo);
    }

    @PostMapping("getDeliveryTaskAll")
    private ResponseEntity<ResultModel> getDeliveryTaskAll(DeliveryTaskReqVo deliveryTaskReqVo){
        return deliveryTaskService.getDeliveryTaskAll(deliveryTaskReqVo);
    }

    // PC端查看详情
    @PostMapping("getDeliveryTaskById")
    private ResponseEntity<ResultModel> getDeliveryTaskById(Integer id){
        return deliveryTaskService.getDeliveryTaskById(id);
    }
    // PDA查看详情
    @PostMapping("pgetDeliveryTaskById")
    private ResponseEntity<ResultModel> pgetDeliveryTaskById(Integer id){
        return deliveryTaskService.padGetDeliveryTaskById(id);
    }

    @PostMapping("saveDeliveryTask")
    private ResponseEntity<ResultModel> saveDeliveryTask(DeliveryTaskSaveReqVo saveReqVo){
        return deliveryTaskService.saveDeliveryTask(saveReqVo);
    }

    @PostMapping("deleteDeliveryTask")
    private ResponseEntity<ResultModel> deleteDeliveryTask(Integer id){
        return deliveryTaskService.deleteDeliveryTask(id);
    }

    @PostMapping("overTask")
//    @SysLog(value = "订单扫码出库[订单号]",type = SysLog.Type.UPDATE,contentId = "#orderNumber")
    private ResponseEntity<ResultModel> overTask(@RequestParam("taskId") Integer taskId){
        return deliveryTaskService.overTask(taskId);
    }

}
