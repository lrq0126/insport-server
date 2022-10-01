package com.example.warehouse.service.commerical.impl;

import com.example.warehouse.mapper.commerical.CommericalInvoiceItemsDao;
import com.example.warehouse.entity.commerical.CommericalInvoiceItems;
import com.example.warehouse.service.commerical.CommericalInvoiceItemsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CommericalInvoiceItems)表服务实现类
 *
 * @author makejava
 * @since 2020-07-29 19:10:44
 */
@Service("commericalInvoiceItemsService")
public class CommericalInvoiceItemsServiceImpl implements CommericalInvoiceItemsService {
    @Resource
    private CommericalInvoiceItemsDao commericalInvoiceItemsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    @Override
    public CommericalInvoiceItems queryById(Long id) {
        return this.commericalInvoiceItemsDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<CommericalInvoiceItems> queryAllByLimit(int offset, int limit) {
        return this.commericalInvoiceItemsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param commericalInvoiceItems 实例对象
     * @return 实例对象
     */
    @Override
    public CommericalInvoiceItems insert(CommericalInvoiceItems commericalInvoiceItems) {
        this.commericalInvoiceItemsDao.insert(commericalInvoiceItems);
        return commericalInvoiceItems;
    }

    /**
     * 修改数据
     *
     * @param commericalInvoiceItems 实例对象
     * @return 实例对象
     */
    @Override
    public CommericalInvoiceItems update(CommericalInvoiceItems commericalInvoiceItems) {
        this.commericalInvoiceItemsDao.update(commericalInvoiceItems);
        return this.queryById(commericalInvoiceItems.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.commericalInvoiceItemsDao.deleteById(id) > 0;
    }
}