package com.example.warehouse.mapper;

import com.example.warehouse.entity.AgentOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AgentOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AgentOrder record);

    int insertSelective(AgentOrder record);

    AgentOrder selectByPrimaryKey(Integer id);

    AgentOrder selectByOrderNumber(String orderNumber);

    int updateByPrimaryKeySelective(AgentOrder record);

    int updateByPrimaryKey(AgentOrder record);

    int batchInsert(List<AgentOrder> list);

    int batchUpdate(List<AgentOrder> list);

    Map<Object,Object> getOrderNumberList();

    List<String> getOrderNumberHaveNoSettle();

    List<AgentOrder> getAgentOrderListByAgentId(Map<String,Object> map);
    int countAgentOrderListByAgentId(Map<String,Object> map);

    int updateAgentNumber(Map<String,Object> map);

    int updateByOrderNumber(AgentOrder order);
}
