package com.example.warehouse.service.customer;

import com.example.warehouse.entity.customer.CustomerIdentity;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.PageVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
* 客户身份证信息(CustomerIdentity)表服务类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/9/28 14:49
*/
public interface CustomerIdentityService {

    ResponseEntity<PageResultModel> getIdentityList(int customerId, PageVo pageVo);

    ResponseEntity<ResultModel> saveIdentity(CustomerIdentity customerIdentity);

    ResponseEntity<ResultModel> getIdentityInfo(int customerIdentityId);

    ResponseEntity<ResultModel> deleteIdentity(int customerIdentityId);
}