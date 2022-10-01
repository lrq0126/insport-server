package com.example.warehouse.service.account;

import com.example.warehouse.entity.Agent;
import com.example.warehouse.entity.AgentOrder;
import com.example.warehouse.entity.AgentOrderAccount;
import com.example.warehouse.entity.User;
import com.example.warehouse.model.ResultModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AgentService {
    /**
     * 保存代理商
     * @param map
     * @return
     */
    ResponseEntity<ResultModel> saveAgent(Map<String,Object> map);

    /**
     * 列表查询
     * @param map
     * @return
     */
    public List<Agent> selectAll(Map<String,Object> map);
    public int countSelectALl(Map<String,Object> map);

    public int deleteAgent(String id);

    /**
     * 批量成本结算
     * @param list
     * @return
     */
    int batchImport(List<AgentOrder> list,User user);

    /**
     * 查看某个供应商下的所有结算订单
     * @param map
     * @return
     */
    List<AgentOrder> getAgentOrderList(Map<String,Object> map);
    int countAgentOrderList(Map<String,Object> map);

    /**
     * 下拉菜单专用
     * @return
     */
    List<Agent> getAgentList();
}
