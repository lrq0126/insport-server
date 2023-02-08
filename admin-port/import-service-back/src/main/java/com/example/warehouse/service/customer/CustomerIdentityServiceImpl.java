package com.example.warehouse.service.customer;

import com.example.warehouse.common.*;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.customer.CustomerIdentity;
import com.example.warehouse.entity.image.Images;
import com.example.warehouse.mapper.customer.CustomerIdentityMapper;
import com.example.warehouse.mapper.image.ImagesMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.PageVo;
import com.example.warehouse.vo.customer.CustomerIdentityRespVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.warehouse.entity.image.ImageType.CustomerIdentityPicture;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;


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
    public ResponseEntity<ResultModel> saveIdentity(CustomerIdentity customerIdentity) {
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if(user == null){
                return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
            }
            if(customerIdentity.getFiles().size() < 2){

            }
            if(customerIdentity.getId() == null){
                customerIdentity.setCreateId(user.getId());
                customerIdentity.setCreateName(user.getUsername());
                customerIdentity.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                customerIdentity.setStatus(1);
                customerIdentity.setVersion(1);

                customerIdentityMapper.insertSelective(customerIdentity);
            }else {
                customerIdentity.setUpdateId(user.getId());
                customerIdentity.setUpdateName(user.getUsername());
                customerIdentity.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

                customerIdentityMapper.updateByPrimaryKeySelective(customerIdentity);
            }

            List<Images> imagesList = new ArrayList<>(4);
            File file = null;
            for (String fileStr : customerIdentity.getFiles()) {
                String path = ImgUtil.imageProcess(fileStr, customerIdentity.getCustomerId() + "_"+ new Date().getTime(), "identity");
                file = new File(path);
                Images images = TencentObjectMemory.uploadObject("flycloud", CustomerIdentityPicture.toString(), file);
                images.setStatus(1);
                imagesList.add(images);
            }

            for (Images images : imagesList) {
                images.setContentId(String.valueOf(customerIdentity.getId()));
            }
            imagesMapper.insertImagesList(imagesList);

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
        PageData pageData = PageHelp.editPage(pageVo);
        int total = customerIdentityMapper.getIdentityCount(customerId);
        pageData.setTotal(total);

        List<CustomerIdentityRespVo> customerIdentityRespVoList = new ArrayList<>();
        if(total > 0){
            pageVo.setPageNumber(pageData.getPageNumber());
            customerIdentityRespVoList = customerIdentityMapper.getIdentityList(customerId, pageVo);
        }
        return new ResponseEntity<>(PageResultModel.ok(customerIdentityRespVoList, pageData), HttpStatus.OK);
    }

}