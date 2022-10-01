package com.dwgj.mlxydedicatedline.service.impl;

import com.alibaba.fastjson.JSON;
import com.dwgj.mlxydedicatedline.commons.DateUtil;
import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.*;
import com.dwgj.mlxydedicatedline.entity.activityReward.ActivityPoster;
import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCoupons;
import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCouponsCenter;
import com.dwgj.mlxydedicatedline.entity.CustomerPack;
import com.dwgj.mlxydedicatedline.entity.CustomerPackReceiverAddress;
import com.dwgj.mlxydedicatedline.entity.customer.CustomerIntegral;
import com.dwgj.mlxydedicatedline.entity.customerPack.CustomerPackPriceDetail;
import com.dwgj.mlxydedicatedline.entity.customerPack.PackValuation;
import com.dwgj.mlxydedicatedline.entity.insurance.Insurance;
import com.dwgj.mlxydedicatedline.entity.insurance.InsurancePrice;
import com.dwgj.mlxydedicatedline.entity.customerPack.PackInsurancePrice;
import com.dwgj.mlxydedicatedline.entity.ping.PingMain;
import com.dwgj.mlxydedicatedline.entity.ping.PingMainOrder;
import com.dwgj.mlxydedicatedline.entity.ping.PingMember;
import com.dwgj.mlxydedicatedline.enums.coupons.customerCouponsStatusEnum;
import com.dwgj.mlxydedicatedline.enums.operating.IntegralsType;
import com.dwgj.mlxydedicatedline.mapper.*;
import com.dwgj.mlxydedicatedline.mapper.activityReward.ActivityPosterMapper;
import com.dwgj.mlxydedicatedline.mapper.coupons.CustomerCouponsCenterMapper;
import com.dwgj.mlxydedicatedline.mapper.coupons.CustomerCouponsMapper;
import com.dwgj.mlxydedicatedline.mapper.customer.CustomerIntegralMapper;
import com.dwgj.mlxydedicatedline.mapper.customer2address.Customer2addressMapper;
import com.dwgj.mlxydedicatedline.mapper.customerMoney.CustomerMoneyMapper;
import com.dwgj.mlxydedicatedline.mapper.CustomerPackMapper;
import com.dwgj.mlxydedicatedline.mapper.customerPack.CustomerPackPriceDetailMapper;
import com.dwgj.mlxydedicatedline.mapper.customerPack.PackValuationMapper;
import com.dwgj.mlxydedicatedline.mapper.customerPackReceiverAddress.CustomerPackReceiverAddressDao;
import com.dwgj.mlxydedicatedline.mapper.goods2address.Goods2addressMapper;
import com.dwgj.mlxydedicatedline.mapper.goods2pack.Goods2packMapper;
import com.dwgj.mlxydedicatedline.mapper.insurance.InsuranceMapper;
import com.dwgj.mlxydedicatedline.mapper.insurance.InsurancePriceMapper;
import com.dwgj.mlxydedicatedline.mapper.customerPack.PackInsurancePriceMapper;
import com.dwgj.mlxydedicatedline.mapper.ping.PingMainMapper;
import com.dwgj.mlxydedicatedline.mapper.ping.PingMainOrderMapper;
import com.dwgj.mlxydedicatedline.mapper.ping.PingMemberMapper;
import com.dwgj.mlxydedicatedline.mapper.timeLimitActivity.TimeLimitActivityDao;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.CustomerPackService;
import com.dwgj.mlxydedicatedline.utils.CustomerPackUtil;
import com.dwgj.mlxydedicatedline.vo.GoodsPackVo;
import com.dwgj.mlxydedicatedline.vo.GoodsVo;
import com.dwgj.mlxydedicatedline.vo.pack.ConfirmShipmentReqVo;
import com.dwgj.mlxydedicatedline.vo.pack.CustomerPackRespVo;
import com.dwgj.mlxydedicatedline.vo.pin.PingMainRespVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.*;
import static com.dwgj.mlxydedicatedline.enums.ResultStatus.ERROR;
import static com.dwgj.mlxydedicatedline.enums.goods.GoodsTypeEnums.WAIT_OUT_WAREHOUSE;

/**
 * @author lrq
 */
