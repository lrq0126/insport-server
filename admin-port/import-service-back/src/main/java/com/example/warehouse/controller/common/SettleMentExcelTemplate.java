package com.example.warehouse.controller.common;


import com.example.warehouse.entity.AgentOrder;
import com.example.warehouse.mapper.AgentOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 结算成本的导入excel模板
 */
@Component
public class SettleMentExcelTemplate extends AbstractExcelTemplate {

    private static final String[] TITLES = {"系统订单号","转单号","代理商编码","代理商名称","成本价"};

    public SettleMentExcelTemplate() {
        super(TITLES);
    }

    static SettleMentExcelTemplate settleMentExcelTemplate;

    @Autowired
    private AgentOrderMapper agentOrderMapper;

    @PostConstruct
    public void init(){
        settleMentExcelTemplate = this;
        settleMentExcelTemplate.agentOrderMapper = this.agentOrderMapper;
    }



    @Override
    ExcelCheckMessage doCheck(ExcelCheckMessage excelCheckMessage) {
        List<AgentOrder> agentOrders = new ArrayList<>();
        AgentOrder agentOrder;
        List<String> messages = excelCheckMessage.getMessage();
        String[][] excel = excelCheckMessage.getExcel();

        int rowNum;

        // 校验系统订单重复
        List<String> orderNumbers = new ArrayList<>();
        // 校验转单号重复
        List<String> agentNumbers = new ArrayList<>();

        List<String> noSettleOrderNumberList = settleMentExcelTemplate.agentOrderMapper.getOrderNumberHaveNoSettle();

        for (int i = 1; i < excel.length; i++) {
            rowNum = i + 1;
            agentOrder = new AgentOrder();

            // 系统订单号
            if( StringUtils.hasText(excel[i][0])){
                agentOrder.setOrderNumber(excel[i][0].trim());
                if ( !noSettleOrderNumberList.contains(excel[i][0].trim()) ){
                    messages.add("第"+rowNum+"行【系统订单号】在未结算订单中不存在，请检查单号是否正确");
                    return excelCheckMessage;
                }
                orderNumbers.add(excel[i][0].trim());
            }else {
                messages.add(emptyMessage(rowNum, TITLES[0]));
            }

            // 转单号
            if( StringUtils.hasText(excel[i][1])){
                agentOrder.setAgentNumber(excel[i][1].trim());
                agentNumbers.add(excel[i][1].trim());
            }else {
                messages.add(emptyMessage(rowNum, TITLES[1]));
            }

            // 代理商编码
            if( StringUtils.hasText(excel[i][2])){
                agentOrder.setAgentId(excel[i][2].trim());
            }else {
                messages.add(emptyMessage(rowNum, TITLES[2]));
            }

            // 代理商名称
            if( StringUtils.hasText(excel[i][3])){
                agentOrder.setAgentName(excel[i][3].trim());
            }

            // 成本价
            if( StringUtils.hasText(excel[i][4])){
                agentOrder.setMoney(new BigDecimal((excel[i][4].trim())));
            }else {
                messages.add(emptyMessage(rowNum, TITLES[4]));
            }

            agentOrders.add(agentOrder);

        }

        // 基本信息校验完了，如果没有错误继续校验重复信息
        if (messages.isEmpty()) {
            // 系统订单重复
            messages.addAll(duplicateValue(orderNumbers, TITLES[0]));
            // 转单号重复
            messages.addAll(duplicateValue(agentNumbers, TITLES[1]));
        }

        excelCheckMessage.setObjects(agentOrders);
        return excelCheckMessage;
    }

}

