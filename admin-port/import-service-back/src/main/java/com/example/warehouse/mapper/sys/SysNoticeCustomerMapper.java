package com.example.warehouse.mapper.sys;

import com.example.warehouse.entity.sys.SysNoticeCustomer;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface SysNoticeCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysNoticeCustomer record);

    int insertSelective(SysNoticeCustomer record);

    SysNoticeCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysNoticeCustomer record);

    int updateByPrimaryKey(SysNoticeCustomer record);

    int checkIsRead(Map<String, Object> map);

    int deleteCustomerNotice(String noticeId);
}