@Service
@Slf4j
public class CustomerPackServiceImpl implements CustomerPackService {

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CustomerMoneyMapper customerMoneyMapper;
    @Autowired
    private CustomerAccountMapper accountMapper;
    @Autowired
    private CustomerAddressMapper customerAddressMapper;
    @Autowired
    private TransportationRouteMapper routeMapper;
    @Autowired
    private Customer2addressMapper customer2addressMapper;
    @Autowired
    private Goods2packMapper goods2packMapper;
    @Autowired
    private Goods2addressMapper goods2addressMapper;
    @Autowired
    private CustomerPackReceiverAddressDao customerPackReceiverAddressDao;
    @Autowired
    private CustomerPackPriceDetailMapper customerPackPriceDetailMapper;
    @Resource
    private TimeLimitActivityDao timeLimitActivityDao;

    @Autowired
    private CustomerCouponsMapper customerCouponsMapper;
    @Autowired
    private CustomerCouponsCenterMapper customerCouponsCenterMapper;

    @Autowired
    private InsuranceMapper insuranceMapper;
    @Autowired
    private InsurancePriceMapper insurancePriceMapper;
    @Autowired
    private PackInsurancePriceMapper packInsurancePriceMapper;
    @Autowired
    private CustomerIntegralMapper customerIntegralMapper;
    @Autowired
    private PackValuationMapper packValuationMapper;

    @Autowired
    private PingMainMapper pingMainMapper;
    @Autowired
    private PingMemberMapper pingMemberMapper;
    @Autowired
    private PingMainOrderMapper pingMainOrderMapper;

    @Autowired
    private ActivityPosterMapper activityPosterMapper;

