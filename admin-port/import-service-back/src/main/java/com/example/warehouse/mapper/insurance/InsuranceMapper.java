package com.example.warehouse.mapper.insurance;

import com.example.warehouse.entity.insurance.Insurance;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.insurance.InsuranceReqVo;
import com.example.warehouse.vo.insurance.InsuranceRespVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 保险基础信息表(Insurance)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/11/3 14:42
*/
@Repository
public interface InsuranceMapper extends MyBatisBaseDao<Insurance, Integer> {

    @Override
    int insert(Insurance record);

    @Override
    int insertSelective(Insurance record);

    @Override
    Insurance selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(Insurance record);

    @Override
    int updateByPrimaryKey(Insurance record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int selectCount(InsuranceReqVo insuranceReqVo);

    List<InsuranceRespVo> selectInsurance(InsuranceReqVo insuranceReqVo);

    InsuranceRespVo selectInsuranceById(int id);

    int selectAllNumber();

    InsuranceRespVo selectInsuranceByOrderNumber(String orderNumber);
}