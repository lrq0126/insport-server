package com.example.warehouse.mapper.wx;

import com.example.warehouse.entity.wx.WechatMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WechatMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatMessage record);

    int insertSelective(WechatMessage record);

    WechatMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatMessage record);

    int updateByPrimaryKey(WechatMessage record);

    void insertList(@Param("wechatMessageList") List<WechatMessage> wechatMessageList);

    int selectTrackingUpdateCount(Map<String, Object> requestMap);

    List<WechatMessage> selectTrackingUpdate(Map<String, Object> requestMap);
}