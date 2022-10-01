package com.dwgj.mlxydedicatedline.service.customer;

import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.http.ResponseEntity;

public interface CustomerIntegralRuleService {

    ResponseEntity<ResultModel> getIntegralRuleList(String integralType);

}
