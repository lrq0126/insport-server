package com.dwgj.mlxydedicatedline.controller;

import com.dwgj.mlxydedicatedline.commons.ShiroUtil;
import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.*;
import com.dwgj.mlxydedicatedline.entity.image.ImageType;
import com.dwgj.mlxydedicatedline.enums.ResultStatus;
import com.dwgj.mlxydedicatedline.resultType.PageData;
import com.dwgj.mlxydedicatedline.resultType.PageHelp;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.CustomerPackService;
import com.dwgj.mlxydedicatedline.service.GoodsService;
import com.dwgj.mlxydedicatedline.service.OrderService;
import com.dwgj.mlxydedicatedline.service.TransportationRouteService;
import com.dwgj.mlxydedicatedline.vo.GoodsPackVo;
import com.dwgj.mlxydedicatedline.vo.GoodsVo;
import com.dwgj.mlxydedicatedline.vo.goods.ConfirmPackReqVo;
import com.dwgj.mlxydedicatedline.vo.pack.ConfirmShipmentReqVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteQuotationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.*;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.*;
import static com.dwgj.mlxydedicatedline.enums.ResultStatus.NULL_ADDRESS_ERROR;

@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CustomerPackService customerPackService;
    @Autowired
    private TransportationRouteService routeService;

    /**
     * 获取 国家-仓库列表
     * @return
     */
    @GetMapping("/getGoodsCommercialArea")
    public ResponseEntity<ResultModel> getGoodsCommercialArea(HttpServletRequest httpServletRequest){
        return goodsService.getGoodsCommercialArea(httpServletRequest);
    }

    @PostMapping("selectGoodsInfoByDeliveryOrderNo")
    public ResponseEntity<ResultModel> selectGoodsInfoByDeliveryOrderNo(@RequestParam String deliveryOrderNo){
        return goodsService.selectGoodsInfoByDeliveryOrderNo(deliveryOrderNo);
    }

    /**
     * 订单包裹查询
     * @param paramMap
     * 具体参数 deliveryOrderNo 快递单号； deliveryName 快递公司名称； goodsName 货物名称； orderTime 日期 格式 2019-10-01；
     * @return
     */
    @PostMapping("/find/no/warehousing")
    public ResponseEntity<ResultModel> noWarehousing(@RequestParam Map<String, Object> paramMap, HttpSession session){
        Customer customer = UserThreadContext.getUser();
        paramMap.put("customerId", UserThreadContext.getUser().getId());
        if( !paramMap.containsKey("pageNumber")){
            paramMap.put("pageNumber",0);
        }
        if( !paramMap.containsKey("pageSize")){
            paramMap.put("pageSize",10);
        }
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(),paramMap.get("pageSize").toString());
        paramMap.put("pageNumber",data.getPageNumber());
        paramMap.put("pageSize",data.getPageSize());
        paramMap.put("goodsType",3);
        int count =goodsService.countFindAll(paramMap);
        if(count < 1){
            data.setTotal(0);
        }else {
            data.setTotal(count);
        }
        List<GoodsVo> listOrder = goodsService.findGoods(paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("page",data);
        resultMap.put("goods",listOrder);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);

    }

    /**
     * 已入库 包裹 查询
     * @param paramMap
     * 具体参数 deliveryOrderNo 快递单号； deliveryNo快递公司名称； goodsName 货物名称； orderTime 日期 格式 2019-10-01；
     * @return
     */
    @PostMapping("/find/warehoused")
    public ResponseEntity<ResultModel> Warehoused(@RequestParam Map<String, Object> paramMap){
        paramMap.put("customerId", UserThreadContext.getUser().getId());
        //TODO 防止没有传参出错，初始化分页参数
        if( !paramMap.containsKey("pageNumber")){
            paramMap.put("pageNumber",0);
        }
        if( !paramMap.containsKey("pageSize")){
            paramMap.put("pageSize",10);
        }
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(),paramMap.get("pageSize").toString());
        paramMap.put("pageNumber",data.getPageNumber());
        paramMap.put("pageSize",data.getPageSize());
        int count =goodsService.countFindAll(paramMap);
        if(count < 1){
            data.setTotal(0);
        }else {
            data.setTotal(count);
        }
        List<GoodsVo> listGooods = goodsService.findGoods(paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("page",data);
        resultMap.put("goods",listGooods);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

    /**
     * 根据货物编码集合查询货物
     * @param goodsNos
     * @return
     */
    @PostMapping("/select/byGoodsNos")
    public ResponseEntity<ResultModel> selectByGoodsNos(List<String> goodsNos){
        List<GoodsVo> goodsVoList = goodsService.findByGoodsNos(goodsNos);
        return new ResponseEntity<>(ResultModel.ok(goodsVoList),HttpStatus.OK);
    }


    /**
     * 描述：申请打包操作，返回预计价格
     * @param paramMap
     * 具体参数：goodsNos 包裹编号集合； packType 打包类型 1、各自打包 2、合并打包； TransportationRouteId  运送方式Id , routeType 1、空运，2、海运
     * @return
     */
    @PostMapping("/apply/pack")
    public ResponseEntity<ResultModel> applyPack(@RequestParam Map<String, Object> paramMap){
        log.info("UserThreadContext.getUser():{}",UserThreadContext.getUser());
        paramMap.put("customerId",UserThreadContext.getUser().getId());
        return goodsService.applyPack(paramMap);

    }

    /**
     * 描述 ：确认申请打包操作
     * @param confirmPackReqVo
     * 具体参数：goodsNos 包裹编号集合； packType 打包类型 1、各自打包 2、合并打包； TransportationRouteId  运送方式Id；addressId  收货地址
     * @return
     */
    @PostMapping("/confirm/pack")
    public ResponseEntity<ResultModel> confirmPacking(ConfirmPackReqVo confirmPackReqVo){
        return goodsService.confirmPacking(confirmPackReqVo);
    }

    /**
     * 查询 未打包/待打包 包裹
     * @param paramMap
     * @param session
     * @return
     */
    @PostMapping("/find/packing/list")
    public ResponseEntity<ResultModel> findPacking(@RequestParam Map<String, Object> paramMap, HttpSession session){
        Customer customer = UserThreadContext.getUser();
        paramMap.put("customerId",customer.getId());
        //TODO 防止没有传参出错，初始化分页参数
        if( !paramMap.containsKey("pageNumber")){
            paramMap.put("pageNumber",0);
        }
        if( !paramMap.containsKey("pageSize")){
            paramMap.put("pageSize",10);
        }
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(),paramMap.get("pageSize").toString());
        paramMap.put("pageNumber",data.getPageNumber());
        paramMap.put("pageSize",data.getPageSize());
        int count = customerPackService.countFindPageNumber(paramMap, false);
        if(count < 1){
            data.setTotal(0);
        }
        List<GoodsPackVo> customerPackList = customerPackService.findPacking(paramMap);
        data.setTotal(customerPackList.size());
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("customerPack",customerPackList);
        resultMap.put("page",data);

        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

    // TODO 查询 待收货 的包裹
    /**
     * 查询 待收货/未发货 包裹
     * @param paramMap
     * @return
     */
    @PostMapping("/find/toBeShipped/list")
    public ResponseEntity<ResultModel> toBeShipped(@RequestParam Map<String, Object> paramMap){
        Customer customer = UserThreadContext.getUser();
        paramMap.put("customerId",customer.getId());
        //TODO 防止没有传参出错，初始化分页参数
        if( !paramMap.containsKey("pageNumber")){
            paramMap.put("pageNumber",0);
        }
        if( !paramMap.containsKey("pageSize")){
            paramMap.put("pageSize",10);
        }
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(),paramMap.get("pageSize").toString());
        paramMap.put("pageSize",data.getPageSize());
        paramMap.put("pageNumber",data.getPageNumber());

        int count =customerPackService.countFindPageNumber(paramMap, true);
        if(count < 1){
            data.setTotal(0);
        }else {
            data.setTotal(count);
        }

        List<GoodsPackVo> customerPackList = customerPackService.findPacked(paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("page",data);
        resultMap.put("customerPack",customerPackList);

        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

    /**
     * 查看相关打包的包裹里面有哪些包裹
     * @param businessNumber
     * @return
     */
    @PostMapping("/find/by/businessNumber/goods/list")
    public ResponseEntity<ResultModel> findPackage(@RequestParam String businessNumber){
        List<GoodsVo> list = customerPackService.findByBusinessNumberToGoodsNos(businessNumber);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("goods",list);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

    // TODO 拆包加上只有 未打包 和 已打包 的包裹才能申请拆包的条件限制，未打包的订单可以直接拆包  \\已完成
    /**
     * 申请拆包操作
     * @param businessNumber
     * @return
     */
    @PostMapping("/unpack")
    public ResponseEntity<ResultModel> unpack(@RequestParam String businessNumber, HttpSession session){
        Customer customer = UserThreadContext.getUser();
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR),HttpStatus.OK);
        }
        int customerId = customer.getId();
        int result = customerPackService.unpack(businessNumber,customerId);
        if(result == 102){
            return new ResponseEntity<>(ResultModel.error(USER_ERROR),HttpStatus.OK);
        }
        if(result == 103){
            return new ResponseEntity<>(ResultModel.error(GOODS_PACK_TYPE_ERROR),HttpStatus.OK);
        }
        if(result == -100){
            return new ResponseEntity<>(ResultModel.error(ACTIVITY_PACK_UNPACK_ERROR),HttpStatus.OK);
        }
        if(result < 1){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    // TODO 加上只有已打包包裹才能发货的限制
    /**
     * 确认发货操作
     * @param confirmShipmentReqVo
     * 具体参数 ： businessNumbers  ：流水号
     */
    @PostMapping("/confirm/shipment")
    public ResponseEntity<ResultModel> confirmShipment(ConfirmShipmentReqVo confirmShipmentReqVo){
//        Customer customer = UserThreadContext.getUser();
//        paramMap.put("id",customer.getId());
        return customerPackService.confirmShipment(confirmShipmentReqVo);

    }

  /*  @PostMapping("/find/confirm/shipment")
    public ResponseEntity<ResultModel> findConfirmShipment(@RequestParam Map<String, Object> paramMap, HttpSession session){
        return customerPackService.confirmShipment(paramMap);
//        int result = customerPackService.confirmShipment(paramMap);
//        if(result < 1){
//            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }*/


    /**
     * 路线查询
     * @param routeType
     * @return
     */
    @GetMapping("/find/route")
    public ResponseEntity<ResultModel> findRoute(Integer routeType){
        Map<String,Object> resultMap = new HashMap<>();
        List<TransportationRoute> transportationRouteList = routeService.findRoute(routeType);
        resultMap.put("transportationRoute",transportationRouteList);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

    @PostMapping("/checkPicture")
    public ResponseEntity<ResultModel> checkPicture(String deliveryOrderNo){
        return goodsService.checkPicture(ImageType.GoodsInWarePicture.toString(), deliveryOrderNo);
    }

    @PostMapping("/checkOrderPicture")
    public ResponseEntity<ResultModel> checkOrderPicture(String orderNumber){
        return goodsService.checkPicture(ImageType.CustomerPackPicture.toString(), orderNumber);
    }
}