    /**
     * 查询待打包和已打包
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<GoodsPackVo> findPacking(Map<String, Object> paramMap) {
        Integer packType = 0;
        List<Integer> packTypes = new ArrayList<>();
        if (paramMap.containsKey("packType")) {
            packType = Integer.valueOf((String) paramMap.get("packType"));
        }
        List<GoodsPackVo> goodsPackVoList;
        if (packType != 0) {
            packTypes.add(packType);
            paramMap.put("packTypes", packTypes);
        } else {
            packTypes.add(1);
            packTypes.add(2);
            paramMap.put("packTypes", packTypes);
        }
        goodsPackVoList = customerPackMapper.findToBeShipped(paramMap);
        goodsPackVoList.forEach(goodsPackVol -> goodsPackVol.setGoodsNumber(goodsPackVol.getGoods2pack().size()));
        return goodsPackVoList;
    }

    @Override
    public List<GoodsPackVo> findPacked(Map<String, Object> paramMap) {
        Integer packType = 0;
        List<Integer> packTypes = new ArrayList<>();
        if (paramMap.containsKey("packType")) {
            packType = Integer.valueOf((String) paramMap.get("packType"));
        }
        if (packType != 0) {
            packTypes.add(packType);
            paramMap.put("packTypes", packTypes);
        } else {
            packTypes.add(3);
            packTypes.add(4);
            paramMap.put("packTypes", packTypes);
        }
        List<GoodsPackVo> goodsPackVoList = customerPackMapper.findToBeShipped(paramMap);
        goodsPackVoList.forEach(goodsPackVol -> goodsPackVol.setGoodsNumber(goodsPackVol.getGoods2pack().size()));
        return goodsPackVoList;
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> confirmShipment(ConfirmShipmentReqVo confirmShipmentReqVo) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("customer_confirm_shipment");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String businessNumbers = confirmShipmentReqVo.getBusinessNumber();
            CustomerPack customerPack = customerPackMapper.findByBusinessNumber(businessNumbers);
            if (customerPack == null) {
                return new ResponseEntity<>(ResultModel.error(ORDERNUMBER_NOT_EXSIT), HttpStatus.OK);
            }
            // 检验是否为拼团订单并判断当前拼团订单是否已全部打包完
            PingMainOrder pingMainOrder = pingMainOrderMapper.selectOrderByOrderNumber(customerPack.getOrderNumber());
            if (pingMainOrder != null) {
//                // 检验拼团订单是否已经完全打包完成
//                PingMain pingMain = pingMainMapper.selectById(pingMainOrder.getPId());
                // 获取当前拼团订单的所有成员
                List<PingMember> pingMemberList = pingMemberMapper.getMembersByPinId(String.valueOf(pingMainOrder.getPId()));
                List<Integer> customerIdList = pingMemberList.stream().map(PingMember::getCustomerId).distinct().collect(Collectors.toList());
                // 检查所有成员的包裹是否已打包完成
                // 1、获取所有已经打包好的拼团成员的数量
                int packedPinMainOrderNum = pingMainOrderMapper.selectPackedOrderByPid(pingMainOrder.getPId());
                // 2、对比成员数量和已打包订单数量
                if (customerIdList.size() != packedPinMainOrderNum) {
                    return new ResponseEntity<>(ResultModel.error(PIN_MAIN_IS_NOTP_ACKED_ERROR), HttpStatus.OK);
                }
            }

            if (customerPack.getPackType() != 2) {
                return new ResponseEntity<>(ResultModel.error(GOODS_CONFIRM_ERROR), HttpStatus.OK);
            }

            // 查询到地址
            CustomerPackReceiverAddress customerPackReceiverAddress = customerPackReceiverAddressDao.findByCustomerPackId(customerPack.getId());
            if (customerPackReceiverAddress == null) {
                return new ResponseEntity<>(ResultModel.error(ADDRESS_NULL_ERROR), HttpStatus.OK);
            }

            Customer customer = UserThreadContext.getUser();
//            paramMap.put("id",customer.getId());
            if (customer == null) {
                return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR), HttpStatus.OK);
            }
//            int customerId = (Integer) paramMap.get("id");
//            Customer customer = customerMapper.selectByPrimaryKey(customerId);
            CustomerMoney customerMoney = customerMoneyMapper.selectByCustomerNo(customer.getCustomerNo());
            if (customerMoney == null) {
                return new ResponseEntity<>(ResultModel.error(BALANCE_INSUFFICIENT_ERROR), HttpStatus.OK);
            }

            // 最后总费用 allActualPricesCalculate()
            BigDecimal allActualPrices;

            // 费用明细表
            // 要兼容以前没有明细表的数据，当明细表为空时，打包/编辑(优惠金额、附加费等)后的实际价格作为运费计算
            CustomerPackPriceDetail customerPackPriceDetail = this.updatePriceDetail(confirmShipmentReqVo, customerPack);

            if (customerPackPriceDetail.getId() == null) {
                customerPackPriceDetail.setFreight(customerPack.getActualPrice());
                allActualPrices = customerPack.getActualPrice();
            } else {
                allActualPrices = pricesDetailCalculate(customerPackPriceDetail);
            }


            List<PackValuation> packValuations = null;
            // 是否有关税预付
            if (confirmShipmentReqVo.getIsTariffs() == 1) {
                if (confirmShipmentReqVo.getPackValuations() == null || confirmShipmentReqVo.getPackValuations().isEmpty()) {
                    return new ResponseEntity<>(ResultModel.error(ROUTE_IS_TARIFFS_PLEASE_INPUT_PACK_VALUATION), HttpStatus.OK);
                }

                packValuations = JSON.parseArray(confirmShipmentReqVo.getPackValuations(), PackValuation.class);
                for (PackValuation packValuation : packValuations) {

                    if (packValuation.getUnitPrice() == null ||
                            packValuation.getUnitPrice().compareTo(new BigDecimal(0)) <= 0) {
                        return new ResponseEntity<>(ResultModel.error(ROUTE_IS_TARIFFS_PLEASE_INPUT_PACK_VALUATION), HttpStatus.OK);
                    }

                    if (packValuation.getGoodsValue() == null ||
                            packValuation.getGoodsValue().compareTo(new BigDecimal(0)) <= 0) {
                        return new ResponseEntity<>(ResultModel.error(ROUTE_IS_TARIFFS_PLEASE_INPUT_PACK_VALUATION), HttpStatus.OK);
                    }

                }

                if (confirmShipmentReqVo.getTariffsPrice() != null &&
                        confirmShipmentReqVo.getTariffsPrice().compareTo(new BigDecimal(0)) > 0) {
                    // 向上取整的2位小数
                    allActualPrices = allActualPrices.add(confirmShipmentReqVo.getTariffsPrice());

                    customerPack.setTariffsPrice(confirmShipmentReqVo.getTariffsPrice());
                    customerPackPriceDetail.setTariffsPrice(confirmShipmentReqVo.getTariffsPrice());
                } else {
                    return new ResponseEntity<>(ResultModel.error(ROUTE_IS_TARIFFS_PLEASE_INPUT_PACK_VALUATION), HttpStatus.OK);
                }
            }

            /**
             *  暂时屏蔽产品货值更新的操作，客户端支付的时候，不能进行产品货值的变更
             */
/*         else {
            packValuations = JSON.parseArray(confirmShipmentReqVo.getPackValuations(), PackValuation.class);
            // 产品货值数据整理
            packValuations = CustomerPackUtil.dealWithPackValuations(packValuations);

        }
        //先删除申请打包时候填写的数据，重新插入付款时候的数据
        // 如果没有数据则是客户删除了产品货值信息
        packValuationMapper.deleteByPackId(customerPack.getId());
        // 产品货值不为空
        if (packValuations != null && !packValuations.isEmpty()) {
            for (PackValuation packValuation : packValuations) {
                packValuation.setCreateId(customerPack.getCustomerId());
                packValuation.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                packValuation.setStatus(1);
                packValuation.setVersion(1);
                packValuation.setPackId(customerPack.getId());
                packValuation.setOrderNumber(customerPack.getOrderNumber());
            }
            packValuationMapper.insertList(packValuations);
        }*/


