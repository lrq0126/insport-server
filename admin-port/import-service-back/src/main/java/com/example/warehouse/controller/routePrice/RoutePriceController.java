package com.example.warehouse.controller.routePrice;

import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.TransportationRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lrq
 * 2019年12月1日23:35:37
 */
@RestController
@RequestMapping("/routePrice")
public class RoutePriceController {

    @Autowired
    private TransportationRouteService routeService;

    /**
     * 根据 id / priceNo 删除相关
     * @return
     */
    @RequestMapping("/delete/routePrice")
    public ResponseEntity<ResultModel> deleteRoutePrice(@RequestParam String priceNo){
        routeService.deleteRoutePriceByPriceNo(priceNo);
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }
}
