package com.example.warehouse.service.sys;

public interface AnalysisService {
    /**
     * 保存快递面单图片和预报包裹信息
     * @param deliveryOrderNo
     * @param loginName
     * @param imgUrl
     */
    void saveDeliveryOrderNoAndLoginName(String deliveryOrderNo,String loginName,String imgUrl);
}
