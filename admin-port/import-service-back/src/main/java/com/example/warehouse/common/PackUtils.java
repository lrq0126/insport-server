package com.example.warehouse.common;

import com.example.warehouse.entity.*;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.customerPack.CustomerPackPriceDetail;
import com.example.warehouse.entity.ping.PingMainOrder;
import com.example.warehouse.entity.ping.PingPack;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customer.CustomerPackNumberVo;
import com.example.warehouse.vo.detailedListVo.GoodsDetailedListVo;
import com.example.warehouse.vo.packVo.PackSonVo;
import com.example.warehouse.vo.route.RouteVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.*;

@Configuration
public class PackUtils {

    @Autowired
    private CalculationUtils calculationUtils;

    // 重量和体积的赋予
    public void weightAndVolGive(PackSonVo packSonVo, RouteVo routeVo, RoutePrice routePrice) {
        if (routeVo.getRouteType() == 1) {
            // 如果 体积为空，则赋予重量体积
            if (packSonVo.getActualVol() == null || packSonVo.getActualVol() == 0) {
                packSonVo.setActualVol(packSonVo.getActualWeight() * routeVo.getVolConversion() / 1000000);
            }
            //如果 重量为空，则赋予体积重量
            if (packSonVo.getActualWeight() == null || packSonVo.getActualWeight() == 0) {
                packSonVo.setActualWeight(packSonVo.getActualVol() / routeVo.getVolConversion() * 1000000);
            }
        } else {
            // 如果 体积为空，则赋予重量体积
            if (packSonVo.getActualVol() == null || packSonVo.getActualVol() == 0) {
                packSonVo.setActualVol(packSonVo.getActualWeight() / routeVo.getVolConversion());
            }
            //如果 重量为空，则赋予体积重量
            if (packSonVo.getActualWeight() == null || packSonVo.getActualWeight() == 0) {
                packSonVo.setActualWeight(packSonVo.getActualVol() * routeVo.getVolConversion());
            }
        }

    }

    /**
     * 包裹实际价格计算
     *
     * @param routeVo           路线内容
     * @param actualPackWeight  包裹实际重量
     * @param actualPackVol     包裹实际体积
     * @param isSensitive       是否敏感货物
     * @param settlementWeight  结算重量
     * @param settlementVol     结算体积
     * @param isCalculateVolWeight     是否计算体积
     * @return
     */
    public Map<String, Object> actualPriceCalculate(RouteVo routeVo, double actualPackWeight, double actualPackVol,
                                                    boolean isSensitive, double settlementWeight, double settlementVol,
                                                    BigDecimal packActualPrice, boolean isCalculateVolWeight) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> utilsMap;
        String KVremarks = "";
        String remarks = "";
        String errorMessage = "";
        if (routeVo.getRouteType() == 1) {

            utilsMap = calculationUtils.countKGMoney(routeVo.getRoutePrices(), routeVo, actualPackWeight, actualPackVol, false, isCalculateVolWeight);
            if (utilsMap == null) {
                errorMessage = "实际重量或者体积重量超出路线限制，请检查箱子信息";
                resultMap.put("errorMessage", errorMessage);
                return resultMap;
            }
            if (utilsMap.containsKey("finalPrice")) {
                packActualPrice = (BigDecimal) utilsMap.get("finalPrice");
            }
            if (utilsMap.containsKey("finalKG")) {
                settlementWeight = (Double) utilsMap.get("finalKG");
            }
            // 体积过大，转体积重
            if (utilsMap.containsKey("KG_VOL")) {
                settlementWeight = (Double) utilsMap.get("KG_VOL");
                KVremarks = "打包后，实际重量与体积重对比，体积重较大，取体积重: " + utilsMap.get("KG_VOL") + "KG";
            }

        } else {
            utilsMap = calculationUtils.countVOLMoney(routeVo.getRoutePrices(), routeVo, actualPackVol, actualPackWeight, false);
            if (utilsMap == null) {
                errorMessage = "箱子体积超出路线限制，请检查箱子信息";
                resultMap.put("errorMessage", errorMessage);
                return resultMap;
            }
            if (utilsMap.containsKey("finalPrice")) {
                packActualPrice = (BigDecimal) utilsMap.get("finalPrice");
            }
            if (utilsMap.containsKey("finalVOL")) {
                settlementVol = (Double) utilsMap.get("finalVOL");
            }
            // 体积过大，转体积重
            if (utilsMap.containsKey("KG_VOL")) {
                settlementVol = (Double) utilsMap.get("KG_VOL");
                KVremarks = "打包后，实际体积与重量体积对比，重量体积较大，取重量体积：" + utilsMap.get("KG_VOL") + "m³";
            }
        }

