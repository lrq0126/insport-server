package com.example.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.DeliveryCompany;
import com.example.warehouse.entity.Storage;
import com.example.warehouse.entity.customer.CustomerIdentity;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.CustomerService;
import com.example.warehouse.service.DeliveryCompanyService;
import com.example.warehouse.service.StorageService;
import com.example.warehouse.service.customer.CustomerIdentityService;
import com.example.warehouse.vo.PageVo;
import com.example.warehouse.vo.customer.CustomerOrderReqVo;
import com.example.warehouse.vo.customer.CustomerReqVo;
import com.example.warehouse.vo.customer.PopularizeDetailReqVo;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.example.warehouse.enums.ResultStatus.*;

@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private DeliveryCompanyService deliveryCompanyService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private CustomerIdentityService customerIdentityService;

    @RequestMapping(value = "/find/all",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PageResultModel> findAll(CustomerReqVo customerReqVo){
        log.info("查询所有客户列表------>>>>>>/find/all");
        return customerService.findAll(customerReqVo);
    }


    @PostMapping("getGoodsListByCustomerNo")
    public ResponseEntity<PageResultModel> getGoodsListByCustomerNo(CustomerOrderReqVo customerOrderReqVo){
        return customerService.getGoodsListByCustomerNo(customerOrderReqVo);
    }

    @PostMapping("getOrderListByCustomerId")
    public ResponseEntity<PageResultModel> getOrderListByCustomerId(CustomerOrderReqVo customerOrderReqVo){
        return customerService.getOrderListByCustomerId(customerOrderReqVo);
    }

    /**
     * 获取不在当前分组的客户
     * @param map
     * @return
     */
    @RequestMapping(value = "/getNotInGroupCustomerList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PageResultModel> getNotInGroupCustomerList(@RequestParam Map<String, Object> map){
        return customerService.getNotInGroupCustomerList(map);
    }

    @GetMapping("/find/all/name")
    @ResponseBody
    public ResponseEntity<ResultModel> findAllName(){
        log.info("查询所有客户名称------>>>>>>/find/all/name");
        Map<String, Object> map = new HashMap<>();
        List<Customer> customerLists = customerService.find(map);  //TODO 查询条件我放了一个空Map2019年4月25日15:52:38
        List<Object> lists = new ArrayList<>();
        for (Customer customer : customerLists) {
            map.put("customerName",customer.getCustomerName());
            map.put("customerNo",customer.getCustomerNo());
            lists.add(map);
        }
        return new ResponseEntity<>(ResultModel.ok(lists),HttpStatus.OK);
    }

    @GetMapping("/find/by/id")
    public ResponseEntity<ResultModel> findById(int id){
        Customer customer = customerService.findById(id);
        Map<String, Object> resultMap = new HashMap<>(3);
        resultMap.put("customer",customer);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

//    @RequiresPermissions("customer:create")
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<ResultModel> create(@RequestParam Map<String, Object> params){
        return customerService.save(params);
    }
    @RequiresPermissions("customer:update")
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<ResultModel> update(@RequestParam Map<String, Object> params){
        log.info("修改开始----------->>>>>>>>>>>，参数：{}",params);
        String str = JSON.toJSON(params).toString();
        Customer customer = JSON.parseObject(str,Customer.class);
        customer.setStatus(1);
        int i = 0;
        try{
            i = customerService.update(customer);
        }catch (Exception e){
            log.error("系统错误----------->>>>>>>>>>>，参数：{}",e);
        }
        if(i == 0){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        log.info("修改完成-----------<<<<<<<<");
        return new ResponseEntity<>(ResultModel.ok(SUCCESS),HttpStatus.OK);
    }
    @RequiresPermissions("customer:delete")
    @PostMapping("/delete")
    public ResponseEntity<ResultModel> del(@RequestParam Map<String, Object> params){
        log.info("删除客户----->>>，参数{}",params);
        int i = customerService.del(params.get("customerNo").toString(),params.get("usercode").toString());
        if(0 == i){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        if(-1 == i){
            return new ResponseEntity<>(ResultModel.error(CUSTOMER_HAVE_GOODS_ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(SUCCESS),HttpStatus.OK);
    }

    @PostMapping("find/name")
    @ResponseBody
    public ResponseEntity<ResultModel> findByName(@Param("name") String name){
        log.info("根据客户名查询客户信息,参数：{}",name);
        Map<String, Object> map = new HashMap<>();
        map.put("customerName",name);
        List<Customer> lists = customerService.find(map);
        return new ResponseEntity<>(ResultModel.ok(lists),HttpStatus.OK);
    }

    @GetMapping("/list/name")
    @ResponseBody
    public ResponseEntity<ResultModel> findList(){
        log.info("查询下拉列表开始,参数：{}");
        Map<String,Object> pam = new HashMap<>();
//        List<Customer> customerList = customerService.find(pam);//TODO 查询条件我放了一个空Map2019年4月25日15:52:38

        List<DeliveryCompany> deliveryCompanyList = deliveryCompanyService.find(pam);//TODO 查询条件我放了一个空Map2019年4月25日15:52:38

        Map newMap = new HashMap();
//        newMap.put("customer",customerSelectList);
        newMap.put("deliveryCompany",deliveryCompanyList);
        return new ResponseEntity<>(ResultModel.ok(newMap),HttpStatus.OK);
    }


    @GetMapping("/list/storage")
    @ResponseBody
    public ResponseEntity<ResultModel> findStorage(){
        log.info("查询货架地址列表开始,参数：{}");
        Map<String,Object> pam = new HashMap<>();
        List<Storage> storageList = storageService.find(pam);//TODO 查询条件我放了一个空Map2019年4月25日15:52:38
        List<Map> newStorage = new ArrayList<>();
        for (Storage storage : storageList) {
            Map<String, Object> map = new HashMap<>();
            map.put("storageName",storage.getStorageName());
            map.put("storageCode",storage.getStorageCode());
            map.put("storageArea",storage.getStorageArea());
            map.put("storageRow",storage.getStorageRow());
            newStorage.add(map);
        }
        Map newMap = new HashMap();
        newMap.put("storage",newStorage);
        return new ResponseEntity<>(ResultModel.ok(newMap),HttpStatus.OK);
    }

    @GetMapping("/list/getDeliveryCompany")
    @ResponseBody
    public ResponseEntity<ResultModel> getDeliveryCompany(){
        log.info("查询快递公司列表开始,参数：{}");
        Map<String,Object> pam = new HashMap<>();
        List<DeliveryCompany> deliveryCompanyList = deliveryCompanyService.find(pam);//TODO 查询条件我放了一个空Map2019年4月25日15:52:38
        Map newMap = new HashMap();
        newMap.put("deliveryCompany",deliveryCompanyList);
        return new ResponseEntity<>(ResultModel.ok(newMap),HttpStatus.OK);
    }

    /**
     * 重置客户密码
     * @param customerId
     * @return
     */
    @RequiresPermissions("customer:reset")
    @PostMapping("/reset/password")
    @ResponseBody
    public ResponseEntity<ResultModel> resetPassword(int customerId){
        int resultType = customerService.resetPassword(customerId);
        if(resultType == 1){
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
    }

    /**
     * 生成客户海报
     * @param id
     * @return
     */
    @GetMapping("/generatePoster")
    @ResponseBody
    public void generateCustomerPoster(@RequestParam("id") int id, HttpServletRequest request, HttpServletResponse response){
        customerService.generateCustomerPoster(id, "sharePoster", request, response);
    }

    /**
     * 生成微信绑定海报
     * @param id
     * @return
     */
    @GetMapping("/generateBindingPoster")
    @ResponseBody
    public void generateBindingPoster(@RequestParam("id") int id, HttpServletRequest request, HttpServletResponse response){
        customerService.generateCustomerPoster(id, "bindingPoster", request, response);
    }

    /**
     * 查询客户推广明细
     * @param popularizeDetailReqVo
     * @return
     */
    @PostMapping("/getPopularizeDetailByCustomerId")
    @ResponseBody
    public ResponseEntity<PageResultModel> getPopularizeDetailByCustomerId(PopularizeDetailReqVo popularizeDetailReqVo){
        return customerService.getPopularizeDetailByCustomerId(popularizeDetailReqVo);
    }

    /**
     * 查询客户积分明细
     * @param customerReqVo
     * @return
     */
    @PostMapping("/getIntegralsDetail")
    @ResponseBody
    public ResponseEntity<PageResultModel> getIntegralsDetail(CustomerReqVo customerReqVo){
        return customerService.getIntegralsDetailByCustomerId(customerReqVo);
    }

    @PostMapping("/getCustomerIntegrals")
    @ResponseBody
    public ResponseEntity<ResultModel> getCustomerIntegrals(int customerId){
        return customerService.getCustomerIntegrals(customerId);
    }

    @PostMapping("/copyWareAddress")
    @ResponseBody
    public ResponseEntity<ResultModel> copyWareAddress(int customerId){
        return customerService.copyWareAddress(customerId);
    }

    /**
     * 客户分身证信息
     */


    @PostMapping("/getIdentityList")
    @ResponseBody
    public ResponseEntity<PageResultModel> getIdentityList(int customerId, PageVo pageVo){
        return customerIdentityService.getIdentityList(customerId, pageVo);
    }

    @PostMapping("/getIdentityInfo")
    @ResponseBody
    public ResponseEntity<ResultModel> getIdentityInfo(int customerIdentityId){
        return customerIdentityService.getIdentityInfo(customerIdentityId);
    }

    @PostMapping("/saveIdentity")
    @ResponseBody
    public ResponseEntity<ResultModel> saveIdentity(
            @ApiParam(name="customerIdentity",value="评价实体",required=true) CustomerIdentity customerIdentity){
        return customerIdentityService.saveIdentity(customerIdentity);
    }

    @PostMapping("/deleteIdentity")
    @ResponseBody
    public ResponseEntity<ResultModel> deleteIdentity(int customerIdentityId){
        return customerIdentityService.deleteIdentity(customerIdentityId);
    }

}
