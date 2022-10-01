package com.example.warehouse.mapper.ping;

import com.example.warehouse.entity.ping.PingMainAudit;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 拼团审核表(PingMainAudit)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/8/30 18:20
*/
@Repository
public interface PingMainAuditMapper extends MyBatisBaseDao<PingMainAudit, Integer> {

    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(PingMainAudit record);

    @Override
    int insertSelective(PingMainAudit record);

    @Override
    PingMainAudit selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(PingMainAudit record);

    @Override
    int updateByPrimaryKey(PingMainAudit record);

    List<PingMainAudit> selectByPid(int pid);

}