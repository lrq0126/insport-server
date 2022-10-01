package com.dwgj.mlxydedicatedline.mapper;

import com.dwgj.mlxydedicatedline.entity.Goods;
import com.dwgj.mlxydedicatedline.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * GoodsMapper继承基类
 * @author lrq
 */
@Repository
public interface GoodsMapper extends MyBatisBaseDao<Goods, Integer> {

    List<GoodsVo> findGoods(Map<String,Object> map);

    int deleteByIds(Map<String,Object> map);

    int deleteById(Integer id);

    int updateByPrimary(Goods goods);

    int countSelectAll(Map<String,Object> map);

    GoodsVo findGoodsNo(String goodsNo);

    List<GoodsVo> findByGoodsNos(@Param("goodsNos")List<String> goodsNos);

    /**
     * 根据id集合查询  货物
     * @param goodsIds
     * @return
     */
    List<GoodsVo> selectGoodsByIds(@Param("goodsIds")List<Integer> goodsIds);

    List<Goods> selectGoodsBySonPackId(@Param("sonPackId") int sonPackId);

    int updateOfApplyPackGoodsTypeChangeByGoodsNo(@Param("goodsNos") List<String> goodsNos);

    int updateOfApplyPackGoodsTypeChangeByGoodsIds(@Param("goodsIds") List<String> goodsIds);

    int updateToConfirmShipment(@Param("goodsNos") List<String> GoodsNos);

    int updateGoodsByIdListAndType(@Param("goodsIdList")List<Integer> goodsIdList,@Param("goodsType") int goodsType);
    /**
     * 根据goodsNo查询goods实体
     * @param goodsNo
     * @return
     */
    Goods findEntityByGoodsNo(String goodsNo);

    /**
     * 拆包
     * @param goodsNoList
     * @return
     */
    int unpack(@Param("goodsNos") List<String> goodsNoList);

    /**
     * 根据 快递单号查询 包裹
     * @param deliveryOrderNo
     * @return
     */
    Goods findDeliveryOrderNo(String deliveryOrderNo);

    List<Goods> findDeliveryOrderNoAndCustomerId(@Param("deliveryOrderNo")String deliveryOrderNo, @Param("customerNo")String customerNo);

    /**
     * 货物修改签收状态
     * @param goodsIdList
     */
    void receipt(@Param("goodsIdList")List<Integer> goodsIdList);

    /**
     * 修改货物状态为拼团中
     * @param goodsNoList
     */
    void updatePinByGoodsIds(List<String> goodsNoList);

    /**
     * 修改货物状态为入库
     * @param goodsNoList
     */
    void updateInWarehouseByGoodsIds(List<String> goodsNoList);


    List<Goods> findByPackId(int packId);


    //  拼团用
    Double selectPackWeight(int pid);

    List<Map<String, Object>> selectGoodsLocationByCustomerNo(String customerNo);

    List<Goods> selectGoodsByCustomerNo(String customerNo);

    /**
     * 更改订单的绑定客户（用于微信客户多次点击关注，多个账号绑定同一个微信号的时候）
     * @param updateGoodsList
     * @param customerNo
     */
    void updateCustomerNo(@Param("goodsList") List<Goods> updateGoodsList, @Param("customerNo") String customerNo);

    List<Map<String,Object>> getGoodsCommercialArea(String customerNo);

    void insertList(@Param("goodsList") List<Goods> goodsList);
}
