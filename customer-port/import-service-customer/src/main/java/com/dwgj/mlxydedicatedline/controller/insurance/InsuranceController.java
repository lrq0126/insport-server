package com.dwgj.mlxydedicatedline.controller.insurance;


import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.insurance.InsuranceService;
import com.dwgj.mlxydedicatedline.vo.insurance.InsuranceEntityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insurance")
public class InsuranceController {

    @Autowired
    private InsuranceService insuranceService;


    @GetMapping("/getInsuranceDetail")
    public ResponseEntity<ResultModel> getInsuranceDetailByRouteId(@RequestParam("id") int id){
        return insuranceService.getInsuranceDetailById(id);
    }


}