            // 是否有使用保险
            // 如果没有选择保险，则将保险金额改为0
            PackInsurancePrice packInsurancePrice = null;
            if (confirmShipmentReqVo.getInsuranceId() != null && confirmShipmentReqVo.getInsurancePriceId() != null) {

                Insurance insurance = insuranceMapper.selectByPrimaryKey(confirmShipmentReqVo.getInsuranceId());
                InsurancePrice insurancePrice = insurancePriceMapper.selectByPrimaryKey(confirmShipmentReqVo.getInsurancePriceId());

                packInsurancePrice = packInsurancePriceSetting(customerPack, insurance, insurancePrice); // 保险信息详情内容插入

                // 实际价格加上保险价格，向上取整的2位小数
                allActualPrices = allActualPrices.add(insurancePrice.getPrice()).setScale(2, BigDecimal.ROUND_UP);

                customerPack.setInsurancePrice(insurancePrice.getPrice());
                customerPackPriceDetail.setInsurancePrice(confirmShipmentReqVo.getInsurancePrice());
            } else {
                customerPack.setInsurancePrice(new BigDecimal(0));
                customerPackPriceDetail.setInsurancePrice(new BigDecimal(0));
            }


            // 检验是否有使用优惠券
            CustomerCoupons customerCoupons = null;
            if (confirmShipmentReqVo.getCouponsId() != null) {
                customerCoupons = customerCouponsMapper.selectByPrimaryKey(confirmShipmentReqVo.getCouponsId());
                if (customerCoupons != null) {
                    if (customerCoupons.getCouponsStatus() != customerCouponsStatusEnum.WAIT_USE.getValue()) {
                        return new ResponseEntity<>(ResultModel.error(THIS_COUPONS_USED_ERROR), HttpStatus.OK);
                    }

                    if (allActualPrices.compareTo(customerCoupons.getMinimumAmount()) < 0) {
                        return new ResponseEntity<>(ResultModel.error(THIS_PRICE_NOT_SATISFY_COUPONS_MINIMUM_AMOUNT_ERROR), HttpStatus.OK);
                    }

                    // 最终价格为向上取整的2位小数
                    allActualPrices = allActualPrices.subtract(customerCoupons.getCouponsAmount()).setScale(2, BigDecimal.ROUND_UP);

                    customerPackPriceDetail.setCouponsPrice(customerCoupons.getCouponsAmount());
                }
            }
            CustomerIntegral customerIntegral = null;
            // 积分抵扣
            if(confirmShipmentReqVo.getDeductionAmount() != null && confirmShipmentReqVo.getDeductionAmount().compareTo(new BigDecimal(0)) > 0){
                double deductionIntegrals = confirmShipmentReqVo.getDeductionAmount().doubleValue();

                customerIntegral = new CustomerIntegral();
                customerIntegral.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                customerIntegral.setStatus(1);
                customerIntegral.setIntegralType(IntegralsType.deductible.getKey());
                customerIntegral.setIntegrals(-deductionIntegrals);
                customerIntegral.setCustomerId(customerPack.getCustomerId());

                // 最终价格为向上取整的2位小数
                allActualPrices = allActualPrices.subtract(confirmShipmentReqVo.getDeductionAmount()).setScale(2, BigDecimal.ROUND_UP);
                customerPackPriceDetail.setDeductionAmount(confirmShipmentReqVo.getDeductionAmount());

            }



