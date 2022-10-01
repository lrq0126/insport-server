package com.example.warehouse.mapper.ping;

import com.example.warehouse.entity.ping.PingMainOrder;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.ping.responseVo.PingMainOrderRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* (PingMainOrder)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/8/25 16:45
*/
@Repository
public interface PingMainOrderMapper extends MyBatisBaseDao<PingMainOrder, Integer> {

    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(PingMainOrder record);

    @Override
    int insertSelective(PingMainOrder record);

    @Override
    PingMainOrder selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(PingMainOrder record);

    @Override
    int updateByPrimaryKey(PingMainOrder record);


    List<PingMainOrder> selectPingMainOrderByPid(int pid);

    int insertList(@Param("pingMainOrderList")List<PingMainOrder> pingMainOrderList);

    int updateList(@Param("pingMainOrderList")List<PingMainOrder> pingMainOrderList);

    void updateListByOrderNumber(@Param("pingMainOrderList") List<PingMainOrder> pingMainOrderList);

    int removeList(@Param("pingMainOrderList")List<PingMainOrder> removeMainOrderList);

    int removeOrder(PingMainOrder pingMainOrder);

    PingMainOrderRespVo selectById(int id);


    PingMainOrder selectByOrderNumber(String orderNumber);

    void updateShippedByOrderNumber(@Param("orderNumberList") List<String> orderNumberList);
}