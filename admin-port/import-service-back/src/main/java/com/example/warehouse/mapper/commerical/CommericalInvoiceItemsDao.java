package com.example.warehouse.mapper.commerical;

import com.example.warehouse.entity.commerical.CommericalInvoiceItems;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CommericalInvoiceItems)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-29 19:10:44
 */
public interface CommericalInvoiceItemsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    CommericalInvoiceItems queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CommericalInvoiceItems> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param commericalInvoiceItems 实例对象
     * @return 对象列表
     */
    List<CommericalInvoiceItems> queryAll(CommericalInvoiceItems commericalInvoiceItems);

    /**
     * 新增数据
     *
     * @param commericalInvoiceItems 实例对象
     * @return 影响行数
     */
    int insert(CommericalInvoiceItems commericalInvoiceItems);

    /**
     * 修改数据
     *
     * @param commericalInvoiceItems 实例对象
     * @return 影响行数
     */
    int update(CommericalInvoiceItems commericalInvoiceItems);

    /**
     * 通过主键删除数据
     *
     * @param id
     * @return 影响行数
     */
    int deleteById(Long id);

    int insertList(@Param("commericalInvoiceItemsList") List<CommericalInvoiceItems> commericalInvoiceItemsList);

    List<CommericalInvoiceItems> selectByParentId(Long pId);

    /**
     * 刪除旧的货物明细数据
     * @param commericalId
     */
    int deleteByInvoiceId(@Param("commericalId") Long commericalId);
}