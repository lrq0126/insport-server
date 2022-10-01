package com.dwgj.mlxydedicatedline.mapper.sysDictDetail;

import com.dwgj.mlxydedicatedline.entity.SysDictDetail;
import com.dwgj.mlxydedicatedline.vo.sys.CountryCommercialAreaRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据字典明表(SysDictDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-23 14:36:46
 */
@Repository
public interface SysDictDetailDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDictDetail queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDictDetail> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysDictDetail 实例对象
     * @return 对象列表
     */
    List<SysDictDetail> queryAll(SysDictDetail sysDictDetail);

    /**
     * 新增数据
     *
     * @param sysDictDetail 实例对象
     * @return 影响行数
     */
    int insert(SysDictDetail sysDictDetail);

    /**
     * 修改数据
     *
     * @param sysDictDetail 实例对象
     * @return 影响行数
     */
    int update(SysDictDetail sysDictDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 根据字典类型查
     * @return
     */
    List<SysDictDetail> selectDictByType(Map<String, Object> map);

    /**
     * 根据 名称 查询数据
     * @param sddName
     * @return
     */
    SysDictDetail selectDictBySddName(@Param("sddName") String sddName);

    /**
     * 根据 编码 查询数据
     * @param sddCode
     * @return
     */
    SysDictDetail selectDictBySddCode(@Param("sddCode") String sddCode);





    /**
     * 根据 渠道id 查询 相关货值货币类型
     * @param routeId
     * @return
     */
    SysDictDetail selectDictByRouteId(@Param("routeId") Integer routeId);

    List<SysDictDetail> findCountrySimpleList();

    /**
     * 查看国家-仓库列表
     * @return
     */
    List<CountryCommercialAreaRespVo> getCountryCommercialArea();
}