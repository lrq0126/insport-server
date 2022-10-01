package com.example.warehouse.controller.insurance;

import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.insurance.InsuranceService;
import com.example.warehouse.vo.insurance.InsuranceEntityVo;
import com.example.warehouse.vo.insurance.InsuranceReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insurance")
public class InsuranceController {

    @Autowired
    private InsuranceService insuranceService;

    @PostMapping("/getInsuranceList")
    public ResponseEntity<PageResultModel> getInsuranceList(InsuranceReqVo insuranceReqVo){
        return insuranceService.getInsuranceList(insuranceReqVo);
    }

    @GetMapping("/getInsuranceDetail")
    public ResponseEntity<ResultModel> getInsuranceDetail(@RequestParam("id") int id){
        return insuranceService.getInsuranceDetail(id);
    }

    @PostMapping("/add")
    public ResponseEntity<ResultModel> createInsurance(InsuranceEntityVo insuranceEntityVo){
        return insuranceService.save(insuranceEntityVo, false);
    }

    @PostMapping("/edit")
    public ResponseEntity<ResultModel> editInsurance(InsuranceEntityVo insuranceEntityVo){
        return insuranceService.save(insuranceEntityVo, true);
    }

    @PostMapping("/delete")
    public ResponseEntity<ResultModel> deleteInsurance(@RequestParam("id")int id){
        return insuranceService.deleteInsurance(id);
    }

    @PostMapping("/getInsuranceByOrderNumber")
    public ResponseEntity<ResultModel> getInsuranceByOrderNumber(@RequestParam("orderNumber") String orderNumber){
        return insuranceService.getInsuranceByOrderNumber(orderNumber);
    }


}
