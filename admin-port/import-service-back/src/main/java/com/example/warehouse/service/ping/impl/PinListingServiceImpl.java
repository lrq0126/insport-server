package com.example.warehouse.service.ping.impl;

import com.example.warehouse.common.PackUtils;
import com.example.warehouse.common.PdfUtils;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.CustomerPackReceiverAddress;
import com.example.warehouse.entity.ping.PingMember;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.customerPackReceiverAddress.CustomerPackReceiverAddressDao;
import com.example.warehouse.mapper.ping.PingMainMapper;
import com.example.warehouse.mapper.ping.PingMainOrderMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.ping.PinListingService;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customer.CustomerPackVo;
import com.example.warehouse.vo.detailedListVo.GoodsDetailedListVo;
import com.example.warehouse.vo.ping.responseVo.PingMainOrderRespVo;
import com.example.warehouse.vo.ping.responseVo.PingMainRespVo;
import com.example.warehouse.vo.ping.responseVo.PingPackRespVo;
import com.example.warehouse.vo.route.RouteVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.warehouse.enums.ResultStatus.*;

@Service
@Slf4j
public class PinListingServiceImpl implements PinListingService {

    @Autowired
    private PingMainOrderMapper pingMainOrderMapper;
    @Autowired
    private PingMainMapper pingMainMapper;


    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private CustomerPackReceiverAddressDao customerPackReceiverAddressDao;

    @Autowired
    private PackUtils packUtils;
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 上传文件路径
     */
    @Value("${upload.location}")
    private String UPLOAD_LOCAION;

    /**
     * 系统域名
     */
    @Value("${server.domain}")
    private String DOMAIN;

    @Autowired
    private PdfUtils pdfUtils;

    @Override
    public ResponseEntity<ResultModel> printPackTa(int pmoId) {
        String fileLocaltion = UPLOAD_LOCAION + "/template/pinOrder/";
        log.info("fileLocation:{}", fileLocaltion);
        File parent = new File(fileLocaltion);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        PingMainOrderRespVo pingMainOrderRespVo = pingMainOrderMapper.selectById(pmoId);

        if (pingMainOrderRespVo == null) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }

        CustomerPackVo customerPackVo = customerPackMapper.findByOrderNumber(pingMainOrderRespVo.getOrderNumber());

