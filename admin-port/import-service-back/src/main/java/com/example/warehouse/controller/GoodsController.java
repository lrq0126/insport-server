package com.example.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.Goods;
import com.example.warehouse.entity.User;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.log.SysLog;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.CustomerService;
import com.example.warehouse.service.GoodsService;
import com.example.warehouse.service.UserService;
import com.example.warehouse.service.sys.SysNoticeService;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.applyPack.GoodsRequestVo;
import com.example.warehouse.vo.goods.GoodsReqVo;
import com.example.warehouse.vo.goods.OcrInWareSortingReqVo;
import com.example.warehouse.vo.goods.ShelvesReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.*;

@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SysNoticeService sysNoticeService;
    @Autowired
    private UserService userService;

    @PostMapping("selectGoodsInfoByDeliveryOrderNo")
    public ResponseEntity<ResultModel> selectGoodsInfoByDeliveryOrderNo(@RequestParam String deliveryOrderNo){
        return goodsService.selectGoodsInfoByDeliveryOrderNo(deliveryOrderNo);
    }

    // ??????????????????????????????
    @PostMapping("updateShelves")
    @SysLog(value = "??????[????????????]",type = SysLog.Type.UPDATE,contentId = "shelvesReqVo.deliveryOrderNo")
    public ResponseEntity<ResultModel> updateShelves(ShelvesReqVo shelvesReqVo, HttpServletRequest request){
        return goodsService.updateShelves(shelvesReqVo, request);
    }

    @PostMapping("getCustomerGoodsDetail")
    public ResponseEntity<ResultModel> getCustomerGoodsDetail(String deliveryOrderNo){
        return goodsService.getCustomerGoodsDetail(deliveryOrderNo);
    }
    // ??????????????????
    @PostMapping("getCustomerGoods")
    public ResponseEntity<ResultModel> getCustomerGoods(String deliveryOrderNo, String customerNo){
        return goodsService.getCustomerGoods(deliveryOrderNo, customerNo);
    }


    @RequestMapping(value = "/find/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PageResultModel> findAll(GoodsReqVo goodsReqVo) {
        log.info("????????????????????????????????????-------->>>>>/find/all");
        return goodsService.findAll(goodsReqVo);
    }

    @RequestMapping(value = "/find/allRe", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> findAllRe(@RequestParam Map<String, Object> map) {

        log.info("??????????????????????????????????????????-------->>>>>/find/all");
        return goodsService.findAllRe(map);
    }

    /**
     * ????????????????????? ??????
     *
     * @param deliveryOrderNo
     * @return
     */
    @PostMapping("/select/goods/by/deliveryOrderNo")
    @ResponseBody
    private ResponseEntity<ResultModel> selectGoodsByDeliveryOrderNo(@RequestParam("deliveryOrderNo") String deliveryOrderNo) {
        List<GoodsVo> goodsVoList = goodsService.findGoodsByDeliveryOrderNo(deliveryOrderNo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("goods", goodsVoList);
        return new ResponseEntity<>(PageResultModel.ok(resultMap), HttpStatus.OK);
    }

    /**
     * ???????????? ?????????
     *
     * @param
     * @return
     */
//    @RequiresPermissions("goods:create")
    @PostMapping(value = "/create")
    @ResponseBody
    @SysLog(value = "??????[????????????]",type = SysLog.Type.ADD,contentId = "Map.params.deliveryOrderNo")
    public ResponseEntity<ResultModel> create(@RequestParam Map<String, Object> params) {
        log.info("????????????-------------???????????????,?????????{}", params);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_COMMERCIAL_AREA), HttpStatus.OK);
        }
        int commercialAreaId = user.getCommercialAreaId();
        String commercialAreaName = user.getCommercialAreaName();

        String location = "";
        String tempA = "";
        String tempB = "";
        if (params.containsKey("storageArea")) {
            tempA = params.get("storageArea").toString() + ",";
        }
        if (params.containsKey("storageRow")) {
            tempB = params.get("storageRow").toString();
        }
        location = tempA + tempB;
        log.info("?????????????????????{}", location);
        try {
            String str = JSON.toJSON(params).toString();
            Goods newGoods = JSON.parseObject(str, Goods.class);
            //????????????????????????????????????
            /*int i = goodsService.judgeGoods(newGoods, params.get("type").toString());
            if (i != 1) {
                return new ResponseEntity<>(ResultModel.error(PACK_EXISTENCE_ERROR), HttpStatus.OK);
            }*/
            /*if(newGoods.getGoodsType() != null && newGoods.getGoodsType() != 3){
                return new ResponseEntity<>(new ResultModel(PACK_ERROR), HttpStatus.OK);
            }*/

            //????????????????????????
            newGoods.setLocation(location);

            newGoods.setCommercialAreaId(commercialAreaId);
            newGoods.setCommercialAreaName(commercialAreaName);

            int i = goodsService.save(newGoods, params.get("type").toString());
            if (i < 1) {
                return new ResponseEntity<>(new ResultModel(ERROR), HttpStatus.OK);
            }
            if( 100 == i ){// ??????????????????
                return new ResponseEntity<>(new ResultModel(GOODS_HAVE_IN_ERROR), HttpStatus.OK);
            }
            if( 101 == i ){ // ?????????????????????RE
                return new ResponseEntity<>(new ResultModel(GOODS_HAVE_IN_ERROR), HttpStatus.OK);
            }
            if( 102 == i ){ // ?????????????????????????????????RE
                return new ResponseEntity<>(new ResultModel(GOODS_REIN_NUMBER_TOO_MUCH), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("????????????-------------,?????????{}", e.getMessage());
            return new ResponseEntity<>(new ResultModel(ERROR, e.getMessage()), HttpStatus.OK);
        }
        log.info("????????????-------------<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @PostMapping(value = "/warehouseSorting")
    @ResponseBody
    public ResponseEntity<ResultModel> warehouseSorting(@RequestParam Map<String, Object> params) {
        log.info("??????????????????-------------???????????????,?????????{}", params);
        return goodsService.warehouseSorting(params);
    }

    @RequiresPermissions("goods:update")
    @PostMapping("/update")
    @ResponseBody
    @SysLog(value = "??????????????????[????????????]",type = SysLog.Type.UPDATE,contentId = "Map.params.deliveryOrderNo")
    public ResponseEntity<ResultModel> update(@RequestParam Map<String, Object> params) {
        String str = JSON.toJSON(params).toString();
        String location = "";
        String tempA = "";
        String tempB = "";
        if (params.containsKey("storageArea")) {
            tempA = params.get("storageArea").toString() + ",";
        }
        if (params.containsKey("storageRow")) {
            tempB = params.get("storageRow").toString();
        }
        location = tempA + tempB;
        try {
            Goods goods = JSON.parseObject(str, Goods.class);
            goods.setLocation(location);
            int i = goodsService.update(goods);
            if (i == 0) {
                return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }

    }

    @RequiresPermissions("goods:delete")
    @PostMapping("/delete")
    @ResponseBody
    @SysLog(value = "????????????[????????????]",type = SysLog.Type.DELETE,contentId = "Map.params.deliveryOrderNo")
    public ResponseEntity<ResultModel> del(@RequestParam Map<String, Object> params) {
        log.info("????????????-------------???????????????,?????????{}", params);
        try {
            int i = goodsService.del(params.get("goodsNo").toString(), params.get("usercode").toString());
            if(i == -100){
                return new ResponseEntity<>(ResultModel.error(PACK_THERE_ERROR), HttpStatus.OK);
            }
            if (i == 0) {
                return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
            }
            log.info("????????????-------------??????????????????????????????");
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }

    }

    @RequiresPermissions("goods:delete")
    @PostMapping("/batchDelete")
    @ResponseBody
    @SysLog(value = "????????????????????????[???????????????]",type = SysLog.Type.DELETE,contentId = "Map.params.goodsNo")
    public ResponseEntity<ResultModel> batchDelete(@RequestParam Map<String, Object> params) {
        log.info("??????????????????-------------???????????????,?????????{}", params);
        try {
            int i = goodsService.batchDelete(params.get("goodsNo").toString(), params.get("usercode").toString());
            if (i == 0) {
                return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
            }
            log.info("????????????-------------??????????????????????????????");
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }

    }

    /**
     * ????????????
     *
     * @return
     * @param1 customerName --????????????
     * @param2 phoneNumber --????????????
     * @param3 deliveryName --????????????
     * @param4 deliveryNo --?????????
     * @param5 operatorName --?????????
     * @param6 goodsType --????????????
     */
    @PostMapping("/condition/query")
    @ResponseBody
    public ResponseEntity<ResultModel> conditionQuery(@RequestParam Map<String, Object> map) {
        log.info("?????????????????????????????????,--------->>>>>>>>>,?????????{}", map);
        List<GoodsVo> goodsList = goodsService.conditionQuery(map);
        log.info("????????????????????????????????????---------<<<<<<<");
        return new ResponseEntity<>(ResultModel.ok(goodsList), HttpStatus.OK);
    }

    @PostMapping("find/customer/no")
    @ResponseBody
    public ResponseEntity<ResultModel> findByCustomerNo(@Param("customerNo") String customerNo) {
        List<GoodsVo> goodsList = goodsService.findByCustomerNo(customerNo);
        return new ResponseEntity<>(ResultModel.ok(goodsList), HttpStatus.OK);
    }


    /**
     * ??????????????????
     *
     * @param params
     * @return
     */
    @PostMapping("/customer/out/warehouse")
    @ResponseBody
    @SysLog(value = "????????????[????????????]",type = SysLog.Type.UPDATE,contentId = "Map.params.deliveryOrderNo")
    public ResponseEntity<ResultModel> outWarehouse(@RequestParam Map<String, Object> params) {
        log.info("????????????-------->>>>>>,?????????{}", params);
        String goodsNos = params.get("goodsNos").toString();
        String operatorName = params.get("operatorName").toString();
        int i = 0;
        Map map = new HashMap();
        try {
            i = goodsService.outWarehouse(goodsNos, operatorName);
        } catch (Exception e) {
            log.error("????????????------<<<<<??????:{}", e.getMessage());
            map.put("error", e.getMessage());
        }
        if (i > 0) {
            map.put("message", "????????????");
            log.info("????????????-------->>>>>>");
            return new ResponseEntity<>(ResultModel.ok(map), HttpStatus.OK);
        }
        if (i == 0) {
            log.info("????????????-------->>>>>>");
        }
        return new ResponseEntity<>(ResultModel.error(ERROR, map), HttpStatus.OK);
    }

    /**
     * ??????????????????
     *
     * @param params
     * @return
     */
    @RequiresPermissions("goods:inWarehouse")
    @PostMapping("/customer/in/warehouse")
    @ResponseBody
    public ResponseEntity<ResultModel> inWarehouse(@RequestParam Map<String, Object> params) {
        log.info("????????????-------->>>>>>,?????????{}", params);
        String goodsNos = params.get("goodsNos").toString();
        String operatorName = params.get("operatorName").toString();
        try {
            int i = goodsService.inWarehouse(goodsNos, operatorName);
            Map map = new HashMap();
            if (i > 0) {
                map.put("message", "????????????");
                log.info("????????????-------->>>>>>");
                return new ResponseEntity<>(ResultModel.ok(map), HttpStatus.OK);
            }
            log.error("????????????------<<<<<");
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }

    }


    /**
     * ????????????
     *
     * @param params
     * @return
     */
    @PostMapping("/customer/out/warehouse/choice")
    @ResponseBody
    public ResponseEntity<ResultModel> outAllWarehouse(@RequestParam Map<String, Object> params) {
        log.info("????????????-------->>>>>>");
        String goodsNos = params.get("goodsNos").toString();
        String operatorName = params.get("operatorName").toString();
        int i = 0;
        try {
            i = goodsService.outAllWarehouse(goodsNos, operatorName);
        } catch (Exception e) {
            log.info("???????????? ???????????????-------->>>>>>", e.getMessage());
        }
        Map map = new HashMap();
        if (i > 0) {
            map.put("message", "????????????");
            log.info("????????????-------->>>>>>");
            return new ResponseEntity<>(ResultModel.ok(map), HttpStatus.OK);
        }
        log.error("????????????------<<<<<");
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }

    /**
     * ????????????
     * ?????????deliveryNo  ?????????
     *
     * @param params
     * @return
     */
    @PostMapping("scanOutWare")
    @ResponseBody
    @SysLog(value = "????????????[????????????]",type = SysLog.Type.UPDATE,contentId = "Map.params.deliveryOrderNo")
    public ResponseEntity<ResultModel> scanOutWare(@RequestParam Map<String, Object> params) {
        String deliveryNo = params.get("deliveryOrderNo") == null ? "null" : params.get("deliveryOrderNo").toString();
        log.info("????????????----->,???????????????{}", deliveryNo);
        int i = goodsService.scanOutWare(params);
        switch (i) {
            case 110:
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR, "?????????????????????:" + params.get("deliveryOrderNo")), HttpStatus.OK);
            case 100:
                List<GoodsVo> list = goodsService.find(params);
                params.clear();
                params.put("customerNo", list.get(0).getCustomerNo());
                // ???????????????
                List<Customer> customers = customerService.find(params);
                list.get(0).setCustomerNo(customers.get(0).getCustomerName());
                // ?????????????????????
//                sysNoticeService.updateNoticeToCustomer(customers.get(0).getId().toString(),deliveryNo,list.get(0).getGoodsName(),"??????","?????????,?????????????????????????????????");
                log.info("-----????????????????????????{}", customers.get(0).getCustomerName());
                return new ResponseEntity<>(ResultModel.ok(list.get(0)), HttpStatus.OK);
            default:
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR, "????????????"), HttpStatus.OK);
        }
    }

    /**
     * ?????????????????????
     *
     * @param deliveryOrderNo
     * @return
     */
    @PostMapping("/find/by/deliveryOrderNo")
    public ResponseEntity<ResultModel> findByDeliveryOrderNo(@RequestParam("deliveryOrderNo") String deliveryOrderNo) {
        return goodsService.findByDeliveryOrderNo(deliveryOrderNo);
    }

    /**
     * ?????????????????????????????????
     *
     * @param customerNo
     * @return
     */
    @PostMapping("/getCustomerOrderLocation")
    public ResponseEntity<ResultModel> getCustomerOrderLocation(@RequestParam("customerNo") String customerNo) {
        return goodsService.getCustomerOrderLocation(customerNo);
    }

    /**
     * ????????????ID??????????????????
     * @param goodsRequestVo
     * @return
     */
    @PostMapping("/getGoodsByCustomerId")
    public ResponseEntity<ResultModel> getGoodsByCustomerId(GoodsRequestVo goodsRequestVo) {
        return goodsService.getGoodsByCustomerId(goodsRequestVo);

    }

    /**
     * ????????????ID??????
     *
     * @param customerId
     * @return
     */
    @PostMapping("/findGoodsLocalhost")
    public ResponseEntity<ResultModel> findGoodsLocalhost(int customerId) {
        return goodsService.findGoodsLocalhost(customerId);

    }


    /**
     * ?????????????????????
     *
     * @param deliveryOrderNo
     * @return
     */
    @PostMapping("/searchDeliveryOrderNo")
    public ResponseEntity<ResultModel> searchDeliveryOrderNo(@RequestParam("deliveryOrderNo") String deliveryOrderNo) {
        GoodsVo goodsVo = goodsService.searchDeliveryOrderNo(deliveryOrderNo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("goods", goodsVo);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);

    }

    @GetMapping("/test")
    public ResponseEntity<ResultModel> searchDeliveryOrderNo() {
        return new ResponseEntity<>(ResultModel.ok("??????Jenkins???"), HttpStatus.OK);
    }

    /**
     * ????????????????????????????????????????????????????????????
     * @param customerNo
     * @return
     */
    @GetMapping("/getLocationByCustomerNo")
    @ResponseBody
    public ResponseEntity<ResultModel> getLocationByCustomerNo(String customerNo){
        return new ResponseEntity<>(ResultModel.ok(goodsService.getLocationByCustomerNo(customerNo)), HttpStatus.OK);
    }

    /**
     * ?????????????????????????????????????????????
     * @param deliveryOrderNo
     * @return
     */
    @GetMapping("/getGoodsByDeliveryOrderNo")
    @ResponseBody
    public ResponseEntity<ResultModel> getGoodsByDeliveryOrderNo(String deliveryOrderNo){
        List<Goods> list = goodsService.getGoodsBydeliveryOrderNo(deliveryOrderNo);
        return new ResponseEntity<>(ResultModel.ok(list), HttpStatus.OK);
    }

    /**
     * ?????????????????????????????????????????????
     * @param goods : id:??????id,location:?????????,deliveryOrderNo:????????????
     * @return
     */
    @GetMapping("/updataGoods")
    @ResponseBody
    @SysLog(value = "??????[????????????]",type = SysLog.Type.PUTON,contentId = "goods.deliveryOrderNo")
    public ResponseEntity<ResultModel> updataGoods(Goods goods, HttpSession session, HttpServletRequest request){
        if ( goods.getId() == null || StringUtils.isBlank(goods.getLocation())  || StringUtils.isBlank(goods.getDeliveryOrderNo()) ){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
//        User user = (User) session.getAttribute("user");
//        if ( null == user ){
//            String authorization = request.getHeader("Authorization");
//            if ( StringUtils.isNotBlank(authorization) ){
//                authorization = authorization.substring(authorization.indexOf("USER"));
//                user = userService.getUserByUserCode(authorization);
//            }
//        }
        int i = goodsService.updateByPrimaryKey(goods,user);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @PostMapping("/abnormalInStorage")
    @ResponseBody
    public ResponseEntity<ResultModel> abnormalInStorage(Goods goods){
        return goodsService.abnormalInStorage(goods);
    }

//    @PostMapping("/ocr/inWareSortingPhoto")
//    @ResponseBody
//    public ResponseEntity<ResultModel> ocrInWareSortingPhoto(@RequestBody String body,
//                                                             @RequestParam("file") MultipartFile multipartFiles,
//                                                             HttpServletRequest request){
//        OcrInWareSortingReqVo ocrInWareSortingReqVo = JSONObject.parseObject(body, OcrInWareSortingReqVo.class);
//        return goodsService.ocrInWareSorting(ocrInWareSortingReqVo, request);
//    }

    @PostMapping("/ocr/inWareSortingPicture")
    @ResponseBody
    public ResponseEntity<ResultModel> ocrInWareSortingPicture(@RequestParam("deliveryOrderNo") String deliveryOrderNo,
                                                               @RequestParam("systemName") String systemName,
                                                               @RequestParam("file") MultipartFile file){
        return goodsService.ocrInWareSortingPicture(deliveryOrderNo, systemName, file);
    }

    @PostMapping("/ocr/inWareSorting")
    @ResponseBody
    public ResponseEntity<ResultModel> ocrInWareSorting(@RequestBody String body,
                                                        HttpServletRequest request){
        OcrInWareSortingReqVo ocrInWareSortingReqVo = JSONObject.parseObject(body, OcrInWareSortingReqVo.class);
        return goodsService.ocrInWareSorting(ocrInWareSortingReqVo, request);
    }

    @PostMapping("/otherSystemOcrInWareSorting")
    @ResponseBody
    public ResponseEntity<ResultModel> otherSystemOcrInWareSorting(@RequestBody String body){
        OcrInWareSortingReqVo ocrInWareSortingReqVo = JSONObject.parseObject(body, OcrInWareSortingReqVo.class);
        return goodsService.inWareSorting(ocrInWareSortingReqVo);
    }

    // ORC???????????????????????????
    @PostMapping("/ocrAbnormalInWareSorting")
    @ResponseBody
    public ResponseEntity<ResultModel> OcrAbnormalInWareSorting(@RequestBody String body){
        OcrInWareSortingReqVo ocrInWareSortingReqVo = JSONObject.parseObject(body, OcrInWareSortingReqVo.class);
        return goodsService.abnormalInWareSorting(ocrInWareSortingReqVo);
    }

    /**
     * ??????????????????
     * @param deliveryOrderNo ????????????
     * @param loginName ??????ID
     * @return
     */
    @PostMapping("/printDeliveryOrder")
    @ResponseBody
    public ResponseEntity<ResultModel> printDeliveryOrder(String deliveryOrderNo, String loginName){
        return goodsService.printDeliveryOrder(deliveryOrderNo, loginName);
    }
}
