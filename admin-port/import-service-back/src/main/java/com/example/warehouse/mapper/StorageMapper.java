package com.example.warehouse.mapper;

import com.example.warehouse.entity.Storage;
import com.example.warehouse.vo.storage.StorageLocationsRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface StorageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Storage record);

    Storage selectByPrimaryKey(Integer id);

    List<Storage> selectAll(Map<String,Object> map);

    int countSelectAll(Map<String,Object> map);

    int updateByPrimaryKey(Storage record);

    int del(@Param("storageNo")String storageNo, @Param("userId")int userId);

    List<Storage> find(Map<String,Object> map);

    List<StorageLocationsRespVo> getWareLocations();

}