package com.example.warehouse.controller.customerPack;

import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.CustomerPackNumber;
import com.example.warehouse.log.SysLog;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.CustomerPackNumberService;
import com.example.warehouse.service.CustomerPackService;
import com.example.warehouse.service.GoodsService;
import com.example.warehouse.service.sys.TrajectoryService;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customer.CustomerPackNumberVo;
import com.example.warehouse.vo.customer.CustomerPackVo;
import com.example.warehouse.vo.customer.OrderAmountChangeReqVo;
import com.example.warehouse.vo.customer.OrderPayReqVo;
import com.example.warehouse.vo.mobile.PackSortReqVo;
import com.example.warehouse.vo.packVo.PackConditionsQueryReqVo;
import com.example.warehouse.vo.packVo.PackedReqVo;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

import static com.example.warehouse.enums.ResultStatus.*;

/**
 * @author lrq
 * 打包包裹操作接口
 * 2019年11月1日15:25:41
 */
@RestController
@RequestMapping("/customerPack")
@Slf4j
public class CustomerPackController {

    @Autowired
    private CustomerPackService customerPackService;
    @Autowired
    private CustomerPackNumberService customerPackNumberService;
    @Autowired
    private TrajectoryService trajectoryService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SendMessageServer sendMessageServer;
    /**
     * 系统标识
     */
    @Value("${wechat.system_code}")
    private String SYSTEM_CODE;

    /**
     * 立方计算
     *
     * @param paramMap
     * @return
     */
    @PostMapping("/calculation")
    public Map<String, Object> calculation(@RequestParam Map<String, Object> paramMap) {
        double length = Double.valueOf((String) paramMap.get("length"));
        double width = Double.valueOf((String) paramMap.get("width"));
        double height = Double.valueOf((String) paramMap.get("height"));
        double vol = (length / 100) * (width / 100) * (height / 100);
        BigDecimal b = new BigDecimal(vol);
        double d = b.setScale(4, BigDecimal.ROUND_UP).doubleValue();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("actualVol", d);
        return resultMap;
    }

    @PostMapping("getWaitPackList")
    public ResponseEntity<ResultModel> getWaitPackList(PackSortReqVo packSortReqVo){
        return customerPackService.getWaitPackList(packSortReqVo);
    }
    @PostMapping("getOrderDetail")
    @SysLog(value = "拣货[订单流水号]",type = SysLog.Type.UPDATE,contentId = "businessNumber")
    public ResponseEntity<ResultModel> getOrderDetail(String businessNumber){
        return customerPackService.getOrderDetail(businessNumber);
    }

    @PostMapping("getOrderDetailNotLog")
    public ResponseEntity<ResultModel> getOrderDetailNotLog(String businessNumber){
        return customerPackService.getOrderDetail(businessNumber);
    }

    @PostMapping("overPicking")
    @SysLog(value = "拣货完毕[订单流水号]",type = SysLog.Type.UPDATE,contentId = "businessNumber")
    public ResponseEntity<ResultModel> overPicking(String businessNumber){
        return customerPackService.overPicking(businessNumber);
    }

    @PostMapping("getByOrderNumber")
    public ResponseEntity<ResultModel> getByOrderNumber(String orderNumber){
        return customerPackService.getByOrderNumber(orderNumber);
    }

    @PostMapping("orderOutWare")
    @SysLog(value = "订单扫码出库[订单号/转单号]",type = SysLog.Type.UPDATE,contentId = "orderNumber")
    public ResponseEntity<ResultModel> orderOutWare(String orderNumber, HttpServletRequest request){
        return customerPackService.orderOutWare(orderNumber, request);
    }

    /**
     * 查看订单产品货值详情
     * @param packId
     * @return
     */
    @PostMapping("/getProductValueDetail")
    public ResponseEntity<ResultModel> getProductValueDetail(@RequestParam("packId") Integer packId){
        return customerPackService.getProductValueDetail(packId);
    }

    @GetMapping("/getWaitPackDetail")
    public ResponseEntity<ResultModel> getWaitPackDetail(String businessNumber){
        return customerPackService.getWaitPackDetail(businessNumber);
    }

    /**
     * 查询  待打包 包裹
     *
     * @param paramMap 具体参数：packType  包裹状态 1、待打包  2、已打包  3、确认发货  4、已出库
     *                 customerName  客户姓名 ；routeType 路线类型
     * @return
     */
    @PostMapping("/find/by/packType/list")
    public ResponseEntity<ResultModel> findByPackType(@RequestParam Map<String, Object> paramMap) {
        return customerPackService.findByPackType(paramMap);

    }

