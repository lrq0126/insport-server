package com.dwgj.mlxydedicatedline.mapper.customerPack;

import com.dwgj.mlxydedicatedline.entity.customerPack.CustomerPackNumber;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import com.dwgj.mlxydedicatedline.vo.pack.CustomerPackNumberVo;
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

    List<CustomerPackNumber> findToBeShipped(Map<String, Object> map);

    int countSelectAll(Map<String, Object> map);

    CustomerPackNumberVo findByPackOrder(String packOrder);

    CustomerPackNumber findById(int customerPackNumberId);


}