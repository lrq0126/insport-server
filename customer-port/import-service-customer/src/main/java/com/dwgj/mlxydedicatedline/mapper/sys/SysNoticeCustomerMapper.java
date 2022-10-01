package com.dwgj.mlxydedicatedline.mapper.sys;

import com.dwgj.mlxydedicatedline.entity.sys.SysNoticeCustomer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysNoticeCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysNoticeCustomer record);

    int insertSelective(SysNoticeCustomer record);

    SysNoticeCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysNoticeCustomer record);

    int updateByPrimaryKey(SysNoticeCustomer record);

    int checkIsRead(Map<String,Object> map);

    /**
     * 查询未读信息
     * @param customerId
     * @return
     */
    List<SysNoticeCustomer> selectNotReadNoticeMessageByCustomerId(Integer customerId);

    List<SysNoticeCustomer> selectNoticeMessageByCustomerId(Integer id);

    void insertList(@Param("noticeCustomerList") List<SysNoticeCustomer> noticeCustomerList);

    void updateByCustomerIdAndNoticeIds(@Param("customerId") int customerId, @Param("updateNoticeIdList") List<Integer> updateNoticeIdList);
}
