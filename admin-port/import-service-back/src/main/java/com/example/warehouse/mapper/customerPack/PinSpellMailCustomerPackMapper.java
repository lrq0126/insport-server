package com.example.warehouse.mapper.customerPack;

import com.example.warehouse.entity.customerPack.PinSpellMailCustomerPack;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 拼邮&订单关联表(PinSpellMailCustomerPack)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/10/14 17:50
*/
@Repository
public interface PinSpellMailCustomerPackMapper extends MyBatisBaseDao<PinSpellMailCustomerPack, Integer> {

    @Override
    int insert(PinSpellMailCustomerPack record);

    @Override
    int insertSelective(PinSpellMailCustomerPack record);

    @Override
    PinSpellMailCustomerPack selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(PinSpellMailCustomerPack record);

    @Override
    int updateByPrimaryKey(PinSpellMailCustomerPack record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    void insertList(@Param("list") List<PinSpellMailCustomerPack> pinSpellMailCustomerPackList);
}