package com.example.warehouse.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.RoutePrice;
import com.example.warehouse.entity.TransportationRoute;
import com.example.warehouse.entity.User;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.routePrice.RoutePriceMapper;
import com.example.warehouse.mapper.transportationRoute.TransportationRouteMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.TransportationRouteService;
import com.example.warehouse.vo.route.PackageRoutePriceVo;
import com.example.warehouse.vo.route.RouteRequestVo;
import com.example.warehouse.vo.route.RouteVo;
import com.example.warehouse.vo.route.SmallPackageRoutePriceVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.rmi.Remote;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.example.warehouse.enums.ResultStatus.*;

/**
 * @author lrq
 */
@Service
public class TransportationRouteServiceImpl implements TransportationRouteService {

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private TransportationRouteMapper routeMapper;
    @Autowired
    private RoutePriceMapper priceMapper;

    @Autowired
    private CustomerPackMapper customerPackMapper;

    @Override
    public int countSelectAll(Map<String, Object> paramMap) {
        return routeMapper.countSelectAll(paramMap);
    }

    @Override
    public List<RouteVo> findAll(Map<String, Object> paramMap) {
        return routeMapper.findAll(paramMap);
    }

    @Override
    public ResponseEntity<ResultModel> findAllRoute(RouteRequestVo routeRequestVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
            routeRequestVo.setCommercialAreaId(user.getCommercialAreaId());
        }

