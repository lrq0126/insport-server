package com.example.warehouse.mapper.sys;

import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.CustomerAccount;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.Goods;
import com.example.warehouse.entity.sys.SysStatistical;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customer.CustomerPackVo;
import com.example.warehouse.vo.sys.SysStatisticalReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* 系统统计表(SysStatistical)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/9/16 16:5
*/
@Repository
public interface SysStatisticalMapper extends MyBatisBaseDao<SysStatistical, Integer> {

    @Override
    int insert(SysStatistical record);

    @Override
    int insertSelective(SysStatistical record);

    @Override
    SysStatistical selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(SysStatistical record);

    @Override
    int updateByPrimaryKey(SysStatistical record);
	
	@Override
    int deleteByPrimaryKey(Integer id);

    void deleteByKeyList(@Param("idList") List<Integer> idList);

	int removeByPrimaryKey(Integer id);


    /**
     * 计算在仓库的客户人数（昨日）
     * @return
     */
    int countGoodsInCustomerNum();

    /**
     * 计算今日入仓票数
     * @return
     */
    int countGoodsInWareTodayNum(@Param("commercialAreaId") int commercialAreaId);
    /**
     * 计算今日入仓重量
     * @return
     */
    double countGoodsInWareWeightTodaySum(@Param("commercialAreaId") int commercialAreaId);

    /**
     * 计算昨日入仓票数
     * @return
     */
    int countGoodsInWareLastNum(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 今日新增人数
     * @return
     */
    int countTodayAddCustomerNum(@Param("commercialAreaId") int commercialAreaId);

    /**
     * 昨日新增人数
     * @return
     */
    int countLastAddCustomerNum(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 今日预报包裹数
     * @return
     */
    int countTodayAddPackageNum(@Param("commercialAreaId") int commercialAreaId);

    /**
     * 昨日预报包裹数
     * @return
     */
    int countLastAddPackageNum(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 今日申请打包数
     * @return
     */
    int countTodayApplyPackNum(@Param("commercialAreaId") int commercialAreaId);
    /**
     * 今日打包数
     * @return
     */
    int countTodayPackedNum(@Param("commercialAreaId") int commercialAreaId);

    /**
     * 昨日申请打包数
     * @return
     */
    int countLastApplyPackNum(@Param("startTime")String startTime, @Param("endTime")String endTime);
    /**
     * 昨日打包数
     */
    int countLastPackedNum(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 今日充值数
     * @return
     */
    int countTodayAccountNum(@Param("commercialAreaId") int commercialAreaId);

    /**
     * 昨日充值数
     * @return
     */
    int countLastAccountNum(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 今日充值金额
     * @return
     */
    BigDecimal countTodayAccountAmount(@Param("commercialAreaId") int commercialAreaId);

    /**
     * 昨日充值金额
     * @return
     */
    BigDecimal countLastAccountAmount(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 仓库内总重量（昨日）
     * @return
     */
    Double countWarePackagesWeight();
    /**
     * 入库总重量（昨日）
     * @return
     */
    Double countInWareWeightLastDay(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 仓库内包裹总数（昨日）
     * @return
     */
    int countWarePackagesNum();

    /**
     * 计算今天出库实际重量
     * @return
     */
    Double countOutWareWeightToday(@Param("commercialAreaId") int commercialAreaId);

    /**
     * 计算昨天出库实际重量
     * @return
     */
    Double countOutWareWeightLastDay(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 计算所有已经付钱发货的客户（昨日）
     * @return
     */
    int countAllOutWarehouseCustomer();

    /**
     * 计算昨天已发货订单数量
     * @return
     */
    int countOutWareNumLastDay(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 仓库内包裹总立方数（昨日）
     * @return
     */
    Double countWarePackagesVol();

    List<SysStatistical> selectByConditions(SysStatisticalReqVo sysStatisticalReqVo);

    List<SysStatistical> getAll();

    // 查询所有的发货客户数量
    int selectOrdersNum(SysStatisticalReqVo sysStatisticalReqVo);

    // 查询新用户的来货客户数量
    int selectNewCustomerGoodsOrdersNum(SysStatisticalReqVo sysStatisticalReqVo);
    // 查询新用户的未来货客户数量
    int selectNewCustomerNotGoodsOrdersNum(SysStatisticalReqVo sysStatisticalReqVo);

    // 查询新用户的发货客户数量
    int selectNewCustomerOrdersNum(SysStatisticalReqVo sysStatisticalReqVo);
    // 查询新用户的未下单客户数量
    int selectNewCustomerNotOrdersNum(SysStatisticalReqVo sysStatisticalReqVo);

    int selectNewCustomerGoodsOrdersNumInCustomerId(SysStatisticalReqVo sysStatisticalReqVo);
    int selectNewCustomerNotGoodsOrdersNumInCustomerId(SysStatisticalReqVo sysStatisticalReqVo);

    int selectNewNumber(SysStatisticalReqVo sysStatisticalReqVo);

    double selectGoodsWeightSum(SysStatisticalReqVo sysStatisticalReqVo);

    // 查询新增的客户信息
    List<Customer> findNewCustNumber(SysStatisticalReqVo sysStatisticalReqVo);

    // 查询新增的已经下过单的客户信息
    List<Customer> findNewCustGoodsOrdersNum(SysStatisticalReqVo sysStatisticalReqVo);
    // 查询未下过单的客户的信息
    List<Customer> findNewCustNotGoodsOrdersNum(SysStatisticalReqVo sysStatisticalReqVo);

    List<Integer> findNewCustomerId(SysStatisticalReqVo sysStatisticalReqVo);

    // 查询昨天出库的货物数量
    int countOutWareGoodsNumLastDay(@Param("startTime") String startTime, @Param("endTime") String endTime);

    int getStatisticGoodsCount(Map<String, Object> requestMap);

    List<GoodsVo> getStatisticGoodsDetail(Map<String, Object> requestMap);

    int getStatisticCustomerPackCount(Map<String, Object> requestMap);

    List<CustomerPackVo> getStatisticCustomerPackDetail(Map<String, Object> requestMap);

    int getStatisticCustomerCount(Map<String,Object> requestMap);

    List<Customer> getStatisticCustomerDetail(Map<String, Object> requestMap);

    int getStatisticTopUpCount(Map<String, Object> requestMap);

    List<CustomerAccount> getStatisticTopUpDetail(Map<String, Object> requestMap);

    BigDecimal getStatisticTopUpAmount(Map<String,Object> requestMap);

    /**
     * 查询下单客户的人数
     * @param sysStatisticalReqVo
     * @return
     */
    List<Customer> findNewCustOrdersNum(SysStatisticalReqVo sysStatisticalReqVo);

    /**
     * 查询未下单的客户的人数
     * @param sysStatisticalReqVo
     * @return
     */
    List<Customer> findNewCustNotOrdersNum(SysStatisticalReqVo sysStatisticalReqVo);


}