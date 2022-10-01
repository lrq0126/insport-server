package com.example.warehouse.service.wechat;

import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.CustomerAccount;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.Goods;
import com.example.warehouse.entity.coupons.SysCoupons;
import com.example.warehouse.entity.ping.PingMain;
import com.example.warehouse.entity.sys.TrajectoryLog;

import java.util.List;
import java.util.Map;

public interface SendMessageServer {
    void sendMessageForPayment();

    void sendMessageForPackage(CustomerPack customerPack,int packNum);

    void sendMessageForShipped();

    /**
     * 包裹入库
     */
    void sendMessageForWareIn(Goods goods);

    /**
     * 发送审核完的提示给团长
     * @param pin 拼团详情
     * @param isPassed 是否审核通过：1否 2是 3拒绝
     */
    void sendMessageForCheckPin(PingMain pin,int isPassed);

    /**
     * 发送充值成功的消息给客户
     * @param customerAccount
     * @param message 页面传过来的消息
     */
    void sendMessageForTopUp(CustomerAccount customerAccount, String message);

    /**
     * 发送优惠券给没有下单过的客户
     */
    void sendMessageForNoOrderCustomer();

    /**
     * 发送优惠券给客户
     */
    void sengMeesageForCustomerSendCoupons(List<Customer> customerList, SysCoupons sysCoupons, int isSend);

    /**
     * 发送将要送优惠券给客户的通知，实际上还没送
     */
    void sengMeesageForCustomerNoOrderBefore();

    /**
     * 申请打包完成，并返回信息给客户
     * @param customerPack
     */
    void sendMessageForApplyPack(CustomerPack customerPack);

    /**
     * 通知已经打包好的客户付款，48小时一次，7天一次
     */
    void sendMessageForAdviceCustomerToPay();

    /**
     * 通知客户有包裹超时未出库：两个月，四个月，六个月
     */
    void sendMessageForGoodsNotOut();

    /**
     * 物流轨迹信息更新通知
     * @param singleTrackLog
     * @param trackingMessage
     */
    void sendMessageForTrackUpdate(TrajectoryLog singleTrackLog, String trackingMessage, String orderStatus);

    /**
     * 售后回复信息
     * @param orderNumber 订单号
     * @param customer    客户信息
     * @param payReturnMessage 回复信息
     */
    void afterSaleReturn(String orderNumber, Customer customer, String payReturnMessage, String remarks);
}
