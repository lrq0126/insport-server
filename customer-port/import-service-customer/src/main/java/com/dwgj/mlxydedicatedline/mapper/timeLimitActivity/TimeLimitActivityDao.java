package com.dwgj.mlxydedicatedline.mapper.timeLimitActivity;

import com.dwgj.mlxydedicatedline.entity.TimeLimitActivity;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * (TimeLimitActivity)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-05 13:53:35
 */
public interface TimeLimitActivityDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TimeLimitActivity queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TimeLimitActivity> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param timeLimitActivity 实例对象
     * @return 对象列表
     */
    List<TimeLimitActivity> queryAll(TimeLimitActivity timeLimitActivity);

    /**
     * 新增数据
     *
     * @param timeLimitActivity 实例对象
     * @return 影响行数
     */
    int insert(TimeLimitActivity timeLimitActivity);

    /**
     * 修改数据
     *
     * @param timeLimitActivity 实例对象
     * @return 影响行数
     */
    int update(TimeLimitActivity timeLimitActivity);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据客户id查询记录
     * @param map
     * @return
     */
    TimeLimitActivity selectByCustomerId(Map<String, Object> map);

    /**
     * 查询是否有数据
     * @param goodsNos
     * @return
     */
    int selectByGoodsNos(@Param("goodsNos") List<String> goodsNos);
}