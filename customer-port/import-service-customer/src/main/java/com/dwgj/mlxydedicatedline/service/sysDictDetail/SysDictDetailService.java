package com.dwgj.mlxydedicatedline.service.sysDictDetail;

import com.dwgj.mlxydedicatedline.entity.SysDictDetail;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据字典明表(SysDictDetail)表服务接口
 *
 * @author makejava
 * @since 2020-03-23 14:36:48
 */
public interface SysDictDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDictDetail queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDictDetail> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysDictDetail 实例对象
     * @return 实例对象
     */
    SysDictDetail insert(SysDictDetail sysDictDetail);

    /**
     * 修改数据
     *
     * @param sysDictDetail 实例对象
     * @return 实例对象
     */
    SysDictDetail update(SysDictDetail sysDictDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 获取国家列表
     * @param map
     * @return
     */
    List<SysDictDetail> selectCountry(Map<String, Object> map);

    ResponseEntity<ResultModel> findCountrySimpleList();


}