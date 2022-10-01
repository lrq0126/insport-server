package com.example.warehouse.service.insurance;

import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.insurance.InsuranceEntityVo;
import com.example.warehouse.vo.insurance.InsuranceReqVo;
import org.springframework.http.ResponseEntity;

public interface InsuranceService {
    ResponseEntity<PageResultModel> getInsuranceList(InsuranceReqVo insuranceReqVo);

    ResponseEntity<ResultModel> getInsuranceDetail(int id);

    ResponseEntity<ResultModel> save(InsuranceEntityVo insuranceEntityVo, boolean isEdit);

    ResponseEntity<ResultModel> deleteInsurance(int id);

    ResponseEntity<ResultModel> getInsuranceByOrderNumber(String orderNumber);
}