        if (isSensitive) {

            BigDecimal sensitivePrice;
            // routeVo.getRouteType() == 1 时，渠道单位是KG，按票加收敏感货费用
            // routeVo.getRouteType() == 2 时，渠道单位是m³，按体积单位加收敏感货费用 例如：4.03m³ 就按 5m³ * 敏感货物费用
            if(routeVo.getRouteType() == 1){
                sensitivePrice = routeVo.getSensitivePrice() == null ? new BigDecimal(0) : routeVo.getSensitivePrice();
            }else {
                BigDecimal bd = new BigDecimal(settlementVol);
                bd = bd.setScale(0, BigDecimal.ROUND_UP);
                sensitivePrice = routeVo.getSensitivePrice() == null ? new BigDecimal(0) : routeVo.getSensitivePrice().multiply(bd) ;
            }
            resultMap.put("sensitivePrice", sensitivePrice);
//            packActualPrice = packActualPrice.add(sensitivePrice);
        }

        if(utilsMap.containsKey("ForkliftFee")){
            remarks = (remarks == null || remarks.isEmpty()) ? "需叉车费:" + utilsMap.get("ForkliftFee") + "元" : remarks + "；需叉车费:" + utilsMap.get("ForkliftFee") + "元" ;

            resultMap.put("ForkliftFee", utilsMap.get("ForkliftFee"));
        }

        if(!KVremarks.equals("") && !KVremarks.isEmpty()){
            resultMap.put("KVremarks", KVremarks);
        }

        resultMap.put("settlementWeight", settlementWeight);
        resultMap.put("settlementVol", settlementVol);
        resultMap.put("packActualPrice", packActualPrice);
        resultMap.put("remarks", remarks);

