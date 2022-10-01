package com.example.warehouse.service.account;

import com.example.warehouse.entity.AgentChannel;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.account.AgentChannelReqVo;
import com.example.warehouse.vo.account.AgentOrderReqVo;
import org.springframework.http.ResponseEntity;

public interface AgentChannelService {

    ResponseEntity<PageResultModel> getAgentChannelList(AgentChannelReqVo agentChannelReqVo);

    ResponseEntity<ResultModel> saveAgentChannel(AgentChannel agentChannel, boolean isAdd);

    ResponseEntity<ResultModel> enableAgentChannel(Integer id, Integer isEnable);

    ResponseEntity<ResultModel> deleteAgentChannel(Integer id);

    ResponseEntity<ResultModel> printAgentOrder(AgentOrderReqVo agentOrderReqVo);
}
