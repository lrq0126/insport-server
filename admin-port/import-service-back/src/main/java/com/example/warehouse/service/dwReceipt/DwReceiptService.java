package com.example.warehouse.service.dwReceipt;

import com.example.warehouse.entity.DwReceipt;
import com.example.warehouse.entity.DwReciptReject;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.receipt.DwReceiptVo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * (DwReceipt)表服务接口
 *
 * @author makejava
 * @since 2020-05-22 13:31:56
 */
public interface DwReceiptService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DwReceiptVo queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<DwReceipt> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param dwReceipt 实例对象
     * @return 实例对象
     */
    DwReceipt insert(DwReceipt dwReceipt);

    /**
     * 修改数据
     *
     * @param dwReceipt 实例对象
     * @return
     */
    int update(DwReceipt dwReceipt);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询总数
     * @param map
     * @return
     */
    int queryAllCount(Map<String,Object> map);

    /**
     * 查询实体
     * @param map
     * @return
     */
    ResponseEntity<ResultModel> queryAll(Map<String,Object> map);

    /**
     * 查询总数
     * @param map
     * @return
     */
    int queryAllRejectCount(Map<String,Object> map);

    /**
     * 查询实体
     * @param map
     * @return
     */
    ResponseEntity<ResultModel> queryAllReject(Map<String,Object> map);

    /**
     * 插入拒收单号
     * @param reject
     * @return
     */
    ResponseEntity<ResultModel> insertReject(DwReciptReject reject);

    /**
     * 更新拒收单号
     * @param reject
     * @return
     */
    int updateReject(DwReciptReject reject);

    /**
     * 删除数据
     * @param reject
     * @return
     */
    int deleteReject(DwReciptReject reject);

    /**
     * 批量插入签收快递信息
     * @param map
     * @return
     */
    ResponseEntity<ResultModel> insertList(Map<String,Object> map);

    /**
     * 导出签收列表
     * @param map
     */
    void export(Map<String,Object> map, HttpServletResponse response, HttpServletRequest request);
}