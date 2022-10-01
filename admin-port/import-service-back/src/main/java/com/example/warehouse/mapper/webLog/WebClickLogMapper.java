package com.example.warehouse.mapper.webLog;

import com.example.warehouse.entity.webLog.WebClickLog;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.webLog.WebClickReqVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 官网点击日志(WebClickLog)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/4/21 10:16
*/
@Repository
public interface WebClickLogMapper extends MyBatisBaseDao<WebClickLog, Integer> {

    @Override
    int insert(WebClickLog record);

    @Override
    int insertSelective(WebClickLog record);

    @Override
    WebClickLog selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(WebClickLog record);

    @Override
    int updateByPrimaryKey(WebClickLog record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int getCount(WebClickReqVo webClickReqVo);

    List<WebClickLog> getList(WebClickReqVo webClickReqVo);

}