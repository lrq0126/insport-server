package com.example.warehouse.service.commerical;

import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.commerical.CommericalInvoice;

import java.util.List;
import java.util.Map;

/**
 * (CommericalInvoice)表服务接口
 *
 * @author makejava
 * @since 2020-07-29 19:10:40
 */
public interface CommericalInvoiceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CommericalInvoice queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CommericalInvoice> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param commericalInvoice 实例对象
     * @return 实例对象
     */
    CommericalInvoice insert(CommericalInvoice commericalInvoice);

    /**
     * 修改数据
     *
     * @param commericalInvoice 实例对象
     * @return 实例对象
     */
    CommericalInvoice update(CommericalInvoice commericalInvoice);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    CommericalInvoice selectByCustomerPackId(Integer customerPackId);

    Map<String, Object> createInvoice(Map<String, Object> requestMap);

    /**
     * 保存发票
     * @param requestMap
     */
    Map<String, Object> saveInvoice(Map<String,Object> requestMap);
}