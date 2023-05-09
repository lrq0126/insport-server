package com.example.warehouse.controller.customer;

import com.example.warehouse.entity.customer.CustomerIntegralRule;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.customer.CustomerIntegralRuleService;
import com.example.warehouse.vo.integralRule.CustomerIntegralRuleReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customerIntegralRule")
public class CustomerIntegralRuleController {

    @Autowired
    private CustomerIntegralRuleService customerIntegralRuleService;

    @PostMapping(value = "getIntegralRuleList")
    public ResponseEntity<PageResultModel> getIntegralRuleList(CustomerIntegralRuleReqVo integralRuleReqVo){
        return customerIntegralRuleService.getIntegralRuleList(integralRuleReqVo);
    }


    @PostMapping(value = "getIntegralRule")
    public ResponseEntity<ResultModel> getIntegralRule(int id){
        return customerIntegralRuleService.getIntegralRuleById(id);
    }

    @PostMapping(value = "saveIntegralRule")
    public ResponseEntity<ResultModel> saveIntegralRule(CustomerIntegralRule customerIntegralRule){
        return customerIntegralRuleService.saveIntegralRule(customerIntegralRule);
    }

    @PostMapping(value = "deleteIntegralRule")
    public ResponseEntity<ResultModel> deleteIntegralRule(int id){
        return customerIntegralRuleService.deleteIntegralRule(id);
    }
}
