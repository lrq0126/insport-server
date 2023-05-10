package com.example.warehouse.service.sys.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.sys.CommercialArea;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.sys.CommercialAreaMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.CommercialAreaService;
import com.example.warehouse.vo.sys.CommercialAreaReqVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.warehouse.enums.ResultStatus.OTHER_SYSTEM_ERROR;

@Service("CommercialAreaService")
public class CommercialAreaServiceImpl implements CommercialAreaService {

    @Autowired
    private CommercialAreaMapper commercialAreaMapper;

    @Override
    public ResponseEntity<PageResultModel> getCommercialAreaList(CommercialAreaReqVo commercialAreaReqVo) {
        PageData pageData = PageHelp.editPage(commercialAreaReqVo);
        int count = commercialAreaMapper.getCommercialAreaCount(commercialAreaReqVo);
        pageData.setTotal(count);
        List<CommercialArea> commercialAreaList = new ArrayList<>();
        if(count > 0){
            commercialAreaReqVo.setPageNumber(pageData.getPageNumber());
            commercialAreaList = commercialAreaMapper.getCommercialAreaList(commercialAreaReqVo);
        }
        return new ResponseEntity<>(PageResultModel.ok(commercialAreaList, pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getCommercialAreaInfo(int id) {
        CommercialArea commercialArea = commercialAreaMapper.selectByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(commercialArea), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveCommercialArea(CommercialArea commercialArea) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        if(StringUtils.isEmpty(commercialArea.getCommercialAreaName())){
            OTHER_SYSTEM_ERROR.setMessage("请输入区域名称");
            return new ResponseEntity<>(ResultModel.error(ResultStatus.OTHER_SYSTEM_ERROR), HttpStatus.OK);
        }
        if(commercialArea.getId() == null){
            commercialArea.setCreateId(user.getId());
            commercialArea.setCreateName(user.getUsername());
            commercialArea.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            String code = SequenceCode.getId("CA");
            commercialArea.setCommercialAreaNo(code);
            commercialArea.setIsEnable(1);
            commercialAreaMapper.insertSelective(commercialArea);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {

            commercialArea.setUpdateId(user.getId());
            commercialArea.setUpdateName(user.getUsername());
            commercialArea.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            commercialAreaMapper.updateByPrimaryKeySelective(commercialArea);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<ResultModel> deleteCommercialArea(int id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        commercialAreaMapper.deleteByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> enableCommercialArea(int id, int isEnable) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        commercialAreaMapper.enableCommercialArea(id, isEnable);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getCommercialAreaSelectList() {

        List<CommercialArea> commercialAreaList = commercialAreaMapper.getCommercialAreaSelectList();

        return new ResponseEntity<>(ResultModel.ok(commercialAreaList), HttpStatus.OK);
    }

}
