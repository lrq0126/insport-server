package com.example.warehouse.controller.common;


import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.AgentOrder;
import com.example.warehouse.entity.Goods;
import com.example.warehouse.entity.User;
import com.example.warehouse.mapper.AgentOrderMapper;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

/**
 * 预报信息的导入excel模板
 */
@Component
public class PreOrderExcelTemplate extends AbstractExcelTemplate {

    private static final String[] TITLES = {"会员ID","快递单号","货物名称","备注"};

    public PreOrderExcelTemplate() {
        super(TITLES);
    }

    static PreOrderExcelTemplate preOrderExcelTemplate;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @PostConstruct
    public void init(){
        preOrderExcelTemplate = this;
        preOrderExcelTemplate.customerMapper = this.customerMapper;
        preOrderExcelTemplate.goodsMapper = this.goodsMapper;
    }


    @Override
    ExcelCheckMessage doCheck(ExcelCheckMessage excelCheckMessage) {
        List<Goods> goodsList = new ArrayList<>();
        Goods goods;
        List<String> messages = excelCheckMessage.getMessage();
        String[][] excel = excelCheckMessage.getExcel();

        int rowNum;


        // 校验快递单号重复
        List<String> deliverOrderNos = new ArrayList<>();

        List<String> userLoginNameList = preOrderExcelTemplate.customerMapper.getUserLoginNameList();

        if ( excel.length > 402 ){
            messages.add("一次导入不能超过400条");
            return excelCheckMessage;
        }
        for (int i = 1; i < excel.length; i++) {
            rowNum = i + 1;
            goods = new Goods();

            // 系统订单号
            if( StringUtils.hasText(excel[i][0])){
                if ( !userLoginNameList.contains(excel[i][0].trim()) ){
                    messages.add("第"+rowNum+"行【会员ID】不存在，请检查会员ID是否正确");
                    return excelCheckMessage;
                }
                goods.setCustomerNo(preOrderExcelTemplate.customerMapper.getCustomerNoByLoginName(excel[i][0].trim()) + "");
            }else {
                messages.add(emptyMessage(rowNum, TITLES[0]));
            }

            // 快递单号
            if( StringUtils.hasText(excel[i][1])){
                Map<String,Object> map = new HashMap<>(1);
                map.put("deliveryOrderNo",excel[i][1]);
                int isInWare = preOrderExcelTemplate.goodsMapper.findGoodsByNo(map);
                if ( isInWare > 0 ){
                    messages.add("第"+rowNum+"行【快递单号】已录入系统，请检查快递单号是否正确");
                    return excelCheckMessage;
                }
                goods.setDeliveryOrderNo(excel[i][1].trim());
                deliverOrderNos.add(excel[i][1].trim());
            }else {
                messages.add(emptyMessage(rowNum, TITLES[1]));
            }

            // 货物名称
            if( StringUtils.hasText(excel[i][2])){
                goods.setGoodsName(excel[i][2].trim());
            }else {
                // 货物名称为空时，默认
                goods.setGoodsName("未知名称");
            }

            // 备注
            if( StringUtils.hasText(excel[i][3])){
                goods.setMessage(excel[i][3].trim());
            }

            String code = SequenceCode.gainSerialNo("GOODS");
            goods.setGoodsNo(code);
            goods.setGoodsType(3);
            goods.setCreateTime(DateUtil.timestamp2String(new Date()));
            goods.setStatus(1);
            goods.setPackageNum(1);

            goodsList.add(goods);

        }

        // 基本信息校验完了，如果没有错误继续校验重复信息
        if (messages.isEmpty()) {
            // 快递单号重复
            messages.addAll(duplicateValue(deliverOrderNos, TITLES[1]));
        }

        excelCheckMessage.setObjects(goodsList);
        return excelCheckMessage;
    }

}