        return resultMap;
    }

    public void extraPriceCalculation(CustomerPack customerPack, CustomerPackPriceDetail packPriceDetail) {

        BigDecimal newActualPrice = customerPack.getActualPrice();
        BigDecimal discount = packPriceDetail.getDiscount();
        BigDecimal incidental = packPriceDetail.getIncidental();
        // 优惠价格  目前只能由管理员做修改
        if (discount != null && discount.compareTo(new BigDecimal(0)) > 0) {
            // 余额取2位小数
            discount = discount.setScale(2, BigDecimal.ROUND_UP);
            customerPack.setDiscount(discount);
            newActualPrice = newActualPrice.subtract(discount);
            if (customerPack.getRemarks() != null && !"".equals(customerPack.getRemarks())) {
                if (customerPack.getRemarks().indexOf("免的优惠") != -1) {
                    customerPack.setRemarks(customerPack.getRemarks());
                } else {
                    customerPack.setRemarks(customerPack.getRemarks() + "；减免的优惠金额：" + discount + "元");
                }
            } else {
                customerPack.setRemarks("减免的优惠金额：" + discount + "元");
            }
        } else {
            // 如果页面未填写优惠金额，则为0
            discount = new BigDecimal(0);
            customerPack.setDiscount(discount);
        }

        // 附加费
        if (incidental != null && incidental.compareTo(new BigDecimal(0)) > 0) {
                // 取2位小数
                incidental = incidental.setScale(2, BigDecimal.ROUND_UP);
                customerPack.setIncidental(incidental);
                newActualPrice = newActualPrice.add(incidental);
                if (customerPack.getRemarks() != null && customerPack.getRemarks() != "") {
                    if (customerPack.getRemarks().indexOf("包含附加费") != -1) {
                        customerPack.setRemarks(customerPack.getRemarks());
                    } else {
                        customerPack.setRemarks(customerPack.getRemarks() + "；包含附加费：" + incidental + "元");
                    }
                } else {
                    customerPack.setRemarks("包含附加费：" + incidental + "元");
                }

        }else {
            // 如果页面没填写附加费，则按照原来的价格
            incidental = new BigDecimal(0);
            customerPack.setIncidental(incidental);
        }

        // 关税
        if(packPriceDetail.getTariffsPrice() != null && packPriceDetail.getTariffsPrice().compareTo(new BigDecimal(0)) > 0){
            newActualPrice = newActualPrice.add(packPriceDetail.getTariffsPrice());
        }

        // 保险
        if(packPriceDetail.getInsurancePrice() != null && packPriceDetail.getInsurancePrice().compareTo(new BigDecimal(0)) > 0){
            newActualPrice = newActualPrice.add(packPriceDetail.getInsurancePrice());
        }

        // 敏感货物
        if(packPriceDetail.getSensitivePrice() != null && packPriceDetail.getSensitivePrice().compareTo(new BigDecimal(0)) > 0){
            newActualPrice = newActualPrice.add(packPriceDetail.getSensitivePrice());
        }

        // 叉车费
        if(packPriceDetail.getForkliftFee() != null && packPriceDetail.getForkliftFee().compareTo(new BigDecimal(0)) > 0){
            newActualPrice = newActualPrice.add(packPriceDetail.getForkliftFee());
        }



        newActualPrice = newActualPrice.setScale(2, BigDecimal.ROUND_UP);
        customerPack.setActualPrice(newActualPrice);
    }

    /**
     * 敏感货物备注
     * @param routeVo
     * @param settlementVol
     * @return
     */
    public String sensitiveRemark(RouteVo routeVo, double settlementVol, int sonPackNumber){
        String sensitiveRemark;
        BigDecimal sensitivePrice;
        if(routeVo.getRouteType() == 1){
            sensitivePrice = routeVo.getSensitivePrice() == null ? new BigDecimal(0) : routeVo.getSensitivePrice();

            if(routeVo.getIsPerTicketPack() == 1){
                sensitivePrice = sensitivePrice.multiply(new BigDecimal(sonPackNumber));
                sensitiveRemark = "货物中包含敏感货物，需要添加对应票数的敏感货物的额外费用: ￥" + sensitivePrice + "元；";
            }else {
                sensitiveRemark = "货物中包含敏感货物，需要添加敏感货物的额外费用: ￥" + sensitivePrice + "元；";
            }

        }else {
            BigDecimal SettlementVol = new BigDecimal(settlementVol).setScale(0, BigDecimal.ROUND_UP);

            sensitivePrice = routeVo.getSensitivePrice() == null ? new BigDecimal(0) :
                    routeVo.getSensitivePrice().multiply(SettlementVol).setScale(2, BigDecimal.ROUND_UP);

            sensitiveRemark = "货物中包含敏感货物，每个方需要添加敏感货物的额外费用，共: ￥" + sensitivePrice  + "元；";
        }
        return sensitiveRemark;
    }

    /**
     * 检查是否存在敏感货物标记的货物
     * @param goodsVoList
     * @param goodsIdList
     * @return
     */
    public boolean checkIsSensitive(List<GoodsVo> goodsVoList, List<Integer> goodsIdList) {
        goodsVoList.forEach(goodsVo -> {
            if(goodsVo.getIsSensitive() == null){
                goodsVo.setIsSensitive(0);
            }
        });
        if (goodsIdList == null || goodsIdList.isEmpty()) {
            for (GoodsVo goodsVo : goodsVoList) {
                if (goodsVo.getIsSensitive() == 1) {
                    return true;
                }
            }
        } else {
            for (int goodsId : goodsIdList) {
                for (GoodsVo goodsVo : goodsVoList) {
                    if (goodsVo.getId() == goodsId && goodsVo.getIsSensitive() == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean checkIsCalculateVolWeight(double sideLengthLimit, double length, double width, double height){
        return sideLengthLimit <= length
                || sideLengthLimit <= width
                || sideLengthLimit <= height;
    }

    public List<Integer> getGoodsIdList(String goodsIds){
        List<Integer> goodsIdReqList = new ArrayList<>();

            String[] split;
            if(goodsIds.contains(",")){
                split = goodsIds.split(",");
            }else if(goodsIds.contains("，")){
                split = goodsIds.split("，");
            }else {
                goodsIdReqList.add(Integer.valueOf(goodsIds));
                return goodsIdReqList;
            }
            for (String goodsId : split){
                goodsIdReqList.add(Integer.parseInt(goodsId));
            }
        return goodsIdReqList;
    }

    /**
     * 拼团子订单数据操作
     * @param customerPack
     * @param pingMainOrder
     */
    public void pinMainOrderSetting(CustomerPack customerPack, PingMainOrder pingMainOrder){
//        List<PingPackRespVo> pingPackList = pingMainOrderRespVo.getPingPack();

        pingMainOrder.setInsideMessage(customerPack.getInsideMessage());//内部备注
        pingMainOrder.setOrderNumber(customerPack.getOrderNumber());//订单号
        pingMainOrder.setPackType(customerPack.getPackType());//packType：1、待打包；  2、已打包；  3、确认发货；  4、已出库； 7、待拆包 ； 9、已收货
        pingMainOrder.setGoodsNumber(customerPack.getGoodsSum());//包裹数量
        pingMainOrder.setAuctualPrice(customerPack.getActualPrice());//实际价格
        pingMainOrder.setPackTime(customerPack.getPackTime());//打包时间
        pingMainOrder.setActualWeight(customerPack.getActualWeight());//结算重量
        pingMainOrder.setActualVol(customerPack.getActualVol());//结算体积
        pingMainOrder.setSettlementWeight(customerPack.getSettlementWeight());//结算重量
        pingMainOrder.setSettlementVol(customerPack.getSettlementVol());//结算体积
        pingMainOrder.setPreQoutedPrice(new BigDecimal(customerPack.getPreQuotedPrice())); //预计价格
        pingMainOrder.setRemarks(customerPack.getRemarks());//备注
        pingMainOrder.setIncidental(customerPack.getIncidental());//杂费
        String internationalTransshipmentNo = customerPack.getInternationalTransshipmentNo() == null ? "" : customerPack.getInternationalTransshipmentNo().trim();
        pingMainOrder.setAgentNumber(internationalTransshipmentNo);//国际转运单号
        pingMainOrder.setDiscount(customerPack.getDiscount());//优惠价格
        pingMainOrder.setIsSensitive(customerPack.getIsSensitive() == null ? 0 : customerPack.getIsSensitive());//是否敏感货物
        pingMainOrder.setRouteId(customerPack.getTransportationRouteId());//路线id
    }


    public void pinSonPackSetting(List<CustomerPackNumberVo> customerPackNumberList, List<PingPack> pingPackList){

        for (CustomerPackNumberVo customerPackNumber : customerPackNumberList) {

            PingPack pingPack = new PingPack();
            pingPack.setStatus(1);//1、可使用，0、已删除
            pingPack.setActualL(customerPackNumber.getLength());//长， 单位：cm
            pingPack.setActualW(customerPackNumber.getWidth());//宽， 单位：cm
            pingPack.setActualH(customerPackNumber.getHeight());//高， 单位：cm
            pingPack.setActualWeight(customerPackNumber.getActualWeight());//实际重量
            pingPack.setActualVol(customerPackNumber.getActualVol());//实际体积
            pingPack.setIsSensitive(customerPackNumber.getIsSensitive());//是否敏感货物
            pingPack.setPackOrder(customerPackNumber.getPackOrder()); // 包裹订单号
            pingPackList.add(pingPack);
        }
    }





    public HSSFWorkbook printGoods (List<GoodsDetailedListVo> goodsList){
        //excel标题
        String[] title = {"序号", "客户名称","会员ID", "快递单号/唛头", "仓库地址", "包装类型", "货物件数", "货物状态","重量/KG","尺寸/CM","体积/m³","货物名称","备注信息"};
        //sheet名
        String sheetName = "货物订单清单";

        String[][] content = new String[goodsList.size() + 1][title.length];
        for (int i = 0; i < goodsList.size(); i++) {
            //content[i] = new String[];
            GoodsDetailedListVo goods = goodsList.get(i);
            content[i][0] = String.valueOf(i + 1);
            if (goods.getCustomerName() != null) {
                content[i][1] = goods.getCustomerName();
            }
            if (goods.getLoginName() != null) {
                content[i][2] = goods.getLoginName();
            }
            content[i][3] = goods.getDeliveryOrderNo();
            if (StringUtils.isNotBlank(goods.getLocation())) {
                if ( goods.getLocation().startsWith(",") ){
                    content[i][4] = "";
                }else {
                    content[i][4] = goods.getLocation().split(",")[0] + "区" + goods.getLocation().split(",")[1] + "排";
                }
            }
            // 货物包装类型判断
            if (null != goods.getPackageType()) {
                content[i][5] = String.valueOf(goods.getPackageType());
            }
            content[i][6] = String.valueOf(goods.getPackageNum());
            // 货物状态判断
            if (goods.getGoodsType() == 1) {
                content[i][7] = String.valueOf("已入库");
            } else if (goods.getGoodsType() == 2) {
                content[i][7] = String.valueOf("已出库");
            } else if (goods.getGoodsType() == 3) {
                content[i][7] = String.valueOf("未入库");
            } else if (goods.getGoodsType() == 6) {
                content[i][7] = String.valueOf("待打包");
            } else if (goods.getGoodsType() == 7) {
                content[i][7] = String.valueOf("已打包");
            } else if (goods.getGoodsType() == 8) {
                content[i][7] = String.valueOf("待出库");
            }

            content[i][8] = String.valueOf(goods.getWeight());
            content[i][9] = goods.getHeight() + "*" + goods.getLength() + "*" + goods.getWidth();
            content[i][10] = goods.getVol();
            content[i][11] = goods.getGoodsName();
            content[i][12] = goods.getRemark();
        }

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setColumnWidth(1, 25 * 256);
        sheet.setColumnWidth(2, 25 * 256);
        sheet.setColumnWidth(3, 18 * 256);
        sheet.setColumnWidth(4, 15 * 256);
        sheet.setColumnWidth(6, 15 * 256);
        sheet.setColumnWidth(7, 15 * 256);
        sheet.setColumnWidth(8, 15 * 256);
        sheet.setColumnWidth(9, 15 * 256);
        sheet.setColumnWidth(10, 15 * 256);
        sheet.setColumnWidth(11, 15 * 256);
        sheet.setColumnWidth(12, 15 * 256);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        //创建内容
        for (int i = 0; i < content.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < content[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(content[i][j]);
            }
        }
        return wb;
    }
}
