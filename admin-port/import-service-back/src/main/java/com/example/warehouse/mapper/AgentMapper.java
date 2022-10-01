package com.example.warehouse.mapper;

import com.example.warehouse.entity.Agent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AgentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Agent record);

    int insertSelective(Agent record);

    Agent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);

    List<Agent> selectAll(Map<String,Object> map);
    int countSelectAll(Map<String,Object> map);

    int deleteById(Agent agent);

    /**
     * 下拉菜单专用--供应商列表
     * @return
     */
    List<Agent> getAgentList();

    int selectCount();
}
