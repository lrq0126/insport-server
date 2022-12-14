package com.example.warehouse.service.customerPack;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.Customer;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.customerPack.OrderEvaluateMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.packVo.OrderEvaluateReqVo;
import com.example.warehouse.vo.packVo.OrderEvaluateRespVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

import static com.example.warehouse.enums.ResultStatus.*;

@Service
public class OrderEvaluateServiceImpl implements OrderEvaluateService {
    @Value("${server.domain}")
    private String DOMAIN;
    @Value("${upload.location}")
    private String LOCATION;

    @Autowired
    private OrderEvaluateMapper orderEvaluateMapper;
    @Autowired
    private SendMessageServer sendMessageServer;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public ResponseEntity<PageResultModel> getEvaluateList(OrderEvaluateReqVo orderEvaluateReqVo) {
        PageData pageData = PageHelp.editPage(String.valueOf(orderEvaluateReqVo.getPageNumber()), String.valueOf(orderEvaluateReqVo.getPageSize()));
        int count = orderEvaluateMapper.selectCount(orderEvaluateReqVo);
        pageData.setTotal(count);
        if(count > 0){
            orderEvaluateReqVo.setPageNumber(pageData.getPageNumber());
            List<OrderEvaluateRespVo> orderEvaluateList = orderEvaluateMapper.getEvaluateList(orderEvaluateReqVo);
            return new ResponseEntity<>(PageResultModel.ok(orderEvaluateList, pageData), HttpStatus.OK);
        }

        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> exportEvaluate(OrderEvaluateReqVo orderEvaluateReqVo) {
        List<OrderEvaluateRespVo> evaluateList = orderEvaluateMapper.getEvaluateListNotPage(orderEvaluateReqVo);
        // ?????????????????????
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        // ??????????????????sheet
        HSSFSheet sheet = hssfWorkbook.createSheet("????????????");
        // ?????????0?????????
        HSSFRow row = sheet.createRow(0);
        // ?????????????????????
        HSSFCellStyle style = hssfWorkbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("??????");

        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("?????????");

        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(6);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(7);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(8);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(9);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(10);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(11);
        cell.setCellStyle(style);
        cell.setCellValue("??????");

        int rowNumber = 1;
        HSSFRow rowBody;
        for (OrderEvaluateRespVo orderEvaluateRespVo : evaluateList) {
            rowBody = sheet.createRow(rowNumber);
//            row.createCell(rowNumber);
            rowBody.createCell(0).setCellValue(rowNumber);

            rowBody.createCell(1).setCellValue(orderEvaluateRespVo.getOrderNumber());

            rowBody.createCell(2).setCellValue(orderEvaluateRespVo.getCustomerName());
            rowBody.createCell(3).setCellValue(orderEvaluateRespVo.getOrderTime());
            rowBody.createCell(4).setCellValue(orderEvaluateRespVo.getCreateTime());
            rowBody.createCell(5).setCellValue(orderEvaluateRespVo.getRouteName());
            rowBody.createCell(6).setCellValue(orderEvaluateRespVo.getPackingSpeed()+"???");
            rowBody.createCell(7).setCellValue(orderEvaluateRespVo.getPackingQuality()+"???");
            rowBody.createCell(8).setCellValue(orderEvaluateRespVo.getLogisticsSpeed()+"???");
            rowBody.createCell(9).setCellValue(orderEvaluateRespVo.getCustomerServerAttitude()+"???");
            rowBody.createCell(10).setCellValue(orderEvaluateRespVo.getComprehensiveServer()+"???");
            String remarks;
            if(orderEvaluateRespVo.getRemarks() == null || orderEvaluateRespVo.getRemarks().isEmpty()
                    || "null".equals(orderEvaluateRespVo.getRemarks())){
                remarks = "?????????";
            }else {
                remarks = orderEvaluateRespVo.getRemarks();
            }
            rowBody.createCell(11).setCellValue(remarks);

            // ????????????????????????
            rowNumber++;
        }
        try {
            String filePath = LOCATION + "/orderEvaluate";
            String outPath = filePath + "/eva_" + DateUtil.FormatDate(new Date(), "yyyyMMddHHmmss")+".xls";
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(outPath);
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();

            outPath = outPath.substring(4);
            String url = DOMAIN + outPath;
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("url", url);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<ResultModel> payReturn(String orderMessage) {
        JSONObject jsonObject = JSON.parseObject(orderMessage);
        if(!jsonObject.containsKey("payReturnMessage")){
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        if(!jsonObject.containsKey("customerId") || !jsonObject.containsKey("orderNumber")){
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        String payReturnMessage = jsonObject.getString("payReturnMessage");
        String remarks = jsonObject.getString("remarks");
        String customerId = jsonObject.getString("customerId");
        Customer customer = customerMapper.findById(Integer.parseInt(customerId));
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(CUSTOMER_NULL_ERROR), HttpStatus.OK);
        }
        String orderNumber = jsonObject.getString("orderNumber");

        sendMessageServer.afterSaleReturn(orderNumber, customer, payReturnMessage, remarks);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

}
