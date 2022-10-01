package com.example.warehouse.mapper.wx;

import com.example.warehouse.entity.wx.WechatReplyLog;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
* 微信公众号自动回复日志记录(WechatReplyLog)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/3/3 15:40
*/
@Repository
public interface WechatReplyLogMapper extends MyBatisBaseDao<WechatReplyLog, Integer> {

    @Override
    int insert(WechatReplyLog record);

    @Override
    int insertSelective(WechatReplyLog record);

    @Override
    WechatReplyLog selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(WechatReplyLog record);

    @Override
    int updateByPrimaryKey(WechatReplyLog record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

}