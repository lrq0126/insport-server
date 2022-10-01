package com.dwgj.mlxydedicatedline.utils;

import com.dwgj.mlxydedicatedline.entity.customerPack.PackValuation;

import java.math.BigDecimal;
import java.util.List;

public class CustomerPackUtil {

    public static List<PackValuation>  dealWithPackValuations(List<PackValuation> packValuations){
        // 如果是非关税预付的渠道，存在
        if (packValuations.size() <= 1) {
            for (PackValuation packValuation : packValuations) {
                if (packValuation.getGoodsValue() == null ||
                        packValuation.getGoodsValue().compareTo(new BigDecimal(0)) <= 0) {
                    packValuations = null;
                    break;
                }
                if (packValuation.getUnitPrice() == null ||
                        packValuation.getUnitPrice().compareTo(new BigDecimal(0)) <= 0) {
                    packValuations = null;
                    break;
                }
                if (packValuation.getNumber() == null || packValuation.getNumber() <= 0) {
                    packValuations = null;
                    break;
                }
            }
        } else {
            for (PackValuation packValuation : packValuations) {
                if (packValuation.getProductName() == null || packValuation.getProductName().isEmpty()) {
                    packValuation.setProductName("客户未输入产品名称");
                }
                if (packValuation.getUnitPrice() == null) {
                    packValuation.setUnitPrice(new BigDecimal(0));
                    packValuation.setGoodsValue(new BigDecimal(0));
                }
                if (packValuation.getNumber() == null || packValuation.getNumber() <= 0) {
                    packValuation.setNumber(0d);
                    packValuation.setGoodsValue(new BigDecimal(0));
                }
            }
        }
        return packValuations;
    }

}
