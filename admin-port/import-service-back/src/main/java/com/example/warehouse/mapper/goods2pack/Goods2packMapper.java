package com.example.warehouse.mapper.goods2pack;

import com.example.warehouse.entity.Goods2pack;
import com.example.warehouse.mapper.MyBatisBaseDao;
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

    List<Goods2pack> findByPackId(int packId);

    /**
     * 根据包裹id删除
     * @param packId
     */
    void deleteByPackId(Integer packId);
    /**
     * 根据包裹id删除
     * @param packIdList
     */
    void deleteByPackIdList(@Param("packIdList") List<Integer> packIdList);

    /**
     * 更新子包裹id
     * @param goods2packList
     */
    void updateGoods2PackList(@Param("goods2packList") List<Goods2pack> goods2packList);

    void resetBySonPackId(Integer packNumberId);

    /**
     * 查询快递包裹id
     * @param sonPackIdList
     * @return
     */
    List<Integer> findGoodsIdBySonPackIdList(@Param("sonPackIdList") List<Integer> sonPackIdList);

    void resetAllByPackId(int packId);

    List<Goods2pack> selectEntityByGoodsNoList(@Param("goodsNoList") List<String> goodsNoList);

    /**
     * 重置关联状态
     * @param sonPackId
     */
    void initByCustomerPackNumberId(int sonPackId);

    /**
     * 查询需要重置装箱状态的快递包裹id
     * @param customerPackNumberId
     * @param goodsIds
     * @return
     */
    List<Integer> findRemoveGoodsIds(@Param("customerPackNumberId") int customerPackNumberId, @Param("goodsIds") List<Integer> goodsIds);

    /**
     * 根据货物id重置子包裹和货物的关联
     * @param outPackGIds
     */
    void resetInPacked(@Param("outPackGIds") List<Integer> outPackGIds);

    void insertList(@Param("goods2packList") List<Goods2pack> goods2packList);
}