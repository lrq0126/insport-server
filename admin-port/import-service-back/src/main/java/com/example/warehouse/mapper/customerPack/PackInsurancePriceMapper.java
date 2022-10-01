package com.example.warehouse.mapper.customerPack;

import com.example.warehouse.entity.customerPack.PackInsurancePrice;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
* 订单保险费用明细(PackInsurancePrice)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/11/10 12:1
*/
@Repository
public interface PackInsurancePriceMapper extends MyBatisBaseDao<PackInsurancePrice, Integer> {

    @Override
    int insert(PackInsurancePrice record);

    @Override
    int insertSelective(PackInsurancePrice record);

    @Override
    PackInsurancePrice selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(PackInsurancePrice record);

    @Override
    int updateByPrimaryKey(PackInsurancePrice record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    PackInsurancePrice selectByPackId(Integer packId);

    void deleteByPackId(Integer packId);
}