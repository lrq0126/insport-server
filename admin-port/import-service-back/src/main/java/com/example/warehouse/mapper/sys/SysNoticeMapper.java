package com.example.warehouse.mapper.sys;

import com.example.warehouse.entity.sys.SysNotice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysNoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysNotice record);

    int insertSelective(SysNotice record);

    SysNotice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysNotice record);

    int updateByPrimaryKey(SysNotice record);

    List<SysNotice> selectNoticeList(Map<String,Object> map);
    int countSelectNoticeList(Map<String,Object> map);

    SysNotice selectNoticeDetail(int id);
}