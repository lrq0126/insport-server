package com.example.warehouse.mapper.customerPack;

import com.example.warehouse.DO.CustomerPackExportDo;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.customerPack.CustomerOrderReqVo;
import com.example.warehouse.vo.customerPack.CustomerPackVo;
import com.example.warehouse.vo.customerPack.RouteCustomerPackReqVo;
import com.example.warehouse.vo.mobile.PackSortReqVo;
import com.example.warehouse.vo.packVo.PackConditionsQueryReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * CustomerPackMapper继承基类
 * @author lrq
 */
@Repository
public interface CustomerPackMapper extends MyBatisBaseDao<CustomerPack, Integer> {

    int getCustomerOrderCount(CustomerOrderReqVo customerOrderReqVo);

    List<Map> getOrderListByCustomerId(CustomerOrderReqVo customerOrderReqVo);

    List<CustomerPack> getWaitPackList(PackSortReqVo packSortReqVo);

    CustomerPackVo selectWaitPackDetailByBusinessNumber(String businessNumber);

    /**
     * 查询总共多少条数据
     * @param paramMap
     * @return
     */
    int countSelectAll(Map<String,Object> paramMap);

    CustomerPack selectById(Integer id);

    /**
     * 根据包裹状态的 打包包裹查询
     * 参数：packType  包裹状态 1、待打包  2、已打包  3、确认发货  4、已出库
     *       addressee  收件人姓名；   phoneNumber   手机号码； businessNumber  流水号
     * @param paramMap
     * @return
     */
    List<CustomerPackVo> findByPackType(Map<String,Object> paramMap);

    /**
     * 关联查询 根据包裹状态的 打包包裹查询  的数量
     * @param paramMap
     * @return
     */
    Integer countSelectByPackType(Map<String,Object> paramMap);

    /**
     * 发货列表
     * @param queryReqVo
     * @return
     */
    List<CustomerPackVo> findOutStorageList(PackConditionsQueryReqVo queryReqVo);
//    List<CustomerPackVo> findOutStorageList(Map<String,Object> paramMap);
    Integer countOutStorageList(PackConditionsQueryReqVo queryReqVo);
//    Integer countOutStorageList(Map<String,Object> paramMap);

    /**
     * 根据 流水号 查询 包裹
     * @param businessNumber
     * @return
     */
    CustomerPackVo findByBusinessNumber(String businessNumber);

    /**
     * 根据 业务号 更新包裹状态
     * @param customerPack
     * @return
     */
    int updatePacking(CustomerPack customerPack);

    /**
     *
     * @param customerPack
     * @return
     */
    int changePackMessage(CustomerPack customerPack);

    /**
     * 操作员出库操作
     * @param id
     * @param transitNumber
     * @return
     */
    int updateDeliver(@Param("id") Integer id, @Param("transitNumber") String transitNumber);

    /**
     * 操作员出库操作
     * @param ids
     * @return
     */
    int updateDeliverBatch(@Param("ids") List<Integer> ids, @Param("userId") Integer userId);

    void updateByIdAndType(@Param("id") int id, @Param("packType") int packType);
    /**
     * 操作员拆包操作
     * @param id
     * @return
     */
    int delete(int id);

    List<CustomerPackVo> findToBeShipped(Map<String,Object> paramMap);

    /**
     * 查询待拆包
     * @param paramMap
     * @return
     */
    List<CustomerPackVo> findByToUnpacking(Map<String,Object> paramMap);

    /**
     * 根据订单号查询
     * @param orderNumber
     * @return
     */
    CustomerPackVo findByOrderNumber(@Param("orderNumber") String orderNumber);

    /**
     * 根据转运号查询
     * @param orderNumber
     * @return
     */
    List<CustomerPackVo> selectByTransshipmentNo(String orderNumber);
    /**
     * 根据 id 集合查询
     * @param ids
     * @return
     */
    List<CustomerPack> selectByPrimaryKeyList(@Param("ids") List<String> ids);

    /**
     * 修改 包裹信息   ——修改备注、修改附加费、修改优惠价、添加转运单号
     * @param customerPack
     */
    void updateCustomerPackMessage(CustomerPack customerPack);

