package com.dwgj.mlxydedicatedline.service.customer;

import com.dwgj.mlxydedicatedline.entity.customer.CustomerIntegralRule;
import com.dwgj.mlxydedicatedline.mapper.customer.CustomerIntegralRuleMapper;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerIntegralRuleServiceImpl implements CustomerIntegralRuleService{

    @Autowired
    private CustomerIntegralRuleMapper customerIntegralRuleMapper;

    @Override
    public ResponseEntity<ResultModel> getIntegralRuleList(String integralType) {
        List<CustomerIntegralRule> customerIntegralRuleList = customerIntegralRuleMapper.getIntegralRuleList(integralType);
        return new ResponseEntity<>(ResultModel.ok(customerIntegralRuleList), HttpStatus.OK);
    }

}
