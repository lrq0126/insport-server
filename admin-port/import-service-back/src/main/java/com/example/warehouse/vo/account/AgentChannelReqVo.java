package com.example.warehouse.vo.account;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class AgentChannelReqVo extends PageVo {

    private String agentName;

    private String agentCode;

    private String channelName;

}
