package com.example.warehouse.mapper.customerPack;

import com.example.warehouse.entity.customerPack.PinSpellMail;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.customerPack.PinSpellMailInfoVo;
import com.example.warehouse.vo.customerPack.PinSpellMailReqVo;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    int getPinSpellMailCount(PinSpellMailReqVo pinSpellMailReqVo);

    List<PinSpellMail> getPinSpellMailList(PinSpellMailReqVo pinSpellMailReqVo);

    double sumOrderWeight(int pinSpellMailId);

    PinSpellMailInfoVo getPinSpellMailInfo(int id);
}