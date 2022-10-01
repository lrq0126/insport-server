package com.example.warehouse.service.account.impl;

import com.example.warehouse.common.BuildOrderApi;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.Agent;
import com.example.warehouse.entity.AgentChannel;
import com.example.warehouse.entity.User;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.AgentChannelMapper;
import com.example.warehouse.mapper.AgentMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.account.AgentChannelService;
import com.example.warehouse.vo.account.AgentChannelReqVo;
import com.example.warehouse.vo.account.AgentOrderReqVo;
import com.example.warehouse.vo.customer.CustomerPackVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;

@Service
public class AgentChannelServiceImpl implements AgentChannelService {
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentChannelMapper agentChannelMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;

    @Override
    public ResponseEntity<PageResultModel> getAgentChannelList(AgentChannelReqVo channelReqVo) {

        PageData pageData = PageHelp.editPage(String.valueOf(channelReqVo.getPageNumber()),
                String.valueOf(channelReqVo.getPageSize()));

        int count = agentChannelMapper.selectCount(channelReqVo);
        pageData.setTotal(count);
        if(count > 0){
            channelReqVo.setPageNumber(pageData.getPageNumber());
            List<AgentChannel> agentChannelList = agentChannelMapper.getAgentChannelListByAgentCode(channelReqVo);
            return new ResponseEntity<>(PageResultModel.ok(agentChannelList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveAgentChannel(AgentChannel agentChannel, boolean isAdd) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }

        if(StringUtils.isEmpty(agentChannel.getInsideCode())){
            int count = agentChannelMapper.selectDataCount();
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);//设置是否使用分组
            nf.setMaximumIntegerDigits(6);//设置最大整数位数
            nf.setMinimumIntegerDigits(6);//设置最小整数位数
            String channelCode = "AGEC-" + DateUtil.getDateFormate(new Date(), "yyyyMM")+nf.format(count+1);
            agentChannel.setInsideCode(channelCode);
        }

        if(isAdd){
            agentChannel.setCreateId(user.getId());
            agentChannel.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            agentChannelMapper.insertSelective(agentChannel);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            agentChannel.setUpdateId(user.getId());
            agentChannel.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            agentChannelMapper.updateByPrimaryKeySelective(agentChannel);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<ResultModel> enableAgentChannel(Integer id, Integer isEnable) {
        if(isEnable == 1){
            isEnable = 2;
        }else {
            isEnable = 1;
        }
        int i = agentChannelMapper.enableAgentChannel(id, isEnable);
        if(i > 0){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> deleteAgentChannel(Integer id) {
        int i = agentChannelMapper.deleteByPrimaryKey(id);
        if(i > 0){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> printAgentOrder(AgentOrderReqVo agentOrderReqVo) {
        CustomerPackVo customerPackVo = customerPackMapper.findByOrderNumber(agentOrderReqVo.getOrderNumber());
        AgentChannel agentChannel = agentChannelMapper.selectByPrimaryKey(agentOrderReqVo.getChannelId());
        Agent agent = agentMapper.selectByPrimaryKey(agentOrderReqVo.getAgentId());
        BuildOrderApi.ResponseBody responseBody = null;
        /**
         * 所有供应商创建订单API都需要定制化开发
         */
        if(agent.getAgentName().contains("德威")){
            responseBody = BuildOrderApi.buildDWOrder(customerPackVo, agentChannel, agentOrderReqVo);
        }else if(agent.getAgentName().contains("国际邮政")){
            responseBody = BuildOrderApi.buildEMSOrder(customerPackVo, agentOrderReqVo);
        }else if(agent.getAgentName().contains("亚美")){
            responseBody = BuildOrderApi.buildYameiApi(customerPackVo,agentChannel, agentOrderReqVo);
        }else if(agent.getAgentName().contains("成达")){
            responseBody = BuildOrderApi.buildChengDaOrder(customerPackVo, agentOrderReqVo);
        }else{

        }
        if(responseBody == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        if(responseBody.getStatusCode() == 500){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR, responseBody), HttpStatus.OK);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", agentOrderReqVo.getOrderNumber());
        map.put("agentNumber", responseBody.getAgentNumber());
        //map.put("insideMessage", responseBody.getMessage());
        customerPackMapper.updateAgentNumber(map);

        return new ResponseEntity<>(ResultModel.ok(responseBody), HttpStatus.OK);
    }
}
