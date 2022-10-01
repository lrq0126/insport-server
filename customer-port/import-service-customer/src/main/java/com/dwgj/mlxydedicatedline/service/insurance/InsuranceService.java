package com.dwgj.mlxydedicatedline.service.insurance;


import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.vo.insurance.InsuranceEntityVo;
import org.springframework.http.ResponseEntity;

public interface InsuranceService {

    ResponseEntity<ResultModel> getInsuranceDetailById(int id);
}
