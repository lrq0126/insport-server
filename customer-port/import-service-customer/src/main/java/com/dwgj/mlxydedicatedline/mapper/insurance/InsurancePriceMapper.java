package com.dwgj.mlxydedicatedline.mapper.insurance;

import com.dwgj.mlxydedicatedline.entity.insurance.InsurancePrice;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 保险表价格表(InsurancePrice)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/11/3 14:42
*/
@Repository
public interface InsurancePriceMapper extends MyBatisBaseDao<InsurancePrice, Integer> {

    @Override
    int insert(InsurancePrice record);

    @Override
    int insertSelective(InsurancePrice record);

    @Override
    InsurancePrice selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(InsurancePrice record);

    @Override
    int updateByPrimaryKey(InsurancePrice record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    void deleteByInsuranceId(@Param("insuranceId") Integer insuranceId);

    void insertList(@Param("insurancePriceList") List<InsurancePrice> insurancePriceList);

    List<InsurancePrice> selectByInsuranceId(@Param("insuranceId") int insuranceId);
}