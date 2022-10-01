package com.example.warehouse.controller.common;

import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.service.CustomerPackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;


@Component
public class AgentNumberExcelTemplate extends AbstractExcelTemplate {
    private static final String[] TITLES = {"订单号","转单号","客服备注"};

    public AgentNumberExcelTemplate() {
        super(TITLES);
    }

    static AgentNumberExcelTemplate agentNumberExcelTemplate;

    @Autowired
    private CustomerPackService customerPackService;


    @PostConstruct
    public void init(){
        agentNumberExcelTemplate = this;
        agentNumberExcelTemplate.customerPackService = this.customerPackService;
    }


    @Override
    ExcelCheckMessage doCheck(ExcelCheckMessage excelCheckMessage) {
        List<CustomerPack> customerPacks = new ArrayList<>();

        CustomerPack customerPack;
        List<String> messages = excelCheckMessage.getMessage();
        String[][] excel = excelCheckMessage.getExcel();

        // 校验订单号重复
        List<String> deliverOrderNos = new ArrayList<>();

        int rowNum;


        if ( excel.length > 402 ){
            messages.add("一次导入不能超过400条");
            return excelCheckMessage;
        }
        for (int i = 1; i < excel.length; i++) {
            rowNum = i + 1;
            customerPack = new CustomerPack();

            // 订单号
            if( StringUtils.hasText(excel[i][0])){
                customerPack.setOrderNumber(excel[i][0].trim());
                deliverOrderNos.add(excel[i][0].trim());
//                int a = agentNumberExcelTemplate.customerPackService.checkOrderNumberIsOutWare(customerPack.getOrderNumber());
//                if ( a == 0 ){
//                    messages.add("第"+rowNum+"行【订单号】不存在或未发货，请检查单号是否正确");
//                    return excelCheckMessage;
//                }
            }else {
                messages.add(emptyMessage(rowNum, TITLES[0]));
            }

            // 转单号
            if( StringUtils.hasText(excel[i][1])){
                customerPack.setInternationalTransshipmentNo(excel[i][1].trim());
            }else {
                messages.add(emptyMessage(rowNum, TITLES[1]));
            }

            // 客服备注
            if( StringUtils.hasText(excel[i][2])){
                customerPack.setInsideMessage(excel[i][2].trim());
            }

            customerPacks.add(customerPack);
        }

        // 基本信息校验完了，如果没有错误继续校验重复信息
        if (messages.isEmpty()) {
            // 快递单号重复
            messages.addAll(duplicateValue(deliverOrderNos, TITLES[1]));
        }

//        if ( customerPacks.size() > 0 ) {
//            // 批量更新轨迹
//            agentNumberExcelTemplate.customerPackService.batchUpdateAgentNumberByOrderNumber(customerPacks);
//        }

        excelCheckMessage.setObjects(customerPacks);
        return excelCheckMessage;
    }

}
