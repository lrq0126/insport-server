package com.dwgj.mlxydedicatedline.service.timeLimitActivity;

import com.dwgj.mlxydedicatedline.entity.TimeLimitActivity;
import com.dwgj.mlxydedicatedline.vo.timeLimitActivityVo.ActivityRouteVO;

import java.util.List;
import java.util.Map;

/**
 * (TimeLimitActivity)表服务接口
 *
 * @author makejava
 * @since 2020-04-05 13:53:36
 */
public interface TimeLimitActivityService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TimeLimitActivity queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TimeLimitActivity> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param timeLimitActivity 实例对象
     * @return
     */
    int insert(TimeLimitActivity timeLimitActivity, String addressId);

    /**
     * 修改数据
     *
     * @param timeLimitActivity 实例对象
     * @return 实例对象
     */
    TimeLimitActivity update(TimeLimitActivity timeLimitActivity);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据客户id查询是否有记录
     * @param map
     * @return
     */
    TimeLimitActivity selectByCustomerId(Map<String, Object> map);

    /**
     * 获取活动路线
     * @return
     */
    List<ActivityRouteVO> getRoute();
}