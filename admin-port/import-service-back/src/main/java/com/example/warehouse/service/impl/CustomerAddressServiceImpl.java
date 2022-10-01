package com.example.warehouse.service.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.CustomerAddress;
import com.example.warehouse.entity.User;
import com.example.warehouse.mapper.CustomerAddressMapper;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.UserMapper;
import com.example.warehouse.service.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("customerAddressService")
public class CustomerAddressServiceImpl implements CustomerAddressService {
    @Autowired
    private CustomerAddressMapper customerAddressMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public int save(CustomerAddress record) {
        // TODO 添加address_no
        String code = SequenceCode.gainSerialNo("Addr");
        record.setAddressNo(code);
        return customerAddressMapper.insert(record);
    }

    @Override
    public List<CustomerAddress> findAll(Map<String, Object> map) {
        return customerAddressMapper.selectAll(map);
    }

    @Override
    public int countFindAll(Map<String, Object> map) {
        return customerAddressMapper.countSelectAll(map);
    }

    @Override
    public int update(CustomerAddress record) {
        record.setLastUpdateTime(DateUtil.timestamp2String(new Date()));//设置更新时间
        return customerAddressMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int del(Integer addressId) {
        return customerAddressMapper.del(addressId);
    }

    @Override
    public List<CustomerAddress> find(Map<String, Object> map) {
        return customerAddressMapper.find(map);
    }

    @Override
    public List<CustomerAddress> findByName(String name) {
        return customerAddressMapper.findByName(name);
    }

    @Override public List<CustomerAddress> findByCustomerId(int customerId) {
        return customerAddressMapper.findByCustomerId(customerId);
    }
}
