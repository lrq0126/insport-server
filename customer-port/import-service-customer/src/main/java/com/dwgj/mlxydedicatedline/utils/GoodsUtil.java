package com.dwgj.mlxydedicatedline.utils;

import com.dwgj.mlxydedicatedline.entity.Goods;
import com.dwgj.mlxydedicatedline.vo.GoodsVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodsUtil {

    public void goodsNameCheck(Goods goods){
        if(goods.getGoodsName() == null || goods.getGoodsName().isEmpty()){
            return;
        }
        if(goods.getGoodsName().contains("&")){
            String goodsNames [] = goods.getGoodsName().split("&");
            StringBuilder goodsName = new StringBuilder();
            for (String str : goodsNames){
                goodsName.append(str);
            }
            goods.setGoodsName(goodsName.toString());
        }
    }


    /**
     * 对比单边边长
     * @param sideLengthLimit
     * @param length
     * @param width
     * @param height
     * @return
     */
    public boolean checkIsCalculateVolWeight(double sideLengthLimit, Double length, Double width, Double height){
        if(length == null || width == null || height == null){
            return true;
        }
        return sideLengthLimit <= length
                || sideLengthLimit <= width
                || sideLengthLimit <= height;
    }

}
