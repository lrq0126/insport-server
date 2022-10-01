package com.example.warehouse.mapper.user;

import com.example.warehouse.entity.wechat.WxAccessToken;
import org.springframework.stereotype.Repository;

@Repository
public interface WxAccessTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxAccessToken record);

    int insertSelective(WxAccessToken record);

    WxAccessToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WxAccessToken record);

    int updateByPrimaryKey(WxAccessToken record);

    /**
     * 获取最新的access_token对象
     * @return
     */
    WxAccessToken getNewAccessToken();

    /**
     * 获取最新的access_token
     * @return
     */
    String getAccessToken();

    /**
     * 获取最新的jsapi_ticket
     * @return
     */
    String getJsapiTicket();
}
