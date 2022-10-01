package com.example.warehouse.service.customerGroup;

import com.example.warehouse.entity.customerGroup.CustomerGroup;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.customerGroup.CustomerGroupReqVo;
import org.springframework.http.ResponseEntity;

public interface CustomerGroupService {
    ResponseEntity<PageResultModel> getGroupList(CustomerGroupReqVo customerGroupReqVo);

    ResponseEntity<PageResultModel> getGroupDetailById(int id, Integer pageNumber, Integer pageSize);

    ResponseEntity<ResultModel> saveGroup(CustomerGroup customerGroup, boolean isEdit);

    ResponseEntity<ResultModel> deleteGroup(int id);
}
