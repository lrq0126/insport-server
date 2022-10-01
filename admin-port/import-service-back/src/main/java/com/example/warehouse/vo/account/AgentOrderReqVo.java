package com.example.warehouse.vo.account;

import lombok.Data;

@Data
public class AgentOrderReqVo {

    private Integer agentId;

    private String agentCode;

    private Integer channelId;

    private String channelCode;

    private String orderNumber;

    private String code;

    private String province;

    private String city;

    private String sonPackProductDetail;

}
