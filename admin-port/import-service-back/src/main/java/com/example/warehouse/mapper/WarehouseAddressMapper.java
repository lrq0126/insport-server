package com.example.warehouse.mapper;


import com.example.warehouse.entity.WarehouseAddress;
import com.example.warehouse.vo.WarehouseAddressReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 仓库地址(WarehouseAddress)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/7/5 16:24
*/
@Repository
public interface WarehouseAddressMapper extends MyBatisBaseDao<WarehouseAddress, Integer>{
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

    int getWarehouseAddressCount(WarehouseAddressReqVo warehouseAddressReqVo);

    List<WarehouseAddress> getWarehouseAddressList(WarehouseAddressReqVo warehouseAddressReqVo);

    void unableWarehouseAddressAll(@Param("commercialAreaId") int commercialAreaId);

    void enableWarehouseAddress(int id);

    void unableWarehouseAddress(int id);

    /**
     * 获取启用中的仓库收件地址
     * @return
     */
    WarehouseAddress getIsEnableAddress(@Param("commercialAreaId") int commercialAreaId);

}