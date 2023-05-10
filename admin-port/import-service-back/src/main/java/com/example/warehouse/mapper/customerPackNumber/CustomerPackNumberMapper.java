package com.example.warehouse.mapper.customerPackNumber;

import com.example.warehouse.entity.CustomerPackNumber;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.customerPack.CustomerPackNumberVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * CustomerPackNumberMapper继承基类
 */
@Repository
public interface CustomerPackNumberMapper extends MyBatisBaseDao<CustomerPackNumber, Integer> {

    /**
     * 根据包裹主表id查询子包裹
     * @param packId
     * @return
     */
    List<CustomerPackNumberVo> findByPackId(Integer packId);

    List<CustomerPackNumber> findByPackIdList(@Param("packIdList") List<Integer> packIdList);

    List<CustomerPackNumber> findToBeShipped(Map<String,Object> map);

    int countSelectAll(Map<String,Object> map);

    CustomerPackNumberVo findByPackOrder(String packOrder);

    CustomerPackNumber findByOrderNumber(String orderNumber);

    /**
     * 确认拆包删除对应的子包裹数据
     * @param packId
     */
    void deleteByPackId(int packId);

    /**
     * 批量插入
     * @param customerPackNumbers
     */
    void insertSelectiveList(@Param("customerPackNumbers") List<CustomerPackNumber> customerPackNumbers);

    CustomerPackNumber findById(int customerPackNumberId);

    void updatePackNumberList(@Param("customerPackNumbers") List<CustomerPackNumber> customerPackNumbers);

    void updateById(CustomerPackNumber customerPackNumber);

    void deleteById(int id);

    void insertList(@Param("customerPackNumberList") List<CustomerPackNumber> customerPackNumberList);

    List<CustomerPackNumberVo> selectByBusinessNumber(String businessNumber);
}