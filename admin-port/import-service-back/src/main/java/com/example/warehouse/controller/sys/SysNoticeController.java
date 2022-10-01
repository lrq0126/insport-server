package com.example.warehouse.controller.sys;

import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.sys.SysNotice;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.log.SysLog;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.SysNoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/sys/sysNotice")
public class SysNoticeController {
    @Autowired
    private SysNoticeService sysNoticeService;

    /**
     * 查询通知列表
     * @param param pageSize  pageNumber
     *              noticeContent 消息内容
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> list(@RequestParam Map<String,Object> param){
        PageHelp.initPage(param);
        if(param.containsKey("createTime[0]")){
            param.put("startTime", param.get("createTime[0]"));
        }
        if(param.containsKey("createTime[1]")){
            param.put("endTime", param.get("createTime[1]"));
        }
        int total = 0;
        total = sysNoticeService.countSelectNoticeList(param);
        List<SysNotice> list = new ArrayList<>();
        if(total > 0){
            list = sysNoticeService.selectNoticeList(param);
        }
        PageData data = (PageData) param.get("page");
        Map<String,Object> resultMap = new HashMap<>();
        data.setTotal(total);
        resultMap.put("list",list);
        resultMap.put("page",data);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    /**
     * 查看通知详情
     */
    @RequestMapping(value = "getNoticeDetail",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResultModel> getNoticeDetail(@RequestParam int id){

        return sysNoticeService.getNoticeDetail(id);
    }
    /**
     * 发布通知
     * @param sysNotice  userCode 操作员id  content  消息内容
     * @return
     */
    @RequiresPermissions("sysNotice:publish")
    @RequestMapping(value = "publish",method = RequestMethod.POST)
    @ResponseBody
    @SysLog(value = "发布通知[通知标题]",type = SysLog.Type.PUBLISH,contentId = "sysNotice.noticeTitle")
    public ResponseEntity<ResultModel> publishNotice(SysNotice sysNotice){
        if(sysNotice.getNoticeContent() == null || sysNotice.getNoticeContent().isEmpty()){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.NOTICE_CONTENT_NULL), HttpStatus.OK);
        }

        return sysNoticeService.publishNotice(sysNotice);
    }

    /**
     * 删除通知
     * @param param  noticeId 通知id
     * @return
     */
    @RequiresPermissions("sysNotice:delete")
    @RequestMapping(value = "delete",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResultModel> deleteNotice(@RequestParam Map<String,Object> param){
        int i = sysNoticeService.deleteNotice(param.get("noticeId").toString());
        if(i > 0){
            return new ResponseEntity<>(ResultModel.ok("删除成功"),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
    }
}
