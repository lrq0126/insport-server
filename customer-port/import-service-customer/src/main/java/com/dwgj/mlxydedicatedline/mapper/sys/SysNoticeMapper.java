package com.dwgj.mlxydedicatedline.mapper.sys;

import com.dwgj.mlxydedicatedline.entity.sys.SysNotice;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 查某个客户的所有消息
     * @param map
     * @return
     */
    List<SysNotice> selectNoticeListByCustomerId(Map<String,Object> map);

    /**
     * 查某个客户的所有消息
     * @param param
     * @return
     */
    List<SysNotice> selectAllNoticeListByCustomerId(Map<String,Object> param);

    int countSelectNoticeListByCustomerId(Map<String,Object> map);

    // 分页查询

    List<SysNotice> selectNoticeListByGroupId(@Param("groupIdList")List<Integer> groupIdList);
    // 分页查询END


    List<SysNotice> selectNotReadNoticeListByCustomerId(@Param("customerId") int customerId,
                                                        @Param("isReadNoticeIdList")List<Integer> isReadNoticeIdList);

    List<SysNotice> selectNotReadNoticeListByGroupId(@Param("groupIdList") List<Integer> groupIdList,
                                                        @Param("isReadNoticeIdList")List<Integer> isReadNoticeIdList);


}