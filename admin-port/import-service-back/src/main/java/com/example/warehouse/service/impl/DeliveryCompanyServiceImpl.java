package com.example.warehouse.service.impl;

import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.DeliveryCompany;
import com.example.warehouse.entity.User;
import com.example.warehouse.mapper.DeliveryCompanyMapper;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.UserMapper;
import com.example.warehouse.service.DeliveryCompanyService;
import com.example.warehouse.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("deliveryCompanyService")
public class DeliveryCompanyServiceImpl implements DeliveryCompanyService {

    @Autowired
    private DeliveryCompanyMapper deliveryCompanyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public int save(DeliveryCompany deliveryCompany) {
        //TODO 添加deliveryCompany_code
        String code = SequenceCode.gainSerialNo("DEC");
        deliveryCompany.setDeliveryNo(code);
        return deliveryCompanyMapper.insert(deliveryCompany);
    }

    @Override
    public int update(DeliveryCompany deliveryCompany) {
        return deliveryCompanyMapper.updateByPrimaryKey(deliveryCompany);
    }

    @Override
    public int del(String deliveryCompanyNo, String usercode) {
        Map<String,Object> map = new HashMap<>();
        map.put("deliveryName",deliveryCompanyNo);
        List<GoodsVo> goodsList = goodsMapper.find(map);
        if(!goodsList.isEmpty()){ //不为空，说明货物表里面有引用该快递公司，则不可以删除
            return 500;
        }
        User user = userMapper.findByCode(usercode);
        return deliveryCompanyMapper.del(deliveryCompanyNo, user.getId());
    }

    @Override
    public List<DeliveryCompany> findAll(Map<String,Object> map) {
        return deliveryCompanyMapper.selectAll(map);
    }

    @Override
    public int countFindAll(Map<String,Object> map){
        return deliveryCompanyMapper.countSelectAll(map);
    }

    @Override
    public List<DeliveryCompany> find(Map<String, Object> map) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username",map.get("createName"));
        List<User> userLists = userMapper.find(userMap);
        map.remove("createName");
        return deliveryCompanyMapper.find(map);
    }
}
