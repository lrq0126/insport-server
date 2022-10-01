package com.example.warehouse.controller.account;


import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.Agent;
import com.example.warehouse.entity.AgentChannel;
import com.example.warehouse.entity.AgentOrder;
import com.example.warehouse.entity.AgentOrderAccount;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.account.AgentChannelService;
import com.example.warehouse.service.account.AgentService;
import com.example.warehouse.service.account.CustomerAccountService;
import com.example.warehouse.vo.account.AgentChannelReqVo;
import com.example.warehouse.vo.account.AgentOrderReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.*;

@Controller
@Slf4j
@RequestMapping("/agent")
/**
 * 供应代理商控制器
 */
public class AgentController {
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentChannelService agentChannelService;

    @Autowired
    private CustomerAccountService customerAccountService;

    /**
     * 列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity list(@RequestParam Map<String,Object> param, HttpSession session){
        PageData data = (PageData) PageHelp.initPage(param).get("page");
        Map<String,Object> result = new HashMap<>();
        List<Agent> list = new ArrayList<>();
        if( param.containsKey("agentName") && StringUtils.isBlank(param.get("agentName").toString()) ){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        try{
            int total = agentService.countSelectALl(param);
            if( total > 0 ){
                list = agentService.selectAll(param);
            }
            data.setTotal(total);
            result.put("page",data);
        }catch (Exception e) {
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(list,result), HttpStatus.OK);
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity create(@RequestParam Map<String,Object> param){
        if( param.containsKey("agentName") && StringUtils.isBlank(param.get("agentName").toString()) ){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        return agentService.saveAgent(param);
    }

    /**
     *
     * @param param id : 供应代理商id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity delete(@RequestParam Map<String,Object> param){
        if( param.containsKey("id") && StringUtils.isBlank(param.get("id").toString()) ){
            return new ResponseEntity<>(ResultModel.error(NULL_ERROR), HttpStatus.OK);
        }
        String id = param.get("id").toString();
        try {
            agentService.deleteAgent(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除供应代理商失败:{}",e.getMessage());
            return new ResponseEntity<>(PageResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok("删除成功"), HttpStatus.OK);
    }

    /**
     * 获取某个供应商下的所有已经结算的订单
     * @param param
     * @return
     */
    @RequestMapping(value = "/getAgentOrderList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getAgentOrderList(@RequestParam Map<String,Object> param){
        PageData data = (PageData) PageHelp.initPage(param).get("page");
        Map<String,Object> result = new HashMap<>();
        List<AgentOrder> list = new ArrayList<>();
        try{
            int total = agentService.countAgentOrderList(param);
            if( total > 0 ){
                list = agentService.getAgentOrderList(param);
            }
            data.setTotal(total);
            result.put("page",data);
        }catch (Exception e) {
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(list,result), HttpStatus.OK);
    }

    /**
     * 下拉菜单专用--代理商列表
     * @return
     */
    @RequestMapping(value = "/getAgentList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getAgentList(){
        List<Agent> list = agentService.getAgentList();
        return new ResponseEntity<>(PageResultModel.ok(list), HttpStatus.OK);
    }

    /**
     * 进入成本结算页面，获取订单详情
     * @param orderNumber 系统订单号
     * @return
     */
    @RequestMapping(value = "/getSingleAgentOrder",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getSingleAgentOrder(String orderNumber){
        Map<String,Object> resultMap = new HashMap<>(2);
        AgentOrderAccount agentOrderAccount = customerAccountService.getSingleSettleMentOrder(orderNumber);
        List<Agent> agentList = agentService.getAgentList();
        resultMap.put("content",agentOrderAccount);
        resultMap.put("agentList",agentList);
        return new ResponseEntity<>(PageResultModel.ok(resultMap), HttpStatus.OK);
    }

    /**
     * 保存或者更新成本结算订单，注意有些是新增，有些是编辑修改
     * @param agentOrderAccount
     * @return
     */
    @RequestMapping(value = "/saveSingleAgentOrder",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity saveSingleAgentOrder(AgentOrderAccount agentOrderAccount){
        try {
            customerAccountService.saveSingleSettleMentOrder(agentOrderAccount);
        }catch (Exception e){
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(), HttpStatus.OK);
    }


    /**
     * 查询供应商渠道
     * @param agentChannelReqVo
     * @return
     */
    @RequestMapping(value = "/getAgentChannel",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PageResultModel> getAgentChannel(AgentChannelReqVo agentChannelReqVo){
        return agentChannelService.getAgentChannelList(agentChannelReqVo);
    }

    /**
     * 新增供应商渠道
     * @param agentChannel
     * @return
     */
    @RequestMapping(value = "/createAgentChannel",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> createAgentChannel(AgentChannel agentChannel){
        return agentChannelService.saveAgentChannel(agentChannel, true);
    }

    /**
     * 编辑供应商渠道
     * @param agentChannel
     * @return
     */
    @RequestMapping(value = "/editAgentChannel",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> editAgentChannel(AgentChannel agentChannel){
        return agentChannelService.saveAgentChannel(agentChannel, false);
    }

    /**
     * 更改供应商渠道使用状态
     * @param id
     * @param isEnable
     * @return
     */
    @RequestMapping(value = "/enableAgentChannel",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> enableAgentChannel(@RequestParam("id")Integer id, @RequestParam("isEnable")Integer isEnable){
        return agentChannelService.enableAgentChannel(id, isEnable);
    }

    /**
     * 编辑供应商渠道
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteAgentChannel",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> deleteAgentChannel(Integer id){
        return agentChannelService.deleteAgentChannel(id);
    }

    @PostMapping(value = "printAgentOrder")
    @ResponseBody
    public ResponseEntity<ResultModel> printAgentOrder(AgentOrderReqVo agentOrderReqVo){
        return agentChannelService.printAgentOrder(agentOrderReqVo);
    }

}

