package com.example.warehouse.mapper.webLog;

import com.example.warehouse.entity.webLog.CustomerContactLog;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.webLog.CustomerContactLogReqVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 客户联系日志(CustomerContactLog)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/4/21 10:12
*/
@Repository
public interface CustomerContactLogMapper extends MyBatisBaseDao<CustomerContactLog, Integer> {

    @Override
    int insert(CustomerContactLog record);

    @Override
    int insertSelective(CustomerContactLog record);

    @Override
    CustomerContactLog selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CustomerContactLog record);

    @Override
    int updateByPrimaryKey(CustomerContactLog record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int getCount(CustomerContactLogReqVo customerContactLogReqVo);

    List<CustomerContactLog> getLogList(CustomerContactLogReqVo customerContactLogReqVo);

}