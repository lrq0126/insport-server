package com.dwgj.mlxydedicatedline.service.customer;


import com.dwgj.mlxydedicatedline.entity.customer.CustomerIdentity;
import com.dwgj.mlxydedicatedline.resultType.PageResultModel;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.vo.PageVo;
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

    ResponseEntity<ResultModel> saveIdentity(CustomerIdentity customerIdentity, MultipartFile[] multipartFiles);

    ResponseEntity<ResultModel> getIdentityInfo(int customerIdentityId);

    ResponseEntity<ResultModel> deleteIdentity(int customerIdentityId);
}