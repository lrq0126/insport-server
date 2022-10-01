package com.example.warehouse.service.customer;

import com.example.warehouse.entity.customer.CustomerIntegralRule;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.integralRule.CustomerIntegralRuleReqVo;
import org.springframework.http.ResponseEntity;

public interface CustomerIntegralRuleService {

    ResponseEntity<PageResultModel> getIntegralRuleList(CustomerIntegralRuleReqVo integralRuleReqVo);

    ResponseEntity<ResultModel> getIntegralRuleById(int id);

    ResponseEntity<ResultModel> saveIntegralRule(CustomerIntegralRule customerIntegralRule);

    ResponseEntity<ResultModel> deleteIntegralRule(int id);
}
