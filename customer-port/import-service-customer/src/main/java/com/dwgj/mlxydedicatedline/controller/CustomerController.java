package com.dwgj.mlxydedicatedline.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.controller.wx.WXMessageBean;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.DeliveryCompany;
import com.dwgj.mlxydedicatedline.entity.Goods;
import com.dwgj.mlxydedicatedline.entity.customer.CustomerIdentity;
import com.dwgj.mlxydedicatedline.resultType.PageData;
import com.dwgj.mlxydedicatedline.resultType.PageHelp;
import com.dwgj.mlxydedicatedline.resultType.PageResultModel;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.CustomerInfoService;
import com.dwgj.mlxydedicatedline.service.CustomerService;

import com.dwgj.mlxydedicatedline.service.DeliveryCompanyService;
import com.dwgj.mlxydedicatedline.service.GoodsService;
import com.dwgj.mlxydedicatedline.service.customer.CustomerIdentityService;
import com.dwgj.mlxydedicatedline.vo.GoodsVo;
import com.dwgj.mlxydedicatedline.vo.PageVo;
import com.dwgj.mlxydedicatedline.vo.customer.CustomerReqVo;
import com.dwgj.mlxydedicatedline.vo.customer.PopularizeDetailReqVo;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.*;

/**
 * 客户操作页面api
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private DeliveryCompanyService deliveryCompanyService;
    @Autowired
    private CustomerIdentityService customerIdentityService;

    @GetMapping("/selectBaseInfoById")
    public ResponseEntity<ResultModel> selectBaseInfoById() {
        return customerInfoService.selectBaseInfoById();
    }

    @PostMapping("/update")
    public ResponseEntity<ResultModel> update(@RequestParam Map<String,Object> paramMap){
        Customer customer = JSON.parseObject(JSONObject.toJSONString(paramMap),Customer.class);
        return customerService.update(customer);
    }

    @PostMapping("/updateCustomerEmail")
    public ResponseEntity<ResultModel> updateCustomerEmail(@RequestParam Integer id, @RequestParam String email){
        return customerService.updateCustomerEmail(id, email);
    }




    @PostMapping("/editPassword")
    public ResponseEntity<ResultModel> editPassword(@RequestParam Map<String,Object> paramMap){
        return customerService.updatePassword(paramMap);
    }

    @GetMapping(value = "/find/all")
    public List<Customer> findAll(){
        Session session = SecurityUtils.getSubject().getSession();
        System.out.println("------>>> session "+session.getId());
        return customerService.findAll();
    }

    @PostMapping("/find/goods")
    public ResponseEntity<ResultModel> findGoods(@RequestParam Map<String,Object> map, HttpSession session){

        //TODO 防止没有传参出错，初始化分页参数
        if( !map.containsKey("pageNumber")){
            map.put("pageNumber",0);
        }
        if( !map.containsKey("pageSize")){
            map.put("pageSize",10);
        }

        PageData data = PageHelp.editPage(map.get("pageNumber").toString(),map.get("pageSize").toString());
        map.put("pageNumber",data.getPageNumber());
        map.put("pageSize",data.getPageSize());
        Map<String,Object> resultMap = new HashMap<>();
        Customer customer = UserThreadContext.getUser();
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR),HttpStatus.OK);
        }
        System.out.println("获取的id为------》》》》》》》" + customer.getId());
        map.put("id",customer.getId());
        int count = goodsService.countFindAll(map);
        data.setTotal(count);
        resultMap.put("page",data);
        if(count < 1){
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        }
        List<GoodsVo> goodsList = goodsService.findGoods(map);
        resultMap.put("goods",goodsList);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

    @PostMapping(value = "/goods/delete")
    public ResponseEntity<ResultModel>  goodsDelete(@RequestParam("goodsIds")String goodsIds, HttpSession session){

        //Arrays.asList(str .split(",")).stream().map(s -> (s.trim())).collect(Collectors.toList());
        List<String> ids = Arrays.asList(goodsIds.split(",")).stream().map(s -> (s.trim())).collect(Collectors.toList());
        Customer customer = (Customer)session.getAttribute("customer");
        System.out.println("id --------->>>>>>"+customer.getId());
        Integer customerId = customer.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("customerId",customerId);
        map.put("ids",ids);
        int i = goodsService.del(map);
        if(i < 1){
            return new ResponseEntity<>(PageResultModel.error(ERROR),HttpStatus.OK);
        }
       return new ResponseEntity<>(PageResultModel.ok(),HttpStatus.OK);
    }

    @PostMapping(value = "/goods/update")
    public ResponseEntity<ResultModel> goodsUpdate(@RequestParam Map<String, Object> paramMap){
        Goods goods = JSON.parseObject(JSONObject.toJSONString(paramMap),Goods.class);
        int i = goodsService.update(goods);
        if(i < 1){
            return new ResponseEntity<>(PageResultModel.error(ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(),HttpStatus.OK);
    }

    /**
     * 订单号匹配
     * @param paramMap
     * @param session
     * @return
     */
    @PostMapping("/matching")
    public ResponseEntity<ResultModel> matchingOrder(@RequestParam Map<String,Object> paramMap, HttpSession session){
        Customer customer = UserThreadContext.getUser();
        List<Goods> goodsList = customerService.matchingOrder(paramMap,customer.getCustomerNo());
        Map<String,Object> resultMap = new HashMap<>();
        if(goodsList.size() < 1){
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        }
        resultMap.put("goods",goodsList);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }
    /**
     * 客户下单接口
     * @param paramMap
     * 具体参数：
     *      deliveryNo 快递公司名称；deliveryOrderNo  快递单号； goodsName  货物名称； goodsNumber  包裹数量； remark  备注； addressId  收货地址
     * @return
     */
    @PostMapping("/order")
    public ResponseEntity<ResultModel> anOrder(@RequestParam Map<String,Object> paramMap){
        Customer customer = UserThreadContext.getUser();
        int i = customerService.downOrder(paramMap,customer.getId());
        if(i < 1){
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }
        if( 2 == i ){
            return new ResponseEntity<>(ResultModel.error(DELIVERY_NO_REPEAT_ERROR),HttpStatus.OK);
        }
        if( 3 == i ){
            return new ResponseEntity<>(ResultModel.error(DELIVERY_HAS_IN_ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
    }

    /**
     * 查询快递公司接口
     */
    @GetMapping("/find/all/deliveryCompany")
    public ResponseEntity<ResultModel> findDeliveryCompany(){
        List<DeliveryCompany> deliveryCompanyList = deliveryCompanyService.findAll();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("deliveryCompany",deliveryCompanyList);
        return new ResponseEntity<>(ResultModel.ok(resultMap),HttpStatus.OK);
    }

    /**
     * 生成客户海报
     * @param id
     * @return
     */
    @GetMapping("/generatePoster")
    @ResponseBody
    public ResponseEntity<ResultModel> generateCustomerPoster(@Param("id") int id, HttpServletRequest request, HttpServletResponse response){
        return customerService.generateCustomerPoster(id, request, response);
    }

    /**
     * 生成客户分享链接
     * @param loginName
     * @return
     */
    @GetMapping("/generateShareLink")
    @ResponseBody
    public ResponseEntity<ResultModel> generateShareLink(String loginName){
        return customerService.generateShareLink(loginName);
    }

    /**
     * 查询客户推广明细
     * @param popularizeDetailReqVo
     * @return
     */
    @PostMapping("/getPopularizeDetailByCustomerId")
    public ResponseEntity<PageResultModel> getPopularizeDetailByCustomerId(@RequestBody PopularizeDetailReqVo popularizeDetailReqVo){
        return customerService.getPopularizeDetailByCustomerId(popularizeDetailReqVo);
    }

    /**
     * 查询客户积分
     * @param id
     * @return
     */
    @GetMapping("/getIntegralsSum")
    public ResponseEntity<ResultModel> getIntegralsSum(int id){
        return customerService.getIntegralsSum(id);
    }
    /**
     * 查询客户积分明细
     * @param customerReqVo
     * @return
     */
    @PostMapping("/getIntegralsDetail")
    public ResponseEntity<PageResultModel> getIntegralsDetail(@RequestBody CustomerReqVo customerReqVo){
        return customerService.getIntegralsDetail(customerReqVo);
    }

    @GetMapping("/getCustomerWareAddress")
    public ResponseEntity<ResultModel> getCustomerWareAddress(int commercialAreaId, HttpServletRequest request){
        return customerService.getCustomerWareAddress(commercialAreaId,request);
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
            @ApiParam(name="file",value="图片组",required=true) @RequestParam("file") MultipartFile[] multipartFiles,
            @ApiParam(name="customerIdentity",value="客户实体",required=true) CustomerIdentity customerIdentity){
        return customerIdentityService.saveIdentity(customerIdentity, multipartFiles);
    }

    @PostMapping("/deleteIdentity")
    @ResponseBody
    public ResponseEntity<ResultModel> deleteIdentity(int customerIdentityId){
        return customerIdentityService.deleteIdentity(customerIdentityId);
    }

}
