package com.example.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.example.warehouse.entity.Operator;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.OperatorService;
import com.example.warehouse.service.wechat.SendMessageServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.ERROR;

@RestController
@RequestMapping("/operator")
@Slf4j
public class OperatorController {

    @Autowired
    private SendMessageServer sendMessageServer;

    @Autowired
    private OperatorService operatorService;

    @GetMapping("/find/all")
    @ResponseBody
    public ResponseEntity<ResultModel> findAll(){
        return new ResponseEntity<>(ResultModel.ok(operatorService.findAll()),HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<ResultModel> create(@RequestParam Map<String, Object> params){
        String str = JSON.toJSON(params).toString();
        Operator operator = JSON.parseObject(str,Operator.class);
        int i = operatorService.save(operator);
        if(i == 0){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<ResultModel> update(@RequestParam Map<String, Object> params){
        String str = JSON.toJSON(params).toString();
        Operator operator = JSON.parseObject(str,Operator.class);
        int i = operatorService.update(operator);
        if(i == 0){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<ResultModel> del(@Param("operatorCode")String operatorCode, @Param("usercode") String usercode){
        int i = operatorService.del(operatorCode,usercode);
        if(i == 0){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    @GetMapping("/send")
    @ResponseBody
    public ResponseEntity<ResultModel> send(){
        sendMessageServer.sendMessageForNoOrderCustomer();
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }
}
