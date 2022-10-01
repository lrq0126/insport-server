package com.dwgj.mlxydedicatedline.controller.customerPack;

import com.dwgj.mlxydedicatedline.entity.customerPack.OrderEvaluate;
import com.dwgj.mlxydedicatedline.resultType.PageResultModel;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.customerPack.OrderEvaluateService;
import com.dwgj.mlxydedicatedline.vo.orderEvaluate.OrderEvaluateReqVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/orderEvaluate")
public class OrderEvaluateController {
    @Autowired
    private OrderEvaluateService orderEvaluateService;

    @PostMapping("/getEvaluateList")
    public ResponseEntity<PageResultModel> getEvaluateList(OrderEvaluateReqVo orderEvaluateReqVo){
        return orderEvaluateService.getEvaluateList(orderEvaluateReqVo);
    }

    @PostMapping("/getEvaluateDetail")
    public ResponseEntity<ResultModel> getEvaluateDetail(int packId){
        return orderEvaluateService.getEvaluateByPackId(packId);
    }

    /**
     *
     * @param multipartFiles 图片组
     * @param orderEvaluate 评价实体
     * @return
     */
    @ApiOperation(value = "订单评价")
    @PostMapping("/evaluate")
    public ResponseEntity<ResultModel> evaluate(
            @ApiParam(name="file",value="图片组",required=true) @RequestParam("file") MultipartFile[] multipartFiles,
            @ApiParam(name="orderEvaluate",value="评价实体",required=true) OrderEvaluate orderEvaluate){
        return orderEvaluateService.saveEvaluate(orderEvaluate, multipartFiles);
    }


}
