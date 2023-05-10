package com.dwgj.mlxydedicatedline.mapper.customerPack;

import com.dwgj.mlxydedicatedline.entity.customerPack.PinSpellMail;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
* 拼邮订单表(PinSpellMail)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/10/14 17:50
*/
@Repository
public interface PinSpellMailMapper extends MyBatisBaseDao<PinSpellMail, Integer> {

    @Override
    int insert(PinSpellMail record);

    @Override
    int insertSelective(PinSpellMail record);

    @Override
    PinSpellMail selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(PinSpellMail record);

    @Override
    int updateByPrimaryKey(PinSpellMail record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

}