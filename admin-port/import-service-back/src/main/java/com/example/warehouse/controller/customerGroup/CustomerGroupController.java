package com.example.warehouse.controller.customerGroup;

import com.example.warehouse.entity.customerGroup.CustomerGroup;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.customerGroup.CustomerGroupLinkService;
import com.example.warehouse.service.customerGroup.CustomerGroupService;
import com.example.warehouse.vo.customerGroup.CustomerGroupReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customerGroup")
public class CustomerGroupController {
    @Autowired
    private CustomerGroupService customerGroupService;

    @Autowired
    private CustomerGroupLinkService customerGroupLinkService;

    /**
     * 查询分组列表
     * @param customerGroupReqVo
     * @return
     */
    @PostMapping("getGroupList")
    public ResponseEntity<PageResultModel> getGroupList(CustomerGroupReqVo customerGroupReqVo){
        return customerGroupService.getGroupList(customerGroupReqVo);
    }

    /**
     * 获取分组明细
     * @param id
     * @return
     */
    @PostMapping("getGroupDetailById")
    public ResponseEntity<PageResultModel> getGroupDetailById(int id, Integer pageNumber, Integer pageSize){
        return customerGroupService.getGroupDetailById(id, pageNumber, pageSize);
    }

    /**
     * 新增分组
     * @param customerGroup
     * @return
     */
    @PostMapping("addGroup")
    public ResponseEntity<ResultModel> addGroup(CustomerGroup customerGroup){
        return customerGroupService.saveGroup(customerGroup, false);
    }

    /**
     * 编辑分组
     * @param customerGroup
     * @return
     */
    @PostMapping("editGroup")
    public ResponseEntity<ResultModel> editGroup(CustomerGroup customerGroup){
        return customerGroupService.saveGroup(customerGroup, true);
    }

    /**
     * 删除分组
     * @param id
     * @return
     */
    @PostMapping("deleteGroup")
    public ResponseEntity<ResultModel> deleteGroup(int id){
        return customerGroupService.deleteGroup(id);
    }

    /**
     * 新增组成员
     * @param id
     * @param customerIds
     * @return
     */
    @PostMapping("addGroupCustomer")
    public ResponseEntity<ResultModel> addGroupCustomer(int id, String customerIds){
        return customerGroupLinkService.editGroupCustomer(id, customerIds, true);
    }

    /**
     * 删除组成员
     * @param id
     * @param customerIds
     * @return
     */
    @PostMapping("deleteGroupCustomer")
    public ResponseEntity<ResultModel> deleteGroupCustomer(int id, String customerIds){
        return customerGroupLinkService.editGroupCustomer(id, customerIds, false);
    }


    /**
     * 组添加选择了这个渠道的成员
     * @param groupId
     * @param routeIds
     * @return
     */
    @PostMapping("addGroupCustomerByRouteId")
    public ResponseEntity<ResultModel> addGroupCustomerByRouteId(Integer groupId, String routeIds){
        return customerGroupLinkService.addGroupCustomerByRouteId(groupId, routeIds);
    }
}