    /**
     * 拆包
     * @param packId
     */
    void unpacked(@Param("packId") int packId,@Param("version") int version);

    void updateAgentNumber(Map<String,Object> map);

    List<CustomerPack> selectIsRouteUse(@Param("routeIds") List<String> routeIds);

    /**
     * 撤销打包申请-更改打包状态
     * @param packId
     */
    void cancleGoods2pack(int packId);

    /**
     * 撤销打包申请-更改包裹状态
     * @param goodId
     */
    void cancleGoods(int goodId);

    /**
     * 获取所有goods_id
     * @param packId
     * @return
     */
    List<String> getAllGoodsIdByPackId(int packId);

    // 批量插入
    int insertList(@Param("customerPackList") List<CustomerPack> customerPackList);
    //批量更新
    int updateList(@Param("customerPackList") List<CustomerPack> customerPackList);

    /**
     * 根据订单号更新数据
     * @param updateCustomerPackList
     */
    void updateListByOrderNumber(@Param("customerPackList") List<CustomerPack> updateCustomerPackList);

    List<CustomerPackVo> selectByOrderNumberList(@Param("orderNumberList") List<String> orderNumberList);

    CustomerPackVo selectPackVoById(int id);
    /**
     * 查询超时未支付的客户订单
     * @param packTimeFlag
     * @return
     */
    List<CustomerPack> getOrdersToPay(String packTimeFlag);

    void updateShippedByOrderNumber(@Param("orderNumberList") List<String> orderNumberList);

    List<CustomerPack> selectByWaitPack();

    /**
     * 检查订单号是否已发货
     * @param orderNumber
     * @return
     */
    int checkOrderNumberIsOutWare(@Param("orderNumber")String orderNumber);

    /**
     * 更新打印时间
     * @param pack
     */
    int updatePrint(CustomerPack pack);

    List<Integer> selectCustomerByRouteId(@Param("routeIdList") List<String> routeIdList,
                                          @Param("groupCustomerIdList") List<Integer> groupCustomerIdList);


    int getOrderSum(@Param("customerId") int customerId);

    BigDecimal getPayAmountSum(@Param("customerId") int customerId);


    CustomerPackVo getPayOrderByOrderNumber(String orderNumber);

    void confirmShipmentById(CustomerPack customerPack);

    List<CustomerPack> findByTransshipmentNo(String orderNumber);

    /**
     * 查询系统中的有效订单
     * @param orderNumberList 所有订单号
     * @return
     */
    List<CustomerPack> selectEffectivePackByOrderNumberList(@Param("orderNumberList") List<String> orderNumberList);

    List<CustomerPackExportDo> findExportCustomerPack(Map requestMap);

    void cancelPackDelivery(@Param("packId") int packId, @Param("packType") int packType);

    CustomerPack selectByOrderNumber(String orderNumber);

    Map<String, String> selectNewestOrderNumber(String orderNumber);

    List<CustomerPackVo> findPrintInvoiceCustomerPackByIds(@Param("orderIdList") List<Integer> orderIdList);

    List<CustomerPackVo> selectListByPinSpellMailId(@Param("pinSpellMailId") int pinSpellMailId);

    /**
     * 查询当前路线的待打包订单数量
     * @param routeCustomerPackReqVo
     * @param customerPackId
     * @return
     */
    int getRouteCustomerPackCount(@Param("reqVo") RouteCustomerPackReqVo routeCustomerPackReqVo,
                                  @Param("customerPackIdList") List<String> customerPackId);
    /**
     * 查询当前路线的待打包订单数据
     * @param routeCustomerPackReqVo
     * @param customerPackId
     * @return
     */
    List<CustomerPackVo> getRouteCustomerPackList(@Param("reqVo") RouteCustomerPackReqVo routeCustomerPackReqVo,
                                                  @Param("customerPackIdList") List<String> customerPackId);

    void updatePackTypeList(@Param("packIdList") List<Integer> packIdList, @Param("packType") int packType);
}
