package com.dwgj.mlxydedicatedline.mapper.customerPack;

import com.dwgj.mlxydedicatedline.entity.customerPack.CustomerPackPriceDetail;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
* 包裹费用明细表(CustomerPackPriceDetail)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/11/19 17:8
*/
@Repository
public interface CustomerPackPriceDetailMapper extends MyBatisBaseDao<CustomerPackPriceDetail, Integer> {

    @Override
    int insert(CustomerPackPriceDetail record);

    @Override
    int insertSelective(CustomerPackPriceDetail record);

    @Override
    CustomerPackPriceDetail selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CustomerPackPriceDetail record);

    @Override
    int updateByPrimaryKey(CustomerPackPriceDetail record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    CustomerPackPriceDetail selectPackPriceDetailByPackId(Integer packId);
}