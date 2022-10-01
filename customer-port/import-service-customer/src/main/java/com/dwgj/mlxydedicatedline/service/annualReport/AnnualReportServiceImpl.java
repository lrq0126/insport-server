package com.dwgj.mlxydedicatedline.service.annualReport;

import com.dwgj.mlxydedicatedline.entity.annualReport.AnnualReport;
import com.dwgj.mlxydedicatedline.mapper.annualReport.AnnualReportMapper;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* 年度报告(AnnualReport)表服务实现类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/12/31 10:39
*/
@Service("annualReportService")
public class AnnualReportServiceImpl implements AnnualReportService {
    /**
     * 年度报告Mapper
     */
    @Resource
    private AnnualReportMapper annualReportMapper;


    @Override
    public ResponseEntity<ResultModel> getMyAnnualReport(Integer customerId) {
        int year = 2021;
        AnnualReport annualReport = annualReportMapper.selectCustomerAnnualReport(customerId, year);
        if(annualReport == null){
            return new ResponseEntity<>(ResultModel.ok("暂无数据"), HttpStatus.OK);
        }
        annualReportMapper.updateReadById(annualReport.getId());
        return new ResponseEntity<>(ResultModel.ok(annualReport), HttpStatus.OK);
    }
}