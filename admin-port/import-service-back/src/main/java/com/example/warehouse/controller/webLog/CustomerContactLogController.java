package com.example.warehouse.controller.webLog;

import com.example.warehouse.entity.webLog.CustomerContactLog;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.webLog.CustomerContactLogService;
import com.example.warehouse.vo.webLog.CustomerContactLogReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customerContactLog")
public class CustomerContactLogController {
    @Autowired
    private CustomerContactLogService customerContactLogService;

    @PostMapping("create")
    public ResponseEntity<ResultModel> create(CustomerContactLog customerContactLog){
        return customerContactLogService.create(customerContactLog);
    }

    @PostMapping("update")
    public ResponseEntity<ResultModel> update(CustomerContactLog customerContactLog){
        return customerContactLogService.update(customerContactLog);
    }

    @PostMapping("getContactList")
    public ResponseEntity<PageResultModel> getContactList(CustomerContactLogReqVo customerContactLogReqVo){
        return customerContactLogService.get(customerContactLogReqVo);
    }
}
