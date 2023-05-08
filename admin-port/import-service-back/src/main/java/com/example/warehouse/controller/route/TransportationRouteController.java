package com.example.warehouse.controller.route;

import com.example.warehouse.log.SysLog;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.TransportationRouteService;
import com.example.warehouse.vo.route.RouteRequestVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.*;

/**
 * @author lrq
 * 2019年11月4日00:03:54
 *
 */
@RestController
@RequestMapping("/route/transportationRoute")
public class TransportationRouteController {

    @Autowired
    private TransportationRouteService routeService;

    @PostMapping("/find/list")
    public ResponseEntity<ResultModel> findAllRoute(RouteRequestVo routeRequestVo){
        return routeService.findAllRoute(routeRequestVo);
        /*Map<String, Object> resultMap = new HashMap<>();
        PageData data = (PageData) PageHelp.initPage(paramMap).get("page");
        int count = routeService.countSelectAll(paramMap);
        if (count < 1) {
            data.setTotal(0);
            resultMap.put("page", data);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } else {
            List<RouteVo> routeVo = routeService.findAll(paramMap);
            data.setTotal(count);
            resultMap.put("page",data);
            resultMap.put("route",routeVo);
            return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
        }*/

    }

    @PostMapping("/find/price")
    public ResponseEntity<ResultModel> findRoutePrice(@RequestParam int id){
        return routeService.findById(id);
    }

    /**
     * 新增路线
     * @param paramMap
     * 具体参数：
     *   路线表参数：
     *      routeName 路线名称； englishName  英文名称   routeType  运输方式；
     *      originatingPlace  始发地址；    destination   目的地；
     *      noteType  货币类型； transportationTime   预计运送时间； remoteFee  偏远费用；   remarks  备注；
     *   先生成路线表在生成价格表
     *   价格表参数：
     *      routeId   路线id ； routeType  路线类型 ； countType  计算类型  1、重量  2、体积;
     *      startWeight 起始重量（按照大于计算）；endWeight  结尾重量（按照小于等于）；
     *      startVolume   起始体积（按照大于计算）；endVolume 结束体积（按照小于等于）；
     *      price  价格；
     * @return
     */
//    @RequiresPermissions("transportationRoute:add")
    @PostMapping("/add")
    @SysLog(value = "新增渠道[渠道名称]",type = SysLog.Type.ADD,contentId = "Map.paramMap.routeName")
    public ResponseEntity<ResultModel> addRoute(@RequestParam Map<String, Object> paramMap){
        return routeService.addRoute(paramMap);
//        if(result > 0){
//            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
//        }else if(result == -2){
//            return new ResponseEntity<>(ResultModel.error(ROUTE_PRICE_NULL_ERROR),HttpStatus.OK);
//        }else {
//            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
//        }
    }
    /**
     * 修改路线
     * @param paramMap
     * 具体参数：
     *   路线表参数：
     *      id  id;
     *      routeName 路线名称； englishName  英文名称   routeType  运输方式；   originatingPlace  始发地址；    destination   目的地；
     *      noteType  货币类型； transportationTime   预计运送时间； remoteFee  偏远费用；   remarks  备注； routePrice   价格内容；
     *   先生成路线表在生成价格表
     *   价格表 参数 用 routePrice 属性包装起来 ：
     *      routeId   路线id ； routeType  路线类型 ； countType  计算类型  1、重量  2、体积;
     *      startWeight 起始重量（按照大于计算）；endWeight  结尾重量（按照小于等于）；
     *      startvolume   起始体积（按照大于计算）；endVolume 结束体积（按照小于等于）；
     *      price  价格； priceNo  价格代码
     * @return
     */
    @RequiresPermissions("transportationRoute:update")
    @PostMapping("/update")
    @SysLog(value = "修改渠道信息[渠道名称]",type = SysLog.Type.UPDATE,contentId = "Map.paramMap.routeName")
    public ResponseEntity<ResultModel> updateRoute(@RequestParam Map<String, Object> paramMap){
        return routeService.updateRoute(paramMap);
//        if(result == 1){
//            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
//        }else if(result == -2){
//            return new ResponseEntity<>(ResultModel.error(ROUTE_UPDATE_ERROR),HttpStatus.OK);
//        }else {
//            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
//        }

    }

    @RequiresPermissions("transportationRoute:update")
    @PostMapping("/enable")
    @SysLog(value = "启用渠道[渠道名称]",type = SysLog.Type.ENABLED,contentId = "#routeName")
    public ResponseEntity<ResultModel> enableRoute(@RequestParam("routeName")String routeName,@RequestParam Integer id){
        routeService.enableRoute(id);
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    @RequiresPermissions("transportationRoute:update")
    @PostMapping("/prohibit")
    @SysLog(value = "禁用渠道[渠道名称]",type = SysLog.Type.DISABLED,contentId = "#routeName")
    public ResponseEntity<ResultModel> prohibitRoute(@RequestParam("routeName")String routeName,@RequestParam Integer id){
        routeService.prohibitRoute(id);
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    @RequiresPermissions("transportationRoute:delete")
    @PostMapping("/delete")
    @SysLog(value = "删除渠道[渠道名称]",type = SysLog.Type.DISABLED,contentId = "#routeName")
    public ResponseEntity<ResultModel> deleteRoute(@RequestParam("routeName")String routeName,@RequestParam String ids){
        int i = routeService.deleteByIds(ids);
        if(i == -2){
            return new ResponseEntity<>(ResultModel.error(TRANSPORT_ROUTE_USE_ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    @PostMapping("/getTransportationRouteList")
    public ResponseEntity<ResultModel> getTransportationRouteList(Integer countryId){
        return routeService.getTransportationRouteList(countryId);
    }
}
