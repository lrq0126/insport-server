package com.example.warehouse.vo.order;

import lombok.Data;

@Data
public class Track17RespVo {

    private String orderNumber;
    private String packageStatus;

    private String shippingCountry;
    private String recipientCountry;
    private String registerTime;
    private String pushTime;
    private String trackingStatus;

    private String systemCode;


}
