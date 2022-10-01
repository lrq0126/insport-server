package com.example.warehouse.mapper.annualReport;

import com.example.warehouse.entity.annualReport.AnnualReport;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 年度报告(AnnualReport)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/12/31 10:39
*/
@Repository
public interface AnnualReportMapper extends MyBatisBaseDao<AnnualReport, Integer> {

    @Override
    int insert(AnnualReport record);

    @Override
    int insertSelective(AnnualReport record);

    @Override
    AnnualReport selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(AnnualReport record);

    @Override
    int updateByPrimaryKey(AnnualReport record);

	@Override
    int deleteByPrimaryKey(Integer id);

	int removeByPrimaryKey(Integer id);

	void generateCustomerAnnualReport(String generateSql);

	List<AnnualReport> getAnnualReportList();
}