package com.dwgj.mlxydedicatedline.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.Goods;
import com.dwgj.mlxydedicatedline.entity.Order;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.ERROR;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 客户下单修改接口
     * @param paramMap
     * 具体参数：
     *      deliveryNo 快递公司名称；deliveryOrderNo  快递单号； goodsName  货物名称； goodsNumber  包裹数量； remark  备注；
     *      addressId  收货地址； id    包裹id；
     * @param session
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<ResultModel> updateOrder(@RequestParam Map<String,Object> paramMap, HttpSession session){
        Customer customer = UserThreadContext.getUser();
        Order order = JSON.parseObject(JSONObject.toJSONString(paramMap),Order.class);
        int i = orderService.updateByDeliveryOrderNo(order,customer.getId());
        if(i < 1){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    @PostMapping("/updateGoods")
    public ResponseEntity<ResultModel> updateOrder(Goods goods){
        Customer customer = UserThreadContext.getUser();
        int i = orderService.updateByGoodsId(goods,customer.getId());
        if(i < 1){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    /**
     * 客户删除订单接口
     * @param id
     * 具体参数：
     *      id  快递单号；
     * @param session
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<ResultModel> deleteOrder(@RequestParam int id, HttpSession session){
        Customer customer = UserThreadContext.getUser();
        int i = orderService.deleteByid(id);
        if(i < 1){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

}
