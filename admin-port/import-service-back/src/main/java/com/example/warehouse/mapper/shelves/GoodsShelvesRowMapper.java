package com.example.warehouse.mapper.shelves;

import com.example.warehouse.entity.shelves.GoodsShelvesRow;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 货物所在货架信息表(GoodsShelvesRow)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/7/25 16:1
*/
@Repository
public interface GoodsShelvesRowMapper extends MyBatisBaseDao<GoodsShelvesRow, Integer> {

    @Override
    int insert(GoodsShelvesRow record);

    @Override
    int insertSelective(GoodsShelvesRow record);

    @Override
    GoodsShelvesRow selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(GoodsShelvesRow record);

    @Override
    int updateByPrimaryKey(GoodsShelvesRow record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    GoodsShelvesRow selectByGoodsId(Integer goodsId);

    /**
     * 拣货下架
     * @param goodsId
     */
    void sortGoods(@Param("goodsId") Integer goodsId);

    /**
     * 批量拣货
     * @param goodsIdList
     */
    void batchSortGoods(@Param("goodsIdList") List<Integer> goodsIdList);

    void insertList(@Param("goodsShelvesRowList") List<GoodsShelvesRow> goodsShelvesRowList);
}