        //文件名
        String fileName = "DW_" + SequenceCode.gainSerialNo("order") + ".pdf";
        try {
            log.info("fileName:{}", fileName);

            Map<String, Object> resultMap = new HashMap<>();
            //String finalURl = "129.204.211.240/upload/" + fileName;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Map<String, Object> inMap = new HashMap<>();
            // 生成的pdf的    TODO 后续修改PDF的生成地址
            String outPath = fileLocaltion + fileName;
            CustomerPackReceiverAddress customerPackReceiverAddress = customerPackReceiverAddressDao.findByCustomerPackId(customerPackVo.getId());
            if (customerPackReceiverAddress != null) {
                RouteVo transportationRoute = customerPackVo.getRouteVo();
                inMap.put("addressee", customerPackReceiverAddress.getAddressee());
                inMap.put("address", "phone：" + customerPackReceiverAddress.getPhoneNumber() + "     " + customerPackReceiverAddress.getReceiverAddress());
                if (transportationRoute != null) {
                        inMap.put("country", transportationRoute.getCountry()
                                + "-" + transportationRoute.getRouteName()
                                + "-" + (transportationRoute.getTransportType() == 1 ? "空运" : "海运"));
                }
            }

            int number = customerPackVo.getPackNum();
            inMap.put("number", number);
            inMap.put("orderNumber", customerPackVo.getOrderNumber());
            inMap.put("datetime", sdf.format(new Date()));
            pdfUtils.pdfout(inMap, number, outPath, null);
            System.out.println("---------------->>>>>>>>>>>>phy_outPath:" + outPath);
            System.out.println("---------------->>>>>>>>>>>>ralation_outPath:" + outPath.substring(4));

            String finalURl = DOMAIN + outPath.substring(4);
            log.info("finalURL:{}", finalURl);
            resultMap.put("url", finalURl);

            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> printAllGoods(int pId) {
        PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(pId);

        List<PingMember> pingMemberList = pingMainRespVo.getPingMember();
        if(pingMemberList == null || pingMemberList.isEmpty()){
            return new ResponseEntity<>(ResultModel.error(PIN_MEMBER_NULL_ERROR), HttpStatus.OK);
        }

        try {
            List<Integer> goodsIdList = pingMemberList.stream().map(PingMember::getGoodsId).collect(Collectors.toList());
            List<GoodsVo> goodsVoList = goodsMapper.selectByIds(goodsIdList);

            //excel路径
            String fileLocation = UPLOAD_LOCAION + "/template/pinMainGoodsList/";
            //文件名
            String fileName = "GOODS_" + SequenceCode.gainSerialNo("goods") + ".xls";
            File parent = new File(fileLocation);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            File file = new File(fileLocation + fileName);
            if (!file.exists()) {
                parent.createNewFile();
            }

            // 获取要填入表格的内容
            List<GoodsDetailedListVo> goodsList = goodsMapper.selectByGoodsNos(goodsVoList.stream().map(GoodsVo::getGoodsNo).collect(Collectors.toList()));

            HSSFWorkbook wb = packUtils.printGoods(goodsList);
            //响应到客户端
            FileOutputStream fout = new FileOutputStream(fileLocation + fileName);
            wb.write(fout);
            fout.close();
            wb.close();
            Map<String, Object> resultMap = new HashMap<>();
            String finalLocation = DOMAIN + fileLocation.substring(4) + fileName;
            log.info("货物清单路径：{}", finalLocation);
            resultMap.put("url", finalLocation);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> printPingMain(int pid) {
        String fileLocaltion = UPLOAD_LOCAION + "/template/pinMain/";
        log.info("fileLocation:{}", fileLocaltion);
        File parent = new File(fileLocaltion);
        if (!parent.exists()) {
            parent.mkdirs();
        }

        PingMainRespVo pingMain = pingMainMapper.selectPingMainDetailedById(pid);

        if (pingMain == null) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        List<PingMainOrderRespVo> pingMainOrder = pingMain.getPingMainOrder();
        if(pingMainOrder == null || pingMainOrder.isEmpty()){
            return new ResponseEntity<>(ResultModel.error(PIN_ORDER_PACK_NULL), HttpStatus.OK);
        }
//        CustomerPackVo customerPackVo = customerPackMapper.findByOrderNumber(pingMainOrderRespVo.getOrderNumber());

        //文件名
        String fileName = "PFL_" + SequenceCode.gainSerialNo("pinMainOrder") + ".pdf";
        try {
            log.info("fileName:{}", fileName);

            Map<String, Object> resultMap = new HashMap<>();
            //String finalURl = "129.204.211.240/upload/" + fileName;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Map<String, Object> inMap = new HashMap<>();
            // 生成的pdf的    TODO 后续修改PDF的生成地址
            String outPath = fileLocaltion + fileName;
            CustomerPackReceiverAddress customerPackReceiverAddress = customerPackReceiverAddressDao.queryById(pingMain.getAddressId());
            if (customerPackReceiverAddress != null) {
                RouteVo transportationRoute = pingMain.getTransportationRoute();
                inMap.put("addressee", customerPackReceiverAddress.getAddressee());
                inMap.put("phone", customerPackReceiverAddress.getPhoneNumber());
                inMap.put("address", customerPackReceiverAddress.getReceiverAddress());
                if (transportationRoute != null) {
                    inMap.put("country", transportationRoute.getCountry()
                            + "-" + transportationRoute.getRouteName()
                            + "-" + (transportationRoute.getTransportType() == 1 ? "空运" : "海运"));
                }
            }

            int number = 0;
            List<String> loginNameList = new ArrayList<>();
            for (PingMainOrderRespVo pingMainOrderRespVo : pingMainOrder) {
                number += pingMainOrderRespVo.getPingPack().size();
                for (PingPackRespVo pingPackRespVo : pingMainOrderRespVo.getPingPack()) {
                    loginNameList.add(pingPackRespVo.getPackOrder());
                }

            }

            inMap.put("number", number);
            inMap.put("orderNumber", pingMain.getOrderNumber());
            inMap.put("datetime", sdf.format(new Date()));
            pdfUtils.pdfout(inMap, number, outPath, loginNameList);
            System.out.println("---------------->>>>>>>>>>>>phy_outPath:" + outPath);
            System.out.println("---------------->>>>>>>>>>>>ralation_outPath:" + outPath.substring(4));

            String finalURl = DOMAIN + outPath.substring(4);
            log.info("finalURL:{}", finalURl);
            resultMap.put("url", finalURl);

            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
