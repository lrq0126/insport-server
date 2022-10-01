package com.dwgj.mlxydedicatedline.mapper.customer2address;

import com.dwgj.mlxydedicatedline.entity.Customer2address;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
 * Customer2addressMapper继承基类
 */
@Repository
public interface Customer2addressMapper extends MyBatisBaseDao<Customer2address, Integer> {

    Customer2address findByPackId(Integer customerPackId);

    int deleteById(Integer id);
}