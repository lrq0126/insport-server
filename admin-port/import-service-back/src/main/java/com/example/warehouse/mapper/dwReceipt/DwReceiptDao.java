package com.example.warehouse.mapper.dwReceipt;

import com.example.warehouse.entity.DwReceipt;
import com.example.warehouse.entity.DwReciptReject;
import com.example.warehouse.vo.receipt.DwReceiptVo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * (DwReceipt)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-22 13:31:53
 */
public interface DwReceiptDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DwReceipt queryById(Integer id);

    DwReceiptVo findById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<DwReceipt> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param map
     * @return 对象列表
     */
    List<DwReceiptVo> queryAll(Map<String, Object> map);

    /**
     * 查询总数
     * @param map
     * @return
     */
    Integer queryAllCount(Map<String, Object> map);

    /**
     * 新增数据
     *
     * @param dwReceipt 实例对象
     * @return 影响行数
     */
    int insert(DwReceipt dwReceipt);

    /**
     * 修改数据
     *
     * @param dwReceipt 实例对象
     * @return 影响行数
     */
    int update(DwReceipt dwReceipt);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 批量插入签收信息
     * @param dwReceiptList
     * @return
     */
    int insertList(@Param("dwReceiptList") List<DwReceipt> dwReceiptList);

    /**
     * 根据id集合获取数据
     * @param idList
     * @return
     */
    List<DwReceiptVo> findByIdList(@Param("idList")List<String> idList);

    /**
     * 插入一条拒收消息
     * @param reject
     * @return
     */
    int insertReject(DwReciptReject reject);

    /**
     * 根据deliveryOrderNo查询是否已经签收
     * @param deliveryOrderNo
     * @return
     */
    int queryByDeliveryOrderNo(String deliveryOrderNo);

    /**
     * 更新数据
     * @param reject
     * @return
     */
    int updateReject(DwReciptReject reject);

    /**
     * 根据deliveryOrderNo查询是否拒收
     * @param deliveryOrderNo
     * @return
     */
    int checkDeliveryOrderNo(String deliveryOrderNo);

    int countRejectList(Map<String, Object> map);

    List<DwReciptReject> selectRejectList(Map<String, Object> map);
}