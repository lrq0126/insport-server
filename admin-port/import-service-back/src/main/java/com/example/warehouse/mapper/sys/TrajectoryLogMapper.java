package com.example.warehouse.mapper.sys;

import com.example.warehouse.entity.sys.TrajectoryLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrajectoryLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TrajectoryLog record);

    int insertSelective(TrajectoryLog record);

    TrajectoryLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TrajectoryLog record);

    int updateByPrimaryKey(TrajectoryLog record);

    int batchInsert(List<TrajectoryLog> list);

    void batchUpdateByOrderNumber(@Param("trajectoryLogList") List<TrajectoryLog> trajectoryLogList);

    int checkOrderNumberLog(String orderNumber);

    TrajectoryLog getSingleTrackLog(String orderNumber);

    int checkTrackLog(String orderNumber);

    int checkTrackLogIsIn(String orderNumber);

    int updateByOrderNumber(TrajectoryLog record);

    int checkTrackIsInside(String orderNumber);

    List<TrajectoryLog> getTrajectoryLogList(Map<String,Object> map);

    int countTrajectoryLogList(Map<String,Object> map);

    List<TrajectoryLog> getTrajectoryLogListByOrderNumber(@Param("orderNumbers") List<String> orderNumbers);

    List<TrajectoryLog> selectLogListByOrderNumber(@Param("orderNumberList") List<String> orderNumberList);

}