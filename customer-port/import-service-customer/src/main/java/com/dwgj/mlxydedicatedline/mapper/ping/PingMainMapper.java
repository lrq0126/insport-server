package com.dwgj.mlxydedicatedline.mapper.ping;

import com.dwgj.mlxydedicatedline.entity.ping.PingMain;
import com.dwgj.mlxydedicatedline.vo.pin.PingMainRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PingMainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PingMain record);

    int insertSelective(PingMain record);

    PingMain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingMain record);

    int updateByPrimaryKey(PingMain record);

    PingMain selectByOrderNumber(@Param("orderNumber")String orderNumber,@Param("id")int id);

    PingMain getByOrderNumber(String orderNumber);

    PingMain selectById(int id);

    List<PingMain> selectByIdList(@Param("pinMainIdList") List<Integer> pinMainIdList);

    PingMainRespVo selectVoById(int id);

    /**
     * 自己参加过的拼团列表（有包裹的才算）
     */
    List<PingMain> getTakePartInList(Map<String,Object> map);
    int countTakePartInList(Map<String,Object> map);

    /**
     * 自己发起的拼团列表
     */
    List<PingMain> getOwnPinList(Map<String,Object> map);
    int countOwnPinList(Map<String,Object> map);

    /**
     * 主页的拼团列表
     */
    List<PingMain> getPinList(Map<String,Object> map);
    int countPinList(Map<String,Object> map);

    /**
     * 获取主页的置顶拼团列表数据
     * @return
     */
    List<PingMain> getHomePinList();

    /**
     * 处理过期未成团的拼团数据
     * @return
     */
    int updateIsCutOffTimePin();

    /**
     * 获取过期未成团的数据
     * @return
     */
    List<PingMain> selectIsCutOffTimePinList();

}