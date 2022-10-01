package com.dwgj.mlxydedicatedline.controller;

import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.annualReport.AnnualReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("annualReport")
public class AnnualReportController {

    @Autowired
    private AnnualReportService annualReportService;

    @PostMapping("getMyAnnualReport")
    public ResponseEntity<ResultModel> getMyAnnualReport(Integer customerId){
        return annualReportService.getMyAnnualReport(customerId);
    }

}
