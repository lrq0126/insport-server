package com.example.warehouse.controller.sys;

import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.StatisticService;
import com.example.warehouse.vo.sys.SysStatisticalReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页统计
 */
@Controller
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    /**
     * 首页统计--所有
     * @return
     */
    @PostMapping("/getAll")
    @ResponseBody
    public ResponseEntity<ResultModel> getAll(SysStatisticalReqVo sysStatisticalReqVo){
        return statisticService.getAll(sysStatisticalReqVo);
    }

    /**
     * 查询图表内容
     * @param sysStatisticalReqVo
     * @return
     */
    @PostMapping("/getChart")
    @ResponseBody
    public ResponseEntity<ResultModel> getChart(SysStatisticalReqVo sysStatisticalReqVo){
        return statisticService.getChart(sysStatisticalReqVo);
    }

    /**
     * 首页统计--客户
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public ResponseEntity<ResultModel> get(){
        int customerNum = statisticService.countGoodsInOfCustomerNum();

        Map<String,Object> result = new HashMap<>();
        result.put("customerNum",customerNum);
        return new ResponseEntity<>(ResultModel.ok(result), HttpStatus.OK);
    }

    @PostMapping("/getCustomerList")
    @ResponseBody
    public ResponseEntity<PageResultModel> getCustomerList(SysStatisticalReqVo sysStatisticalReqVo){
        return statisticService.getCustomerList(sysStatisticalReqVo);
    }

    @PostMapping("/issueCoupons")
    @ResponseBody
    public void issueCoupons(SysStatisticalReqVo sysStatisticalReqVo){
         statisticService.issueCoupons(sysStatisticalReqVo);
    }

    @PostMapping("/getStatisticGoodsDetail")
    @ResponseBody
    public ResponseEntity<PageResultModel> getStatisticGoodsDetail(@RequestParam Map<String, Object> requestMap){
        return statisticService.getStatisticGoodsDetail(requestMap);
    }

    @PostMapping("/getStatisticCustomerPackDetail")
    @ResponseBody
    public ResponseEntity<PageResultModel> getStatisticCustomerPackDetail(@RequestParam Map<String, Object> requestMap){
        return statisticService.getStatisticCustomerPackDetail(requestMap);
    }


    @PostMapping("/getStatisticCustomerDetail")
    @ResponseBody
    public ResponseEntity<PageResultModel> getStatisticCustomerDetail(@RequestParam Map<String, Object> requestMap){
        return statisticService.getStatisticCustomerDetail(requestMap);
    }

    @PostMapping("/getStatisticTopUpDetail")
    @ResponseBody
    public ResponseEntity<PageResultModel> getStatisticTopUpDetail(@RequestParam Map<String, Object> requestMap){
        return statisticService.getStatisticTopUpDetail(requestMap);
    }
}
