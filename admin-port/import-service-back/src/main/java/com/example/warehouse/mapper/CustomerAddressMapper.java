package com.example.warehouse.mapper;

import com.example.warehouse.entity.CustomerAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomerAddressMapper {
    int insert(CustomerAddress record);

    List<CustomerAddress> selectAll(Map<String,Object> map);

    int countSelectAll(Map<String,Object> map);

    int updateByPrimaryKey(CustomerAddress record);

    int updateByPrimaryKeySelective(CustomerAddress address);

    int del(@Param("addressId") Integer addressId);

    int deleteByCustomerNo(@Param("addressNo")String addressNo, @Param("userId")int userId);

    CustomerAddress findById(int id);

    List<CustomerAddress> find(Map<String,Object> map);

    List<CustomerAddress> findByCustomerId(int customerId);

    List<CustomerAddress> findByName(String name);

    List<CustomerAddress> getCustomerAddressById(CustomerAddress address);
}
