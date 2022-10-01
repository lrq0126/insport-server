package com.dwgj.mlxydedicatedline.mapper;

import com.dwgj.mlxydedicatedline.entity.CustomerPack;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import com.dwgj.mlxydedicatedline.vo.GoodsPackVo;
import com.dwgj.mlxydedicatedline.vo.pack.CustomerPackRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * CustomerPackMapper继承基类
 */
@Repository
public interface CustomerPackMapper extends MyBatisBaseDao<CustomerPack, Integer> {

    List<GoodsPackVo> findPacked(Map<String, Object> paramMap);

    CustomerPack findByBusinessNumber(String businessNumber);

    CustomerPackRespVo findVoByBusinessNumber(String businessNumber);
    /**
     * 确认发货
     * @param businessNumberList
     * @return
     */
    int confirmShipment(@Param("businessNumberList") List<String> businessNumberList);

//    int confirmShipmentById(int customerPackId);
    int confirmShipmentById(CustomerPack customerPack);

    void updatePackByIdAndType(@Param("id")int id, @Param("packType")int packType);

    int countFindPageNumber(Map<String, Object> paramMap);

    int unpack(String businessNumber);

    List<GoodsPackVo> findConfirmShipment(Map<String, Object> paramMap);

    int deleteByBusinessNumber(String businessNumber);

    /**
     * 查询 打包的包裹集合
     * @param businessNumbers
     * @return
     */
    List<CustomerPack> findByBusinessNumbers(@Param("businessNumbers") List<String> businessNumbers);

    List<CustomerPack> findByOrderNumbers(@Param("orderNumberList") List<String> orderNumberList);

    List<GoodsPackVo> findToBeShipped(Map<String,Object> paramMap);

    int countFindToBeShipped(Map<String,Object> paramMap);

    /**
     * 客户包裹签收
     * @param packId
     */
    void receipt(@Param("packId") Integer packId);

    int insertList(@Param("customerPackList") List<CustomerPack> customerPackList);

    void updateListByOrderNumber(@Param("customerPackList")List<CustomerPack> customerPackList);

    List<CustomerPackRespVo> getCustomerPackByCustomerIdAndPackType(@Param("customerId") Integer customerId, @Param("packType") int packType);

    List<CustomerPackRespVo> getUnEvaluatePackByCustomer(@Param("customerId")Integer customerId,@Param("evaluateOrderIdList") List<Integer> evaluateOrderIdList);

    CustomerPackRespVo selectPackByPackId(int packId);

    int getOrderSum(@Param("customerId") int customerId);

    BigDecimal getPayAmountSum(@Param("customerId") int customerId);

    List<CustomerPack> findPackByCustomerId(Integer customerId);

    /**
     * 更改订单的绑定客户（用于微信客户多次点击关注，多个账号绑定同一个微信号的时候）
     * @param updateCustomerPackList
     * @param customerId
     */
    void updateCustomerId(@Param("customerPackList") List<CustomerPack> updateCustomerPackList,
                          @Param("customerId") Integer customerId);
}
