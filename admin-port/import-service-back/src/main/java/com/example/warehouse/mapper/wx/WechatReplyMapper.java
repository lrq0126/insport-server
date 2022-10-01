package com.example.warehouse.mapper.wx;

import com.example.warehouse.entity.wx.WechatReply;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.wechat.WechatReplyReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 微信公众号自动回复(WechatReply)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/3/3 15:40
*/
@Repository
public interface WechatReplyMapper extends MyBatisBaseDao<WechatReply, Integer> {

    @Override
    int insert(WechatReply record);

    @Override
    int insertSelective(WechatReply record);

    @Override
    WechatReply selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(WechatReply record);

    @Override
    int updateByPrimaryKey(WechatReply record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int getReplyListCount(WechatReplyReqVo wechatReplyReqVo);

    List<WechatReply> getReplyList(WechatReplyReqVo wechatReplyReqVo);

    List<WechatReply> selectByKeyWord(@Param("keyWord") String keyWord);

    List<String> selectAllKeyWord();

    WechatReply selectByReplyRule(@Param("replyRule")String replyRule);

    List<WechatReply> checkRuleAndKeyWord(@Param("replyRule")String replyRule, @Param("keyWord") String keyWord, @Param("id") Integer id);
}