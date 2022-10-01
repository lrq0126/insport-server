package com.dwgj.mlxydedicatedline.controller.ping;

import com.dwgj.mlxydedicatedline.entity.ping.PingMain;
import com.dwgj.mlxydedicatedline.resultType.PageData;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.ping.PingMainService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


/**
 * @author guoshengwen
 * @message 拼团主类
 * @date 2020年12月30日17:37
 */
@Controller
@RequestMapping("/pin")
@Api
public class PingMainController {

    @Autowired
    private PingMainService pingMainService;

    /**
     * 我参与的拼团列表
     * @param paramMap
     * 具体参数 pageNumber 当前页； pageSize 每页多少条； orderNumber 拼团订单号；pinName 拼团名称；
     * @return
     */
    @ApiOperation(value = "我参与的拼团列表,参数：paramMap(pageNumber 当前页； pageSize 每页多少条； orderNumber 拼团订单号；pinName 拼团名称；)")
    @PostMapping("/myTakePartInlist")
    @ResponseBody
    public ResponseEntity<ResultModel> myTakePartInlist(@RequestParam Map<String, Object> paramMap){
        if( !paramMap.containsKey("pageNumber")){
            paramMap.put("pageNumber",0);
        }
        if( !paramMap.containsKey("pageSize")){
            paramMap.put("pageSize",10);
        }
        return pingMainService.takePartInList(paramMap);
    }

    /**
     * 我发起的拼团列表
     * @param paramMap
     * 具体参数 pageNumber 当前页； pageSize 每页多少条； orderNumber 拼团订单号；pinName 拼团名称；
     * @return
     */
    @ApiOperation(value = "我发起的拼团列表,参数：paramMap(pageNumber 当前页； pageSize 每页多少条； orderNumber 拼团订单号；pinName 拼团名称；)")
    @PostMapping("/ownList")
    @ResponseBody
    public ResponseEntity<ResultModel> ownList(@RequestParam Map<String, Object> paramMap){
        if( !paramMap.containsKey("pageNumber")){
            paramMap.put("pageNumber",0);
        }
        if( !paramMap.containsKey("pageSize")){
            paramMap.put("pageSize",10);
        }
        return pingMainService.ownList(paramMap);
    }

    /**
     * 所有拼团列表
     * @param paramMap
     * 具体参数 pageNumber 当前页； pageSize 每页多少条； orderNumber 拼团订单号；pinName 拼团名称；
     * @return
     */
    @ApiOperation(value = "所有拼团列表,参数：paramMap(pageNumber 当前页； pageSize 每页多少条； orderNumber 拼团订单号；pinName 拼团名称；)")
    @ApiResponses({
        @ApiResponse(code = 100, message = "ok", response=PingMain.class ),
        @ApiResponse(code = 200, message = "跟100是一样的，两个实体加在一起", response= PageData.class ),
    })
    @PostMapping("/pinList")
    @ResponseBody
    public ResponseEntity<ResultModel> pinList(@RequestParam Map<String, Object> paramMap){
        if( !paramMap.containsKey("pageNumber")){
            paramMap.put("pageNumber",0);
        }
        if( !paramMap.containsKey("pageSize")){
            paramMap.put("pageSize",10);
        }
        return pingMainService.pinList(paramMap);
    }

    /**
     * 首页拼团置顶列表，最多12条，每次显示3条，向上循环滚动
     * @return
     */
    @ApiOperation(value = "首页拼团置顶列表，最多12条，每次显示3条，向上循环滚动")
    @GetMapping("/homeList")
    @ResponseBody
    public ResponseEntity<ResultModel> homeList(){
        return pingMainService.homeList();
    }

    /**
     * 创建拼团
     * @param multipartFiles
     * @param pin
     * @return
     */
    @ApiOperation(value = "创建拼团")
    @PostMapping("/addPin")
    @ResponseBody
    public ResponseEntity<ResultModel> addPin(
        @ApiParam(name="file",value="图片组",required=true)@RequestParam("file") MultipartFile[] multipartFiles,
        @ApiParam(name="pin",value="拼团实体类",required=true)PingMain pin){
//        if ( UserThreadContext.getUser() == null )
//            return new ResponseEntity<>(ResultModel.error(NULL_USER), HttpStatus.OK);
        return pingMainService.savePingtuan(multipartFiles,pin, false);
    }

    /**
     * 重新激活
     * @param pin
     * @return
     */
    @ApiOperation(value = "重新激活")
    @PostMapping("/reactivate")
    @ResponseBody
    public ResponseEntity<ResultModel> reactivate(
        @ApiParam(name="pin",value="拼团实体类,只给我pId和时间cutOffTime就行",required=true)PingMain pin){
        return pingMainService.reactivatePin(pin);
    }

    /**
     * 根据订单号获取拼团详情
     * @param orderNumber
     * @return
     */
    @ApiOperation(value = "根据订单号获取拼团详情")
    @GetMapping("/getPinTuanByOrderNumber")
    @ResponseBody
    public ResponseEntity<ResultModel> getPinTuanByOrderNumber(@ApiParam(name="orderNumber",value="拼团订单号",required=true)String orderNumber){
        return pingMainService.getPingtuan(orderNumber);
    }

