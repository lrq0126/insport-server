package com.dwgj.mlxydedicatedline.controller.sys;

import com.dwgj.mlxydedicatedline.commons.ShiroUtil;
import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.sys.SysNotice;
import com.dwgj.mlxydedicatedline.resultType.PageData;
import com.dwgj.mlxydedicatedline.resultType.PageHelp;
import com.dwgj.mlxydedicatedline.resultType.PageResultModel;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.sys.SysNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.ERROR;

/**
 * 消息通知管理
 * @author guoshengwen
 * @date 2019年12月23日23点32分
 */
@RequestMapping("/sys")
@Controller
@Slf4j
public class SysNoticeController {

    @Autowired
    private SysNoticeService sysNoticeService;

    @GetMapping("getNotReadMessage")
    public ResponseEntity<ResultModel> getNotReadMessage(){
        return sysNoticeService.getNotReadMessage();
    }

    /**
     * paramMap-- customerId客户id，isRead是否已读：0未读，1已读，content消息内容
     *             pageSize pageNumber
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "selectNoticeList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> list(@RequestParam Map<String,Object> paramMap){
        if(StringUtils.isEmpty(paramMap.get("pageSize"))){
            paramMap.put("pageSize",10);
        }
        if(StringUtils.isEmpty(paramMap.get("pageNumber"))){
            paramMap.put("pageNumber",0);
        }
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(),paramMap.get("pageSize").toString());
        paramMap.put("pageNumber",data.getPageNumber());
        paramMap.put("pageSize",data.getPageSize());
        Customer customer = UserThreadContext.getUser();
        paramMap.put("customerId",customer.getId());
        int total = 0;
        List<SysNotice> list = new ArrayList<>();
        try {
            total = sysNoticeService.countSelectNoticeList(paramMap);
            if( total > 0 ){
                list = sysNoticeService.selectNoticeList(paramMap);
            }
        }catch (Exception e){
            log.error("分页查询消费消息列表出错，原因：{}",e.getMessage());
            return new ResponseEntity<>(PageResultModel.error(ERROR),HttpStatus.OK);
        }
        data.setTotal(total);
        Map<String,Object> result = new HashMap<>();
        result.put("page",data);
        return new ResponseEntity<>(PageResultModel.ok(list,result), HttpStatus.OK);
    }


    /**
     * @param noticeIds
     * @return
     */
    @RequestMapping(value = "updateRead",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResultModel> updateRead(@RequestParam("noticeIds") String noticeIds){

        return sysNoticeService.updateRead(noticeIds);

    }

    /**
     * paramMap-- noticeId消息id
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "updateNoticeRead",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResultModel> updateNoticeRead(@RequestParam Map<String,Object> paramMap){
        Customer customer = UserThreadContext.getUser();
        paramMap.put("customerId",customer.getId());
        try {
            sysNoticeService.updateNoticeRead(paramMap);
        }catch (Exception e){
            return new ResponseEntity<>(PageResultModel.error(ERROR,e.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(), HttpStatus.OK);
    }
}
