package com.dwgj.mlxydedicatedline.mapper.ping;


import com.dwgj.mlxydedicatedline.entity.ping.PingMainOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PingMainOrderMapper {
    int insert(PingMainOrder record);

    int insertSelective(PingMainOrder record);

    PingMainOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingMainOrder record);

    int updateByPrimaryKey(PingMainOrder record);

    int deleteByPrimaryKey(Integer id);

    int removeByPrimaryKey(Integer id);
    /**
     * 批量插入成员订单
     * @param list
     */
    void insertOrderList(List<PingMainOrder> list);

    List<PingMainOrder> selectOrderByPid(int pid);

    void updateListByOrderNumber(@Param("pingMainOrderList") List<PingMainOrder> pingMainOrderList);

    PingMainOrder selectOrderByOrderNumber(String orderNumber);

    List<PingMainOrder> selectOrderByOrderNumberList(@Param("orderNumberList") List<String> orderNumberList);
	// 查询已打包好的订单(包括已付款)
    int selectPackedOrderByPid(Integer pId);
}