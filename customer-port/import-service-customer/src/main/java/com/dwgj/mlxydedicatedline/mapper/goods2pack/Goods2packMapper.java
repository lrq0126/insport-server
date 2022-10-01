package com.dwgj.mlxydedicatedline.mapper.goods2pack;

import com.dwgj.mlxydedicatedline.entity.Goods2pack;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Goods2packMapper继承基类
 */
@Repository
public interface Goods2packMapper extends MyBatisBaseDao<Goods2pack, Integer> {

    /**
     * 根据快递包裹id 获取 德威包裹id
     * @param goodsId
     * @return
     */
    Goods2pack findByGoodsId(int goodsId);

    List<Goods2pack> findByPackId(Integer id);

    /**
     * 根据包裹id删除
     * @param packId
     */
    void deleteByPackId(Integer packId);

    /**
     * 根据包裹id集合删除
     * @param packIdList
     */
    void deleteByPackIdList(@Param("packIdList") List<Integer> packIdList);

    void insertList(@Param("goods2packList") List<Goods2pack> goods2packList);

    List<Goods2pack> selectByPackIdList(@Param("packIdList") List<Integer> packIdList);
}