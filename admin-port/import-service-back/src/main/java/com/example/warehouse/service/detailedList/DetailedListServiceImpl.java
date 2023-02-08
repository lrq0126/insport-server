package com.example.warehouse.service.detailedList;

import com.example.warehouse.common.PackUtils;
import com.example.warehouse.common.PdfUtils;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.*;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.SysDictDetailMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.customerPackReceiverAddress.CustomerPackReceiverAddressDao;
import com.example.warehouse.mapper.goods2pack.Goods2packMapper;
import com.example.warehouse.mapper.transportationRoute.TransportationRouteMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.customerPack.CustomerPackVo;
import com.example.warehouse.vo.detailedListVo.GoodsDetailedListVo;
import com.example.warehouse.vo.detailedListVo.PrintOrderReqVo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.example.warehouse.common.PdfUtils.getPdfChineseFont;
import static com.example.warehouse.enums.ResultStatus.ERROR;

/**
 * 2020年03月17日13:20
 *
 * @author lrq
 */
@Service
@Slf4j
public class DetailedListServiceImpl implements DetailedListService {
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


    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private CustomerPackReceiverAddressDao customerPackReceiverAddressDao;
    @Autowired
    private TransportationRouteMapper transportationRouteMapper;
    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;

    @Autowired
    private Goods2packMapper goods2packMapper;

    @Autowired
    private PdfUtils pdfUtils;
    @Autowired
    private PackUtils packUtils;
    /**
     * 公众号名称
     */
    @Value("${wechat.name}")
    private String WECHAT_NAME;


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    @Override
    public ResponseEntity<ResultModel> printGoodsXls(Map map) {

        try {
            //excel路径
            String fileLocaltion = UPLOAD_LOCAION + "/template/goodsList/";
            //文件名
            String fileName = "GOODS_" + SequenceCode.gainSerialNo("goods") + ".xls";
            File parent = new File(fileLocaltion);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            File file = new File(fileLocaltion + fileName);
            if (!file.exists()) {
                parent.createNewFile();
            }
            // 获取要填入表格的内容
            List<String> goodsNos = Arrays.asList(map.get("goodsNos").toString().split(","));
            List<GoodsDetailedListVo> goodsList = goodsMapper.selectByGoodsNos(goodsNos);

            HSSFWorkbook wb = packUtils.printGoods(goodsList);
            //响应到客户端
            FileOutputStream fout = new FileOutputStream(fileLocaltion + fileName);
            wb.write(fout);
            fout.close();
            wb.close();
            Map<String, Object> resultMap = new HashMap<>();
            String finalLocation = DOMAIN + fileLocaltion.substring(4) + fileName;
            log.info("货物清单路径：{}", finalLocation);
            resultMap.put("url", finalLocation);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
    }

    @Override
    public int printCustomerPack(String businessNumber,String localtion) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //输出模板
        FileOutputStream fos = new FileOutputStream(localtion);
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, fos);
        writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
        //设置A4
        document.setPageSize(PageSize.A4);
        document.open();
        float[] widths = {100, 180, 100, 180};
        PdfPTable table = new PdfPTable(widths);
        table.setLockedWidth(true);
        table.setTotalWidth(550);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        //标题
        String[] title = {"序号", "快递单号/唛头", "仓库地址", "包装类型", "件数", "重量/尺寸", "快递公司", "备注"};
        // 获取要填入表格的内容
//        String businessNumber = map.get("businessNumber").toString();
        CustomerPackVo customerPack = customerPackMapper.findByBusinessNumber(businessNumber);
        List<Goods2pack> goods2packList = goods2packMapper.findByPackId(customerPack.getId());

        List<Integer> goodsIds = new ArrayList<>();
        goods2packList.forEach(goods2pack -> goodsIds.add(goods2pack.getGoodsId()));

        //        List<GoodsDetailedListVo> goodsList = goodsMapper.selectByGoodsNos(goodsNos);
        List<GoodsDetailedListVo> goodsList = goodsMapper.selectByGoodsIds(goodsIds);
        double allWeight = goodsList.stream().mapToDouble(goods -> Double.valueOf(goods.getWeight())).sum();
        TransportationRoute transportationRoute = transportationRouteMapper.selectByPrimaryKey(customerPack.getTransportationRouteId());

