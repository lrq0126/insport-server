package com.example.warehouse.service.account.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.entity.Agent;
import com.example.warehouse.entity.AgentOrder;
import com.example.warehouse.entity.User;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.AgentMapper;
import com.example.warehouse.mapper.AgentOrderMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.account.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private AgentOrderMapper agentOrderMapper;

    @Override
    public ResponseEntity<ResultModel>saveAgent(Map<String, Object> map) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        Agent agent = new Agent();
        String agentName = map.get("agentName").toString();
        int thisCount = agentMapper.countSelectAll(map);
        if(thisCount > 0){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.THIS_AGENT_IS_HAVE), HttpStatus.OK);
        }
        int count = agentMapper.selectCount();
        agent.setAgentName(agentName);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);//设置是否使用分组
        nf.setMaximumIntegerDigits(6);//设置最大整数位数
        nf.setMinimumIntegerDigits(6);//设置最小整数位数
        agent.setAgentCode("AGENT-"+nf.format(count+1));
        agent.setCreateId(user.getId());
        agent.setCreateTime(DateUtil.timestamp2String(new Date()));
        agent.setStatus(1);
        agent.setAmount(0.0);
        int i = agentMapper.insert(agent);
        if(i <= 0 ){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public List<Agent> selectAll(Map<String, Object> map) {
        return agentMapper.selectAll(map);
    }

    @Override
    public int countSelectALl(Map<String, Object> map) {
        return agentMapper.countSelectAll(map);
    }

    @Override
    public int deleteAgent(String id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Agent agent = new Agent();
        agent.setId(id);
        agent.setLastUpdateId(user.getId());
        agent.setLastUpdateTime(DateUtil.timestamp2String(new Date()));
        return agentMapper.deleteById(agent);
    }

    @Override
    public int batchImport(List<AgentOrder> list,User user) {
        // 所有已结算的订单号，暂时不打算修改已结算的订单，因为系统消耗资源过大
        //Map<Object,Object> orderNumberList = agentOrderMapper.getOrderNumberList();
        // 所有未结算的订单号
//        List<String> noSettleOrderNumberList = agentOrderMapper.getOrderNumberHaveNoSettle();
        int i = 0;
        List<AgentOrder> orderList = new ArrayList<>(list.size());
        try {
            for ( AgentOrder order : list ){
                order.setCreateId(user.getId().toString());
                order.setCreateTime(DateUtil.timestamp2String(new Date()));
                orderList.add(order);
            }
            i = agentOrderMapper.batchInsert(orderList);
        }catch (Exception e){
            e.printStackTrace();
            log.error("批量成本结算错误：{}",e.getMessage());
            return -1;
        }
        return i;
    }

    @Override
    public List<AgentOrder> getAgentOrderList(Map<String, Object> map) {
        return agentOrderMapper.getAgentOrderListByAgentId(map);
    }

    @Override
    public int countAgentOrderList(Map<String, Object> map) {
        return agentOrderMapper.countAgentOrderListByAgentId(map);
    }

    @Override
    public List<Agent> getAgentList(){
        return agentMapper.getAgentList();
    }

}
