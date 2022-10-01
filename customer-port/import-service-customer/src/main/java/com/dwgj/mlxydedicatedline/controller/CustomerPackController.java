package com.dwgj.mlxydedicatedline.controller;

import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.CustomerPackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.*;
import static com.dwgj.mlxydedicatedline.enums.ResultStatus.GOODS_UPDATE_ADDRESS_ERROR;

@RestController
@RequestMapping("/customerPack")
public class CustomerPackController {

    @Autowired
    private CustomerPackService customerPackService;

    @PostMapping("/getPackDetail")
    public ResponseEntity<ResultModel> getPackDetail(@RequestParam("businessNumber") String businessNumber){
        return customerPackService.getPackDetail(businessNumber);
    }

    @PostMapping("/update/address")
    public ResponseEntity<ResultModel> updateAddress(@RequestParam Map<String, Object> paramMap){
        if (!paramMap.containsKey("addressId") || "undefined".equals(paramMap.get("addressId"))){
            return new ResponseEntity<>(ResultModel.error(ADDRESS_CHOICE_ERROR), HttpStatus.OK);
        }
        int i = customerPackService.updateAddress(paramMap);
        if(i == -3){
            return new ResponseEntity<>(ResultModel.error(GOODS_UPDATE_ADDRESS_ERROR),HttpStatus.OK);
        }
        if(i < 1){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    /**
     * 取消拆包操作
     * @param packId
     * @return
     */
    @PostMapping("/cancel/unpack")
    public ResponseEntity<ResultModel> cancelUnpack(@RequestParam Integer packId) {
        int i = customerPackService.cancelUnpack(packId);
        if (i == 1) {
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }

    /**
     * 包裹签收
     * @param packId
     * @return
     */
    @PostMapping("/receipt")
    public ResponseEntity<ResultModel> receipt(@RequestParam Integer packId) {
        return customerPackService.receipt(packId);
    }

    @PostMapping("/getOrderReceivesAddress")
    public ResponseEntity<ResultModel> getOrderReceivesAddress(@RequestParam String orderNumber){
        return customerPackService.getOrderReceivesAddress(orderNumber);
    }

    /**
     * 获取轨迹活动
     * @return
     */
    @GetMapping("/getTrajectoryAdvertising")
    public ResponseEntity<ResultModel> getTrajectoryAdvertising(){
        return customerPackService.getTrajectoryAdvertising();
    }
}
