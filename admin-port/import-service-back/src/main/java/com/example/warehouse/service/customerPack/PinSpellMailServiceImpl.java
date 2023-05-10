package com.example.warehouse.service.customerPack;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.Goods;
import com.example.warehouse.entity.Goods2pack;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.customerPack.PinSpellMail;
import com.example.warehouse.entity.customerPack.PinSpellMailCustomerPack;
import com.example.warehouse.enums.customerPack.PackTypeEnums;
import com.example.warehouse.enums.goods.GoodsStatus;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.customerPack.PinSpellMailCustomerPackMapper;
import com.example.warehouse.mapper.customerPack.PinSpellMailMapper;
import com.example.warehouse.mapper.goods2pack.Goods2packMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.customerPack.CustomerPackVo;
import com.example.warehouse.vo.customerPack.PinSpellMailInfoVo;
import com.example.warehouse.vo.customerPack.PinSpellMailReqVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.warehouse.enums.ResultStatus.USER_NOT_COMMERCIAL_AREA;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;

@Service("pinSpellMailService")
public class PinSpellMailServiceImpl implements PinSpellMailService {
    @Autowired
    private PinSpellMailMapper pinSpellMailMapper;
    @Autowired
    private PinSpellMailCustomerPackMapper pinSpellMailCustomerPackMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private Goods2packMapper goods2packMapper;


    @Override
    public ResponseEntity<ResultModel> savePinSpellMail(PinSpellMail pinSpellMail) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        if(pinSpellMail.getId() == null){
            pinSpellMail.setPinType(1);
            pinSpellMail.setStatus(1);
            pinSpellMail.setCreateId(user.getId());
            pinSpellMail.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            pinSpellMailMapper.insertSelective(pinSpellMail);
        }else {
            pinSpellMail.setUpdateId(user.getId());
            pinSpellMail.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            pinSpellMailMapper.updateByPrimaryKeySelective(pinSpellMail);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getPinSpellMailList(PinSpellMailReqVo pinSpellMailReqVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }

        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
//            paramMap.put("commercialAreaId", user.getCommercialAreaId());
            pinSpellMailReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }

        PageData pageData = PageHelp.editPage(pinSpellMailReqVo);
        int count = pinSpellMailMapper.getPinSpellMailCount(pinSpellMailReqVo);
        pageData.setTotal(count);
        List<PinSpellMail> pinSpellMailList = new ArrayList<>();
        if(count > 0){
            pinSpellMailReqVo.setPageNumber(pageData.getPageNumber());
            pinSpellMailList = pinSpellMailMapper.getPinSpellMailList(pinSpellMailReqVo);
        }
        return new ResponseEntity<>(PageResultModel.ok(pinSpellMailList, pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getPinSpellMailInfo(int id) {
        PinSpellMailInfoVo pinSpellMailInfoVo = pinSpellMailMapper.getPinSpellMailInfo(id);
        return new ResponseEntity<>(ResultModel.ok(pinSpellMailInfoVo), HttpStatus.OK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResultModel> completeTeamComplete(int id, String customerPackIds) {
        // 订单状态变更
        PinSpellMail pinSpellMail = pinSpellMailMapper.selectByPrimaryKey(id);
        pinSpellMail.setPinType(2);

        // 绑定已装箱的客户订单
        List<PinSpellMailCustomerPack> pinSpellMailCustomerPackList = new ArrayList<>();

        // 更新订单状态
        List<String> customerPackIdList = Arrays.asList(customerPackIds.split(","));
        List<CustomerPack> customerPackList = customerPackMapper.selectByPrimaryKeyList(customerPackIdList);
        for (CustomerPack customerPack : customerPackList) {
            customerPack.setPackType(PackTypeEnums.PIN_SPELL_MAILING.getValue());

            PinSpellMailCustomerPack pinSpellMailCustomerPack = new PinSpellMailCustomerPack();
            pinSpellMailCustomerPack.setPinSpellMailId(pinSpellMail.getId());
            pinSpellMailCustomerPack.setCustomerPackId(customerPack.getId());
            pinSpellMailCustomerPack.setStatus(1);
            pinSpellMailCustomerPackList.add(pinSpellMailCustomerPack);
        }
        List<Integer> packIdList = customerPackList.stream().map(CustomerPack::getId).collect(Collectors.toList());;
        customerPackMapper.updatePackTypeList(packIdList ,PackTypeEnums.PIN_SPELL_MAILING.getValue());

        // 更新快递货物状态
        List<Goods2pack> goods2PackList = goods2packMapper.findByPackIds(customerPackIdList);
        List<Integer> goodsIdList = goods2PackList.stream().map(Goods2pack::getGoodsId).collect(Collectors.toList());
        goodsMapper.updateByIdsAndGoodsType(goodsIdList, GoodsStatus.GOODS_PIN_SPELL_MAILING.getStatus());

        // 插入已装箱的客户订单
        pinSpellMailCustomerPackMapper.insertList(pinSpellMailCustomerPackList);

        pinSpellMailMapper.updateByPrimaryKeySelective(pinSpellMail);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> deliver(int id) {
        // 订单状态变更
        PinSpellMail pinSpellMail = pinSpellMailMapper.selectByPrimaryKey(id);
        pinSpellMail.setPinType(3);

        pinSpellMailMapper.updateByPrimaryKeySelective(pinSpellMail);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> arrival(int id) {
        // 订单状态变更
        PinSpellMail pinSpellMail = pinSpellMailMapper.selectByPrimaryKey(id);
        pinSpellMail.setPinType(4);

        List<CustomerPackVo> customerPackVoList = customerPackMapper.selectListByPinSpellMailId(id);

        List<Integer> packIdList = customerPackVoList.stream().map(CustomerPack::getId).collect(Collectors.toList());;
        customerPackMapper.updatePackTypeList(packIdList ,PackTypeEnums.WAIT_PACK.getValue());

        pinSpellMailMapper.updateByPrimaryKeySelective(pinSpellMail);

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> delete(int id) {
        return null;
    }
}