    /**
     * 查询  待打包 包裹
     *
     * @param queryReqVo 具体参数：packType 4.已出库
     *                 customerName  客户姓名 ；routeType 路线类型
     * @return
     */
    @PostMapping("/find/outStorage/list")
//    public ResponseEntity<ResultModel> findOutStorageList(@RequestParam Map<String, Object> paramMap) {
    public ResponseEntity<ResultModel> findOutStorageList(PackConditionsQueryReqVo queryReqVo) {
        return customerPackService.findOutStorageList(queryReqVo);

    }

    /**
     * 查询待确认 和 待发货
     *
     * @param paramMap 具体参数：packType  包裹状态 1、待打包  2、已打包  3、确认发货  4、已出库
     *                 customerName  客户姓名 ；
     * @return
     */
    @PostMapping("/find/toBeShipped/list")
    public ResponseEntity<ResultModel> findToBeShipped(@RequestParam Map<String, Object> paramMap) {
        try {

            if(paramMap.containsKey("deliveryOrderNo") && !StringUtils.isEmpty(paramMap.get("deliveryOrderNo").toString())){
                paramMap.put("deliveryOrderNo", paramMap.get("deliveryOrderNo").toString().trim());
            }
            if(paramMap.containsKey("orderNumber") && !StringUtils.isEmpty(paramMap.get("orderNumber").toString())){
                paramMap.put("orderNumber", paramMap.get("orderNumber").toString().trim());
            }
            if(paramMap.containsKey("businessNumber") && !StringUtils.isEmpty(paramMap.get("businessNumber").toString())){
                paramMap.put("businessNumber", paramMap.get("businessNumber").toString().trim());
            }
            Map<String, Object> resultMap = new HashMap<>();
            PageData data = (PageData) PageHelp.initPage(paramMap).get("page");
            paramMap.put("packType", 2);
            int countF = customerPackService.countSelectAll(paramMap);
            paramMap.put("packType", 3);
            int countS = customerPackService.countSelectAll(paramMap);
            if (countF + countS < 1) {
                data.setTotal(0);
                resultMap.put("page", data);
                return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
            } else {
                data.setTotal(countF + countS);
            }

            List<CustomerPackVo> customerPackVoList = customerPackService.findToBeShipped(paramMap);
            resultMap.put("page", data);
            resultMap.put("customerPack", customerPackVoList);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }

    }

    /**
     * 查询出库订单
     *
     * @param paramMap 具体参数：packType  包裹状态 1、待打包  2、已打包  3、确认发货  4、已出库
     *                 customerName  客户姓名 ；
     * @return
     */
    @PostMapping("/find/outWarehouse/list")
    public ResponseEntity<ResultModel> findOutWarehouse(@RequestParam Map<String, Object> paramMap) {
        try {

            if(paramMap.containsKey("deliveryOrderNo") && !StringUtils.isEmpty(paramMap.get("deliveryOrderNo").toString())){
                paramMap.put("deliveryOrderNo", paramMap.get("deliveryOrderNo").toString().trim());
            }
            if(paramMap.containsKey("orderNumber") && !StringUtils.isEmpty(paramMap.get("orderNumber").toString())){
                paramMap.put("orderNumber", paramMap.get("orderNumber").toString().trim());
            }
            if(paramMap.containsKey("businessNumber") && !StringUtils.isEmpty(paramMap.get("businessNumber").toString())){
                paramMap.put("businessNumber", paramMap.get("businessNumber").toString().trim());
            }

            Map<String, Object> resultMap = new HashMap<>();
            PageData data = (PageData) PageHelp.initPage(paramMap).get("page");
            paramMap.put("packType", 4);
            int count = customerPackService.countSelectAll(paramMap);
            if (count < 1) {
                data.setTotal(0);
                resultMap.put("page", data);
                return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
            } else {
                data.setTotal(count);
            }

            List<CustomerPackVo> customerPackVoList = customerPackService.findShipped(paramMap);
            resultMap.put("page", data);
            resultMap.put("customerPack", customerPackVoList);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }

    }


