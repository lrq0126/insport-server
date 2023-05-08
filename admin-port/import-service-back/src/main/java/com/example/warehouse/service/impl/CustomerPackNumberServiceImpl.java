package com.example.warehouse.service.impl;

import com.example.warehouse.common.CalculationUtils;
import com.example.warehouse.entity.*;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.customerPackNumber.CustomerPackNumberMapper;
import com.example.warehouse.mapper.customerPackReceiverAddress.CustomerPackReceiverAddressDao;
import com.example.warehouse.mapper.goods2pack.Goods2packMapper;
import com.example.warehouse.mapper.transportationRoute.TransportationRouteMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.CustomerPackNumberService;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customer.CustomerPackNumberVo;
import com.example.warehouse.vo.customer.CustomerPackVo;
import com.example.warehouse.vo.route.RouteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.warehouse.enums.ResultStatus.ERROR;

@Service
public class CustomerPackNumberServiceImpl implements CustomerPackNumberService {

    @Autowired
    private CustomerPackNumberMapper customerPackNumberMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Resource
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private TransportationRouteMapper routeMapper;
    @Resource
    private CalculationUtils calculationUtils;


    @Resource
    private CustomerPackReceiverAddressDao customerPackReceiverAddressDao;

    @Autowired
    private Goods2packMapper goods2packMapper;

    @Override
    public List<CustomerPackNumber> findToBeShipped(Map<String, Object> map) {

        return customerPackNumberMapper.findToBeShipped(map);
    }

    @Override
    public int countSelectAll(Map<String, Object> map) {
        return customerPackNumberMapper.countSelectAll(map);
    }

    @Override
    public List<CustomerPackNumberVo> findByCustomerPackId(Integer customerPackId) {

        List<CustomerPackNumberVo> customerPackNumberVoList = customerPackNumberMapper.findByPackId(customerPackId);
        for (CustomerPackNumberVo customerPackNumberVo : customerPackNumberVoList) {
            customerPackNumberVo.setGoodsNum(customerPackNumberVo.getGoodsVoList().size());
        }

        return customerPackNumberVoList;
    }

    @Override
    public CustomerPackNumberVo findByCustomerPackNumberPackOrder(Map<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String packOrder = (String) paramMap.get("packOrder");
        CustomerPackNumberVo customerPackNumber = customerPackNumberMapper.findByPackOrder(packOrder);

        List<GoodsVo> goodsVoList;

        if(customerPackNumber == null){
            customerPackNumber = new CustomerPackNumberVo();
            CustomerPackVo customerPack = customerPackMapper.findByOrderNumber(packOrder);
            customerPackNumber.setCustomerPackId(customerPack.getId());
            customerPackNumber.setPackOrder(customerPack.getOrderNumber());
            customerPackNumber.setLength(customerPack.getLength());
            customerPackNumber.setWidth(customerPack.getWidth());
            customerPackNumber.setHeight(customerPack.getHeight());
            customerPackNumber.setActualWeight(customerPack.getActualWeight());
            customerPackNumber.setSettlementWeight(customerPack.getSettlementWeight());
            customerPackNumber.setActualVol(customerPack.getActualVol());
            customerPackNumber.setSettlementVol(customerPack.getSettlementVol());
            customerPackNumber.setSettlementVol(customerPack.getSettlementVol());
            customerPackNumber.setOutStorageTime(sdf.format(customerPack.getOutStorageTime()));
            customerPackNumber.setPackTime(sdf.format(customerPack.getPackTime()));
            customerPackNumber.setActualPrice(customerPack.getIncidental());
            customerPackNumber.setGoodsNum(customerPack.getGoodsSum());
            customerPackNumber.setGoodsNos(customerPack.getGoodsNo());

            List<Goods2pack> goods2packList = goods2packMapper.findByPackId(customerPack.getId());

            List<Integer> goodsIds = new ArrayList<>();
            goods2packList.forEach(goods2pack -> goodsIds.add(goods2pack.getGoodsId()));

            goodsVoList = goodsMapper.selectByIds(goodsIds);
            customerPackNumber.setGoodsVoList(goodsVoList);

            return customerPackNumber;

        }else {
            List<Integer> sonPackIds = new ArrayList<>();
            sonPackIds.add(customerPackNumber.getId());
            List<Integer> goodsIds = goods2packMapper.findGoodsIdBySonPackIdList(sonPackIds);
            goodsVoList = goodsMapper.selectByIds(goodsIds);

            customerPackNumber.setGoodsVoList(goodsVoList);
            return customerPackNumber;
        }

    }

    @Override
    public List<GoodsVo> findGoodsByPackOrder(Map<String, Object> paramMap) {
        String packOrder = (String) paramMap.get("packOrder");
        CustomerPackNumberVo customerPackNumberVo = customerPackNumberMapper.findByPackOrder(packOrder);
        List<GoodsVo> goodsVoList;

        if(customerPackNumberVo != null){

            goodsVoList = customerPackNumberVo.getGoodsVoList();

        }else {

            CustomerPackVo customerPack = customerPackMapper.findByOrderNumber(packOrder);
            List<Goods2pack> goods2packList = goods2packMapper.findByPackId(customerPack.getId());

            List<Integer> goodsIds = new ArrayList<>();
            goods2packList.forEach(goods2pack -> goodsIds.add(goods2pack.getGoodsId()));

            goodsVoList = goodsMapper.selectByIds(goodsIds);

        }
        return goodsVoList;
    }

    @Override
    public ResponseEntity<ResultModel> findSonPackListByBusinessNumber(String businessNumber) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            CustomerPackVo customerPack = customerPackMapper.findByBusinessNumber(businessNumber);
            if(customerPack == null){
                return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
            }
//            routeMapper.findById();
//            List<CustomerPackNumberVo> customerPackNumbers = customerPackNumberMapper.selectByBusinessNumber(businessNumber);
            List<CustomerPackNumberVo> customerPackNumbers = customerPack.getCustomerPackNumbers();
            if(!CollectionUtils.isEmpty(customerPackNumbers)){
                RouteVo routeVo = customerPack.getRouteVo();
                if(routeVo != null && routeVo.getVolConversion() != null){
                    Double volConversion = routeVo.getVolConversion();
                    int carryType;
                    // 渠道计算价格的类型是重量计算的时候，取进位规则； 按体积计算的时候，进位规则默认取2（即大于0进1）
                    if(routeVo.getRouteType() == 1){
                        carryType = routeVo.getBeforeWeightCarryType() == null ? routeVo.getAfterWeightCarryType() : routeVo.getBeforeWeightCarryType();
                    }else {
                        carryType = 1;
                    }
                    for (CustomerPackNumberVo customerPackNumber : customerPackNumbers) {
                        if(customerPackNumber.getActualVol() != null){
                            Double volWeight = customerPackNumber.getActualVol() * 1000000 / volConversion;
                            customerPackNumber.setVolWeight(calculationUtils.weightCarry(volWeight, carryType));
                        }

                    }

                }

            }

            resultMap.put("customerPackNumbers", customerPackNumbers);

            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
