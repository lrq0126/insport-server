package com.dwgj.mlxydedicatedline.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dwgj.mlxydedicatedline.commons.SequenceCode;
import com.dwgj.mlxydedicatedline.entity.CustomerAddress;
import com.dwgj.mlxydedicatedline.mapper.CustomerAddressMapper;
import com.dwgj.mlxydedicatedline.service.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {

    @Autowired
    private CustomerAddressMapper customerAddressMapper;

    @Override
    public List<CustomerAddress> findByCustomerId(Map<String,Object> paramMap) {
        return customerAddressMapper.findAll(paramMap);
    }

    @Override
    public int countFindPageNumber(Map<String, Object> paramMap) {
        return customerAddressMapper.countFindPageNumber(paramMap);
    }

    @Override
    public int addNewAddress(Map<String,Object> map) {
        CustomerAddress customerAddress = JSON.parseObject(JSONObject.toJSONString(map),CustomerAddress.class);
        customerAddress.setAddressNo(SequenceCode.gainSerialNo("adr"));
        List<CustomerAddress> customerAddressList = customerAddressMapper.findByCustomerId(Integer.parseInt(map.get("customerId").toString()));

        if(customerAddressList.size() < 1){
            // 如果没有地址，则此地址设置为默认地址
            customerAddress.setDefaultFlag("1");
        } else {
            customerAddress.setDefaultFlag("0");
        }
//        customerAddress.setReceiverAddress(customerAddress.getReceiverAddress() + map.get("code"));
        return customerAddressMapper.insertSelective(customerAddress);
    }

    @Override
    public int setDefaultAddress(int CustomerAddressId) {
        CustomerAddress customerAddress = customerAddressMapper.selectByPrimaryKey(CustomerAddressId);
        int result = customerAddressMapper.setDefaultAddress(CustomerAddressId);
        if(result == 1){
            customerAddressMapper.setNotDefaultAddress(CustomerAddressId,customerAddress.getCustomerId());
            return result;
        } else {
            return 0;
        }
    }

    @Override
    public int delteAddress(int customerAddressId) {
        CustomerAddress customerAddress = customerAddressMapper.selectByPrimaryKey(customerAddressId);
        // 如果被删除的地址是默认地址，则将获取到的第一个地址设置成默认地址
        if("1".equals(customerAddress.getDefaultFlag())){
            Map<String, Object> selectMap = new HashMap<>(4);
            selectMap.put("customerId",customerAddress.getCustomerId());
            List<CustomerAddress> customerAddressList = customerAddressMapper.findAll(selectMap);
            if(customerAddressList.size() > 0){
                CustomerAddress newCustomerAddress = customerAddressList.get(0);
                customerAddressMapper.setDefaultAddress(newCustomerAddress.getId());
            }
        }
        return customerAddressMapper.deleteById(customerAddressId);
    }

    @Override
    public int updateAddress(Map<String,Object> map) {
        CustomerAddress customerAddress = JSON.parseObject(JSONObject.toJSONString(map),CustomerAddress.class);
        int version = customerAddressMapper.selectByPrimaryKey(customerAddress.getId()).getVersion();
        customerAddress.setVersion(version);
        return customerAddressMapper.updateByPrimaryKeySelective(customerAddress);
    }
}
