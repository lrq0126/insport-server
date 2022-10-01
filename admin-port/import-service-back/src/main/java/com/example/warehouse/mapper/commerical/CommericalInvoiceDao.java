package com.example.warehouse.mapper.commerical;

import com.example.warehouse.entity.commerical.CommericalInvoice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CommericalInvoice)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-29 19:10:40
 */
public interface CommericalInvoiceDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CommericalInvoice queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CommericalInvoice> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param commericalInvoice 实例对象
     * @return 对象列表
     */
    List<CommericalInvoice> queryAll(CommericalInvoice commericalInvoice);

    /**
     * 新增数据
     *
     * @param commericalInvoice 实例对象
     * @return 影响行数
     */
    int insert(CommericalInvoice commericalInvoice);

    /**
     * 修改数据
     *
     * @param commericalInvoice 实例对象
     * @return 影响行数
     */
    int update(CommericalInvoice commericalInvoice);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据包裹id查询并赋值到发票实体中
     * @param customerPackId
     * @return
     */
    CommericalInvoice selectByPackId(@Param("customerPackId") Integer customerPackId);

    CommericalInvoice selectOldInvoiceByPackId(Integer customerPackId);
}