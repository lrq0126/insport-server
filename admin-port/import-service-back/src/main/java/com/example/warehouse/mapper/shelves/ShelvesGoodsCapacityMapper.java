package com.example.warehouse.mapper.shelves;

import com.example.warehouse.entity.shelves.ShelvesGoodsCapacity;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
* 货架货物容量表(ShelvesGoodsCapacity)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/7/27 15:15
*/
@Repository
public interface ShelvesGoodsCapacityMapper extends MyBatisBaseDao<ShelvesGoodsCapacity, Integer> {

    @Override
    int insert(ShelvesGoodsCapacity record);

    @Override
    int insertSelective(ShelvesGoodsCapacity record);

    @Override
    ShelvesGoodsCapacity selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(ShelvesGoodsCapacity record);

    @Override
    int updateByPrimaryKey(ShelvesGoodsCapacity record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    ShelvesGoodsCapacity getLatestShelvesAreaRow();


}