        CustomerPack pack = new CustomerPack();
        pack.setId(customerPack.getId());
        pack.setVersion(customerPack.getVersion());
        pack.setBusinessNumber(customerPack.getBusinessNumber());
        pack.setPrintTime(sdf.format(new Date()));
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        pack.setPrintOper( user == null ? "管理员" : user.getUsername());
        pack.setPrintCount(customerPack.getPrintCount() + 1);

        // 标头
        String[][] content = new String[7][title.length];
        for (int i = 0; i < 1; i++) {
            //content[i] = new String[];
            content[0][0] = "包裹清单(" + WECHAT_NAME + ")";

            content[1][0] = "客户：";
            content[1][1] = customerPack.getCustomerName() + "(" + customerPack.getLoginName() + ")";
            content[1][2] = "渠道名称：";
            content[1][3] = customerPack.getRouteName();

            content[2][0] = "包裹数量：";
            content[2][1] = String.valueOf(goodsList.size());
            if (1 == transportationRoute.getTransportType()) {
                content[2][2] = "渠道类型：";
                content[2][3] = "空运(" + transportationRoute.getDestination() + ")";
            } else if (2 == transportationRoute.getTransportType()) {
                content[2][2] = "渠道类型：";
                content[2][3] = "海运(" + transportationRoute.getDestination() + ")";
            }

            content[3][0] = "下单时间：";
            content[3][1] = customerPack.getCreateTime();
            content[3][2] = "备注：";
            content[3][3] = customerPack.getRemarks();

            content[4][0] = "打印信息(第" + pack.getPrintCount() + "次):";
            if ( StringUtils.isNotBlank(customerPack.getPrintTime()) ){
                content[4][1] = "本次打印时间：" + pack.getPrintTime() + " ；上次打印时间：" + customerPack.getPrintTime()
                    + "\n本次打印操作：" + pack.getPrintOper() + " ；上次打印操作：" + customerPack.getPrintOper();
            }else {
                content[4][1] = "本次是最新打印：" + pack.getPrintTime() + " 打印操作：" + pack.getPrintOper();
            }


            content[4][2] = "总重量：";
            content[4][3] = String.valueOf(allWeight);

            content[5][0] = "打包注意事项：";
            content[5][1] = transportationRoute.getPackAttention();

            content[6][0] = "货物信息";
        }

        // 货物内容
        String[][] goodsContent = new String[goodsList.size() + 1][8];
        for (int i = 0; i < goodsList.size() + 1; i++) {
            if (0 == i) {
                goodsContent[i][0] = title[0];
                goodsContent[i][1] = title[1];
                goodsContent[i][2] = title[2];
                goodsContent[i][3] = title[3];
                goodsContent[i][4] = title[4];
                goodsContent[i][5] = title[5];
                goodsContent[i][6] = title[6];
                goodsContent[i][7] = title[7];
            } else {
                GoodsDetailedListVo goods = goodsList.get(i - 1);
                goodsContent[i][0] = String.valueOf(i);

                goodsContent[i][1] = goods.getDeliveryOrderNo();
                if(StringUtils.isEmpty(goods.getLocation()) ||
                        goods.getLocation().equals(",") || goods.getLocation().equals("，")){
                    return -1;
                } else {
                    if(goods.getLocation().contains(",")){
                        goodsContent[i][2] = goods.getLocation().split(",")[0] + "区" + goods.getLocation().split(",")[1] + "排";
                    } else if(goods.getLocation().contains("，")){
                        goodsContent[i][2] = goods.getLocation().split("，")[0] + "区" + goods.getLocation().split("，")[1] + "排";
                    }

                }
                // 货物包装类型判断
                if (null != goods.getPackageType()) {
                    goodsContent[i][3] = goods.getPackageType();
                }else {
                    goodsContent[i][3] = " ";
                }
                goodsContent[i][4] = String.valueOf(goods.getPackageNum());

                // 重量
                goodsContent[i][5] = goods.getWeight() + "(" + goods.getLength() + " * " + goods.getWidth() + " * " + goods.getHeight() + ")";

                // 快递公司
                goodsContent[i][6] = goods.getDeliveryCompany();

                // 备注
                goodsContent[i][7] = goods.getRemark();


            }
        }


