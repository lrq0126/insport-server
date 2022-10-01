package com.example.warehouse.controller.detailedList;

import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.log.SysLog;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.detailedList.DetailedListService;
import com.example.warehouse.vo.detailedListVo.PrintOrderReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.example.warehouse.common.PdfUtils.getSystemPath;
import static com.example.warehouse.enums.ResultStatus.*;

/**
 * 2020年03月17日12:52
 * 打印清单接口
 *
 * @author lrq
 */
@RestController
@RequestMapping("/detailedList")
@Slf4j
public class DetailedListController {

    /**
     * 上传文件路径
     */
    @Value("${upload.location}")
    private String UPLOAD_LOCAION;

    @Autowired
    private DetailedListService detailedListService;

    /**
     * 系统域名
     */
    @Value("${server.domain}")
    private String DOMAIN;

    /**
     * 货物导出清单
     * 参数：goodsNos   货物编码集合
     * @param paramMap
     * @return
     */
    @PostMapping("/print/goods")
    public ResponseEntity<ResultModel> printGoods(@RequestParam Map<String, Object> paramMap) {
       return detailedListService.printGoodsXls(paramMap);
    }

    /**
     * 包裹导出清单
     * 参数： 流水号  businessNumber
     * @param businessNumber
     * @return
     */
    @PostMapping("/print/customerPack")
    @SysLog(value = "打印包裹拣货清单[业务号]",type = SysLog.Type.ADD,contentId = "#businessNumber")
    public ResponseEntity<ResultModel> printCustomerPack(@RequestParam("businessNumber") String businessNumber) {
        try {
            String fileLocaltion = UPLOAD_LOCAION + "/template/detailesList/";
            File parent = new File(fileLocaltion);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            //文件名
            String fileName = "BQ_" + businessNumber + "_" + SequenceCode.gainSerialNo("pack") + ".pdf";
            String localtion = fileLocaltion + fileName;
            int i = detailedListService.printCustomerPack(businessNumber,localtion);
            if(i == -1){
                return new ResponseEntity<>(ResultModel.error(GOODS_LOCATION_NULL_ERROR), HttpStatus.OK);
            }
            Map<String, Object> resultMap = new HashMap<>();
            String finalLocation = DOMAIN + fileLocaltion.substring(4) + fileName;
            log.info("包裹清单路径：{}",finalLocation);
            resultMap.put("url",finalLocation);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    /**
     * 打印面单
     *
     * @param params
     * @return
     */
    @PostMapping("/print/order")
    @SysLog(value = "打印面单[业务号]",type = SysLog.Type.ADD,contentId = "Map.params.businessNumber")
    public ResponseEntity<ResultModel> printOrder(@RequestParam Map<String, Object> params) {
        String fileLocaltion = UPLOAD_LOCAION + "/template/order/";
        log.info("fileLocation:{}",fileLocaltion);
        File parent = new File(fileLocaltion);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        //文件名
        String fileName = "DW_" + SequenceCode.gainSerialNo("order") + ".pdf";
        try {
            log.info("fileName:{}",fileName);
            params.put("url",fileLocaltion + fileName);
            params.put("location",fileLocaltion);
            Map<String,Object> orderMap = detailedListService.printOrder(params);
            if(orderMap.get("lastPdfName") == null){
                return new ResponseEntity<>(ResultModel.error(PACK_ERROR), HttpStatus.OK);
            }
            Map<String, Object> resultMap = new HashMap<>();
            //String finalURl = "129.204.211.240/upload/" + fileName;
            String finalURl = DOMAIN + orderMap.get("lastPdfName");
            log.info("finalURL:{}",finalURl);
            resultMap.put("url",finalURl);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    /**
     * 打印面单
     *
     * @param printOrderReqVo
     * @return
     */
    @PostMapping("/orderPrint")
    @SysLog(value = "打印订单[订单号]",type = SysLog.Type.ADD,contentId = "printOrderReqVo.orderNumber")
    public ResponseEntity<ResultModel> orderPrint(PrintOrderReqVo printOrderReqVo) {
        return detailedListService.orderNumberPrint(printOrderReqVo);
    };


}
