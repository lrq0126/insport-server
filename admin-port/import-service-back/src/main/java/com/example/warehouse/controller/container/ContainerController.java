package com.example.warehouse.controller.container;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.ExcelUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.Container;
import com.example.warehouse.entity.ContainerNumber;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.dwReceipt.ContainerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.example.warehouse.enums.ResultStatus.*;

/**
 * 批次
 */
@Controller
@RequestMapping("/container")
@Slf4j
public class ContainerController {
    @Autowired
    private ContainerService containerService;

    /**
     * 查询批次列表
     * @param map name: 批次名称  isout: 是否出仓，0否 1 是
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> list(@RequestParam Map<String, Object> map){
        PageData data = (PageData) PageHelp.initPage(map).get("page");
        int count = containerService.countContainerList(map);
        List<Container> list = new ArrayList<>();
        if ( count > 0 ){
            list = containerService.getContainerList(map);
        }
        data.setTotal(count);
        Map<String,Object> result = new HashMap<>();
        result.put("page",data);
        return new ResponseEntity<>(PageResultModel.ok(list,result), HttpStatus.OK);
    }

    /**
     * 创建批次名称
     * @param name
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity create(String name){
        if( StringUtils.isBlank(name) ){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        try {
            containerService.saveContainer(name);
        }catch (Exception e){
            e.printStackTrace();
            log.error("添加批次名称失败:{}",e.getMessage());
            return new ResponseEntity<>(PageResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok("添加成功"), HttpStatus.OK);
    }

    /**
     * 根据id查询单号详情--进入修改页面
     * @return
     */
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResultModel> get(String id){
        if( StringUtils.isBlank(id) ){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        Container container = containerService.get(Integer.parseInt(id));
        return new ResponseEntity<>(PageResultModel.ok(container), HttpStatus.OK);
    }

    /**
     * 修改批次详情，需要更新语音文件
     * @param container
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity update(Container container){
        if( StringUtils.isBlank(container.getName())){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        try {
            containerService.updateContainer(container);
        }catch (Exception e){
            e.printStackTrace();
            log.error("修改失败:{}",e.getMessage());
            return new ResponseEntity<>(PageResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok("修改成功"), HttpStatus.OK);
    }

    /**
     * 删除批次
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity delete(String ids){
        if( StringUtils.isBlank(ids) ){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        try {
            List<String> idList = Arrays.asList(ids.split(","));
            int i = containerService.deleteContainer(idList);
            if ( i == 101 ){
                return new ResponseEntity<>(ResultModel.error(CONTAINER_HAVE_NUMBER_ERROR), HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除批次名称失败:{}",e.getMessage());
            return new ResponseEntity<>(PageResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok("删除成功"), HttpStatus.OK);
    }

    /**
     * 删除批次
     * @param ids
     * @return
     */
    @RequestMapping(value = "/outContainers",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity outContainers(String ids){
        if( StringUtils.isBlank(ids) ){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        try {
            List<String> idList = Arrays.asList(ids.split(","));
            int i = containerService.outContainers(idList);
        }catch (Exception e){
            e.printStackTrace();
            log.error("操作失败:{}",e.getMessage());
            return new ResponseEntity<>(PageResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok("出仓成功"), HttpStatus.OK);
    }

    /**
     * 批次入库
     * @param containerNumber
     * @return
     */
    @RequestMapping(value = "/inStorage",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity inStorage(ContainerNumber containerNumber){
        if( containerNumber.getContainerId() == null ){
            return new ResponseEntity<>(ResultModel.error(CONTAINER_ID_NULL), HttpStatus.OK);
        }
        if( StringUtils.isBlank(containerNumber.getAgentNumber()) ){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        if ( containerService.checkNumberIsIn(containerNumber) > 0 ){
            return new ResponseEntity<>(ResultModel.error(GOODS_HAVE_IN_ERROR), HttpStatus.OK);
        }
        try {
            containerNumber = containerService.saveContainerNumber(containerNumber);
        }catch (Exception e){
            e.printStackTrace();
            log.error("添加批次名称失败:{}",e.getMessage());
            return new ResponseEntity<>(PageResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(containerNumber), HttpStatus.OK);
    }

    /**
     * 查询所有未出仓的批次--扫描时候的下拉菜单
     * @return
     */
    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResultModel> itemList(){
        List<Container> list = containerService.getContainerItemList("0");
        return new ResponseEntity<>(PageResultModel.ok(list), HttpStatus.OK);
    }

    /**
     * 查询所有的批次--下拉菜单
     * @return
     */
    @RequestMapping(value = "/item/all",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResultModel> itemAll(){
        List<Container> list = containerService.getContainerItemList(null);
        return new ResponseEntity<>(PageResultModel.ok(list), HttpStatus.OK);
    }


    /**
     * 查询单号列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/number/list",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> numberList(@RequestParam Map<String, Object> map){
        PageData data = (PageData) PageHelp.initPage(map).get("page");
        int count = containerService.countContainerNumberList(map);
        List<ContainerNumber> list = new ArrayList<>();
        if ( count > 0 ){
            list = containerService.getContainerNumberList(map);
        }
        data.setTotal(count);
        Map<String,Object> result = new HashMap<>();
        result.put("page",data);
        return new ResponseEntity<>(PageResultModel.ok(list,result), HttpStatus.OK);
    }

    /**
     * 删除批次单号
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteNumber",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteNumber(String ids){
        if( StringUtils.isBlank(ids) ){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        try {
            List<String> idList = Arrays.asList(ids.split(","));
            int i = containerService.deleteContainerNumbers(idList);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除批次单号失败:{}",e.getMessage());
            return new ResponseEntity<>(PageResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok("删除成功"), HttpStatus.OK);
    }

    /**
     * 根据id查询单号详情--进入修改页面
     * @return
     */
    @RequestMapping(value = "/number/get",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResultModel> getNumber(String id){
        if( StringUtils.isBlank(id) ){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        ContainerNumber containerNumber = containerService.getContainerNumber(Integer.parseInt(id));
        List<Container> list = containerService.getContainerItemList("0");
        Map<String,Object> map = new HashMap<>(2);
        map.put("entity",containerNumber);
        map.put("itemList",list);
        return new ResponseEntity<>(PageResultModel.ok(map), HttpStatus.OK);
    }

    /**
     * 修改单号详情
     * @param containerNumber
     * @return
     */
    @RequestMapping(value = "/number/update",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity numberUpdate(ContainerNumber containerNumber){
        if( StringUtils.isBlank(containerNumber.getAgentNumber() ) || containerNumber.getId() == null ){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }
        try {
            containerService.updateContainerNumber(containerNumber);
        }catch (Exception e){
            e.printStackTrace();
            log.error("修改失败:{}",e.getMessage());
            return new ResponseEntity<>(PageResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok("修改成功"), HttpStatus.OK);
    }

    /**
     * 导出excel文件
     * @param
     * @return
     */
    @RequestMapping(value = "/number/exportExcel",method = RequestMethod.GET)
    public void exprotExcel(HttpServletResponse response, HttpServletRequest request,String ids,String containerId,String startTime,String endTime) throws Exception {
        Map<String,Object> map = new HashMap<>();
        if ( StringUtils.isNotBlank( ids )){
            List<String> idList = Arrays.asList(ids.split(","));
            map.put("list",idList);
        }
        if ( StringUtils.isNotBlank( containerId )){
            map.put("containerId",containerId);
        }
        if ( StringUtils.isNotBlank( startTime )){
            map.put("startTime",startTime);
        }
        if ( StringUtils.isNotBlank( endTime )){
            map.put("endTime",endTime);
        }
        List<ContainerNumber> list = containerService.exprotList(map);
        // 表格列标题
        String[] title = {"序号", "批次名称", "单号", "扫描日期"};
        String[][] arrs = new String[list.size()][];
        int i = 0;
        for ( ContainerNumber number : list ){
            String []sttrArr = new String[title.length];
            sttrArr[0] = ++i + "";
            sttrArr[1] = number.getCname();
            sttrArr[2] = number.getAgentNumber();
            sttrArr[3] = number.getCreateTime();
            arrs[i - 1] = sttrArr;
        }
        ExcelUtil.exportExcel1(response, request, arrs, "批次扫描导出[" + DateUtil.getYmdhmsStr() + "].xls", title);
    }
}