        Map<String, Object> resultMap = new HashMap<>();
        PageData data = PageHelp.editPage(routeRequestVo);
        int count = routeMapper.selectRouteCount(routeRequestVo);
        if (count < 1) {
            data.setTotal(0);
            resultMap.put("page", data);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } else {
            routeRequestVo.setPageNumber(data.getPageNumber());
            List<RouteVo> routeVo = routeMapper.selectAllRoute(routeRequestVo);
            data.setTotal(count);
            resultMap.put("page",data);
            resultMap.put("route",routeVo);
            return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<ResultModel> findById(int id) {
//        RouteVo routeVo = routeMapper.findById(id);

//        List<PackageRoutePriceVo> routePriceList = priceMapper.findBigPackageByRouteId(id);
//        routeVo.setPackageRoutePrices(routePriceList);
        RouteVo routeVo = routeMapper.findById(id);
        return  new ResponseEntity<>(ResultModel.ok(routeVo),HttpStatus.OK);
    }

    /**
     * @param map 具体参数：
     *            路线表参数：
         *            routeName 路线名称； englishName  英文名称   routeType  运输方式；   originatingPlace  始发地址；    destination   目的地；
         *            noteType  货币类型； transportationTime   预计运送时间； remoteFee  偏远费用；   remarks  备注；
     *
     *            先生成路线表在生成价格表
     *
     *            packagePrice  大包裹价格表 参数：
     *              routeId   路线id ； routeType  路线类型 ； countType  计算类型  1、重量  2、体积;
     *              startWeight 起始重量（按照大于计算）；endWeight  结尾重量（按照小于等于）；
     *              startvolume   起始体积（按照大于计算）；endVolume 结束体积（按照小于等于）；
     *              price  价格；remarks  备注； weightCarryType  重量进位；
     *              greaterWeight  大于这个重量需要叉车费； forkliftFee 叉车费；
     *
     *            smallPackagePrice  小包裹价格参数：
     *              routeId   路线id ； routeType  路线类型 ； countType  计算类型  1、重量  2、体积;
     *              smallPackageWeight  小包裹重量；peice  价格； remarks 备注；smallWeightCarryType  重量进位 1、大于0进0.5  2、大于0进1；
     *
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ResultModel> addRoute(Map<String, Object> map) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("新增路线价格操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        TransportationRoute route = JSONObject.parseObject(JSONObject.toJSONString(map), TransportationRoute.class);
        route.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        route.setOperationId(user.getId());
        if(route.getAfterWeightCarry() != null && route.getAfterWeightCarry() < route.getBeforeWeightCarry()){
            return new ResponseEntity<>(ResultModel.error(WEIGHT_CARRY_ERROR),HttpStatus.OK);
        }
        String str = (String) map.get("packagePrice");
        List<PackageRoutePriceVo> packageRoutePrices = JSONArray.parseArray(str,PackageRoutePriceVo.class);
        try {

            if(packageRoutePrices.size() < 1){
                return new ResponseEntity<>(ResultModel.error(ROUTE_UPDATE_ERROR),HttpStatus.OK);
            }

            AtomicBoolean bol = new AtomicBoolean(true);
            routeMapper.insertSelective(route);
            packageRoutePrices.forEach(packageRoutePrice -> {
                if(packageRoutePrice.getPrice() == null && packageRoutePrice.getPrice().equals("")){
                    bol.set(false);
                }
                RoutePrice routePrice = new RoutePrice();
                //路线id
                routePrice.setRouteId(route.getId());
                //路线类型
                routePrice.setRouteType(route.getRouteType());
                //包裹类型 1、小包裹 2、大包裹
                routePrice.setPackageType(2);
                //价格代码
                routePrice.setPriceNo(SequenceCode.gainSerialNo("rpc"));
                //大于这个重量需要 叉车费
//                routePrice.setGreaterWeight(packageRoutePrice.getGreaterWeight());
//                //叉车费
//                routePrice.setForkliftFee(packageRoutePrice.getForkliftFee());
//                //重量上限
//                routePrice.setUpperLimitWeight(packageRoutePrice.getUpperLimitWeight());
//                //第一长度上限
//                routePrice.setFirstLength(packageRoutePrice.getFirstLength());
//                //第二长度上限
//                routePrice.setSecondLength(packageRoutePrice.getSecondLength());
//                //敏感货物
//                routePrice.setSensitivePrice(packageRoutePrice.getSensitivePrice());
//                //单立方上线
//                routePrice.setCubeUpper(packageRoutePrice.getCubeUpper());
//                //体积重换算数 如果用户填0，则默认6000，防止报错
//                routePrice.setVolConversion(packageRoutePrice.getVolConversion().compareTo(0.0) == 0 ? 6000.0 : packageRoutePrice.getVolConversion());

                //价格
                routePrice.setPrice(packageRoutePrice.getPrice());
                //起始重量 （按照大于计算）
                routePrice.setStartWeight(packageRoutePrice.getStartWeight());
                //结尾重量（按照小于等于）
                routePrice.setEndWeight(packageRoutePrice.getEndWeight());
                //起始体积（按照大于计算）
                routePrice.setStartVolume(packageRoutePrice.getStartVolume());
                //结束体积（按照小于等于）
                routePrice.setEndVolume(packageRoutePrice.getEndVolume());
                //备注
                routePrice.setRemarks(packageRoutePrice.getRemarks());
                //重量进位类型 1、大于0进0.5   2、大于0进1
//                routePrice.setWeightCarryType(packageRoutePrice.getWeightCarryType());
                priceMapper.insertSelective(routePrice);
            });
            if(!bol.get()){
                System.out.println("价格未填写，返回");
                return new ResponseEntity<>(ResultModel.error(ROUTE_PRICE_NULL_ERROR),HttpStatus.OK);
            }
            transactionManager.commit(status);
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }

    }

    /**
     * 修改路线
     * @param map
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
     *   smallPackagePrice  参数：
     *      routeId   路线id ； routeType  路线类型 ； countType  计算类型  1、重量  2、体积;
     *      smallPackageWeight  小包裹重量 ； price  价格 ； remarks  备注
     *
     */
    @Override
    @Transactional
    public ResponseEntity<ResultModel> updateRoute(Map<String, Object> map) {
        TransportationRoute route = JSONObject.parseObject(JSONObject.toJSONString(map), TransportationRoute.class);

        if(route.getBeforeWeightCarry() != null && route.getAfterWeightCarry() < route.getBeforeWeightCarry()){
            return new ResponseEntity<>(ResultModel.error(WEIGHT_CARRY_ERROR),HttpStatus.OK);
        }

        String str = (String) map.get("packagePrice");
        List<PackageRoutePriceVo> bigPackList = JSONArray.parseArray(str, PackageRoutePriceVo.class);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("路线更新操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            TransportationRoute transportationRoute = routeMapper.selectByPrimaryKey(route.getId());
            route.setVersion(transportationRoute.getVersion());

            if(bigPackList.size() < 1){
                return new ResponseEntity<>(ResultModel.error(ROUTE_UPDATE_ERROR),HttpStatus.OK);
            }
            routeMapper.updateByPrimaryKeySelective(route);

            List<RoutePrice> routePriceList = priceMapper.findByRouteId(route.getId(),transportationRoute.getRouteType());
            List<Integer> priceIdList = new ArrayList<>();
            if(routePriceList.size() > 0){
                routePriceList.forEach(routePrice -> priceIdList.add(routePrice.getId()));
                priceMapper.deleteByIds(priceIdList);
            }
            // 大包裹
            bigPackList.forEach(packageRoutePrice -> {
                RoutePrice bigPack = new RoutePrice();
                //路线id
                bigPack.setRouteId(route.getId());
                //路线类型
                bigPack.setRouteType(route.getRouteType());
                //包裹类型 1、小包裹 2、大包裹
                bigPack.setPackageType(2);
                //价格代码
                bigPack.setPriceNo(SequenceCode.gainSerialNo("rpc"));
                //大于这个重量需要 叉车费
//                bigPack.setGreaterWeight(packageRoutePrice.getGreaterWeight());
//                //叉车费
//                bigPack.setForkliftFee(packageRoutePrice.getForkliftFee());
//                //重量上限
//                bigPack.setUpperLimitWeight(packageRoutePrice.getUpperLimitWeight());
//                //敏感货物
//                bigPack.setSensitivePrice(packageRoutePrice.getSensitivePrice());
//                //单立方上线
//                bigPack.setCubeUpper(packageRoutePrice.getCubeUpper());
//                //体积重换算数
//                bigPack.setVolConversion(packageRoutePrice.getVolConversion());
//                //第一长度上限
//                bigPack.setFirstLength(packageRoutePrice.getFirstLength());
//                //第二长度上限
//                bigPack.setSecondLength(packageRoutePrice.getSecondLength());

                //起始重量 （按照大于计算）
                bigPack.setStartWeight(packageRoutePrice.getStartWeight());
                //结尾重量（按照小于等于）
                bigPack.setEndWeight(packageRoutePrice.getEndWeight());
                //起始体积（按照大于计算）
                bigPack.setStartVolume(packageRoutePrice.getStartVolume());
                //结束体积（按照小于等于）
                bigPack.setEndVolume(packageRoutePrice.getEndVolume());
                //价格
                bigPack.setPrice(packageRoutePrice.getPrice());
                //备注
                bigPack.setRemarks(packageRoutePrice.getRemarks());
                //重量进位类型 1、大于0进0.5   2、大于0进1
//                bigPack.setWeightCarryType(packageRoutePrice.getWeightCarryType());
                priceMapper.insertSelective(bigPack);
            });
            transactionManager.commit(status);
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }

    }

    @Override
    public int deleteByIds(String ids) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("删除渠道路线操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            List<String> routeIds = Arrays.asList(ids.split(","));

            // 检查路线是否正在使用
            List<CustomerPack> customerPackList = customerPackMapper.selectIsRouteUse(routeIds);
            if(customerPackList != null && !customerPackList.isEmpty()){
                return -2;
            }
            routeMapper.deleteByIds(routeIds);
            routeIds.forEach(id -> {
                List<RoutePrice> priceList = priceMapper.findAll(Integer.parseInt(id));
                List<Integer> priceIds = new ArrayList<>();
                priceList.forEach(price ->priceIds.add(price.getId()));
                priceMapper.deleteByIds(priceIds);
            });
            transactionManager.commit(status);
        }catch (Exception e){
            e.printStackTrace();
            transactionManager.rollback(status);
            return -1;
        }
        return 0;
    }

    @Override
    public int enableRoute(int id) {
        return routeMapper.enableRoute(id);
    }

    @Override
    public int prohibitRoute(int id) {
        return routeMapper.prohibitRoute(id);
    }

    @Override
    public void deleteRoutePriceByPriceNo(String priceNo) {
        priceMapper.deleteByPriceNo(priceNo);
    }
}
