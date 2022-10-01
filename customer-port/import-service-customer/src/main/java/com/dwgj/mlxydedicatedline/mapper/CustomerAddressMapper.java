package com.dwgj.mlxydedicatedline.mapper;

import com.dwgj.mlxydedicatedline.entity.CustomerAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * CustomerAddressMapper继承基类
 */
@Repository
public interface CustomerAddressMapper extends MyBatisBaseDao<CustomerAddress, Integer> {

    List<CustomerAddress> findByCustomerId(int customerId);

    int countFindPageNumber(Map<String,Object> paramMap);

    int setDefaultAddress(int id);

    int setNotDefaultAddress(@Param("id") int id, @Param("customerId") int CustomerId);

    int deleteById(int id);

    List<CustomerAddress> findAll(Map<String,Object> paramMap);
}