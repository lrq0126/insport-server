package com.example.warehouse.service.sys;

import com.example.warehouse.entity.sys.SysStatistical;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.sys.SysStatisticalReqVo;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface StatisticService {

    /**
     * 统计入库的客户人数有多少
     * @return
     */
    int countGoodsInOfCustomerNum();

    /**
     * 获取所有统计数据
     * @return
     */
    ResponseEntity<ResultModel> getAll(SysStatisticalReqVo sysStatisticalReqVo);

    ResponseEntity<ResultModel> getChart(SysStatisticalReqVo sysStatisticalReqVo);

    ResponseEntity<ResultModel> everyDayAdd();

    ResponseEntity<PageResultModel> getCustomerList(SysStatisticalReqVo sysStatisticalReqVo);

    void issueCoupons(SysStatisticalReqVo sysStatisticalReqVo);

    ResponseEntity<PageResultModel> getStatisticGoodsDetail(Map<String, Object> requestMap);

    ResponseEntity<PageResultModel> getStatisticCustomerPackDetail(Map<String, Object> requestMap);

    ResponseEntity<PageResultModel> getStatisticCustomerDetail(Map<String, Object> requestMap);

    ResponseEntity<PageResultModel> getStatisticTopUpDetail(Map<String, Object> requestMap);
}