    /**
     * 根据拼团订单参加拼团或者删除该团下的某些包裹
     * @param orderNumber
     * @param goodsIds
     * @param type 操作类型：add添加  delete删除
     * @return
     */
    @ApiOperation(value = "根据拼团订单参加拼团或者删除该团下的某些包裹")
    @PostMapping("/takePartInPin")
    @ResponseBody
    public ResponseEntity<ResultModel> takePartInPin(@ApiParam(name="orderNumber",value="拼团订单号",required=true)String orderNumber,
        @ApiParam(name="goodsIds",value="包裹id族，用逗号隔开，例如：14581,4512",required=true)String goodsIds,
        @ApiParam(name="type",value="操作类型：add添加  delete删除",required=true)String type){
        boolean takeIn = false;
        if ( "add".equals(type) ){
            takeIn = true;
        }
        return pingMainService.takePartIn(orderNumber,goodsIds,takeIn);
    }

    /**
     * 退出拼团，需要删除所有包裹，把所有之前参加过该团的包裹改为可以申请打包的状态
     * @param orderNumber
     * @return
     */
    @ApiOperation(value = "退出拼团，需要删除所有包裹，把所有之前参加过该团的包裹改为可以申请打包的状态")
    @GetMapping("/quitPin")
    @ResponseBody
    public ResponseEntity<ResultModel> quitPin(@ApiParam(name="orderNumber",value="拼团订单号",required=true)String orderNumber){
        return pingMainService.quitPin(orderNumber);
    }

    /**
     * 检查成员是否有资格进入拼团（是否设置密码）
     * @param orderNumber
     * @return
     */
    @ApiOperation(value = "检查成员是否有资格进入拼团（是否设置密码）")
    @GetMapping("/checkIsInPin")
    @ResponseBody
    @ApiResponses({
        @ApiResponse(code = 101, message = "有权限进入"),
        @ApiResponse(code = 102, message = "没权限进入，需要输入密码，注意：不要返回提示，直接弹出密码框"),
        @ApiResponse(code = 103, message = "该团已达上限人数"),
        @ApiResponse(code = 104, message = "该团已过截止日期"),
    })
    public ResponseEntity<ResultModel> checkIsInPin(@ApiParam(name="orderNumber",value="拼团订单号",required=true)String orderNumber,
        @ApiParam(name="pId",value="拼团id",required=true)int pId)throws Exception{
        return pingMainService.checkIsIn(orderNumber,pId);
    }

    /**
     * 检查密码是否正确
     * @param orderNumber
     * @param password
     * @return
     */
    @ApiOperation(value = "检查密码是否正确")
    @GetMapping("/checkPassword")
    @ResponseBody
    public ResponseEntity<ResultModel> checkPassword(@ApiParam(name="orderNumber",value="拼团订单号",required=true)String orderNumber,
        @ApiParam(name="password",value="密码",required=true)String password){
        return pingMainService.checkPassword(orderNumber,password);
    }

    /**
     * 踢出某一个成员
     * @param pId  拼团id
     * @param memberId 成员id
     * @return
     */
    @ApiOperation(value = "踢出某一个成员")
    @GetMapping("/kickMember")
    @ResponseBody
    public ResponseEntity<ResultModel> kickMember(@ApiParam(name="pId",value="拼团id",required=true)String pId,
        @ApiParam(name="memberId",value="成员id",required=true)String memberId){
        return pingMainService.kickMember(pId,memberId);
    }

    /**
     * 删除拼团信息
     * @param pId  拼团id
     * @param orderNumber 成员id
     * @return
     */
    @ApiOperation(value = "删除拼团信息")
    @GetMapping("/deletePin")
    @ResponseBody
    public ResponseEntity<ResultModel> deletePin(@ApiParam(name="pId",value="拼团id",required=true)String pId,
        @ApiParam(name="orderNumber",value="拼团订单号",required=true)String orderNumber){
        return pingMainService.deletePin(pId,orderNumber);
    }

    /**
     * 提醒成员添加包裹
     * @param pId  拼团id
     * @param orderNumber 拼团订单号
     * @param memberId 成员的id
     * @return
     */
    @ApiOperation(value = "提醒成员添加包裹")
    @GetMapping("/messageMember")
    @ResponseBody
    public ResponseEntity<ResultModel> messageMember(@ApiParam(name="pId",value="拼团id",required=true)String pId,
        @ApiParam(name="orderNumber",value="拼团订单号",required=true)String orderNumber,
        @ApiParam(name="memberId",value="成员的id",required=true)String memberId){
        return pingMainService.messgeMember(pId,orderNumber,memberId);
    }

    /**
     * 检查是否有成团条件
     * @param pId  拼团id
     * @param orderNumber 拼团订单号
     * @return
     */
    @ApiOperation(value = "检查是否有成团条件")
    @GetMapping("/checkConfirm")
    @ResponseBody
    public ResponseEntity<ResultModel> checkConfirm(@ApiParam(name="pId",value="拼团id",required=true)String pId,
        @ApiParam(name="orderNumber",value="拼团订单号",required=true)String orderNumber){
        return pingMainService.checkConfirm(pId,orderNumber);
    }

    /**
     * 确认成团
     * @param pId  拼团id
     * @param orderNumber 拼团订单号
     * @return
     */
    @ApiOperation(value = "确认成团")
    @GetMapping("/confirm")
    @ResponseBody
    public ResponseEntity<ResultModel> confirm(@ApiParam(name="pId",value="拼团id",required=true)String pId,
        @ApiParam(name="orderNumber",value="拼团订单号",required=true)String orderNumber){
        return pingMainService.confirm(pId,orderNumber);
    }

}
