package com.example.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.HttpUtilManager;
import com.example.warehouse.entity.sys.LogRequest;
import com.example.warehouse.entity.sys.TrajectoryLog;
import com.example.warehouse.mapper.sys.LogRequestMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.AnalysisService;
import com.example.warehouse.service.sys.TrajectoryService;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.order.Track17ReqVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/AppDataService")
/**
 * 查询转单轨迹
 */
public class OrderController {

    @Autowired
    private LogRequestMapper logRequestMapper;

    @Autowired
    private TrajectoryService trajectoryService;

    @Autowired
    private AnalysisService analysisService;

    @Autowired
    private SendMessageServer sendMessageServer;

    /**
     * 系统标识
     */
    @Value("${wechat.system_code}")
    private String SYSTEM_CODE;

    /**
     * 支持多单号的查询接口
     * @param orderNumber
     * @return
     */
    //    @ResponseBody
    //    @RequestMapping(value = "/getOrderData",method = RequestMethod.GET)
    public JSONObject getOrderData(String orderNumber, HttpServletRequest req) {

        if ( orderNumber.startsWith("MY") ){
            log.info("-------->>>发现是马来的单号，不请求数据");
            return JSONObject.parseObject("{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\"1Z840YV520950053512\",\"message\":\"单号系统中不存在！\"}]}");
        }

        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        Map<String,Object> params = new HashMap<>(2);

        Map<String,String> verify = new HashMap<>(2);
        verify.put("Clientid","DW");
        verify.put("Token","yEqO80H9KRu4C37OqIV9");
        params.put("Verify",verify);

        List<JSONObject> data = new ArrayList<>();

        if (StringUtils.isNotBlank(orderNumber)){
            String[] nums = orderNumber.split(",");
            for ( String number : nums ){
                JSONObject object = new JSONObject();
                object.put("TrackNumber",number.trim());
                data.add(object);
            }
        }
        params.put("Datas",data);

        String jsonString = JSON.toJSONString(params);
        System.out.println(jsonString);
        String result = httpUtil.post("http://dwgj.kingtrans.net/PostInterfaceService?method=searchTrack",jsonString,req);
        System.out.println("通过请求后，获取的结果数据集合："+result);
        if ( result.startsWith("<!") ){
            result = "{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\"1Z840YV520950053512\",\"message\":\"单号系统中不存在！\"}]}";
        }
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    /**
     * 获取马来西亚的轨迹
     * @param orderNumber
     * @return
     */
    public JSONObject getMYOrderData(String orderNumber, HttpServletRequest req) {

        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        Map<String,Object> params = new HashMap<>(2);

        Map<String,String> verify = new HashMap<>(2);
        verify.put("Clientid","20010109");
        verify.put("Token","R1pEVw==EsDRHnptA72sK9mcXvUW");
        params.put("Verify",verify);

        List<JSONObject> data = new ArrayList<>();

        if (StringUtils.isNotBlank(orderNumber)){
            String[] nums = orderNumber.split(",");
            for ( String number : nums ){
                JSONObject object = new JSONObject();
                object.put("TrackNumber",number.trim());
                data.add(object);
            }
        }
        params.put("Datas",data);

        String jsonString = JSON.toJSONString(params);
        System.out.println(jsonString);
        String result = httpUtil.post("http://gzdw.kingtrans.net/PostInterfaceService?method=searchTrack",jsonString,req);
        System.out.println("getMYOrderData请求：" + result);
        if ( result.indexOf("访问太过频繁") > 0 ){
            result = "{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\"DWG81238809357\",\"message\":\"单号系统中不存在！\"}]}";
        }
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    /**
     * 获取飞通东马-菲律宾的轨迹
     * @param orderNumber
     * @return
     */
    public JSONObject getFTOrderData(String orderNumber, HttpServletRequest req) {

        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        Map<String,Object> params = new HashMap<>(2);

        Map<String,String> verify = new HashMap<>(2);
        verify.put("Clientid","HW");
        verify.put("Token","AG4xxjv1u7oY6f9UYy18");
        params.put("Verify",verify);

        List<JSONObject> data = new ArrayList<>();

        if (StringUtils.isNotBlank(orderNumber)){
            String[] nums = orderNumber.split(",");
            for ( String number : nums ){
                JSONObject object = new JSONObject();
                object.put("TrackNumber",number.trim());
                data.add(object);
            }
        }
        params.put("Datas",data);

        String jsonString = JSON.toJSONString(params);
        System.out.println(jsonString);
        String result = httpUtil.post("http://fto.kingtrans.cn/PostInterfaceService?method=searchTrack",jsonString,req);
        System.out.println("getFTOrderData请求:" + result);
        if ( result.indexOf("访问太过频繁") > 0 ){
            result = "{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\"DWG81238809357\",\"message\":\"单号系统中不存在！\"}]}";
        }
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    /**
     * 获取澳洲豹的轨迹
     * @param orderNumber
     * @return
     */
    public JSONObject getAozhoubaoOrderData(String orderNumber, HttpServletRequest req) {

        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        Map<String,Object> params = new HashMap<>(2);

        Map<String,String> verify = new HashMap<>(2);
        verify.put("Clientid","feilunyun");
        verify.put("Token","AvKSO9512irj78A36Rjf");
        params.put("Verify",verify);

        List<JSONObject> data = new ArrayList<>();

        if (StringUtils.isNotBlank(orderNumber)){
            String[] nums = orderNumber.split(",");
            for ( String number : nums ){
                JSONObject object = new JSONObject();
                object.put("TrackNumber",number.trim());
                data.add(object);
            }
        }
        params.put("Datas",data);

        String jsonString = JSON.toJSONString(params);
        System.out.println(jsonString);
        String result = httpUtil.post("http://hgzx.kingtrans.cn/PostInterfaceService?method=searchTrack",jsonString,req);
        System.out.println(result);
        if ( result.indexOf("访问太过频繁") > 0 ){
            result = "{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\"DWG81238809357\",\"message\":\"单号系统中不存在！\"}]}";
        }
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    /**
     * 轨迹更新消息提醒（17轨迹网触发接口）
     * @param body
     */
    @PostMapping(value = "/updateTracking")
    @ResponseBody
    public void updateTracking(@RequestBody String body){
        log.info("17轨迹网日志推送触发，请求参数：{}", body);
        trajectoryService.updateTrack17Data(body);
    }
    /**
     * 只支持单个单号的查询接口
     * @param orderNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderDateByK5",method = RequestMethod.GET)
    public JSONObject getOrderDateByK5(String orderNumber){
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        Map<String,Object> params = new HashMap<>(2);
        params.put("TrackingNumber",orderNumber);
        params.put("Language","cn");
        String jsonString = JSON.toJSONString(params);
        String result = httpUtil.post("http://dw.kingtrans.cn/AppDataService?method=searchTrackByBillid",jsonString);
        if ( result.indexOf("访问太过频繁") > 0 ){
            result = "{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\"DWG81238809357\",\"message\":\"单号系统中不存在！\"}]}";
        }
        JSONObject jsStr = JSONObject.parseObject(result);
        JSONArray a = jsStr.getJSONArray("Datas");
        System.out.println(a);
        return jsStr;
    }

    /**
     * 只支持单个单号的查询接口---优化后可以支持多单查询的直客包裹轨迹--旧版本
     * @param orderNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderDatas",method = RequestMethod.GET)
    public JSONObject getOrderDateBySingleNumber(String orderNumber, HttpServletRequest req){
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        Map<String,Object> params = new HashMap<>(2);
        JSONObject resultJson = new JSONObject();
        resultJson.put("statusCode","success");
        JSONArray returnData = new JSONArray();

        LogRequest request = new LogRequest();
        request.setCreateTime(DateUtil.timestamp2String(new Date()));
        request.setMessage("物流轨迹查询");
        request.setRequestParam(orderNumber);
        logRequestMapper.insert(request);

        if (StringUtils.isNotBlank(orderNumber)){
            String[] nums = orderNumber.split(",");
            if ( nums.length > 10 ){
                JSONObject returnDatas = new JSONObject();
                // 如果数组为空，则还没有该订单信息
                returnDatas.put("statusCode","success");
                returnDatas.put("trackNumber","");
                returnDatas.put("message","一次只允许查询10个单号！");
                returnDatas.put("items","一次只允许查询10个单号！");
                returnData.add(returnDatas);
                resultJson.put("returnDatas",returnData);
                return resultJson;
            }
            for ( String number : nums ){
                System.out.println(number);
                params.clear();
                params.put("TrackingNumber",number);
                params.put("Language","cn");
                String jsonString = JSON.toJSONString(params);
                String result = httpUtil.post("http://dw.kingtrans.cn/AppDataService?method=searchTrackByBillid",jsonString,req);
                System.out.println("德威请求：" + result);
                if ( result.indexOf("此请求访问太过频繁") > 0 ){
                    result = "{\"StatusCode\":\"success\",\"Billid\":\"000000000\",\"Transbillid\":\"000000000\",\"TrackStatusCn\":\"\",\"TrackStatusEn\":\"\",\"CountryCode\":\"美国\",\"countryNameCn\":\"\",\"countryNameEn\":\"\",\"Datas\":[]}";
                }
                JSONObject jsStr = JSONObject.parseObject(result);
                JSONArray a = jsStr.getJSONArray("Datas");
                JSONObject returnDatas = new JSONObject();
                if ( a == null || a.size() == 0 ){

                    trajectoryService.saveTrack(number,req);

                    // 如果数组为空，则还没有该订单信息
                    returnDatas.put("statusCode","error");
                    returnDatas.put("trackNumber",number);
                    returnDatas.put("message","单号系统中不存在！");
                    returnData.add(returnDatas);
                }else {
                    // 封装一个适配多单接口的查询
                    returnDatas.put("statusCode","success");
                    returnDatas.put("trackNumber",number);
                    returnDatas.put("track",null);

                    JSONArray items = new JSONArray();
                    for ( int i = 0 ; i < a.size(); i ++ ){
                        JSONObject o = a.getJSONObject(i);

                        JSONObject itemObject = new JSONObject();
                        itemObject.put("dateTime", o.get("Sdate"));
                        itemObject.put("location", o.get("Place"));
                        itemObject.put("info", o.get("Intro"));

                        items.add(itemObject);
                    }
                    returnDatas.put("items",items);
                    returnData.add(returnDatas);
                }
            }
        }
        resultJson.put("returnDatas",returnData);
        return resultJson;
    }

    /**
     * 只支持单个单号的查询接口---优化后可以支持多单查询的直客包裹轨迹,最新优化后的
     * @param orderNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderData",method = RequestMethod.GET)
    public JSONObject getOrderDatas(String orderNumber, HttpServletRequest req){

        JSONObject resultJson = new JSONObject();
        resultJson.put("statusCode","success");
        JSONArray returnData = new JSONArray();

        LogRequest request = new LogRequest();
        request.setCreateTime(DateUtil.timestamp2String(new Date()));
        request.setMessage("物流轨迹查询");
        request.setRequestParam(orderNumber);
        logRequestMapper.insert(request);

        if (StringUtils.isNotBlank(orderNumber)){
            String[] nums;
            if(orderNumber.contains(",")){
                nums = orderNumber.split(",");
            }else {
                nums = orderNumber.split("，");
            }
            if ( nums.length > 10 ){
                JSONObject returnDatas = new JSONObject();
                // 如果数组为空，则还没有该订单信息
                returnDatas.put("statusCode","success");
                returnDatas.put("trackNumber","");
                returnDatas.put("message","一次只允许查询10个单号！");
                returnDatas.put("items","一次只允许查询10个单号！");
                returnData.add(returnDatas);
                resultJson.put("returnDatas",returnData);
                return resultJson;
            }
            for ( String number : nums ) {
                number = number.trim();
                JSONObject returnTrack = new JSONObject();
                returnTrack = trajectoryService.saveTrack(number,req);

                // 查询17网的轨迹信息并拼接
                JSONObject tracking17Result = trajectoryService.getTrack17Tracking(number, req);
                if(tracking17Result != null && !tracking17Result.isEmpty()){
                    JSONObject trackingResult = trajectoryService.joiningTracking(tracking17Result, number);
                    if(trackingResult != null){
                        returnTrack = trackingResult;
                    }
                }

                returnData.add(returnTrack);
            }
        }
        resultJson.put("returnDatas",returnData);
        return resultJson;
    }

    /**
     * 手动更新订单轨迹
     * @param orderNumber
     * @return
     */
    @ResponseBody
    @PostMapping(value = "updateOrderTrack")
    public ResponseEntity<ResultModel> updateOrderTrack(String orderNumber, HttpServletRequest request){
        return trajectoryService.updateOrderTrack(orderNumber, request);
    }

    /**
     * 来自其他系统的手动更新订单轨迹（请求到飞轮的网址）
     * @param orderNumber
     * @return
     */
    @ResponseBody
    @PostMapping(value = "otherSystemUpdateOrderTrack")
    public ResponseEntity<ResultModel> otherSystemUpdateOrderTrack(@RequestBody String orderNumber, HttpServletRequest request){
        return trajectoryService.updateTrack(orderNumber, request);
    }

    @Data
    class Datas{
        private String Sdate; // 时间
        private String Place; // 地点
        private String Intro; // 轨迹说明
    }

    /**
     * 保存签收时解析过来的包裹信息，做预报处理
     * @param deliveryOrderNo 转单号
     * @param loginName 会员号
     * @param imgUrl 图片路径
     */
    @RequestMapping("/saveDeliverOrderNoBySign")
    public void saveDeliverOrderNoBySign(String deliveryOrderNo,String loginName,String imgUrl){
        analysisService.saveDeliveryOrderNoAndLoginName(deliveryOrderNo,loginName,imgUrl);
    }


    /**
     * 轨迹更新消息提醒（系统内部自用）
     * @param requestData
     */
    @PostMapping("/trackingUpdateMessage")
    @ResponseBody
    public void trackingUpdateMessage(@RequestBody String requestData){
        log.info("请求参数：{}", requestData);
        JSONObject jsonObject = JSONObject.parseObject(requestData);

        TrajectoryLog trajectoryLog = JSONObject.parseObject(jsonObject.getString("trajectoryLog"), TrajectoryLog.class);
        String message = jsonObject.getString("trackingMessage");
        String orderStatus = jsonObject.getString("orderStatus");
        sendMessageServer.sendMessageForTrackUpdate(trajectoryLog, message, orderStatus);
    }

    /**
     * 订单注册到17轨迹轨迹网
     * @param requestData
     * @return
     */
    @PostMapping("registerTrack17OrderData")
    @ResponseBody
    public ResponseEntity<ResultModel> registerTrack17OrderData(String requestData, HttpServletRequest req) {
        log.info("请求参数：{}", requestData);
        return trajectoryService.registerTrack17OrderData(requestData, req);
    }

    /**
     * 其他系统订单注册到17轨迹轨迹网
     * @param requestData
     * @return
     */
    @PostMapping("/otherSystemRegister")
    @ResponseBody
    public void otherSystemRegister(@RequestBody String requestData, HttpServletRequest req) {
        log.info("请求参数：{}", requestData);
        JSONObject requestDataJson = JSONObject.parseObject(requestData);

//        trajectoryService.batchInsertOrder(requestDataJson.getString("orderData"));

        trajectoryService.registerTrack17OrderData(requestDataJson.getString("orderData"), req);
    }

    /**
     * 获取17网的轨迹
     * @param track17ReqVo
     * @return
     */
    @PostMapping("getTrack17List")
    @ResponseBody
    public ResponseEntity<PageResultModel> getTrack17List(Track17ReqVo track17ReqVo, HttpServletRequest request) {
        return trajectoryService.getTrack17List(track17ReqVo, request);
    }

    /**
     * 获取订单的轨迹详情
     * @param orderNumber
     * @return
     */
    @PostMapping("getTrack17OrderTracking")
    @ResponseBody
    public ResponseEntity<ResultModel> getTrack17OrderTracking(String orderNumber, HttpServletRequest req) {
        return trajectoryService.getTrack17OrderTracking(orderNumber, req);
    }

    /**
     * 获取订单的更新信息
     * @return
     */
    @PostMapping("getTrackingUpdate")
    @ResponseBody
    public ResponseEntity<PageResultModel> getTrackingUpdate(@RequestParam Map<String, Object> requestMap) {
        return trajectoryService.getTrackingUpdate(requestMap);
    }


}
