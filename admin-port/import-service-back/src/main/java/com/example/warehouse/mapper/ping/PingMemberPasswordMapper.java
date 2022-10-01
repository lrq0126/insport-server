package com.example.warehouse.mapper.ping;

import com.example.warehouse.entity.ping.PingMemberPassword;
import org.springframework.stereotype.Repository;

@Repository
public interface PingMemberPasswordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PingMemberPassword record);

    int insertSelective(PingMemberPassword record);

    PingMemberPassword selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingMemberPassword record);

    int updateByPrimaryKey(PingMemberPassword record);
}