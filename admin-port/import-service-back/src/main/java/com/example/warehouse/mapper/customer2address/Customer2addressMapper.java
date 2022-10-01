package com.example.warehouse.mapper.customer2address;

import com.example.warehouse.entity.Customer2address;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
 * Customer2addressMapper继承基类
 */
@Repository
public interface Customer2addressMapper extends MyBatisBaseDao<Customer2address, Integer> {

    Customer2address findByPackId(Integer customerPackId);

    void updateByCustomerPackId(Customer2address customer2address);
}