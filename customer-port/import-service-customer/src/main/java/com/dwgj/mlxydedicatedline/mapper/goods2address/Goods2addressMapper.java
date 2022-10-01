package com.dwgj.mlxydedicatedline.mapper.goods2address;

import com.dwgj.mlxydedicatedline.entity.Goods2address;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Goods2addressMapper继承基类
 */
@Repository
public interface Goods2addressMapper extends MyBatisBaseDao<Goods2address, Integer> {

    /**
     * 批量插入 货物对应地址
     * @param goods2addressList
     */
    void insertSelectiveList(@Param("goods2addressList") List<Goods2address> goods2addressList);
}