package com.example.warehouse.mapper.ping;

import com.example.warehouse.entity.ping.PingMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PingMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PingMember record);

    int insertSelective(PingMember record);

    PingMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingMember record);

    int updateByPrimaryKey(PingMember record);

    List<PingMember> selectPingMemberByPid(int pid);

    List<PingMember> selectPingMemberByPidList(@Param("pidList") List<Integer> pidList);

    List<PingMember> selectPingMemberByPidAndCustomerId(@Param("pid") int pid, @Param("customerId") int customerId);

    int updateList(@Param("pingMemberList") List<PingMember> pingMemberList);

    int removeList(@Param("pingMemberList") List<PingMember> pingMemberList);

    int removeMember(PingMember removeMember);

    void insertList(@Param("pingMemberList") List<PingMember> pingMemberList);
}