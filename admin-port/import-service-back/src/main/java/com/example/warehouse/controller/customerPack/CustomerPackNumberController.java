package com.example.warehouse.controller.customerPack;

import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.CustomerPackNumber;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.CustomerPackNumberService;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customer.CustomerPackNumberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.ERROR;
import static com.example.warehouse.enums.ResultStatus.SYS_ERROR;

/**
 * @author lrq
 * 2019年11月30日17:29:21
 */
@RestController
@RequestMapping("/customerPackNumber")
@Slf4j
public class CustomerPackNumberController {

    @Autowired
    private CustomerPackNumberService customerPackNumberService;

    /**
     * 根据流水号查询 包裹内的打包的包裹,详细内容
     *
     * @param businessNumber
     * @return
     */
    @PostMapping("/find/sonPackList")
    public ResponseEntity<ResultModel> findSonPackListByBusinessNumber(@RequestParam("businessNumber") String businessNumber) {
        return customerPackNumberService.findSonPackListByBusinessNumber(businessNumber);
    }


    /**
     * @param paramMap
     *      customerName
     * @return
     */
    @PostMapping("/find/tobeShipped/list")
    public ResponseEntity<ResultModel> findToBeShipped(@RequestParam Map<String, Object> paramMap){
        try {
            Map<String, Object> resultMap = new HashMap<>();
            PageData data = (PageData) PageHelp.initPage(paramMap).get("page");
            int count = customerPackNumberService.countSelectAll(paramMap);
            if (count < 1) {
                data.setTotal(0);
                resultMap.put("page", data);
                return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
            } else {
                data.setTotal(count);
                resultMap.put("page", data);
                resultMap.put("customerPack", customerPackNumberService.findToBeShipped(paramMap));
                return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/find/customerPackNumber/list")
    public ResponseEntity<ResultModel> findCustomerPackNumber(@RequestParam Map<String, Object> paramMap){
        Map<String, Object> resultMap = new HashMap<>();
        CustomerPackNumberVo customerPackNumber =  customerPackNumberService.findByCustomerPackNumberPackOrder(paramMap);
        resultMap.put("customerPackNumber",customerPackNumber);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @PostMapping("/find/goods/list/byPackOrder")
    public ResponseEntity<ResultModel> findGoodsByPackOrder(@RequestParam Map<String, Object> paramMap){
        try{
            Map<String, Object> resultMap = new HashMap<>();
            List<GoodsVo> goodsList =  customerPackNumberService.findGoodsByPackOrder(paramMap);
            resultMap.put("goods",goodsList);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }

    }
}
