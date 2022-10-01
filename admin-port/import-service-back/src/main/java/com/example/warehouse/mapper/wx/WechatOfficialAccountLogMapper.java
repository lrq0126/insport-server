package com.example.warehouse.mapper.wx;

import com.example.warehouse.entity.wx.WechatOfficialAccountLog;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.wechat.WechatLogReqVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 微信公众号日志(WechatOfficialAccountLog)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/4/2 16:36
*/
@Repository
public interface WechatOfficialAccountLogMapper extends MyBatisBaseDao<WechatOfficialAccountLog, Integer> {

    @Override
    int insert(WechatOfficialAccountLog record);

    @Override
    int insertSelective(WechatOfficialAccountLog record);

    @Override
    WechatOfficialAccountLog selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(WechatOfficialAccountLog record);

    @Override
    int updateByPrimaryKey(WechatOfficialAccountLog record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int getWeChatLogCount(WechatLogReqVo wechatLogReqVo);

    List<WechatOfficialAccountLog> getWeChatLog(WechatLogReqVo wechatLogReqVo);
}