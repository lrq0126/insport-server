package com.dwgj.mlxydedicatedline.controller.routePrice;

import com.dwgj.mlxydedicatedline.entity.TransportationRoute;
import com.dwgj.mlxydedicatedline.resultType.PageData;
import com.dwgj.mlxydedicatedline.resultType.PageHelp;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.RoutePriceService;
import com.dwgj.mlxydedicatedline.service.TransportationRouteService;
import com.dwgj.mlxydedicatedline.vo.route.RouteForTypeVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteQuotationVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author lrq
 * 2019年11月5日18:33:26
 */
@RestController
@RequestMapping("/routePrice/price")
public class PriceController {

    @Autowired
    private TransportationRouteService routeService;
    @Autowired
    private RoutePriceService routePriceService;
    /**
     * 2019年11月6日00:46:05
     * @param paramMap
     * 具体参数：
     *     routeName 路线名称；routeType 运输类型；
     * @return
     */
    @PostMapping("/find/route/list")
    public ResponseEntity<ResultModel> findAllRoute(@RequestParam Map<String, Object> paramMap){

        if( !paramMap.containsKey("pageNumber")){
            paramMap.put("pageNumber",0);
        }
        if( !paramMap.containsKey("pageSize")){
            paramMap.put("pageSize",10);
        }
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(),paramMap.get("pageSize").toString());
        paramMap.put("pageNumber",data.getPageNumber());
        paramMap.put("pageSize",data.getPageSize());
        Map<String,Object> resultMap = new HashMap<>();
        int count = routeService.findRouteAll(paramMap);
        if(count < 1){
            return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
        }
        List<RouteQuotationVo> routeQuotationVos = routePriceService.quotationPrice(paramMap);
        if(routeQuotationVos == null){
            data.setTotal(0);
        }else {
            data.setTotal(routeQuotationVos.size());
        }
        resultMap.put("page",data);
        List<TransportationRoute> routeList = routeService.findAllRoute(paramMap);
        resultMap.put("route",routeList);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

    @PostMapping("/find/by/routeId")
    public ResponseEntity<ResultModel> findByRouteId(@RequestParam Integer routeId) {
        Map<String,Object> resultMap = new HashMap<>();

        RouteVo routeVo = routeService.findByRouteId(routeId);

        resultMap.put("route",routeVo);

        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

    /**
     * 2019年11月5日18:35:37
     * @param paramMap
     * 具体参数：
     *      routeId 路线 id；kg  包裹重量； vol  包裹体积
     * @return
     */
    @PostMapping("/calculation")
    public ResponseEntity<ResultModel> calculation(@RequestParam Map<String, Object> paramMap){
        BigDecimal price = routePriceService.calculation(paramMap);
        return new ResponseEntity<>(ResultModel.ok(price),HttpStatus.OK);
    }

    /**
     * 根据路线类型 获取 路线
     * @param routeType
     * @return
     */
    @PostMapping("/find/route/by/routeType/list")
    public ResponseEntity<ResultModel> findAllRoute(@RequestParam Integer routeType){
        List<TransportationRoute> routeList = routeService.findRoute(routeType);
        List<RouteForTypeVo> routeForTypeVos = new ArrayList<>();
        routeList.forEach(route -> {
            RouteForTypeVo routeForTypeVo = new RouteForTypeVo();
            routeForTypeVo.setRouteId(route.getId());
            routeForTypeVo.setRouteName(route.getRouteName());
            routeForTypeVos.add(routeForTypeVo);
        });
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("route",routeForTypeVos);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }
    /**
     * 报价系统
     * @param paramMap
     *      参数： 重量 ； 体积 ； 长 ； 宽 ； 高 ； 渠道类型 ； 具体路线id
     * @return
     */
    @PostMapping("/quotation")
    public ResponseEntity<ResultModel> quotation(@RequestParam Map<String, Object> paramMap){
        if( !paramMap.containsKey("pageNumber")){
            paramMap.put("pageNumber",0);
        }
        if( !paramMap.containsKey("pageSize")){
            paramMap.put("pageSize",10);
        }
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(),paramMap.get("pageSize").toString());
        paramMap.put("pageNumber",data.getPageNumber());
        paramMap.put("pageSize",data.getPageSize());
        Map<String,Object> resultMap = new HashMap<>();
        int count = routeService.findRouteAll(paramMap);
        if(count < 1){
            return new ResponseEntity<>(ResultModel.ok("暂无报价信息"),HttpStatus.OK);
        }

        List<RouteQuotationVo> routeQuotationVos = routePriceService.quotationPrice(paramMap);

        if (routeQuotationVos != null){  // 排序
            Collections.sort(routeQuotationVos);
        }

        data.setTotal(routeQuotationVos == null ? 0 : routeQuotationVos.size());
        resultMap.put("page",data);
        resultMap.put("routePrices",routeQuotationVos);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

}
