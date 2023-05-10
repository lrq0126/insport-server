package com.dwgj.mlxydedicatedline.service.timeLimitActivity;

import com.dwgj.mlxydedicatedline.commons.SequenceCode;
import com.dwgj.mlxydedicatedline.entity.*;
import com.dwgj.mlxydedicatedline.mapper.*;
import com.dwgj.mlxydedicatedline.mapper.customerPackReceiverAddress.CustomerPackReceiverAddressDao;
import com.dwgj.mlxydedicatedline.mapper.goods2address.Goods2addressMapper;
import com.dwgj.mlxydedicatedline.mapper.goods2pack.Goods2packMapper;
import com.dwgj.mlxydedicatedline.mapper.sysDictDetail.SysDictDetailDao;
import com.dwgj.mlxydedicatedline.mapper.timeLimitActivity.TimeLimitActivityDao;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.timeLimitActivity.TimeLimitActivityService;
import com.dwgj.mlxydedicatedline.vo.timeLimitActivityVo.ActivityRouteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.ROUTE_NULL_ERROR;

/**
 * (TimeLimitActivity)表服务实现类
 *
 * @author makejava
 * @since 2020-04-05 13:53:36
 */
@Service("timeLimitActivityService")
public class TimeLimitActivityServiceImpl implements TimeLimitActivityService {

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Resource
    private TimeLimitActivityDao timeLimitActivityDao;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private Goods2addressMapper goods2addressMapper;
    @Resource
    private Goods2packMapper goods2packMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private CustomerPackMapper customerPackMapper;
    @Resource
    private TransportationRouteMapper transportationRouteMapper;
    @Resource
    private RoutePriceMapper routePriceMapper;
    @Resource
    private CustomerAddressMapper customerAddressMapper;
    @Resource
    private CustomerPackReceiverAddressDao customerPackReceiverAddressDao;
    @Resource
    private SysDictDetailDao sysDictDetailDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TimeLimitActivity queryById(Long id) {
        return this.timeLimitActivityDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<TimeLimitActivity> queryAllByLimit(int offset, int limit) {
        return this.timeLimitActivityDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param timeLimitActivity 实例对象
     * @return
     */
    @Override
    @Transactional
    public int insert(TimeLimitActivity timeLimitActivity, String addressId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("新增限定包裹");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String goodsNo = SequenceCode.gainSerialNo("GOODS");
            double goodsWeight = 1.0;
            String remarks = "仅限美国地址用户领取且每个用户一次，其他客户禁止领取，领取不送";
            timeLimitActivity.setGoodsName("活动产品——莲花清瘟胶囊");
            timeLimitActivity.setRemarks(remarks);
            timeLimitActivity.setCreateTime(new Date());
            timeLimitActivity.setGoodsNo(goodsNo);
            timeLimitActivity.setStatus(1);
            int i = timeLimitActivityDao.insert(timeLimitActivity);
            if (i < 1) {
                transactionManager.rollback(status);
                return i;
            }

            // 获取路线信息
            TransportationRoute transportationRoute = transportationRouteMapper.selectByPrimaryKey(Math.toIntExact(timeLimitActivity.getTransportRouteId()));
            if (transportationRoute == null) {
                return -1;
            }


            Customer customer = customerMapper.selectByPrimaryKey(Math.toIntExact(timeLimitActivity.getCustomerId()));

            // 固定渠道路线为 “美国海运小货专线”  id 为 100
            //int routeId = Math.toIntExact(timeLimitActivity.getTransportRouteId());
            // 固定产品价格  目前为 220.0
            double productPrice =220.0;
            //List<RoutePrice> routePriceList = routePriceMapper.fingByRouteId(transportationRoute.getId());
            RoutePrice routePriceList = routePriceMapper.findByWeight(transportationRoute.getId(),goodsWeight);
            RoutePrice routePrice =  routePriceList;
            // 新增包裹信息
            CustomerPack customerPack = new CustomerPack();
            customerPack.setBusinessNumber(SequenceCode.businessNumber());
            customerPack.setOrderNumber(SequenceCode.orderNumber(customer.getId().toString()));
            customerPack.setCustomerId(customer.getId());
            // 包裹状态设置为 2、已打包
            customerPack.setPackType(2);
            customerPack.setGoodsSum(1);
            customerPack.setPackNum(1);
            customerPack.setGoodsNo(goodsNo);
            // 预报价格为 运送费用 + 产品价格
            customerPack.setPreQuotedPrice(routePrice.getPrice().add(BigDecimal.valueOf(productPrice)));
            customerPack.setTransportationRouteId(transportationRoute.getId());
            // 实际重量和结算重量设置为 1KG
            customerPack.setActualWeight(goodsWeight);
            customerPack.setSettlementWeight(goodsWeight);
            // 实际价格为 运送费用 + 产品价格
            customerPack.setActualPrice(routePrice.getPrice().add(BigDecimal.valueOf(productPrice)));
            customerPack.setRouteType(transportationRoute.getTransportType());
            customerPack.setPackTime(sdf.format(new Date()));
            customerPack.setRemarks("美国用户限时活动包裹");
            customerPack.setStatus(1);
            customerPack.setVersion(1);
            customerPackMapper.insertSelective(customerPack);

            CustomerAddress customerAddress = customerAddressMapper.selectByPrimaryKey(Integer.valueOf(addressId));
            if(customerAddress == null ){
                return -2;
            }
            CustomerPackReceiverAddress customerPackReceiverAddress = new CustomerPackReceiverAddress();
            customerPackReceiverAddress.setAddressee(customerAddress.getAddressee());
            customerPackReceiverAddress.setCustomerPackId(customerPack.getId());
            customerPackReceiverAddress.setAddressId(customerAddress.getId());
            customerPackReceiverAddress.setPhoneNumber(customerAddress.getPhoneNumber());
            customerPackReceiverAddress.setReceiverAddress(customerAddress.getReceiverAddress());
            customerPackReceiverAddress.setStatus(1);
            customerPackReceiverAddressDao.insert(customerPackReceiverAddress);

            customerPack.setAddressId(customerPackReceiverAddress.getId());
            customerPackMapper.updateByPrimaryKeySelective(customerPack);
            // 新增货物信息
            Goods goods = new Goods();
            goods.setDeliveryOrderNo("无唛头");
            goods.setGoodsName(timeLimitActivity.getGoodsName());
            goods.setGoodsNo(goodsNo);
            goods.setCustomerNo(customer.getCustomerNo());
            // 包裹数量
            goods.setPackageNum(1);
            goods.setPackageType("纸箱");
            goods.setMessage("美国用户限时活动包裹——莲花清瘟胶囊");
            // 货物状态设置为 7、已打包
            goods.setGoodsType("7");
            goods.setKg(goodsWeight);
            // 设置已拣货
            goods.setSortType(1);
            // 设置已入包
            goods.setInPacked(2);
            goods.setAddressId(Integer.valueOf(addressId));
            goods.setVersion(1);
            goods.setStatus(1);
            goodsMapper.insertSelective(goods);

            // 插入货物地址对应表
            Goods2address goods2address = new Goods2address();
            goods2address.setCustomerId(customer.getId());
            goods2address.setCustomerName(customer.getCustomerName());
            goods2address.setGoodsId(goods.getId());
            goods2address.setAddressee(customerPackReceiverAddress.getAddressee());
            goods2address.setAddress(customerPackReceiverAddress.getReceiverAddress());
            goods2address.setTelephone(customerPackReceiverAddress.getPhoneNumber());
            goods2address.setCode(customerPackReceiverAddress.getCode());
            goods2addressMapper.insertSelective(goods2address);

            // 插入货物对应包裹表  goods2pack
            Goods2pack goods2pack = new Goods2pack();
            goods2pack.setPackId(customerPack.getId());
            goods2pack.setGoodsId(goods.getId());
            goods2pack.setStatus(1);
            goods2packMapper.insertSelective(goods2pack);

            transactionManager.commit(status);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return -1;
        }
    }

    /**
     * 修改数据
     *
     * @param timeLimitActivity 实例对象
     * @return 实例对象
     */
    @Override
    public TimeLimitActivity update(TimeLimitActivity timeLimitActivity) {
        this.timeLimitActivityDao.update(timeLimitActivity);
        return this.queryById(timeLimitActivity.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.timeLimitActivityDao.deleteById(id) > 0;
    }

    @Override
    public TimeLimitActivity selectByCustomerId(Map<String, Object> map) {
        return timeLimitActivityDao.selectByCustomerId(map);
    }

    @Override
    public List<ActivityRouteVO> getRoute() {
        /*String activityCountry = "美国";
        List<ActivityRouteVO> resultList = new ArrayList<>();
        SysDictDetail sysDictDetail = sysDictDetailDao.selectDictBySddName(activityCountry);*/
        double goodsWeight = 1.0;
        // 固定渠道路线为 “美国海运小货专线”  id 为 100
        //int routeId = 100;

        List<ActivityRouteVO> resultList = new ArrayList<>();
        //Map<String, Object> selectRouteMap = new HashMap<>(4);
        List<Integer> routeIds = new ArrayList<>();
        // 选择渠道路线为 “美国海运小货专线”  id 为 100
        routeIds.add(100);
        // 选择渠道路线为 “广州UPS/FEDEX专线”  id 为 101
        routeIds.add(101);
        List<TransportationRoute> transportationRouteList = transportationRouteMapper.findRouteById(routeIds);
        if(transportationRouteList.size() < 1){
            return resultList;
        }

        for (TransportationRoute transportationRoute: transportationRouteList) {
            //List<RoutePrice> routePriceList = routePriceMapper.findByRouteIdOfActivity(transportationRoute.getId());
            RoutePrice routePriceList = routePriceMapper.findByWeight(transportationRoute.getId(),goodsWeight);
            if(routePriceList == null){
                continue;
            }
            RoutePrice routePrice = routePriceList;
            ActivityRouteVO activityRouteVO = new ActivityRouteVO();
            activityRouteVO.setNoteType("人民币");
            activityRouteVO.setPrices(routePrice.getPrice());
            activityRouteVO.setTransportationTime(transportationRoute.getTransportationTime());
            activityRouteVO.setRouteId(transportationRoute.getId());
            activityRouteVO.setRoutrName(transportationRoute.getRouteName());
            activityRouteVO.setWeight(routePrice.getStartWeight());
            if(transportationRoute.getTransportType() == 1){
                activityRouteVO.setTransportType("空运");
            }else {
                activityRouteVO.setTransportType("海/铁运");
            }
            resultList.add(activityRouteVO);
        }

        return resultList;
    }
}
