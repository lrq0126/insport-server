package com.example.warehouse.controller.customerPack;

import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.customerPack.OrderEvaluateService;
import com.example.warehouse.vo.packVo.OrderEvaluateReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderEvaluate")
public class OrderEvaluateController {

    @Autowired
    private OrderEvaluateService orderEvaluateService;

    @PostMapping("getEvaluateList")
    public ResponseEntity<PageResultModel> getEvaluateList(OrderEvaluateReqVo orderEvaluateReqVo){
        return orderEvaluateService.getEvaluateList(orderEvaluateReqVo);
    }

    @PostMapping("exportEvaluate")
    public ResponseEntity<ResultModel> exportEvaluate(OrderEvaluateReqVo orderEvaluateReqVo){
        return orderEvaluateService.exportEvaluate(orderEvaluateReqVo);
    }

    @PostMapping("payReturn")
    public ResponseEntity<ResultModel> payReturn(String orderMessage){
        return orderEvaluateService.payReturn(orderMessage);
    }


}
