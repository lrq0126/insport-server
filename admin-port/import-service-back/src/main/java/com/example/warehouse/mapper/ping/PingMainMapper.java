package com.example.warehouse.mapper.ping;

import com.example.warehouse.entity.ping.PingMain;
import com.example.warehouse.vo.ping.requestVo.PingRequestVo;
import com.example.warehouse.vo.ping.responseVo.PingMainRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PingMainMapper {

    /**
     * 条件查询总数量
     * @param pingRequestVo
     * @return
     */
    int selectCount(PingRequestVo pingRequestVo);

    List<PingMainRespVo> getPingMainList(PingRequestVo pingRequestVo);

    // 查询已删除的订单
    List<PingMainRespVo> selectDeletedList(PingRequestVo pingRequestVo);

    int deleteByPrimaryKey(Integer id);

    int insert(PingMain record);

    int insertSelective(PingMain record);

    PingMain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingMain record);

    int updateByPrimaryKey(PingMain record);

    PingMain selectById(int pId);

    List<PingMain> selectByIdList(@Param("idList") List<Integer> idList);

    PingMainRespVo selectPingMainDetailedById(int pid);

    int deleteByIds(@Param("idList") List<Integer> idList, @Param("updateId") int updateId);

    int updateTop(@Param("id") int id, @Param("isTop") int isTop);

    void updateByShipped(int id);
}