            // 客户可用余额
            BigDecimal amount = customerMoney.getAmount();

            // 余额不足，返回提示
            if (amount.compareTo(allActualPrices) < 0) {
                return new ResponseEntity<>(ResultModel.error(BALANCE_INSUFFICIENT_ERROR), HttpStatus.OK);
            }

            // 查询渠道名称
            RouteVo route = routeMapper.findByRouteIdNotStatus(customerPack.getTransportationRouteId());
            String routeName = null;
            if (route != null) {
                routeName = route.getRouteName();
            }
            // 插入消费记录表 start
            CustomerAccount account = new CustomerAccount();
            account.setCustomerNo(customerPack.getCustomerId().toString());
            account.setCustomerName(customerMoney.getCustomerName());
            account.setLoginName(customer.getLoginName());
            account.setAmount(allActualPrices.negate()); // 取费用的相反数，存负数
            account.setBusinessNumber(customerPack.getBusinessNumber());
            account.setOrderNumber(customerPack.getOrderNumber());
            account.setCostType("2"); // 扣费
            account.setCurrency("人民币");
            account.setCreateTime(DateUtil.timestamp2String(new Date()));
            account.setRouteName(routeName);
            account.setAddress(customerPackReceiverAddress.getReceiverAddress());
            account.setGoodsSum(customerPack.getGoodsSum()); // 包裹总数
            account.setStatus(1);
            account.setVersion("1");
            accountMapper.insert(account);
            // 插入消费记录表 end


            // 插入包裹对地址中间记录表 pack2address  start
            Customer2address customer2address = new Customer2address();

            customer2address.setCustomerId(customer.getId());
            customer2address.setCustomerName(customer.getCustomerName());

            customer2address.setCustomerPackId(customerPack.getId());
            customer2address.setAddressee(customerPackReceiverAddress.getAddressee());
            customer2address.setTelephone(customerPackReceiverAddress.getPhoneNumber());
            customer2address.setAddress(customerPackReceiverAddress.getReceiverAddress());
            customer2address.setCode(customerPackReceiverAddress.getCode());

            customer2addressMapper.insertSelective(customer2address);
            // 插入地址中间记录表 end

            // 插入货物对地址中间记录表 goods2address  start
            System.out.println("开始插入 货物对地址表 goods2address");
            List<Goods2pack> goods2packList = goods2packMapper.findByPackId(customerPack.getId());
            List<Goods2address> goods2addressList = new ArrayList<>();
            for (Goods2pack goods2pack : goods2packList) {
                Goods2address goods2address = new Goods2address();
                goods2address.setCustomerId(customer.getId());
                goods2address.setCustomerName(customer.getCustomerName());
                goods2address.setGoodsId(goods2pack.getGoodsId());
                goods2address.setAddressee(customerPackReceiverAddress.getAddressee());
                goods2address.setAddress(customerPackReceiverAddress.getReceiverAddress());
                goods2address.setTelephone(customerPackReceiverAddress.getPhoneNumber());
                goods2address.setCode(customerPackReceiverAddress.getCode());
                goods2addressList.add(goods2address);
            }
            goods2addressMapper.insertSelectiveList(goods2addressList);
            // 插入货物对地址中间记录表 goods2address  end

            customerMoney.setAmount(amount.subtract(allActualPrices));
            customerMoneyMapper.updateMoneyByPrimaryKeySelective(customerMoney);  // 扣除余额表的费用


            //确认发货 修改 包裹状态
            List<Integer> goodsIdList = goods2packList.stream().map(Goods2pack::getGoodsId).collect(Collectors.toList());

//            goodsMapper.updateToConfirmShipment(goodsNoList);
            // 根据货物编码更新变更为根据货物ID更新
            goodsMapper.updateGoodsByIdListAndType(goodsIdList, WAIT_OUT_WAREHOUSE.getValue());
            // 检验是否拼团包裹，如果是拼团包裹的话，会一同更新拼团子订单的信息
            checkPinOrder(customerPack);

            // 删除申请打包的时候勾选的保险信息
            packInsurancePriceMapper.deleteByPackId(customerPack.getId());

            // 如果订单保险信息不为空
            if (packInsurancePrice != null) {
                packInsurancePriceMapper.insert(packInsurancePrice);
            }

