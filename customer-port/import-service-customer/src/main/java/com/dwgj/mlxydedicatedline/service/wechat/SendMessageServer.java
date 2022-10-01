package com.dwgj.mlxydedicatedline.service.wechat;

import com.dwgj.mlxydedicatedline.entity.CustomerPack;
import com.dwgj.mlxydedicatedline.entity.ping.PingMain;

import java.util.List;

public interface SendMessageServer {
    void sendMessageForPayment();

    void sendMessageForShipped();

    /**
     * 告诉团长有成员加入或者退出团
     * @param pin
     * @param userId
     * @param packageNum
     * @param type
     */
    void sendMessageForPinLeader(PingMain pin,String userId,int packageNum,String type);

    /**
     * 告诉团长已过期
     * @param list
     */
    void sendMessageForCutOffTime(List<PingMain> list);

    /**
     * 告诉成员拼团加快速度
     * @param pin
     * @param memberId
     */
    void sendMessageForPinMember(PingMain pin,String memberId);

    /**
     * 团长确认成团
     * @param pin
     */
    void sendMessageForConfirmPin(PingMain pin);

    /**
     * 申请打包发送消息给客户如何支付订单
     */
    void sendMessageForApplyPack(CustomerPack customerPack);
}
