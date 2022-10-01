package com.example.warehouse.service.sys.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.Goods;
import com.example.warehouse.entity.image.Images;
import com.example.warehouse.enums.image.ImageType;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.image.ImagesMapper;
import com.example.warehouse.service.sys.AnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ImagesMapper imagesMapper;

    /**
     * 预报包裹信息，如果存在，则不做处理
     * @param deliveryOrderNo
     * @param loginName
     * @param imgUrl
     */
    @Transactional
    @Override public void saveDeliveryOrderNoAndLoginName(String deliveryOrderNo, String loginName, String imgUrl) {
        log.info("保存签收系统识别的面单信息");
        // 检查单号是否存在系统，存在则不再录入
        int i = goodsMapper.checkGoodsIn(deliveryOrderNo);
        if ( i > 0 ){
            log.info("快递已经存在系统或者客户已经预报");
            return;
        }else {
            Goods goods = new Goods();
            goods.setCreateId(1);
            goods.setCreateTime(DateUtil.timestamp2String(new Date()));
            goods.setStatus(1);
            goods.setGoodsType(3);
            goods.setDeliveryOrderNo(deliveryOrderNo);
            goods.setGoodsNo(SequenceCode.gainSerialNo("GOODS"));
            goods.setLoginName(loginName);
            goods.setMessage("系统自动识别");
            goods.setPackageNum(1);

            Customer customer = customerMapper.getCustomerByLoginName(loginName);
            if ( null == customer ){
                // 如果系统没有该会员号，则不处理，面单识别失败
                log.error("系统不存在该会员号，识别失败");
                return;
            }
            goods.setGoodsName("未预报名称"); // 区别于前端自动文字：客户未预报名称
            goods.setCustomerNo(customer.getCustomerNo());

            Images images = new Images();
            images.setCreateId(1);
            images.setPicUrl(imgUrl);
            images.setCreateTime(new Date());
            images.setStatus(1);
            images.setContentId(deliveryOrderNo);
            images.setPicType(ImageType.GoodsPic.toString());
            images.setImageName("快递");
            images.setSortNo(1);
            // 保存图片
            imagesMapper.insert(images);
            // 保存货物信息
            goodsMapper.insert(goods);
            log.info("保存成功");
        }
    }
}