            // 如果优惠券信息不为空
            if (customerCoupons != null) {
                customerCoupons.setUsedTime(DateUtil.FormatDate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                customerCoupons.setUsedOrder(customerPack.getOrderNumber());
                customerCoupons.setCouponsStatus(customerCouponsStatusEnum.USED.getValue());
                customerCouponsMapper.updateByPrimaryKey(customerCoupons);

                // 优惠券使用人数+1
                CustomerCouponsCenter customerCouponsCenter = customerCouponsCenterMapper.selectByPrimaryKey(customerCoupons.getCenterCouponsId());
                if (customerCouponsCenter != null) {
                    customerCouponsCenter.setCustomerUsedNum(customerCouponsCenter.getCustomerUsedNum() + 1);
                    customerCouponsCenterMapper.updateByPrimaryKey(customerCouponsCenter);
                }
            }

            // 更新积分使用情况
            if(customerIntegral != null){
                customerIntegralMapper.insertSelective(customerIntegral);
            }

            // 更新价格明细内容
            if (customerPackPriceDetail.getId() == null) {
                customerPackPriceDetailMapper.insertSelective(customerPackPriceDetail);
            } else {
                customerPackPriceDetailMapper.updateByPrimaryKeySelective(customerPackPriceDetail);
            }

            // 最后更新最终支付价格和customerPack表的数据
            customerPack.setActualPrice(allActualPrices);
            customerPackMapper.confirmShipmentById(customerPack);

            transactionManager.commit(status);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("付款错误信息：{}" + e.getMessage());
            transactionManager.rollback(status);
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }

    }

    /**
     *  保险信息详情内容插入
     * @param customerPack
     * @param insurance
     * @param insurancePrice
     * @return
     */
    private PackInsurancePrice packInsurancePriceSetting(CustomerPack customerPack, Insurance insurance, InsurancePrice insurancePrice) {
        PackInsurancePrice packInsurancePrice = new PackInsurancePrice();
        packInsurancePrice.setStatus(1);
        packInsurancePrice.setVersion(1);

        packInsurancePrice.setCreateId(customerPack.getCustomerId());
        packInsurancePrice.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

        packInsurancePrice.setInsuranceId(insurance.getId());
        packInsurancePrice.setInsuranceName(insurance.getInsuranceName());
        packInsurancePrice.setInsuranceNo(insurance.getInsuranceNo());
        packInsurancePrice.setClaimsDetail(insurance.getClaimsDetail());

        packInsurancePrice.setInsurancePriceId(insurancePrice.getId());
        packInsurancePrice.setInsurancePrice(insurancePrice.getPrice());
        packInsurancePrice.setGinsengPrice(insurancePrice.getGinsengPrice());
        packInsurancePrice.setPriceClaimsDetail(insurancePrice.getClaimsDetail());

        packInsurancePrice.setPackId(customerPack.getId());
        packInsurancePrice.setOrderNumber(customerPack.getOrderNumber());

        return packInsurancePrice;
    }

    // 计算价格明细
    // 要兼容以前没有明细表的数据，当明细表为空时，打包/编辑(优惠金额、附加费等)后的实际价格作为运费计算
    private BigDecimal pricesDetailCalculate(CustomerPackPriceDetail customerPackPriceDetail) {
        BigDecimal allActualPrices = customerPackPriceDetail.getFreight();

        // 如果存在叉车费
        if (customerPackPriceDetail.getForkliftFee() != null &&
                customerPackPriceDetail.getForkliftFee().compareTo(new BigDecimal(0)) > 0) {
            allActualPrices = allActualPrices.add(customerPackPriceDetail.getForkliftFee());
        }
        // 如果存在敏感货物费用
        if (customerPackPriceDetail.getSensitivePrice() != null &&
                customerPackPriceDetail.getSensitivePrice().compareTo(new BigDecimal(0)) > 0) {
            allActualPrices = allActualPrices.add(customerPackPriceDetail.getSensitivePrice());
        }
        // 如果存在附加费用
        if (customerPackPriceDetail.getIncidental() != null &&
                customerPackPriceDetail.getIncidental().compareTo(new BigDecimal(0)) > 0) {
            allActualPrices = allActualPrices.add(customerPackPriceDetail.getIncidental());
        }
        // 如果存在后台优惠金额
        if (customerPackPriceDetail.getDiscount() != null &&
                customerPackPriceDetail.getDiscount().compareTo(new BigDecimal(0)) > 0) {
            allActualPrices = allActualPrices.subtract(customerPackPriceDetail.getDiscount());
        }
        return allActualPrices;
    }

    /**
     * 费用明细更新内容：确认付款操作只更新 渠道预付关税、保险价格、优惠券金额
     * 如果费用明细为空，则新建一条费用明细
     *
     * @param confirmShipmentReqVo
     * @param customerPack
     * @return
     */
    private CustomerPackPriceDetail updatePriceDetail(ConfirmShipmentReqVo confirmShipmentReqVo, CustomerPack customerPack) {
        CustomerPackPriceDetail customerPackPriceDetail = customerPackPriceDetailMapper.selectPackPriceDetailByPackId(customerPack.getId());
        if (customerPackPriceDetail == null) {
            customerPackPriceDetail = new CustomerPackPriceDetail();
            customerPackPriceDetail.setPackId(customerPack.getId());
            customerPackPriceDetail.setStatus(1);
            customerPackPriceDetail.setVersion(1);
            customerPackPriceDetail.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            customerPackPriceDetail.setCreateId(customerPack.getCustomerId());

        } else {
            customerPackPriceDetail.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            customerPackPriceDetail.setUpdateId(customerPack.getCustomerId());
            customerPackPriceDetail.setVersion(customerPackPriceDetail.getVersion() + 1);
        }
        customerPackPriceDetail.setInsurancePrice(confirmShipmentReqVo.getInsurancePrice() == null
                ? new BigDecimal(0) : confirmShipmentReqVo.getInsurancePrice());

        customerPackPriceDetail.setTariffsPrice(confirmShipmentReqVo.getTariffsPrice() == null
                ? new BigDecimal(0) : confirmShipmentReqVo.getTariffsPrice());

        customerPackPriceDetail.setCouponsPrice(confirmShipmentReqVo.getCouponsPrice() == null
                ? new BigDecimal(0) : confirmShipmentReqVo.getCouponsPrice());

        return customerPackPriceDetail;
    }


    private void checkPinOrder(CustomerPack customerPack) {
        PingMainOrder pingMainOrder = pingMainOrderMapper.selectOrderByOrderNumber(customerPack.getOrderNumber());
        if (pingMainOrder == null) {
            return;
        }
        pingMainOrder.setPayTime(new Date());
        pingMainOrder.setIsPaid(1);
        pingMainOrderMapper.updateByPrimaryKeySelective(pingMainOrder);// 先更新此订单的数据

        PingMainRespVo pingMainRespVo = pingMainMapper.selectVoById(pingMainOrder.getPId());
        for (PingMainOrder order : pingMainRespVo.getPingMainOrderList()) {
            if (order.getIsPaid() != 1) {
                pingMainRespVo.setIsPaid(0);
                break;
            }
            pingMainRespVo.setIsPaid(1);
        }

        // 如果所有成员都支付了货款，则拼团主单状态变更为已付款
        if (pingMainRespVo.getIsPaid() == 1) {
            pingMainMapper.updateByPrimaryKeySelective(pingMainRespVo);
        }
    }

    @Override
    public int countFindPageNumber(Map<String, Object> paramMap, boolean isOutWare) {
        Integer packType = 0;
        List<Integer> packTypes = new ArrayList<>();
        if (paramMap.containsKey("packType")) {
            packType = Integer.valueOf((String) paramMap.get("packType"));
        }
        if (packType != 0) {
            packTypes.add(packType);
            paramMap.put("packTypes", packTypes);
            return customerPackMapper.countFindToBeShipped(paramMap);
        } else {
            if (isOutWare) {
                packTypes.add(3);
                packTypes.add(4);
            } else {
                packTypes.add(1);
                packTypes.add(2);
            }
            paramMap.put("packTypes", packTypes);
            return customerPackMapper.countFindToBeShipped(paramMap);
        }

    }

    @Override
    public List<GoodsVo> findByBusinessNumberToGoodsNos(String businessNumber) {
        CustomerPack customerPack = customerPackMapper.findByBusinessNumber(businessNumber);
        List<Goods2pack> goods2packList = goods2packMapper.findByPackId(customerPack.getId());
        return goodsMapper.selectGoodsByIds(goods2packList.stream().map(Goods2pack::getGoodsId).collect(Collectors.toList()));
    }


    @Override
    @Transactional
    public int unpack(String businessNumber, int customerId) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("客户申请拆包操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            CustomerPack customerPack = customerPackMapper.findByBusinessNumber(businessNumber);
            List<String> goodsNoList = Arrays.asList(customerPack.getGoodsNo().split(","));
            int selectCount = timeLimitActivityDao.selectByGoodsNos(goodsNoList);
            if (selectCount > 0) {
                return -100;
            }
            if (customerPack.getPackType() != 1 && customerPack.getPackType() != 2) {
                return 103;
            }
            if (!customerPack.getCustomerId().equals(customerId)) {
                return 102;
            }
            if (customerPack.getPackType() == 1) {
                customerPackMapper.deleteByBusinessNumber(businessNumber);
                goods2packMapper.deleteByPackId(customerPack.getId());
                goodsMapper.unpack(goodsNoList);
            } else {
                customerPackMapper.unpack(businessNumber);
            }
            transactionManager.commit(status);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return -1;
        }
    }

    @Override
    public int cancelUnpack(Integer packId) {
        CustomerPack customerPack = customerPackMapper.selectByPrimaryKey(packId);
        customerPack.setPackType(2);
        return customerPackMapper.updateByPrimaryKeySelective(customerPack);
    }

    @Override
    @Transactional
    public int updateAddress(Map<String, Object> paramMap) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("客户修改收货地址");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            int packId = Integer.valueOf(paramMap.get("customerPackId").toString());
            int addressId = Integer.valueOf(paramMap.get("addressId").toString());
            // 获取包裹信息
            CustomerPack customerPack = customerPackMapper.selectByPrimaryKey(packId);
            if (customerPack.getPackType() == 3 || customerPack.getPackType() == 4) {
                return -3;
            }
            // 获取地址信息
            CustomerAddress customerAddress = customerAddressMapper.selectByPrimaryKey(addressId);

            // 更改发货地址表信息
            customerPackReceiverAddressDao.deleteByCustomerPackId(customerPack.getId());
            CustomerPackReceiverAddress customerPackReceiverAddress = new CustomerPackReceiverAddress();
            customerPackReceiverAddress.setReceiverAddress(customerAddress.getReceiverAddress());
            customerPackReceiverAddress.setCustomerPackId(customerPack.getId());
            customerPackReceiverAddress.setPhoneNumber(customerAddress.getPhoneNumber());
            customerPackReceiverAddress.setAddressee(customerAddress.getAddressee());
            customerPackReceiverAddress.setCode(customerAddress.getCode());
            customerPackReceiverAddress.setProvinces(customerAddress.getProvinces());
            customerPackReceiverAddressDao.insert(customerPackReceiverAddress);

            customerPack.setAddressId(customerPackReceiverAddress.getId());
            customerPack.setUpdateTime(sdf.format(new Date()));
            customerPackMapper.updateByPrimaryKeySelective(customerPack);

            transactionManager.commit(status);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return -1;
        }
    }

    @Override
    public ResponseEntity<ResultModel> receipt(Integer packId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("客户签收");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            System.out.println("包裹修改签收状态SQL执行——————");
            customerPackMapper.receipt(packId);
            List<Goods2pack> goods2packList = goods2packMapper.findByPackId(packId);

            List<Integer> goodsIdList =  goods2packList.stream().map(Goods2pack::getGoodsId).collect(Collectors.toList());
            System.out.println("货物修改签收状态SQL执行——————");
            goodsMapper.receipt(goodsIdList);
            transactionManager.commit(status);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<ResultModel> getPackDetail(String businessNumber) {
//        GoodsPackVo goodsPackVo = customerPackMapper.findVoByBusinessNumber(businessNumber);
        CustomerPackRespVo customerPackRespVo = customerPackMapper.findVoByBusinessNumber(businessNumber);
        return new ResponseEntity<>(ResultModel.ok(customerPackRespVo), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getOrderReceivesAddress(String orderNumber) {
        CustomerPackReceiverAddress receiverAddress = customerPackReceiverAddressDao.findByOrderNumber(orderNumber);
        return new ResponseEntity<>(ResultModel.ok(receiverAddress), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getTrajectoryAdvertising() {
        ActivityPoster activityPoster = activityPosterMapper.selectEnableAdvertisingPoster();
        return new ResponseEntity<>(ResultModel.ok(activityPoster), HttpStatus.OK);
    }


}
