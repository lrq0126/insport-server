package com.example.warehouse.mapper;

import com.example.warehouse.entity.Goods;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.applyPack.GoodsRequestVo;
import com.example.warehouse.vo.customer.CustomerOrderReqVo;
import com.example.warehouse.vo.detailedListVo.GoodsDetailedListVo;
import com.example.warehouse.vo.goods.GoodsReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    Goods selectByPrimaryKey(Integer id);

//    List<GoodsVo> selectAll(Map<String,Object> map);
//    int countSelectAll(Map<String,Object> map);
    List<GoodsVo> selectAll(GoodsReqVo goodsReqVo);
    int countSelectAll(GoodsReqVo goodsReqVo);

    List<GoodsVo> selectAllRe(Map<String,Object> map);

    int countSelectAllRe(Map<String,Object> map);

    int updateByPrimaryKey(Goods record);

    int updateShelvesById(Goods goods);

    int del(@Param("goodsNo")String goodsNo, @Param("operatorCode")String operatorCode);

    int batchDelete(Map<String,Object> map);

    Goods findByCode(String goodsNo);

    GoodsVo findByGoodsNo(String goodsNo);

    List<GoodsVo> find(Map<String,Object> map);

    List<GoodsVo> findByCustomerNo(String customerNo);

    int outWarehouse(Goods record);

    /**
     * 根据条形码的订单号查询系统中是否存在该货物
     * @param map
     * @return
     */
    int findGoodsByNo(Map<String,Object> map);

    /**
     * 扫码出库
     * @param map
     */
    int scanOutWare(Map<String,Object> map);

    int scanPicking(String deliveryOrderNo);

    List<GoodsVo> findByDeliveryOrderNo(Map<String, Object> map);

    List<GoodsVo> getGoodsInWare(String deliveryOrderNo);
    /**
     * 查询 未入包 的包裹
     * @param customerPackId
     * @param goodsNoList
     * @return
     */
    List<GoodsVo> selectPack(@Param("customerPackId") int customerPackId, @Param("goodsNoList") List<String> goodsNoList);

    int inPacked(@Param("goodsNoList") List<String> goodsNoList);

    /**
     * 初始化 拣货状态
     * @param goodsIds
     */
    void initPicking(@Param("goodsIds")List<Integer> goodsIds);

    /**
     * 初始化 入包状态
     * @param goodsIds
     */
    void initInPacked(@Param("goodsIds")List<Integer> goodsIds);

    void outPacked(@Param("outPackGoodsNoList")List<String> outPackGoodsNoList);

    List<GoodsVo> findByGoodsNos(@Param("goodsNos")List<String> goodsNos);

    /**
     * 包裹打包
     * @param goodsIdList
     * @return
     */
    int batchPackIn(@Param("goodsIdList") List<Integer> goodsIdList);

    /**
     * 批量出库
     * @param goodsNos
     * @return
     */
    int batchOutWare(@Param("goodsNos") List<String> goodsNos);

    /**
     * 订单取消打包，货物状态变更为入库状态
     * @param record
     * @return
     */
    int unpacked(Goods record);

    /**
     * 根据Id集合获取货物
     * @param goodsNos
     * @return
     */
    List<GoodsDetailedListVo> selectByGoodsNos(@Param("goodsNos") List<String> goodsNos);

    List<GoodsDetailedListVo> selectByGoodsIds(@Param("goodsIds") List<Integer> goodsIds);

	List<GoodsDetailedListVo> findGoodsDetailedVoByPackId(@Param("customerPackId") Integer customerPackId);
    /**
     * 查询所有包裹
     * @param customerPackId
     * @return
     */
    List<GoodsVo> selectPackAll(int customerPackId);

    /**
     * 查询所有未装包的包裹
     * @param goodsIdList
     * @return
     */
    List<GoodsVo> selectGoodsByRemoveSelectedGoodsIds(@Param("packId") Integer packId, @Param("goodsIdList") List<Integer> goodsIdList);

    /**
     * 根据包裹id查询未入包的快递
     * @param packId
     * @return
     */
    List<GoodsVo> selectGoodsVoByPackId(@Param("packId") Integer packId);

    /**
     * 根据子包裹id获取快递包裹
     * @param sonPackId
     * @return
     */
    List<GoodsVo> selectGoodsVoBySonPackId(@Param("sonPackId") int sonPackId);

    /**
     * 重置快递包裹的入包状态
     * @param selectedGoodsIdList
     */
    void resetInPacked(@Param("goodsIdList") List<Integer> selectedGoodsIdList);

    /**
     * 重置这个子包裹的快递货物的入包状态
     * @param sonPackId
     */
    void initInPackedBySonPackId(int sonPackId);

    /**
     * 获取客户最新放置仓库位置
     * @param customerNo
     * @return
     */
    String getLocationByCustomerNo(String customerNo);

    /**
     * 根据快递单号获取货物列表
     * @param deliveryOrderNo
     * @return
     */
    List<Goods> getGoodsBydeliveryOrderNo(String deliveryOrderNo);

    int updateAllSort(@Param("goodsNoList") List<String> goodsNoList);

    List<GoodsVo> selectByIds(@Param("goodsIds") List<Integer> goodsIds);

    void updateByIdsAndGoodsType(@Param("goodsIdList") List<Integer> goodsIdList, @Param("goodsType") int goodsType);

    /**
     * 打包完成状态更新
     * @param goodsVoList
     */
    void updatePacked(@Param("goodsVoList") List<GoodsVo> goodsVoList);

    /**
     * 打包完成状态更新（优化版）
     * @param goodsIdList
     */
    void updatePackedByGoodsIdList(@Param("goodsIdList") List<Integer> goodsIdList);

    /**
     * 批量插入货物信息
     * @param list
     * @return
     */
    int batchInsert(List<Goods> list);

    /**
     * 查询该客户的包裹
     * @param goodsRequestVo
     * @return
     */
    List<GoodsVo> selectByVo(@Param("requestVo") GoodsRequestVo goodsRequestVo, @Param("goodsType") int goodsType);

    /**
     *
     * @param customerId
     * @return
     */
    List<Goods> selectInWareByCustomerId(int customerId);

    /**
     * 获取超时未出库的包裹
     * @param goodsType  two 两个月 four 四个月 six 六个月
     * @return
     */
    List<Goods> getOverTimeInWarehouse(String goodsType);

    /**
     * 批量更新出库状态
     * @param goodsIdList
     */
    void updateOutWare(@Param("goodsIdList") List<Integer> goodsIdList);

    List<Integer> selectIdByGoodsNos(@Param("goodsNoList") List<String> goodsNoList);

    /**
     * 根据 快递单号查询 包裹
     * @param deliveryOrderNo
     * @return
     */
    Goods findDeliveryOrderNo(String deliveryOrderNo);

    /**
     * 根据订单号查询待上架的货物
     * @param deliveryOrderNo
     * @return
     */
    Goods findWaitShelvesDeliveryOrderNo(String deliveryOrderNo);

    /**
     * 查询已入库和待打包状态的包裹
     * @param deliveryOrderNo
     * @return
     */
    List<Goods> selectCountByDeliveryOrderNo(String deliveryOrderNo);

    List<Map<String,Object>> selectGoodsLocationByCustomerNo(String customerNo);

    /**
     * 查询客户在货架上的货物
     * @param customerNo
     * @return
     */
    List<Goods> selectInShelvesCustomerGoods(String customerNo);

    Goods sortGoods(@Param("customerNo") String customerNo, @Param("deliveryOrderNo") String deliveryOrderNo);

    /**
     * 检查包裹是否预报、入库或者待打包状态
     * @param deliveryOrderNo
     * @return
     */
    int checkGoodsIn(String deliveryOrderNo);



    List<GoodsVo> getGoodsDetailByOrder(String orderNumber);

    List<Goods> findGoodsByPackId(@Param("customerPackId") Integer customerPackId);


    int getCustomerGoodsCount(CustomerOrderReqVo customerOrderReqVo);

    List<GoodsVo> getGoodsListByCustomerNo(CustomerOrderReqVo customerOrderReqVo);



    int updateGoodsByIdListAndType(@Param("goodsIdList")List<Integer> goodsIdList,@Param("goodsType") int goodsType);

    void cancelGoodsDelivery(@Param("goodsIdList") List<Integer> goodsIdList, @Param("goodsType") int goodsType);

    /**
     * 查询预录入的订单
     * @param deliveryOrderNo
     * @return
     */
    Goods findReInWareByDeliveryOrderNo(String deliveryOrderNo);

    /**
     * 查询客户最新的在货架上的货物信息
     * @param customerNo
     * @return
     */
    Goods findLatestInShelvesByCustomerNo(String customerNo);

    List<GoodsVo> selectByPackBusinessNumber(@Param("businessNumber") String businessNumber);


    List<Goods> selectGoodsByGoodsType(int goodsType);

    int getGoodsSum(String customerNo);
}
