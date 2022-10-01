package com.dwgj.mlxydedicatedline.mapper;

import com.dwgj.mlxydedicatedline.entity.WarehouseAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* 仓库地址(WarehouseAddress)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/7/6 15:55
*/
@Repository
public interface WarehouseAddressMapper extends MyBatisBaseDao<WarehouseAddress, Integer> {

    @Override
    int insert(WarehouseAddress record);

    @Override
    int insertSelective(WarehouseAddress record);

    @Override
    WarehouseAddress selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(WarehouseAddress record);

    @Override
    int updateByPrimaryKey(WarehouseAddress record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    WarehouseAddress getIsEnableAddress(@Param("commercialAreaId") int commercialAreaId);


}