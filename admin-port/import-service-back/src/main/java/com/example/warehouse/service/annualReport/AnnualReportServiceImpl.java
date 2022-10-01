package com.example.warehouse.service.annualReport;

import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.annualReport.AnnualReportMapper;
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

    @Resource
    private CustomerMapper customerMapper;

}