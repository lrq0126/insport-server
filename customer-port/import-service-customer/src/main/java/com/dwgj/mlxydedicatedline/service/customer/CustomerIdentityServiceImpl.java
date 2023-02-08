package com.dwgj.mlxydedicatedline.service.customer;

import com.dwgj.mlxydedicatedline.commons.DateUtil;
import com.dwgj.mlxydedicatedline.entity.customer.CustomerIdentity;
import com.dwgj.mlxydedicatedline.entity.image.Images;
import com.dwgj.mlxydedicatedline.enums.ResultStatus;
import com.dwgj.mlxydedicatedline.mapper.customer.CustomerIdentityMapper;
import com.dwgj.mlxydedicatedline.mapper.image.ImagesMapper;
import com.dwgj.mlxydedicatedline.resultType.PageData;
import com.dwgj.mlxydedicatedline.resultType.PageHelp;
import com.dwgj.mlxydedicatedline.resultType.PageResultModel;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.utils.ImgUtil;
import com.dwgj.mlxydedicatedline.utils.TencentObjectMemory;
import com.dwgj.mlxydedicatedline.vo.PageVo;
import com.dwgj.mlxydedicatedline.vo.customer.CustomerIdentityRespVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.dwgj.mlxydedicatedline.entity.image.ImageType.CustomerIdentityPicture;


/**
* 客户身份证信息(CustomerIdentity)表服务实现类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/9/28 14:49
*/
@Service("customerIdentityService")
public class CustomerIdentityServiceImpl implements CustomerIdentityService {
    /**
     * 客户身份证信息Mapper
     */
    @Autowired
    private CustomerIdentityMapper customerIdentityMapper;
    @Autowired
    private ImagesMapper imagesMapper;

    @Override
    @Transactional
    public ResponseEntity<ResultModel> saveIdentity(CustomerIdentity customerIdentity, MultipartFile[] multipartFiles) {
        try {

            if(customerIdentity.getId() == null){
//                customerIdentity.setCreateId(user.getId());
//                customerIdentity.setCreateName(user.getUsername());
                customerIdentity.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                customerIdentity.setStatus(1);
                customerIdentity.setVersion(1);

                customerIdentityMapper.insertSelective(customerIdentity);
            }else {
//                customerIdentity.setUpdateId(user.getId());
//                customerIdentity.setUpdateName(user.getUsername());
                customerIdentity.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

                customerIdentityMapper.updateByPrimaryKeySelective(customerIdentity);
            }

            List<Images> imagesList = ImgUtil.imagesUpload(multipartFiles, CustomerIdentityPicture.toString());

            for (Images images : imagesList) {
                images.setContentId(String.valueOf(customerIdentity.getId()));
            }
            imagesMapper.insertImagesList(imagesList);

            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> getIdentityInfo(int id) {
        CustomerIdentityRespVo customerIdentity = customerIdentityMapper.getIdentityInfo(id);
        return new ResponseEntity<>(ResultModel.ok(customerIdentity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> deleteIdentity(int customerIdentityId) {
        customerIdentityMapper.deleteByPrimaryKey(customerIdentityId);
        List<Images> imagesList = imagesMapper.getCustomerIdentityPicture(customerIdentityId);
        List<String> imageNameList = imagesList.stream().map(Images::getImageName).collect(Collectors.toList());
        List<Integer> imageIdList = imagesList.stream().map(Images::getId).collect(Collectors.toList());
        TencentObjectMemory.deleteObject(CustomerIdentityPicture.toString(), imageNameList);
        imagesMapper.deleteImages(imageIdList);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getIdentityList(int customerId, PageVo pageVo) {
        PageData pageData = PageHelp.editPage(String.valueOf(pageVo.getPageNumber()), String.valueOf(pageVo.getPageSize()));
        int total = customerIdentityMapper.getIdentityCount(customerId);
        pageData.setTotal(total);

        List<CustomerIdentityRespVo> customerIdentityRespVoList = new ArrayList<>();
        if(total > 0){
            pageVo.setPageNumber(pageData.getPageNumber());
            customerIdentityRespVoList = customerIdentityMapper.getIdentityList(customerId, pageVo);
        }
        Map<String ,Object> pageMap = new HashMap<>();
        pageMap.put("number", pageData.getNumber());
        pageMap.put("limit", pageData.getPageSize());
        pageMap.put("total", pageData.getTotal());
        return new ResponseEntity<>(PageResultModel.ok(customerIdentityRespVoList, pageMap), HttpStatus.OK);
    }


}