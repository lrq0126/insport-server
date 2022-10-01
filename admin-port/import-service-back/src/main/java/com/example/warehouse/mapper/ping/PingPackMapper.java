package com.example.warehouse.mapper.ping;

import com.example.warehouse.entity.ping.PingPack;
import com.example.warehouse.vo.ping.responseVo.PingPackRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PingPackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PingPack record);

    int insertSelective(PingPack record);

    PingPack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingPack record);

    int updateByPrimaryKey(PingPack record);

    int insertList(@Param("pingPackList") List<PingPack> pingPackList);

    /**
     * 根据pinMainId和客户id查询相关客户的信息
     * @param pmId 拼团订单id
     * @param customerId 拼团成员id
     * @return
     */
    List<PingPackRespVo> selectByPmIdAndCustomerId(@Param("pmId") int pmId, @Param("customerId") int customerId);

    void deleteByOrderId(@Param("pmId") int pmId, @Param("customerId") int customerId);
}