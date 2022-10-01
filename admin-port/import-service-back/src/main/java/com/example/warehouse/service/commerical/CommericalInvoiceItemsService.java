package com.example.warehouse.service.commerical;

import com.example.warehouse.entity.commerical.CommericalInvoiceItems;

import java.util.List;

/**
 * (CommericalInvoiceItems)表服务接口
 *
 * @author makejava
 * @since 2020-07-29 19:10:44
 */
public interface CommericalInvoiceItemsService {

    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    CommericalInvoiceItems queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CommericalInvoiceItems> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param commericalInvoiceItems 实例对象
     * @return 实例对象
     */
    CommericalInvoiceItems insert(CommericalInvoiceItems commericalInvoiceItems);

    /**
     * 修改数据
     *
     * @param commericalInvoiceItems 实例对象
     * @return 实例对象
     */
    CommericalInvoiceItems update(CommericalInvoiceItems commericalInvoiceItems);

    /**
     * 通过主键删除数据
     *
     * @param 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}