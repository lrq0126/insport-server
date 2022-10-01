package com.example.warehouse.mapper.sys;

import com.example.warehouse.entity.sys.Trajectory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrajectoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Trajectory record);

    int insertSelective(Trajectory record);

    Trajectory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Trajectory record);

    int updateByPrimaryKey(Trajectory record);

    int batchInsert(List<Trajectory> list);

    Trajectory getSingleTrack(String orderNumber);

    List<Trajectory> getTrackListByOrderNumber(@Param("orderNumber")String orderNumber,@Param("desc")int desc);

    /**
     * 检查是否有轨迹详情在内部系统
     * @param orderNumber
     * @return
     */
    int checkTrackHaveDatas(String orderNumber);

}