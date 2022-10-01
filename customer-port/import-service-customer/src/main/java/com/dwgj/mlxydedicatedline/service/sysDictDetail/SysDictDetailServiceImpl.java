package com.dwgj.mlxydedicatedline.service.sysDictDetail;


import com.dwgj.mlxydedicatedline.entity.SysDictDetail;
import com.dwgj.mlxydedicatedline.mapper.sysDictDetail.SysDictDetailDao;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 数据字典明表(SysDictDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-03-23 14:36:48
 */
@Service("sysDictDetailService")
public class SysDictDetailServiceImpl implements SysDictDetailService {
    @Resource
    private SysDictDetailDao sysDictDetailDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysDictDetail queryById(Integer id) {
        return this.sysDictDetailDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysDictDetail> queryAllByLimit(int offset, int limit) {
        return this.sysDictDetailDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysDictDetail 实例对象
     * @return 实例对象
     */
    @Override
    public SysDictDetail insert(SysDictDetail sysDictDetail) {
        this.sysDictDetailDao.insert(sysDictDetail);
        return sysDictDetail;
    }

    /**
     * 修改数据
     *
     * @param sysDictDetail 实例对象
     * @return 实例对象
     */
    @Override
    public SysDictDetail update(SysDictDetail sysDictDetail) {
        this.sysDictDetailDao.update(sysDictDetail);
        return this.queryById(sysDictDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysDictDetailDao.deleteById(id) > 0;
    }

    @Override
    public List<SysDictDetail> selectCountry(Map<String, Object> map) {

        return sysDictDetailDao.selectDictByType(map);
    }

    @Override
    public ResponseEntity<ResultModel> findCountrySimpleList() {
        List<SysDictDetail> sysDictDetailList = sysDictDetailDao.findCountrySimpleList();
        return new ResponseEntity<>(ResultModel.ok(sysDictDetailList), HttpStatus.OK);
    }


}