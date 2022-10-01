package com.example.warehouse.service.detailedList;

import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.detailedListVo.PrintOrderReqVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * 2020年03月17日13:20
 *
 * @author lrq
 */
public interface DetailedListService {

    /**
     * 货物清单打印接口
     * @param map
     * @return
     */
    ResponseEntity<ResultModel> printGoodsXls(Map map);

    /**
     * 包裹清单打印接口
     *
     * @param map
     * @throws Exception
     */
    int printCustomerPack(String businessNumber,String localtion) throws Exception;

    /**
     * 面单打印接口
     * @param paramMap
     * @return
     * @throws Exception
     */
    Map<String, Object> printOrder(Map<String,Object> paramMap) throws Exception;

    ResponseEntity<ResultModel> orderNumberPrint(PrintOrderReqVo printOrderReqVo);
}
