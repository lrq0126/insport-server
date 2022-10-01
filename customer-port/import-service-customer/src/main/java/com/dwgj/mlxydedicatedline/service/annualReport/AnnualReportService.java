package com.dwgj.mlxydedicatedline.service.annualReport;


import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.http.ResponseEntity;

/**
* 年度报告(AnnualReport)表服务类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/12/31 10:39
*/
public interface AnnualReportService{

    ResponseEntity<ResultModel> getMyAnnualReport(Integer customerId);
}