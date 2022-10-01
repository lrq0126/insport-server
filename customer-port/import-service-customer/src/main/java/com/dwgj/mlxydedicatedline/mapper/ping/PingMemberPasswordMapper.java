package com.dwgj.mlxydedicatedline.mapper.ping;

import com.dwgj.mlxydedicatedline.entity.ping.PingMemberPassword;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PingMemberPasswordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PingMemberPassword record);

    int insertSelective(PingMemberPassword record);

    PingMemberPassword selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingMemberPassword record);

    int updateByPrimaryKey(PingMemberPassword record);

    int checkIsIn(PingMemberPassword record);

    PingMemberPassword selectByPingId(@Param("pId") Integer pId);
}