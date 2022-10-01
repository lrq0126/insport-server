package com.example.warehouse.service.sys;

import com.example.warehouse.entity.sys.SysNotice;
import com.example.warehouse.model.ResultModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SysNoticeService {
    /**
     * 查询所有通知
     * @param map
     * @return
     */
    List<SysNotice> selectNoticeList(Map<String,Object> map);
    int countSelectNoticeList(Map<String,Object> map);

    /**
     *  * 客户订单变更通知
     * @param customerId 客户id
     * @param number    订单号或者货物号
     * @param goodsName 货物名称
     * @param type      填中文：包裹、订单
     * @param content   填中文：已入库、已发货、待打包、已打包、待确认、待出库
     * @return
     */
    int updateNoticeToCustomer(String customerId,String number,String goodsName,String type,String content);

    ResponseEntity<ResultModel> getNoticeDetail(int id);

    /**
     * 发布通知
     * @param map
     * @return
     */
    ResponseEntity<ResultModel> publishNotice(SysNotice sysNotice);

    /**
     * 删除通知
     * @param noticeId
     * @return
     */
    int deleteNotice(String noticeId);
}