    /**
     * 根据流水号查询 包裹内的快递包裹,详细内容
     *
     * @param businessNumber
     * @return
     */
    @PostMapping("/find/goods/byBusinessNumber/list")
    public ResponseEntity<ResultModel> findByBusinessNumber(@RequestParam("businessNumber") String businessNumber) {
        try {
            goodsService.initInPacked(businessNumber);
            Map<String, Object> resultMap = new HashMap<>();
            CustomerPackVo packVo = customerPackService.findPackByBusinessNumber(businessNumber);
            resultMap.put("customerPack", packVo);
            List<GoodsVo> goodsVoList = customerPackService.findByBusinessNumber(businessNumber);
            resultMap.put("goods", goodsVoList);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    // TODO  查询 打包后的 包裹内容

    /**
     * 根据流水号查询 包裹内的打包的包裹,详细内容
     *
     * @param businessNumber
     * @return
     */
    @PostMapping("/find/packed/byBusinessNumber/list")
    public ResponseEntity<ResultModel> findPackByBusinessNumber(@RequestParam("businessNumber") String businessNumber) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            goodsService.initInPacked(businessNumber);
            CustomerPackVo packVo = customerPackService.findPackByBusinessNumber(businessNumber);
            resultMap.put("customerPack", packVo);
            List<CustomerPackNumberVo> customerPackNumbers = customerPackNumberService.findByCustomerPackId(packVo.getId());
            resultMap.put("customerPackNumbers", customerPackNumbers);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    /**
     * 拣货
     *
     * @param deliveryOrderNo
     */
    @PostMapping("/update/sort")
    public ResponseEntity<ResultModel> sort(@RequestParam("businessNumber") String businessNumber, @RequestParam("deliveryOrderNo") String deliveryOrderNo) {
        return customerPackService.sort(businessNumber, deliveryOrderNo);
    }


    /**
     * 一次性拣货
     *
     * @param businessNumber
     */
    @PostMapping("/update/all/sort")
    @SysLog(value = "一件拣货[业务号]",type = SysLog.Type.UPDATE,contentId = "#businessNumber")
    public ResponseEntity<ResultModel> allSort(@RequestParam("businessNumber") String businessNumber) {

        return customerPackService.updateAllSort(businessNumber);
    }

    //private static Integer customerPackNumberId = 2406;


    /**
     * 查询 未入包 的包裹
     *
     * @param paramMap {customerPackId； customerPackNumberId;}
     * @return
     */
    @RequestMapping("/select/package/list")
    public ResponseEntity<ResultModel> selectPack(@RequestParam Map<String, Object> paramMap) {
        log.info("参数：{}", paramMap);
        Map<String, Object> resultMap = new HashMap<>();
        //paramMap.put("customerPackNumberId",customerPackNumberId);
        List<GoodsVo> goodsVoList = goodsService.selectPack(paramMap);
        resultMap.put("goods", goodsVoList);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    /**
     * 入包
     *
     * @param paramMap
     *        paramMap：{customerPackId；customerPackNumberId；goodsNos }
     * @return
     */
    @RequestMapping("/in/package")
    public ResponseEntity<ResultModel> inPacked(@RequestParam Map<String, Object> paramMap) {
        log.info("参数：{}", paramMap);
        if (!paramMap.containsKey("goodsNos") || paramMap.get("goodsNos") == null) {
            return new ResponseEntity<>(ResultModel.ok("未添加包裹"), HttpStatus.OK);
        }

        CustomerPackNumber customerPackNumber = goodsService.inPacked(paramMap);
        if (customerPackNumber == null) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(customerPackNumber), HttpStatus.OK);

    }

    /**
     * 删除子包裹
     * 参数：customerPackNumberId
     */
    @RequestMapping("/delete/package")
    public ResponseEntity<ResultModel> deletePacked(@RequestParam Map<String, Object> paramMap) {
        log.info("参数：{}", paramMap);
        int i = goodsService.deletePacked(paramMap);
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }


    /**
     * 插入包裹信息
     * @param id
     * @param goodsIds
     * @return
     */
    @PostMapping("/addGoodsInPack")
    public ResponseEntity<ResultModel> addGoodsInPack(@RequestParam("id") int id, @RequestParam("goodsIds") String goodsIds, @RequestParam("isPinPage") int isPinPage) {
        log.info("参数：{}", id, goodsIds);

        return customerPackService.addGoodsInPack(id, goodsIds, isPinPage);

    }

    /**
     * 重写打包
     * @param packedReqVo 包裹数据
     * @return
     */
    @PostMapping("/confirmPack")
    public ResponseEntity<ResultModel> confirmPack(@ApiParam(name="packedReqVo",value="包裹数据",required=true) PackedReqVo packedReqVo) {
        log.info("参数：{}", packedReqVo);
        return customerPackService.packing(packedReqVo, false);

    }

    /**
     * 重写包裹编辑
     * @param packedReqVo 包裹数据
     * @return
     */
    @PostMapping("/editPack")
    public ResponseEntity<ResultModel> editPack(@ApiParam(name="packedReqVo",value="包裹数据",required=true)PackedReqVo packedReqVo) {
        log.info("参数：{}", packedReqVo);
        return customerPackService.packing(packedReqVo, true);
    }

    @PostMapping("/updateWeighed")
    public ResponseEntity<ResultModel> updateWeighed(int packId){
        return customerPackService.updateWeighed(packId);
    }

    @PostMapping("/selectGoodsListByPackId")
    public ResponseEntity<ResultModel> selectGoodsListByPackId(@RequestParam("packId") int packId){
        return goodsService.findByPackId(packId);
    }

    /**
     * 发货
     *
     * @param paramMap id集合
     * @return
     */
    @PostMapping("/update/deliver")
    public ResponseEntity<ResultModel> updateDeliver(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
        return customerPackService.updateDeliver(paramMap, request);
    }

    /**
     * 发货
     *
     * @param paramMap id集合
     * @return
     */
    @PostMapping("confirmDelivery")
    public ResponseEntity<ResultModel> confirmDelivery(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
        return customerPackService.updateDeliver(paramMap, request);
//        if(Objects.requireNonNull(responseEntity.getBody()).getCode() == 100){
//            List<Map> orderDataList = new ArrayList<>();
//            paramMap.put("systemCode", SYSTEM_CODE);
//            orderDataList.add(paramMap);
//            trajectoryService.registerTrack17OrderData(JSONObject.toJSONString(orderDataList), request);
//        }
//        return responseEntity;
    }

    /**
     * 查询带拆包
     *
     * @param paramMap
     * @return
     */
    @PostMapping("/find/byToUnpacking")
    public ResponseEntity<ResultModel> byToUnpacking(@RequestParam Map<String, Object> paramMap) {
        return null;
//        try {
//            if(paramMap.containsKey("deliveryOrderNo") && !StringUtils.isEmpty(paramMap.get("deliveryOrderNo").toString())){
//                paramMap.put("deliveryOrderNo", paramMap.get("deliveryOrderNo").toString().trim());
//            }
//            if(paramMap.containsKey("orderNumber") && !StringUtils.isEmpty(paramMap.get("orderNumber").toString())){
//                paramMap.put("orderNumber", paramMap.get("orderNumber").toString().trim());
//            }
//            if(paramMap.containsKey("businessNumber") && !StringUtils.isEmpty(paramMap.get("businessNumber").toString())){
//                paramMap.put("businessNumber", paramMap.get("businessNumber").toString().trim());
//            }
//            Map<String, Object> resultMap = new HashMap<>();
//
//            PageData data = (PageData) PageHelp.initPage(paramMap).get("page");
//            paramMap.put("unpacking", 2);
//            int count = customerPackService.countSelectAll(paramMap);
//            if (count < 1) {
//                data.setTotal(0);
//                resultMap.put("page", data);
//                return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
//            } else {
//                data.setTotal(count);
//            }
//            paramMap.put("unpacking", 2);
//            return customerPackService.findByPackType(paramMap);
////            resultMap.put("page", data);
////            resultMap.put("customerPack", customerPackVoList);
////            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
//        }
    }

    /**
     * 取消拆包操作
     *
     * @param packId
     * @return
     */
    @PostMapping("/cancel/unpack")
    public ResponseEntity<ResultModel> cancelUnpack(@RequestParam Integer packId) {
        int i = customerPackService.cancelUnpack(packId);
        if (i == 1) {
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }

    /**
     * 拆包
     * customerPackId：包裹id
     * goods：包裹集合，包括（  goodsId：快递包裹id；  location：仓库地址 ）
     * money：拆包价格
     *
     * @param paramMap
     * @return
     */
    @RequiresPermissions("pack:delete")
    @PostMapping("/delete")
    public ResponseEntity<ResultModel> delete(@RequestParam Map<String, Object> paramMap) {
        int i = customerPackService.delete(paramMap);
        if (i == -2) {
            return new ResponseEntity<>(ResultModel.error(PACK_ERROR), HttpStatus.OK);
        }
        if (i == 999) {
            return new ResponseEntity<>(ResultModel.error(BALANCE_INSUFFICIENT_ERROR), HttpStatus.OK);
        }
        if (i < 1) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 修改包裹信息
     *
     * @param paramMap
     * @return
     */
//    @RequiresPermissions("pack:update")
    @PostMapping("/update")
    public ResponseEntity<ResultModel> update(@RequestParam Map<String, Object> paramMap,HttpSession session) {
        int i = customerPackService.updateCustomerPackMessage(paramMap);
        if(i == -151){
            return new ResponseEntity<>(ResultModel.error(PACK_LENGTH_ERROR), HttpStatus.OK);
        }
        if (i == -2) {
            return new ResponseEntity<>(ResultModel.error(PACK_SURPLUS_ERROR), HttpStatus.OK);
        }

        if (i == -3) {
            return new ResponseEntity<>(ResultModel.error(TRANSPORTROUTE_ERROR), HttpStatus.OK);
        }

        if (i == -4) {
            return new ResponseEntity<>(ResultModel.error(TRANSPORT_ROUTE_PRICE_ERROR), HttpStatus.OK);
        }

        if(i == -6){
            return new ResponseEntity<>(ResultModel.error(ROUTE_WEIGHT_OR_VOLUME_LIMIT_ERROR), HttpStatus.OK);
        }

        if (i < 0) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @PostMapping("/find/updateCustomerPack/by/businessNumber")
    public ResponseEntity<ResultModel> findUpdateCustomerPackByBusinessNumber(@RequestParam Map<String, Object> paramMap) {
        if (paramMap.containsKey("businessNumber")) {

            CustomerPackVo customerPackVo = customerPackService.findPackByBusinessNumber(paramMap.get("businessNumber").toString());
            if (customerPackVo == null) {
                return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
            }
            List<CustomerPackNumberVo> customerPackNumberVoList = customerPackVo.getCustomerPackNumbers();
            if (customerPackNumberVoList == null) {
                return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
            }
            Map<String, Object> resultMap = new HashMap<>(4);
            resultMap.put("customerPack", customerPackVo);
            resultMap.put("customerPackNumbers", customerPackNumberVoList);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }

    /**
     * 修改转单号信息
     *
     * @param customerPack
     * @return
     */
//    @RequiresPermissions("pack:update")
    @PostMapping("/changeAgentNumber")
    @SysLog(value = "编辑转单号或客服备注[订单号]",type = SysLog.Type.ADD,contentId = "customerPack.orderNumber")
    public ResponseEntity<ResultModel> changeAgentNumber(CustomerPack customerPack, HttpServletRequest request) {
        int i = customerPackService.updateAgentNumber(customerPack, request);
        if (i < 0) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 创建亚美订单
     *
     * @param customerPack
     * @return
     */
    //    @RequiresPermissions("pack:update")
    @PostMapping("/buildOrderForYameiApi")
    @SysLog(value = "API自助下单[订单号]",type = SysLog.Type.ADD,contentId = "customerPack.orderNumber")
    public ResponseEntity<ResultModel> buildOrderForYameiApi(CustomerPack customerPack) {
        return customerPackService.buildOrderForYamei(customerPack.getOrderNumber());
    }

    /**
     * 撤销申请打包
     * @param packId 打包id
     * @return
     */
    @PostMapping("/cancle/apply")
    @SysLog(value = "撤销申请打包[业务号]",type = SysLog.Type.ADD,contentId = "#businessNumber")
    public ResponseEntity<ResultModel> cancleApply(@RequestParam("businessNumber")String businessNumber,@RequestParam("packId") int packId) {
        return customerPackService.cancleApply(packId);
    }

    /**
     * 受理订单
     * @param packId 打包id
     * @param accepted 是否受理的状态：1 开始受理，2拣货完成
     * @return
     */
    @PostMapping("/handleCustomerPack")
    public ResponseEntity<ResultModel> handleCustomerPack(int packId,int accepted) {
        return customerPackService.handleOrder(packId,accepted);
    }

    /**
     * 打包编辑备注信息
     *
     * @param customerPack
     * @return
     */
    //    @RequiresPermissions("pack:update")
    @PostMapping("/changePackMessage")
    @SysLog(value = "打包编辑备注信息[业务号]",type = SysLog.Type.ADD,contentId = "customerPack.businessNumber")
    public ResponseEntity<ResultModel> changePackMessage(CustomerPack customerPack, HttpServletRequest request) {
        return customerPackService.updatePackMessage(customerPack, request);

    }

    /**
     * 更新产品货值内容
     * @param packId
     * @param packValuations
     * @return
     */
    @PostMapping("/updatePackValuation")
    public ResponseEntity<ResultModel> updatePackValuations(int packId, String packValuations) {
        return customerPackService.updatePackValuations(packId, packValuations);
    }

    /**
     * 变更渠道信息
     *
     * @param customerPack
     * @return
     */
    //    @RequiresPermissions("pack:update")
    @PostMapping("/changePackChannel")
    public ResponseEntity<ResultModel> changePackChannel(CustomerPack customerPack) {
        return customerPackService.updateChannel(customerPack);
    }

    /**
     * 订单作废
     *
     * @param customerPack
     * @return
     */
    //    @RequiresPermissions("pack:update")
    @PostMapping("/orderVoided")
    @SysLog(value = "订单作废[订单号]",type = SysLog.Type.ADD,contentId = "customerPack.orderNumber")
    public ResponseEntity<ResultModel> orderVoided(CustomerPack customerPack) {
        return customerPackService.updateOrderVoided(customerPack);
    }

    @PostMapping("exportCustomerPack")
    public void exportCustomerPack(@RequestBody String map,
                                   HttpServletRequest request, HttpServletResponse response) {
        Map requestMap = JSONObject.parseObject(map, Map.class);
        customerPackService.exportCustomerPack(requestMap, request, response);
    }


    @PostMapping("/getPayOrderByOrderNumber")
    public ResponseEntity<ResultModel> getPayOrderByOrderNumber(String orderNumber){
        return customerPackService.getPayOrderByOrderNumber(orderNumber);
    }

    @PostMapping("/paymentOrder")
    @SysLog(value = "订单付款[订单号]",type = SysLog.Type.UPDATE,contentId = "orderPayReqVo.orderNumber")
    public ResponseEntity<ResultModel> paymentOrder(OrderPayReqVo orderPayReqVo){
        return customerPackService.paymentOrder(orderPayReqVo);
    }

    @PostMapping("/orderAmountChange")
    @SysLog(value = "订单额外扣款[订单号]",type = SysLog.Type.UPDATE,contentId = "orderAmountChangeReqVo.orderNumber")
    public ResponseEntity<ResultModel> orderAmountChange(OrderAmountChangeReqVo orderAmountChangeReqVo){
        return customerPackService.orderAmountChange(orderAmountChangeReqVo);
    }

    @PostMapping("/updateOrderReceiverAddress")
    public ResponseEntity<ResultModel> updateOrderReceiverAddress(@RequestParam Map<String, Object> requestMap){
        return customerPackService.updateOrderReceiverAddress(requestMap);
//        return null;
    }

    /**
     * 取消发货
     * @param packId 订单id
     * @return
     */
    @PostMapping("/cancelDelivery")
    public ResponseEntity<ResultModel> cancelDelivery(int packId){
        return customerPackService.cancelDelivery(packId);
    }

    /**
     * 退仓重发
     * @param packId
     * @return
     */
    @PostMapping("/rejectionDelivery")
    public ResponseEntity<ResultModel> rejectionDelivery(int packId){
        return customerPackService.rejectionDelivery(packId);
    }

    /**
     * 打印发票
     * @param requestMapStr invoiceType
     * @param requestMapStr orderIds
     * @return
     */
    @PostMapping("printInvoice")
    @ResponseBody
    public void printInvoice(@RequestBody String requestMapStr, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> requestMap = JSONObject.parseObject(requestMapStr, Map.class);
        customerPackService.printInvoice(requestMap, request, response);
    }




    @PostMapping("/ocr/uploadOrderPicture")
    @ResponseBody
    public ResponseEntity<ResultModel> ocrUploadOrderPicture(@RequestParam String orderNumber,
                                                             @RequestParam("file") MultipartFile file){
        return customerPackService.ocrUploadOrderPicture(orderNumber, file);
    }

    @PostMapping("/orc/updateOrder")
    public ResponseEntity<ResultModel> orcUpdateOrder(@RequestBody String body, HttpServletRequest request){
        return customerPackService.orcUpdateOrderRe(body, request);
    }

    @PostMapping("/otherOrcUpdateOrder")
    public ResponseEntity<ResultModel> otherOrcUpdateOrder(@RequestBody String body){
        return customerPackService.orcUpdateOrder(body);
    }

}