        //创建内容
        Font font = getPdfChineseFont();
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                //表格的单元格
                PdfPCell pdfCell = new PdfPCell();
                // 第一列 和 第三列
                if (j == 0 || j == 2) {
                    pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                }

                // 第一行
                if (i == 0) {
                    //                    pdfCell.setRowspan(1);
                    pdfCell.setColspan(4);
                    pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                }

                // 第三行
                if(i == 2){
                    if (j == 0 || j == 2) {
                        pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    }else {
                        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    }
                }

                // 第四行
                if(i == 3){
                    if (j== 0 || j == 2) {
                        pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    }else {
                        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    }
                }

                // 第五行
                if (i == 4) {
                    if (j== 1 || j == 3) {
                        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    }else {
                        pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    }

                }

                //  第六行
                if (i == 5) {
                    if (j== 0 || j == 2) {
                        pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    }else {
                        pdfCell.setColspan(3);
                        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    }
                }

                //  第七行
                if (i == 6) {
                    //                    pdfCell.setRowspan(1);
                    pdfCell.setColspan(4);
                    pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                }

                font.setSize(12);
                Paragraph paragraph = new Paragraph(content[i][j],  font);
                pdfCell.setPhrase(paragraph);
                table.addCell(pdfCell);

            }
        }

        //插入子表格
        PdfPTable suTtable = new PdfPTable(new float[]{30, 100, 95, 60, 45, 80, 60, 110});
        suTtable.setLockedWidth(true);
        suTtable.setTotalWidth(550);
        suTtable.setHorizontalAlignment(Element.ALIGN_CENTER);
        //创建内容
        for (int i = 0; i < goodsContent.length; i++) {
            for (int j = 0; j < goodsContent[i].length; j++) {
                PdfPCell subPdfCell = new PdfPCell();
                subPdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                if (i == 0) {
                    subPdfCell.setBackgroundColor(new BaseColor(211, 211, 211));

                }
                subPdfCell.setPhrase(new Paragraph(goodsContent[i][j], getPdfChineseFont()));
                suTtable.addCell(subPdfCell);
            }
        }


        //pdf文档中加入table
        document.add(table);
        document.addTitle("详细包裹信息");
        document.add(suTtable);
        document.close();
        fos.close();

        customerPackMapper.updatePrint(pack);
        // 执行完成 返回1；
        return 1;
    }

    @Override
    public Map<String, Object> printOrder(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
//        Map<String, Object> inMap = new HashMap<>();
        // 图片的地址  TODO 后续修改图片的生成地址
        //        String path = (String) paramMap.get("location") + "img";
        // 生成的pdf的    TODO 后续修改PDF的生成地址
        String outPath = (String) paramMap.get("url");
        //        generateFile(path);

        CustomerPackVo customerPackVo = customerPackMapper.findByBusinessNumber(paramMap.get("businessNumber").toString());
        if(customerPackVo.getOrderNumber() == null){
            return new HashMap<>();
        }

        print(customerPackVo, outPath);

        System.out.println("---------------->>>>>>>>>>>>phy_outPath:"+outPath);
        System.out.println("---------------->>>>>>>>>>>>ralation_outPath:"+outPath.substring(4));
        resultMap.put("lastPdfName", outPath.substring(4));

        return resultMap;
    }

    @Override
    public ResponseEntity<ResultModel> orderNumberPrint(PrintOrderReqVo printOrderReqVo) {

        String fileLocaltion = UPLOAD_LOCAION + "/template/sysOrder/";
        log.info("fileLocation:{}",fileLocaltion);
        File parent = new File(fileLocaltion);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        //文件名
        String fileName = "FL_" + SequenceCode.gainSerialNo("order") + ".pdf";
        try {
            log.info("fileName:{}",fileName);
            String outPath = fileLocaltion + fileName;

            Map<String, Object> resultMap = new HashMap<>();
            if(printOrderReqVo.getNumber() > 300){
                return new ResponseEntity<>(ResultModel.error(ERROR, "只能打印300张以内的面单"), HttpStatus.OK);
            }
            if(StringUtils.isEmpty(printOrderReqVo.getOrderNumber())){
                return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
            }
            Map<String, Object> inMap = new HashMap<>();

            inMap.put("orderNumber", printOrderReqVo.getOrderNumber());
            inMap.put("number", printOrderReqVo.getNumber());

            inMap.put("country", printOrderReqVo.getCountry());
            inMap.put("addressee", printOrderReqVo.getAddressee());
            inMap.put("phone", printOrderReqVo.getPhoneNumber());
            inMap.put("address", printOrderReqVo.getAddress());

            inMap.put("datetime",sdf.format(new Date()));

            pdfUtils.pdfout(inMap, printOrderReqVo.getNumber(), outPath, null);

            System.out.println("---------------->>>>>>>>>>>>phy_outPath:"+outPath);
            System.out.println("---------------->>>>>>>>>>>>ralation_outPath:"+outPath.substring(4));

            log.info("finalURL:{}",DOMAIN + outPath.substring(4));
            resultMap.put("url",DOMAIN + outPath.substring(4));
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    private void print(CustomerPackVo customerPackVo, String outPath) throws IOException, DocumentException {
        Map<String, Object> inMap = new HashMap<>();

        CustomerPackReceiverAddress customerPackReceiverAddress = customerPackReceiverAddressDao.findByCustomerPackId(customerPackVo.getId());
        if(customerPackReceiverAddress != null ){
            TransportationRoute transportationRoute = customerPackVo.getRouteVo();
            inMap.put("addressee",customerPackReceiverAddress.getAddressee());
            inMap.put("phone", customerPackReceiverAddress.getPhoneNumber());
            String address = customerPackReceiverAddress.getReceiverAddress();
            if(customerPackReceiverAddress.getCode() != null && !customerPackReceiverAddress.getCode().isEmpty()){
                address =  address + "；code: " + customerPackReceiverAddress.getCode();
            }
            inMap.put("address", address);
            if(transportationRoute != null ){
                SysDictDetail sysDictDetail = sysDictDetailMapper.selectByPrimaryKey(transportationRoute.getCountryId());
                if(sysDictDetail != null){
                    inMap.put("country",sysDictDetail.getSddName()
                            + "-" + transportationRoute.getRouteName()
                            + "-" + (transportationRoute.getTransportType() == 1 ? "空运" : "海运"));
                }
            }
        }
        int number = customerPackVo.getPackNum();
        inMap.put("number", number);
        inMap.put("orderNumber", customerPackVo.getOrderNumber());
        inMap.put("datetime",sdf.format(new Date()));
        pdfUtils.pdfout(inMap, number, outPath, null);
    }


    /**
     * 创建文件夹 有则不创建
     *
     * @param filePath
     */
    public void generateFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("创建文件夹");

            file.mkdirs();
        } else {
            System.out.println("文件夹已存在");
            return;
        }
    }

    public void deleteFiles(File file) {

        //判断文件是否存在
        if (file.exists()) {

            //判断是否是文件
            if (file.isFile()) {
                System.out.println(file + "---》》  文件删除");
                file.delete();

                //否则如果它是一个目录
            } else if (file.isDirectory()) {
                System.out.println(file + "---》》  文件夹删除");

                //声明目录下所有的文件 files[];
                File[] files = file.listFiles();

                //遍历目录下所有的文件
                for (int i = 0; i < files.length; i++) {

                    //把每个文件 用这个方法进行迭代
                    this.deleteFiles(files[i]);
                }
            }

        } else {
            System.out.println("所删除的文件不存在！" + '\n');
        }
    }

}
