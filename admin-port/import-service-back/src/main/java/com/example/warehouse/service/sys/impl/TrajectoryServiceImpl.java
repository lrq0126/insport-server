package com.example.warehouse.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.DO.track17.ProviderDo;
import com.example.warehouse.DO.track17.ProviderEventDo;
import com.example.warehouse.DO.track17.ProvidersDo;
import com.example.warehouse.common.*;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.sys.Trajectory;
import com.example.warehouse.entity.sys.TrajectoryLog;
import com.example.warehouse.entity.wx.WechatMessage;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.sys.TrajectoryLogMapper;
import com.example.warehouse.mapper.sys.TrajectoryMapper;
import com.example.warehouse.mapper.wx.WechatMessageMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.TrajectoryService;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.appDataService.Track17ReqVo;
import com.example.warehouse.vo.appDataService.Track17RespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.warehouse.enums.ResultStatus.*;

@Service
@Slf4j
public class TrajectoryServiceImpl implements TrajectoryService {

    @Value("${wechat.system_code}")
    private String SYSTEM_CODE;

    // 轨迹更新信息推送接口
    @Value("${wechat.tracking-update-url}")
    private String TRACKING_UPDATE_URL;
    // 轨迹更新信息推送接口
    @Value("${track-17-token}")
    private String TRACK_17_TOKEN;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private TrajectoryLogMapper trajectoryLogMapper;

    @Autowired
    private TrajectoryMapper trajectoryMapper;

    @Autowired
    private SendMessageServer sendMessageServer;
    @Autowired
    private WechatMessageMapper wechatMessageMapper;

    @Override public int batchInsertToLog(List<TrajectoryLog> logs) {
        if ( logs != null && logs.size() > 0 ){
            return trajectoryLogMapper.batchInsert(logs);
        }
        return 0;
    }

    @Override public int batchInsertToTrack(List<Trajectory> trajectoryList) {
        if ( trajectoryList != null && trajectoryList.size() > 0 ){
            return trajectoryMapper.batchInsert(trajectoryList);
        }
        return 0;
    }

    /**
     * 检查单号是否当天第一次查询或者是历史首次查询，首次查询都插入日志数据，否则更新
     * @param orderNumber
     * @return
     */
    @Override public int checkOrderNumberIsFirst(String orderNumber) {
        int i = trajectoryLogMapper.checkTrackLog(orderNumber);
//        TrajectoryLog log = trajectoryLogMapper.getSingleTrackLog(orderNumber);
//        if ( i == 0 ){
//            // 第一次查询轨迹的话，插入一条记录
//            if ( null == log ){
//                log.setCheckTime(DateUtil.timestamp2String(new Date()));
//                log.setStatus(1);
//                log.setCreateTime(DateUtil.timestamp2String(new Date()));
//                log.setCheckNum(1);
//                log.setOrderNumber(orderNumber);
//                trajectoryLogMapper.insert(log);
//            }else {
//                log.setCheckNum(log.getCheckNum() + 1);
//                log.setCheckTime(DateUtil.timestamp2String(new Date()));
//                trajectoryLogMapper.updateByPrimaryKey(log);
//            }
//        }else {
//            log.setCheckNum(log.getCheckNum() + 1);
//            log.setCheckTime(DateUtil.timestamp2String(new Date()));
//            trajectoryLogMapper.updateByPrimaryKey(log);
//        }
        return i;
    }

    @Override public int checkOrderNumberToday(String orderNumber) {
        return 0;
    }

    @Override public List<Trajectory> getTrackListByOrderNumber(String orderNumbers,int desc) {
        return trajectoryMapper.getTrackListByOrderNumber(orderNumbers,desc);
    }

    @Override public int saveTrackLog(String number) {
        int i = 0;
        if ( StringUtils.isBlank(number) ){
            // 单号为空
            i = -101;
        }
        TrajectoryLog log = trajectoryLogMapper.getSingleTrackLog(number);
        if ( null == log ){
            // 如果是第一次查询
            log = new TrajectoryLog();
            log.setOrderNumber(number);
            log.setCheckNum(1);
            log.setStatus(1);
            log.setCreateTime(DateUtil.timestamp2String(new Date()));
            log.setCheckTime(DateUtil.timestamp2String(new Date()));
            log.setIsInside(0);

            trajectoryLogMapper.insert(log);

            i = 1;
        }else {
            // 之前查询过的单号，则对它进行更新数据
            log.setCheckNum(log.getCheckNum() + 1);
            log.setCheckTime(DateUtil.timestamp2String(new Date()));

            trajectoryLogMapper.updateByPrimaryKeySelective(log);

            i = 2;
        }

        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject saveTrack(String number,HttpServletRequest req) {

        // 检查是否今天第一次查询
        int isFirst = checkOrderNumberIsFirst(number);

        try {
            String ip = NetWorkUtil.getIpAddress(req);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 插入查询轨迹的数据
        int i = saveTrackLog(number);

        // 如果今天已经查询过，则直接从数据库中查询轨迹信息，不再调用其他外部接口
        if ( isFirst > 0 ){
            List<Trajectory> list = getTrackListByOrderNumber(number,1);
//            // 查询17网的轨迹信息
//            JSONObject tracking17Result = getTrack17Tracking(number, req);
//            if(tracking17Result != null && !tracking17Result.isEmpty()){
//                JSONObject trackingResult = joiningTracking(tracking17Result, number);
//                if(trackingResult != null){
//                    return tracking17Result;
//                }
//            }
            return trackDatas(list,number);
        }

        // 如果是内部轨迹，则直接数据库中查询，不调用外部接口
        int isInside = trajectoryLogMapper.checkTrackIsInside(number);
        if ( isInside > 0 ){
            List<Trajectory> list = getTrackListByOrderNumber(number,1);
            return trackDatas(list,number);
        }
//        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
//
//        Map<String,Object> params = new HashMap<>(2);
//        JSONArray returnData = new JSONArray();
//        params.clear();
//        params.put("TrackingNumber",number);
//        params.put("Language","cn");
//        String jsonString = JSON.toJSONString(params);
//        String result = httpUtil.post("http://dw.kingtrans.cn/AppDataService?method=searchTrackByBillid",jsonString,req);
//
//        if ( result.indexOf("此请求访问太过频繁") > 0 ){
//            result = "{\"StatusCode\":\"success\",\"Billid\":\"000000000\",\"Transbillid\":\"000000000\",\"TrackStatusCn\":\"\",\"TrackStatusEn\":\"\",\"CountryCode\":\"美国\",\"countryNameCn\":\"\",\"countryNameEn\":\"\",\"Datas\":[]}";
//        }
//        JSONObject jsStr = JSONObject.parseObject(result);
//        System.out.println("德威轨迹1：" + jsStr);
//        JSONArray a = jsStr.getJSONArray("Datas");
//        JSONObject returnDatas = new JSONObject();
//
//        if ( a == null || a.size() == 0 ) {
//            JSONObject object = getOrderData(number, req);
//            JSONArray ja = object.getJSONArray("returnDatas");
//            if (!"error".equals(ja.getJSONObject(0).get("statusCode"))) {
//                returnDatas.put("statusCode", "success");
//                returnDatas.put("trackNumber", number);
//                returnDatas.put("track", null);
//                returnDatas.put("items", ja.getJSONObject(0).getJSONArray("items"));
//                // 添加处理新加坡末端派送情况
//                if ( number.startsWith("DWG") ){
//                    JSONObject sign = (JSONObject)ja.getJSONObject(0).getJSONArray("items").get(0);
//                    // 是否最后一条是到达当地信息
//                    if ( StringUtils.isNotBlank(sign.get("info").toString()) && sign.get("info").toString().startsWith("到达当地")){
//                        params.clear();
//                        params.put("customTrackingCode",number);
//                        String zhongtong = JSON.toJSONString(params);
//                        String zhongtongResult = httpUtil.post("https://api.ztoasia.com/track-ztoasia-api/deliveries/track",zhongtong);
//                        JSONObject zhongtongObject = JSONObject.parseObject(zhongtongResult);
//                        if ( (boolean)zhongtongObject.get("success") ){
//                            JSONObject zhongtongData = zhongtongObject.getJSONObject("data");
//                            String status = zhongtongData.getString("current_status");
//                            Iterator<ZTOSTATUS> ztostatusIterator = Arrays.asList(ZTOSTATUS.values()).iterator();
//                            while ( ztostatusIterator.hasNext() ){
//                                ZTOSTATUS ztostatus = ztostatusIterator.next();
//                                if ( ztostatus.key.equals(status.toLowerCase()) ){
//                                    JSONArray newArray = new JSONArray();
//                                    JSONObject newObject = new JSONObject();
//                                    newObject.put("dateTime",DateUtil.timestamp2String(new Date()));
//                                    newObject.put("location","SG");
//                                    newObject.put("info",ztostatus.value);
//                                    newArray.add(newObject);
//                                    // 重新组装数组
//                                    newArray.add(ja.getJSONObject(0).getJSONArray("items"));
//                                    returnDatas.remove("items");
//                                    returnDatas.put("items",newArray);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//                returnData.add(returnDatas);
//                checkTrackOrAddData(number,ja.getJSONObject(0).getJSONArray("items"));
//
////                // 查询17网的轨迹信息并拼接
////                JSONObject tracking17Result = getTrack17Tracking(number, req);
////                if(tracking17Result != null && !tracking17Result.isEmpty()){
////                    JSONObject trackingResult = joiningTracking(tracking17Result, number);
////                    if(trackingResult != null){
////                        return tracking17Result;
////                    }
////                }
//
//                return returnDatas;
//            }
//
////            // 马来轨迹
////            JSONObject my = getMYOrderData(number, req);
////            JSONArray myArray = my.getJSONArray("returnDatas");
////            if (!"error".equals(myArray.getJSONObject(0).get("statusCode"))) {
////                returnDatas.put("statusCode", "success");
////                returnDatas.put("trackNumber", number);
////                returnDatas.put("track", null);
////                returnDatas.put("items", myArray.getJSONObject(0).getJSONArray("items"));
////                returnData.add(returnDatas);
////                checkTrackOrAddData(number,myArray.getJSONObject(0).getJSONArray("items"));
////                return returnDatas;
////            }
//
//            //  骏川国际轨迹-马来
//            JSONObject junChuanObj = getOrderDateByJunChuan(number);
//            JSONArray junchuanArray = junChuanObj.getJSONArray("data");
//
//            if (!"无效的单号".equals(junchuanArray.getJSONObject(0).get("errormsg"))) {
//                returnDatas.put("statusCode", "success");
//                returnDatas.put("trackNumber", number);
//                returnDatas.put("track", null);
//                returnDatas.put("items", junchuanArray.getJSONObject(0).getJSONArray("trackItems"));
//                returnData.add(returnDatas);
//                checkTrackOrAddData(number,junchuanArray.getJSONObject(0).getJSONArray("trackItems"));
//                return returnDatas;
//            }
//
//            JSONObject ymObj = getYAMOrderData(number,req);
//            JSONArray ymArray = ymObj.getJSONArray("data");
//
//            if (!"0".equals(ymObj.get("success").toString())) {
//                returnDatas.put("statusCode", "success");
//                returnDatas.put("trackNumber", number);
//                returnDatas.put("track", null);
//                JSONArray ymAdetails = ymArray.getJSONObject(0).getJSONArray("details");
//                JSONArray items = new JSONArray();
//                for ( int j = 0 ; j < ymAdetails.size(); j ++ ){
//                    JSONObject o = ymAdetails.getJSONObject(j);
//
//                    JSONObject itemObject = new JSONObject();
//                    itemObject.put("dateTime", o.get("track_occur_date"));
//                    itemObject.put("location", o.get("track_location"));
//                    itemObject.put("info", o.get("track_description"));
//
//                    items.add(itemObject);
//
//                    // 如果是ok，说明已经派送完成
//                    if ( StringUtils.isNotBlank( o.get("track_code").toString() ) && "OK".equals( o.get("track_code").toString() )){
//                        itemObject = new JSONObject();
//                        itemObject.put("dateTime", DateUtil.timestamp2String(new Date()));
//                        itemObject.put("location", "");
//                        itemObject.put("info", "您的订单已完成，感谢您的支持，有疑问请联系客服");
//
//                        items.add(itemObject);
//                    }
//                }
//                returnDatas.put("items",items);
//                returnData.add(returnDatas);
//                checkTrackOrAddData(number,items);
//
////                // 查询17网的轨迹信息并拼接
////                JSONObject tracking17Result = getTrack17Tracking(number, req);
////                if(tracking17Result != null && !tracking17Result.isEmpty()){
////                    JSONObject trackingResult = joiningTracking(tracking17Result, number);
////                    if(trackingResult != null){
////                        return tracking17Result;
////                    }
////                }
//
//                return returnDatas;
//            }
//
//        }else {
//            // 封装一个适配多单接口的查询
//            returnDatas.put("statusCode","success");
//            returnDatas.put("trackNumber",number);
//            returnDatas.put("track",null);
//
//            JSONArray items = new JSONArray();
//            for ( int j = 0 ; j < a.size(); j ++ ){
//                JSONObject o = a.getJSONObject(j);
//
//                JSONObject itemObject = new JSONObject();
//                itemObject.put("dateTime", o.get("Sdate"));
//                itemObject.put("location", o.get("Place"));
//                itemObject.put("info", o.get("Intro"));
//
//                items.add(itemObject);
//            }
//            returnDatas.put("items",items);
//            returnData.add(returnDatas);
//            checkTrackOrAddData(number,items);
//
////            // 查询17网的轨迹信息并拼接
////            JSONObject tracking17Result = getTrack17Tracking(number, req);
////            if(tracking17Result != null && !tracking17Result.isEmpty()){
////                JSONObject trackingResult = joiningTracking(tracking17Result, number);
////                if(trackingResult != null){
////                    return tracking17Result;
////                }
////            }
//
//            return returnDatas;
//        }
//
//        // 如果数组为空，则还没有该订单信息
//        returnDatas.put("statusCode","error");
//        returnDatas.put("trackNumber",number);
//        returnDatas.put("message","单号系统中不存在！");
//        returnData.add(returnDatas);
//
//        return returnDatas;
        return getOrderTrack(number, req);
    }

    @Override
    public JSONObject getTrack17Tracking(String number, HttpServletRequest req) {
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        JSONObject returnDatas = new JSONObject();
        // 查询17Track渠道网的轨迹数据
        // 只查询17网轨迹不做注册
        String requestData = "[{\"number\": \""+number+"\"}]";
        String trackInfoUrl = "https://api.17track.net/track/v2/gettrackinfo";
        String track17Result = httpUtil.postTrack17(trackInfoUrl, requestData, req, TRACK_17_TOKEN);
        JSONObject track17Obj = JSONObject.parseObject(track17Result);
        System.out.println(track17Obj);
        JSONObject track17Data = track17Obj.getJSONObject("data");
        JSONArray rejectedArray = track17Data.getJSONArray("rejected");
        if (track17Obj.getInteger("code") == 0 && rejectedArray.isEmpty()) {
            returnDatas.put("statusCode", "success");
            returnDatas.put("trackNumber", number);
            returnDatas.put("track", null);

            JSONArray acceptedList = track17Data.getJSONArray("accepted");
            JSONObject acceptedJson = acceptedList.getJSONObject(0);
            JSONObject trackInfoJson = acceptedJson.getJSONObject("track_info");

            // 运输明细
            JSONObject trackingJson = trackInfoJson.getJSONObject("tracking");
            List<ProvidersDo> providersList
                    = JSONObject.parseArray(trackingJson.getString("providers"), ProvidersDo.class);

            JSONArray items = new JSONArray();
            for (ProvidersDo providers : providersList) {
                for (ProviderEventDo event : providers.getEvents()) {
                    if(event.getDescription().toUpperCase().contains("CREATED A LABEL")){
                        continue;
                    }
                    String dateTime = event.getTime_utc().split("T")[0] +" "+ event.getTime_utc().split("T")[1].split("Z")[0];
                    JSONObject itemObject = new JSONObject();
                    itemObject.put("dateTime", dateTime);
                    itemObject.put("info", event.getDescription());
                    itemObject.put("location", event.getLocation());
                    items.add(itemObject);
                }
            }
//            List<Trajectory> trajectoryList = trajectoryMapper.getTrackListByOrderNumber(number, 1);
//            if(!CollectionUtils.isEmpty(trajectoryList)){
//                for (Trajectory trajectory : trajectoryList) {
//                    JSONObject itemObject = new JSONObject();
//                    itemObject.put("dateTime", trajectory.getTrackTime());
//                    itemObject.put("info", trajectory.getMessage());
//                    itemObject.put("location", trajectory.getLocation());
//                    items.add(itemObject);
//                }
//            }
            returnDatas.put("items",items);
            return returnDatas;
        }
        return null;
    }

    /**
     * 拼接物流信息
     * @param returnDatas
     * @param number
     * @return
     */
    @Override
    public JSONObject joiningTracking(JSONObject returnDatas, String number){
        if(!returnDatas.containsKey("items")){
            return null;
        }
        JSONArray items = returnDatas.getJSONArray("items");

        List<Trajectory> trajectoryList = trajectoryMapper.getTrackListByOrderNumber(number, 1);
        if(!CollectionUtils.isEmpty(trajectoryList)){
            for (Trajectory trajectory : trajectoryList) {
                JSONObject itemObject = new JSONObject();
                itemObject.put("dateTime", trajectory.getTrackTime());
                itemObject.put("info", trajectory.getMessage());
                itemObject.put("location", trajectory.getLocation());
                items.add(itemObject);
            }
        }
        returnDatas.put("items",items);
        return returnDatas;
    }

    @Override
    public ResponseEntity<ResultModel> updateOrderTrack(String orderNumber, HttpServletRequest request) {
        if("FLY".equals(SYSTEM_CODE)){
            return updateTrack(orderNumber, request);
        }else {
            log.info("------>>>>> 调用飞轮系统的轨迹更新接口 <<<<<------");
            HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
//            String requestUrl = "http://jiyun.flycloudstorage.com/api/AppDataService/otherSystemUpdateOrderTrack";
            String requestUrl = "http://www.lrqcloud.work/api/AppDataService/otherSystemUpdateOrderTrack";

            String post = httpUtilManager.post(requestUrl, orderNumber);
            System.out.println("轨迹更新回传参数：" + post);
            JSONObject jsonObject = JSON.parseObject(post);
            ResponseEntity<ResultModel> responseEntity;

            String code = jsonObject.getString("code");
            String content = jsonObject.getString("content");
            String message = jsonObject.getString("message");
            JSONObject contentJSON = JSONObject.parseObject(content);
            if (code.equals(jsonObject.getString("content"))) {
                responseEntity = new ResponseEntity<>(ResultModel.ok(contentJSON), HttpStatus.OK);
            } else {
                OTHER_SYSTEM_ERROR.setCode(Integer.parseInt(code));
                OTHER_SYSTEM_ERROR.setMessage(message);
                responseEntity = new ResponseEntity<>(ResultModel.error(OTHER_SYSTEM_ERROR, contentJSON), HttpStatus.OK);
            }
            log.info("------>>>>> 调用结束 <<<<<------");
            return responseEntity;
        }

    }

    @Override
    public ResponseEntity<ResultModel> updateTrack(String orderNumber, HttpServletRequest request) {
        CustomerPack customerPack = customerPackMapper.selectByOrderNumber(orderNumber);
        // 如果转单号不为空，则根据转单号更新轨迹信息
        if(!StringUtils.isEmpty(customerPack.getInternationalTransshipmentNo())){
            orderNumber = customerPack.getInternationalTransshipmentNo();
        }
        JSONObject returnDatas = getOrderTrack(orderNumber, request);
        if("error".equals(returnDatas.getString("statusCode"))){
            return new ResponseEntity<>(ResultModel.error(PACK_ORDER_TRACK_MESSAGE_NULL_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }


    @Override public TrajectoryLog getLogByOrderNumber(String orderNumber) {
        return trajectoryLogMapper.getSingleTrackLog(orderNumber);
    }

    @Override public int checkLogByOrdernumberIsIn(String orderNumber) {
        return trajectoryLogMapper.checkTrackLogIsIn(orderNumber);
    }

    @Override public int updateLogByOrderNumber(TrajectoryLog log) {
        return trajectoryLogMapper.updateByOrderNumber(log);
    }

    @Override public ResponseEntity<ResultModel> getTrajectoryLog(Map<String,Object> map) {
        PageHelp.initPage(map);
        PageData data = (PageData) map.get("page");
        if ( map.containsKey("isInside") ){
            map.put("isInside","".equals(map.get("isInside").toString()) ? 2 : 1);
        }
        int count = trajectoryLogMapper.countTrajectoryLogList(map);
        List<TrajectoryLog> list = new ArrayList<>();
        if ( count > 0 ){
            list = trajectoryLogMapper.getTrajectoryLogList(map);
        }
        data.setTotal(count);
        Map<String, Object> result = new HashMap<>();
        result.put("page", data);
        result.put("trajectoryLogList", list);
        return new ResponseEntity<>(PageResultModel.ok(result), HttpStatus.OK);
    }

    @Override public ResponseEntity<ResultModel> insertTrajectory(Trajectory trajectory) {
        int i = trajectoryLogMapper.checkTrackLogIsIn(trajectory.getOrderNumber());
        if ( i < 1 ){
            TrajectoryLog log = new TrajectoryLog();
            log.setCreateTime(DateUtil.timestamp2String(new Date()));
            log.setIsInside(1);
            log.setCheckTime(DateUtil.timestamp2String(new Date()));
            log.setOrderNumber(trajectory.getOrderNumber());
            log.setStatus(1);
            log.setCheckNum(1);
            trajectoryLogMapper.insert(log);
        }else {
            TrajectoryLog log = new TrajectoryLog();
            log.setOrderNumber(trajectory.getOrderNumber());
            log.setIsInside(1);
            trajectoryLogMapper.updateByOrderNumber(log);
        }
        trajectory.setStatus(1);
        trajectory.setCreateTime(new Date());
        trajectoryMapper.insert(trajectory);
        return new ResponseEntity<>(PageResultModel.ok(), HttpStatus.OK);
    }

    @Override public int checkTrackHaveDatas(String orderNumber) {
        return trajectoryMapper.checkTrackHaveDatas(orderNumber);
    }

    /**
     * 支持多单号的查询接口
     * @param orderNumber
     * @return
     */
    public JSONObject getOrderData(String orderNumber, HttpServletRequest req) {

        if ( orderNumber.startsWith("MY") ){
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
        String result = httpUtil.post("http://159.75.41.10:8888/PostInterfaceService?method=searchTrack",jsonString,req);
        System.out.println("德威轨迹2：" + result);
        if ( result.startsWith("<!") ){
            result = "{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\"1Z840YV520950053512\",\"message\":\"单号系统中不存在！\"}]}";
        }
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    /**
     * 获取马来西亚轨迹，骏川国际
     * @param orderNumber
     * @return
     */
    public JSONObject getOrderDateByJunChuan(String orderNumber){
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String result = null;
        try {
            result = httpUtil.requestHttpGet("http://193.112.147.251:8980/api/track?waybillnumber="+orderNumber);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if ( result.indexOf("未提交单号") > 0 ){
            result = "{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\"DWG81238809357\",\"message\":\"单号系统中不存在！\"}]}";
            return JSONObject.parseObject(result);
        }
        JSONObject jsStr = JSONObject.parseObject(result);
//        JSONArray a = jsStr.getJSONArray("data");
//        System.out.println(a);
//        JSONObject ab = a.getJSONObject(0);
//        System.out.println(ab);
//        JSONArray ac = ab.getJSONArray("trackItems");
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
        String result = httpUtil.post("http://gzdw.kingtrans.net/PostInterfaceService?method=searchTrack",jsonString,req);
        if ( result.indexOf("访问太过频繁") > 0 ){
            result = "{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\"DWG81238809357\",\"message\":\"单号系统中不存在！\"}]}";
        }else if(result.startsWith("<!")){
            result = "{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\""+orderNumber+"\",\"message\":\"单号系统中不存在！\"}]}";
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
     * 获取亚美轨迹,一次只支持单个单号查询
     * @param orderNumber
     * @return
     */
    public JSONObject getYAMOrderData(String orderNumber, HttpServletRequest req) {

        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        Map<String,Object> params = new HashMap<>(4);

        params.put("appToken","c613d7056dedab2575db83373e2e7a91");
        params.put("appKey","32289d42acbcc83cf0e4b3399b93abdb32289d42acbcc83cf0e4b3399b93abdb");
        params.put("serviceMethod","gettrack");

        String tracking_number = "{\"tracking_number\":\"" + orderNumber + "\"}";
        params.put("paramsJson",tracking_number);

        String jsonString = JSON.toJSONString(params);
        String urlString = "appToken=c613d7056dedab2575db83373e2e7a91"
            + "&appKey=32289d42acbcc83cf0e4b3399b93abdb32289d42acbcc83cf0e4b3399b93abdb"
            + "&serviceMethod=gettrack"
            + "&paramsJson=%7B%22tracking_number%22:%22" + orderNumber + "%22%7D";
        System.out.println(jsonString);
        String result = null;
        try {
            result =
                httpUtil.requestHttpGet("http://yamei.rtb56.com/webservice/PublicService.asmx/ServiceInterfaceUTF8?" + urlString);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("亚美轨迹: " + result);

//        if ( result.indexOf("success") > 0 ){
//            result = "{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\"DWG81238809357\",\"message\":\"单号系统中不存在！\"}]}";
//        }

        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    /**
     * 获取17TRACK的订单轨迹
     * @param orderNumber
     * @param req
     * @return
     */
    public JSONObject getTrack17OrderData(String orderNumber, HttpServletRequest req){
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        // 注册订单号
        String requestData = "[{\"number\": \""+orderNumber+"\"}]";
        String trackInfoUrl = "https://api.17track.net/track/v2/gettrackinfo";
        String result = httpUtil.postTrack17(trackInfoUrl, requestData, req, TRACK_17_TOKEN);
        JSONObject trackResultJson = JSONObject.parseObject(result);
        JSONArray rejectedArray = trackResultJson.getJSONObject("data").getJSONArray("rejected");
        if(!rejectedArray.isEmpty()){
            JSONObject rejected = (JSONObject) rejectedArray.get(0);
            JSONObject error = rejected.getJSONObject("error");
            // 订单号尚未注册，直接注册
            if("-18019902".equals(error.getString("code"))){
                String requestUrl = "https://api.17track.net/track/v2/register";
                String registerStr = httpUtil.postTrack17(requestUrl, requestData, req, TRACK_17_TOKEN);
                System.out.println("17轨迹注册：" + registerStr);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONObject registerResultJson = JSONObject.parseObject(registerStr);
                JSONArray registerRejectedArray = registerResultJson.getJSONObject("data").getJSONArray("rejected");
                // rejected 参数不为空时，说明注册失败
                if(!registerRejectedArray.isEmpty()){
                    trackResultJson = registerResultJson;
                }else{
                    result = httpUtil.postTrack17(trackInfoUrl, requestData, req, TRACK_17_TOKEN);
                    System.out.println("17轨迹注册完成，再次查询轨迹");
                    trackResultJson = JSONObject.parseObject(result);
                }
            }
        }

       return trackResultJson;
    }

    @Override
    public void updateTrack17Data(String body) {
        if(StringUtils.isEmpty(body)){
            System.out.println("推送数据错误");
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String event = jsonObject.getString("event");
        JSONObject dataJson = jsonObject.getJSONObject("data");
        String number = dataJson.getString("number");
        if("TRACKING_STOPPED".equals(event)){
            System.out.println("订单【"+number+"】：停止推送消息更新");
            return;
        }
        TrajectoryLog singleTrackLog = trajectoryLogMapper.getSingleTrackLog(number);
        if(singleTrackLog == null){
            System.out.println("暂无当前订单的轨迹记录");
            return;
        }
        JSONObject trackInfoJson = dataJson.getJSONObject("track_info");
        ProviderEventDo lastEvent = JSONObject.parseObject(trackInfoJson.getString("latest_event"), ProviderEventDo.class);
        sendMessage(singleTrackLog, lastEvent.getDescription(), lastEvent.getStage());

    }

    @Override
    public void batchInsertOrder(String orderData) {
        List<Map> orderDataList = JSONObject.parseArray(orderData, Map.class);
        if(orderDataList.isEmpty()){
            // 添加日志记录
            System.out.println("没有需要注册的转单号数据");
        }
        if(orderDataList.size() > 400){
            // 添加日志记录
            System.out.println("注册的转单号数量不能超过400个");
        }

        List<TrajectoryLog> trajectoryLogList = new ArrayList<>();
        
        // Set、List转化，去除相同的内容
        Set<Map> orderDataSet = new HashSet<>(orderDataList);
        orderDataList = new ArrayList<>(orderDataSet);

        for (Map map : orderDataList) {

            if(String.valueOf(map.get("transitNumber")).isEmpty()){
                // 日志记录未注册成功的订单
                continue;
            }

            TrajectoryLog trajectoryLog = new TrajectoryLog();

            String transitNumber = String.valueOf(map.get("transitNumber")).trim();
            String orderNumber = String.valueOf(map.get("orderNumber")).trim();
            String carrier = String.valueOf(map.get("carrier")).trim();
            String systemCode = String.valueOf(map.get("systemCode")).trim();

            trajectoryLog.setShipperCarrier(carrier);
            trajectoryLog.setOrderNumber(transitNumber);
            trajectoryLog.setSystemCode(systemCode);

            trajectoryLog.setCheckNum(0);
            trajectoryLog.setStatus(1);
            trajectoryLog.setVersion(1);
            trajectoryLog.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

            trajectoryLogList.add(trajectoryLog);
        }

        trajectoryLogMapper.batchInsert(trajectoryLogList);

    }

    @Override
    public ResponseEntity<ResultModel> registerTrack17OrderData(String orderData, HttpServletRequest req) {
        List<Map> orderDataList = JSONObject.parseArray(orderData, Map.class);
        if(orderDataList.isEmpty()){
           return new ResponseEntity<>(ResultModel.error(PACK_ORDER_NUMBER_CAN_NOT_NULL), HttpStatus.OK);
        }
        if(orderDataList.size() > 400){
            return new ResponseEntity<>(ResultModel.error(PACK_ORDER_NUMBER_MORE_THAN_ERROR), HttpStatus.OK);
        }
        // Set、List转化，去除相同的内容
        Set<Map> orderDataSet = new HashSet<>(orderDataList);
        orderDataList = new ArrayList<>(orderDataSet);
        List<String> acceptedOrderNumbers = new ArrayList<>();
        List<String> rejectedOrderNumbers = new ArrayList<>();

        double registerNum = 40.00;
        int count = 1;
        int number = new BigDecimal(orderDataList.size() / registerNum).setScale(0, BigDecimal.ROUND_UP).intValue();
        int index = 0;
        int indexEnd;
        while (count <= number){
            indexEnd = index+(int)registerNum > orderDataList.size() ? orderDataList.size() : index+(int)registerNum;

            List<Map> phaseOrderList = orderDataList.subList(index, indexEnd);

            String numbers = "";
            for (Map map : phaseOrderList) {
                if(String.valueOf(map.get("transitNumber")).isEmpty()){
                    return new ResponseEntity<>(ResultModel.error(PACK_ORDER_NUMBER_CAN_NOT_NULL), HttpStatus.OK);
                }

                String transitNumber = String.valueOf(map.get("transitNumber")).trim();
                String carrier = String.valueOf(map.get("carrier")).trim();
                String orderNumber;
                // 如果运输商编码不为空，则插入请求条件
                if(carrier.isEmpty()){
                    orderNumber = "{\"number\": \""+transitNumber+"\"}";
                }else {
                    orderNumber = "{\"number\": \""+transitNumber+"\", \"carrier\": \""+ carrier +"\" }";
                }

                numbers = StringUtils.isEmpty(numbers) ? orderNumber : numbers + "," + orderNumber;

            }

            String requestData = "[" + numbers + "]";
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String requestUrl = "https://api.17track.net/track/v2/register";
            String registerStr = httpUtil.postTrack17(requestUrl, requestData, req, TRACK_17_TOKEN);
            System.out.println("17轨迹注册：" + registerStr);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            JSONObject registerResultJson = JSONObject.parseObject(registerStr);
            if(registerResultJson.getInteger("code") != 0){
                return new ResponseEntity<>(ResultModel.ok(registerResultJson), HttpStatus.OK);
            }
            JSONArray acceptedArray = registerResultJson.getJSONObject("data").getJSONArray("accepted");
            JSONArray rejectedArray = registerResultJson.getJSONObject("data").getJSONArray("rejected");

            if(!CollectionUtils.isEmpty(acceptedArray)){
                for (Object accepted : acceptedArray) {
                    JSONObject acceptedJson = JSONObject.parseObject(accepted.toString());
                    acceptedOrderNumbers.add(acceptedJson.getString("number"));
                }
            }
            if (!CollectionUtils.isEmpty(rejectedArray)){
                for (Object accepted : rejectedArray) {
                    JSONObject acceptedJson = JSONObject.parseObject(accepted.toString());
                    rejectedOrderNumbers.add(acceptedJson.getString("number"));
                }
            }

            index = index + (int)registerNum;
            count ++;
        }

        // 需要更新的订单
        List<TrajectoryLog> updateTrajectoryLogList = new ArrayList<>();

        // 如果注册成功的订单号不为空
        if(!CollectionUtils.isEmpty(acceptedOrderNumbers)){
            // 查询已经存在数据库中的转单号
            List<TrajectoryLog> trajectoryLogs =  trajectoryLogMapper.getTrajectoryLogListByOrderNumber(acceptedOrderNumbers);
            List<String> existingOrderNumber = trajectoryLogs.stream().map(TrajectoryLog::getOrderNumber).collect(Collectors.toList());

            List<TrajectoryLog> trajectoryLogList = new ArrayList<>();
            for (Map map : orderDataList) {
                if(acceptedOrderNumbers.contains(String.valueOf(map.get("transitNumber")))
                        && !existingOrderNumber.contains(String.valueOf(map.get("transitNumber")))){
                    TrajectoryLog trajectoryLog = new TrajectoryLog();
                    trajectoryLog.setStatus(1);
                    trajectoryLog.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                    trajectoryLog.setCheckTime(null);
                    trajectoryLog.setVersion(1);
                    trajectoryLog.setIsInside(0);
                    trajectoryLog.setCheckNum(0);
                    trajectoryLog.setOrderNumber(String.valueOf(map.get("transitNumber")));
                    trajectoryLog.setSystemCode(String.valueOf(map.get("systemCode")));
                    trajectoryLog.setShipperCarrier(String.valueOf(map.get("carrier")));
                    trajectoryLogList.add(trajectoryLog);
                }else{
                    TrajectoryLog trajectoryLog = new TrajectoryLog();
                    trajectoryLog.setOrderNumber(String.valueOf(map.get("transitNumber")));
                    trajectoryLog.setSystemCode(String.valueOf(map.get("systemCode")));
                    trajectoryLog.setShipperCarrier(String.valueOf(map.get("carrier")));
                    updateTrajectoryLogList.add(trajectoryLog);
                }
            }
            // 插入转单号数据记录
            if(!trajectoryLogList.isEmpty()){
                trajectoryLogMapper.batchInsert(trajectoryLogList);
            }
        } else {
            for (Map map : orderDataList) {
                TrajectoryLog trajectoryLog = new TrajectoryLog();
                trajectoryLog.setSystemCode(String.valueOf(map.get("systemCode")));
                trajectoryLog.setOrderNumber(String.valueOf(map.get("transitNumber")));
                updateTrajectoryLogList.add(trajectoryLog);
            }
        }
        // 更新转单号数据记录（绑定所属系统信息）
        if(!CollectionUtils.isEmpty(updateTrajectoryLogList)){
            trajectoryLogMapper.batchUpdateByOrderNumber(updateTrajectoryLogList);
        }
        String content = "success";
        if(!rejectedOrderNumbers.isEmpty()){
            content = "";
            for (String order :rejectedOrderNumbers){
                content = content.isEmpty() ? order : content + ", " + order;
            }
        }
        return new ResponseEntity<>(ResultModel.ok(content), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getTrack17List(Track17ReqVo track17ReqVo, HttpServletRequest request) {
        track17ReqVo.setNumber(track17ReqVo.getNumber().trim());
        PageData pageData = new PageData();
        String requestData = JSONObject.toJSONString(track17ReqVo);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String requestUrl = "https://api.17track.net/track/v2/gettracklist";
        String responseStr = httpUtil.postTrack17(requestUrl, requestData, request, TRACK_17_TOKEN);
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        if(jsonObject.getInteger("code") == 0){
            JSONObject pageJsonObject = jsonObject.getJSONObject("page");
            if(pageJsonObject.getInteger("data_total") > 0){
                pageData.setTotal(pageJsonObject.getInteger("data_total"));
                pageData.setNumber(pageJsonObject.getInteger("page_no"));
                pageData.setPageNumber(pageJsonObject.getInteger("page_total"));
                pageData.setPageSize(pageJsonObject.getInteger("page_size"));
                JSONArray trackListArray = jsonObject.getJSONObject("data").getJSONArray("accepted");
                List<Track17RespVo> track17RespVoList = new ArrayList<>();
                for (Object trackObject : trackListArray) {
                    Track17RespVo track17RespVo = new Track17RespVo();
                    JSONObject trackJsonObject = JSONObject.parseObject(trackObject.toString());
                    track17RespVo.setOrderNumber(trackJsonObject.getString("number"));
                    track17RespVo.setShippingCountry(trackJsonObject.getString("shipping_country"));
                    track17RespVo.setRecipientCountry(trackJsonObject.getString("recipient_country"));
                    track17RespVo.setRegisterTime(trackJsonObject.getString("register_time"));
                    track17RespVo.setPushTime(trackJsonObject.getString("push_time"));
                    track17RespVo.setTrackingStatus(trackJsonObject.getString("tracking_status"));
                    track17RespVo.setPackageStatus(trackJsonObject.getString("package_status"));
                    track17RespVoList.add(track17RespVo);
                }
                List<String> orderNumberList = track17RespVoList.stream()
                        .map(Track17RespVo::getOrderNumber).collect(Collectors.toList());
                List<TrajectoryLog> trajectoryLogList = trajectoryLogMapper.selectLogListByOrderNumber(orderNumberList);
                for (Track17RespVo track17RespVo : track17RespVoList) {
                    for (TrajectoryLog trajectoryLog : trajectoryLogList) {
                        if(track17RespVo.getOrderNumber().equals(trajectoryLog.getOrderNumber())){
                            track17RespVo.setSystemCode(trajectoryLog.getSystemCode());
                        }
                    }
                }
                return new ResponseEntity<>(PageResultModel.ok(track17RespVoList, pageData), HttpStatus.OK);
            }
            pageData.setTotal(0);
            return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
        }else {
            String content = "17轨迹网请求错误：" + jsonObject.toJSONString();
            return new ResponseEntity<>(PageResultModel.error(-500, content), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<ResultModel> getTrack17OrderTracking(String number, HttpServletRequest req) {
        // 检查是否今天第一次查询
        int isFirst = checkOrderNumberIsFirst(number);
        if(isFirst < 1){
            this.saveTrack(number, req);
        }
        // 查询17Track渠道网的轨迹数据
        JSONObject track17Obj = getTrack17OrderData(number, req);
        JSONObject track17Data = track17Obj.getJSONObject("data");
        JSONArray rejectedArray = track17Data.getJSONArray("rejected");
        if(rejectedArray.isEmpty()){
            JSONObject accepted = JSONObject.parseObject(track17Data.getJSONArray("accepted").get(0).toString());
            JSONObject trackingJsonObject = accepted.getJSONObject("track_info").getJSONObject("tracking");
            System.out.println(trackingJsonObject);
            List<ProvidersDo> providersList = JSONObject.parseArray(trackingJsonObject.getString("providers"), ProvidersDo.class);
            for (ProvidersDo providers : providersList) {
                for (ProviderEventDo event : providers.getEvents()) {
                    String dateTime = event.getTime_utc().split("T")[0] + " "
                            + event.getTime_utc().split("T")[1].split("Z")[0];
                    event.setTime_utc(dateTime);
                }
            }
            // 查询内部轨迹或者已有的轨迹
            List<Trajectory> trackListByOrderNumber = trajectoryMapper.getTrackListByOrderNumber(number, 1);
            if(!trackListByOrderNumber.isEmpty()){
                ProvidersDo internalTrack = new ProvidersDo();
                ProviderDo providerDo = new ProviderDo();
                providerDo.setCountry("中国");
                providerDo.setAlias("——");
                internalTrack.setProvider(providerDo);
                List<ProviderEventDo> eventDoList = new ArrayList<>();
                for (Trajectory trajectory : trackListByOrderNumber) {
                    ProviderEventDo eventDo = new ProviderEventDo();
                    eventDo.setTime_utc(trajectory.getTrackTime());
                    eventDo.setLocation(trajectory.getLocation());
                    eventDo.setDescription(trajectory.getMessage());
                    eventDoList.add(eventDo);
                }
                internalTrack.setEvents(eventDoList);
                providersList.add(internalTrack);
            }

            return new ResponseEntity<>(ResultModel.ok(providersList), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getTrackingUpdate(Map<String, Object> requestMap) {
        String pageNumber = String.valueOf(requestMap.get("pageNumber"));
        String pageSize = String.valueOf(requestMap.get("pageSize"));
        PageData pageData = PageHelp.editPage(pageNumber, pageSize);
        int count = wechatMessageMapper.selectTrackingUpdateCount(requestMap);
        pageData.setTotal(count);
        List<WechatMessage> wechatMessageList = new ArrayList<>();
        if(count > 0){
            requestMap.put("pageNumber", pageData.getPageNumber());
            requestMap.put("pageSize", pageData.getPageSize());
            wechatMessageList = wechatMessageMapper.selectTrackingUpdate(requestMap);
        }
        return new ResponseEntity<>(PageResultModel.ok(wechatMessageList, pageData), HttpStatus.OK);
    }


    private void sendMessage(TrajectoryLog singleTrackLog, String trackingMessage, String stage) {
        if(StringUtils.isEmpty(singleTrackLog.getSystemCode())){
            return;
        }
        System.out.println("17轨迹网推送, 开始执行微信公众号消息通知");
        String systemCode = singleTrackLog.getSystemCode().trim().toUpperCase();
        switch (systemCode){
            case "FLY":
                sendMessageServer.sendMessageForTrackUpdate(singleTrackLog, trackingMessage, stage);
//                otherSystemInform("http://106.53.93.90",singleTrackLog, trackingMessage);
                break;
            case "FEILUN":
                sendMessageServer.sendMessageForTrackUpdate(singleTrackLog, trackingMessage, stage);
//                otherSystemInform("http://106.53.93.90",singleTrackLog, trackingMessage);
                break;
            case "HUAWEI":
                System.out.println("华威客户通知");
                otherSystemInform("http://jiyun.huawei138.cn", singleTrackLog, trackingMessage, stage);
                break;
            case "TAOMMAO":
                System.out.println("淘猫客户通知");
                otherSystemInform("http://gzdw.gdjiyun.com", singleTrackLog, trackingMessage, stage);
                break;
            case "HUAYOU":
                System.out.println("华邮客户通知");
                otherSystemInform("http://huayou.flycloudstorage.com", singleTrackLog, trackingMessage, stage);
                break;
            case "HUASU":
                System.out.println("华速客户通知");
                otherSystemInform("http://huasu.huawei138.cn", singleTrackLog, trackingMessage, stage);
                break;
            default:
                System.out.println("未匹配到相关系统");
                break;
        }
    }

    private void otherSystemInform(String requestUrl, TrajectoryLog singleTrackLog, String trackingMessage, String orderStatus) {
        requestUrl = requestUrl + TRACKING_UPDATE_URL;
        HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
        Map<String,Object> params = new HashMap<>(2);
        params.put("trajectoryLog",singleTrackLog);
        params.put("trackingMessage",trackingMessage);
        params.put("orderStatus",orderStatus);
        String jsonString = JSONObject.toJSONString(params);

        httpUtilManager.post(requestUrl, jsonString);
    }

    public void checkTrackOrAddData(String orderNumber,JSONArray itemsArray){
        List<Trajectory> trackList = trajectoryMapper.getTrackListByOrderNumber(orderNumber,1);
        // 如果该轨迹已经存在内部系统，则对时间进行比较插入
        if ( trackList != null && trackList.size() > 0 ){
            String newestTime = trackList.get(0).getTrackTime();
            for ( int i = 0 ; i < itemsArray.size(); i ++ ){
                if ( compareTimeByString(itemsArray.getJSONObject(i).get("dateTime") == null ? itemsArray.getJSONObject(i).get("trackdate").toString() : itemsArray.getJSONObject(i).get("dateTime").toString(),newestTime) ){
                    Trajectory trajectory = new Trajectory();
                    trajectory.setCreateTime(new Date());
                    trajectory.setLocation(String.valueOf(itemsArray.getJSONObject(i).get("location")));
                    trajectory.setMessage(itemsArray.getJSONObject(i).get("info").toString());
                    trajectory.setStatus(1);
                    trajectory.setTrackTime(itemsArray.getJSONObject(i).get("dateTime") == null ? itemsArray.getJSONObject(i).get("trackdate").toString() : itemsArray.getJSONObject(i).get("dateTime").toString());
                    trajectory.setOrderNumber(orderNumber);
                    trajectoryMapper.insert(trajectory);
                }
            }
        }else{
            // 如果该轨迹数据还没新增过内部系统，则对该轨迹进行批量插入
            List<Trajectory> list = new ArrayList<>();
            for ( int i = 0 ; i < itemsArray.size(); i ++ ){
                Trajectory trajectory = new Trajectory();
                trajectory.setCreateTime(new Date());
                trajectory.setLocation(String.valueOf(itemsArray.getJSONObject(i).get("location")));
                trajectory.setMessage(String.valueOf(itemsArray.getJSONObject(i).get("info")));
                trajectory.setStatus(1);
                trajectory.setTrackTime(itemsArray.getJSONObject(i).get("dateTime") == null ? itemsArray.getJSONObject(i).get("trackdate").toString() : itemsArray.getJSONObject(i).get("dateTime").toString());
                trajectory.setOrderNumber(orderNumber);
                list.add(trajectory);
            }
            if ( list.size() > 0 ){
                trajectoryMapper.batchInsert(list);
            }
        }
    }


    /**
     * 时间比较方法
     * @param firstTime  第一个时间
     * @param secTime  第二个时间
     * @return 如果第一个时间大于第二个时间，返回true，相反则false
     */
    public static boolean compareTimeByString(String firstTime,String secTime){
        if ( firstTime.compareTo(secTime) <= 0 ){
            return false;
        }
        return true;
    }

    /**
     * 分离轨迹信息，拼装到jsonobject
     * @param trajectoryList
     * @return
     */
    public static JSONObject trackDatas(List<Trajectory> trajectoryList,String number){
        JSONObject returnDatas = new JSONObject();
        if ( trajectoryList != null && trajectoryList.size() > 0 ){
            returnDatas.put("statusCode", "success");
            returnDatas.put("trackNumber", number);
            returnDatas.put("track", null);
            JSONArray array = new JSONArray();
            for ( Trajectory trajectory : trajectoryList ){
                JSONObject item = new JSONObject();
                item.put("dateTime",trajectory.getTrackTime());
                item.put("location",trajectory.getLocation());
                item.put("info",trajectory.getMessage());
                array.add(item);
            }
            returnDatas.put("items", array);
        }else {
            returnDatas.put("statusCode","error");
            returnDatas.put("trackNumber",number);
            returnDatas.put("message","单号系统中不存在！");
        }
        return returnDatas;
    }


    private JSONObject getOrderTrack(String number, HttpServletRequest req){
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();

        Map<String,Object> params = new HashMap<>(2);
        JSONArray returnData = new JSONArray();
        params.clear();
        params.put("TrackingNumber",number);
        params.put("Language","cn");
        String jsonString = JSON.toJSONString(params);
        String result = httpUtil.post("http://dw.kingtrans.cn/AppDataService?method=searchTrackByBillid",jsonString,req);

        if ( result.indexOf("此请求访问太过频繁") > 0 ){
            result = "{\"StatusCode\":\"success\",\"Billid\":\"000000000\",\"Transbillid\":\"000000000\",\"TrackStatusCn\":\"\",\"TrackStatusEn\":\"\",\"CountryCode\":\"美国\",\"countryNameCn\":\"\",\"countryNameEn\":\"\",\"Datas\":[]}";
        }
        JSONObject jsStr = JSONObject.parseObject(result);
        System.out.println("德威轨迹1：" + jsStr);
        JSONArray a = jsStr.getJSONArray("Datas");
        JSONObject returnDatas = new JSONObject();

        if ( a == null || a.size() == 0 ) {
            JSONObject object = getOrderData(number, req);
            JSONArray ja = object.getJSONArray("returnDatas");
            if (!"error".equals(ja.getJSONObject(0).get("statusCode"))) {
                returnDatas.put("statusCode", "success");
                returnDatas.put("trackNumber", number);
                returnDatas.put("track", null);
                returnDatas.put("items", ja.getJSONObject(0).getJSONArray("items"));
                // 添加处理新加坡末端派送情况
                if ( number.startsWith("DWG") ){
                    JSONObject sign = (JSONObject)ja.getJSONObject(0).getJSONArray("items").get(0);
                    // 是否最后一条是到达当地信息
                    if ( StringUtils.isNotBlank(sign.get("info").toString()) && sign.get("info").toString().startsWith("到达当地")){
                        params.clear();
                        params.put("customTrackingCode",number);
                        String zhongtong = JSON.toJSONString(params);
                        String zhongtongResult = httpUtil.post("https://api.ztoasia.com/track-ztoasia-api/deliveries/track",zhongtong);
                        JSONObject zhongtongObject = JSONObject.parseObject(zhongtongResult);
                        if ( (boolean)zhongtongObject.get("success") ){
                            JSONObject zhongtongData = zhongtongObject.getJSONObject("data");
                            String status = zhongtongData.getString("current_status");
                            Iterator<ZTOSTATUS> ztostatusIterator = Arrays.asList(ZTOSTATUS.values()).iterator();
                            while ( ztostatusIterator.hasNext() ){
                                ZTOSTATUS ztostatus = ztostatusIterator.next();
                                if ( ztostatus.key.equals(status.toLowerCase()) ){
                                    JSONArray newArray = new JSONArray();
                                    JSONObject newObject = new JSONObject();
                                    newObject.put("dateTime",DateUtil.timestamp2String(new Date()));
                                    newObject.put("location","SG");
                                    newObject.put("info",ztostatus.value);
                                    newArray.add(newObject);
                                    // 重新组装数组
                                    newArray.add(ja.getJSONObject(0).getJSONArray("items"));
                                    returnDatas.remove("items");
                                    returnDatas.put("items",newArray);
                                    break;
                                }
                            }
                        }
                    }
                }
                returnData.add(returnDatas);
                checkTrackOrAddData(number,ja.getJSONObject(0).getJSONArray("items"));

                return returnDatas;
            }

//            // 马来轨迹
//            JSONObject my = getMYOrderData(number, req);
//            JSONArray myArray = my.getJSONArray("returnDatas");
//            if (!"error".equals(myArray.getJSONObject(0).get("statusCode"))) {
//                returnDatas.put("statusCode", "success");
//                returnDatas.put("trackNumber", number);
//                returnDatas.put("track", null);
//                returnDatas.put("items", myArray.getJSONObject(0).getJSONArray("items"));
//                returnData.add(returnDatas);
//                checkTrackOrAddData(number,myArray.getJSONObject(0).getJSONArray("items"));
//                return returnDatas;
//            }

            //  骏川国际轨迹-马来
            JSONObject junChuanObj = getOrderDateByJunChuan(number);
            JSONArray junchuanArray = junChuanObj.getJSONArray("data");

            if (!"无效的单号".equals(junchuanArray.getJSONObject(0).get("errormsg"))) {
                returnDatas.put("statusCode", "success");
                returnDatas.put("trackNumber", number);
                returnDatas.put("track", null);
                returnDatas.put("items", junchuanArray.getJSONObject(0).getJSONArray("trackItems"));
                returnData.add(returnDatas);
                checkTrackOrAddData(number,junchuanArray.getJSONObject(0).getJSONArray("trackItems"));
                return returnDatas;
            }

            JSONObject ymObj = getYAMOrderData(number,req);
            JSONArray ymArray = ymObj.getJSONArray("data");

            if (!"0".equals(ymObj.get("success").toString())) {
                returnDatas.put("statusCode", "success");
                returnDatas.put("trackNumber", number);
                returnDatas.put("track", null);
                JSONArray ymAdetails = ymArray.getJSONObject(0).getJSONArray("details");
                JSONArray items = new JSONArray();
                for ( int j = 0 ; j < ymAdetails.size(); j ++ ){
                    JSONObject o = ymAdetails.getJSONObject(j);

                    JSONObject itemObject = new JSONObject();
                    itemObject.put("dateTime", o.get("track_occur_date"));
                    itemObject.put("location", o.get("track_location"));
                    itemObject.put("info", o.get("track_description"));

                    items.add(itemObject);

                    // 如果是ok，说明已经派送完成
                    if ( StringUtils.isNotBlank( o.get("track_code").toString() ) && "OK".equals( o.get("track_code").toString() )){
                        itemObject = new JSONObject();
                        itemObject.put("dateTime", DateUtil.timestamp2String(new Date()));
                        itemObject.put("location", "");
                        itemObject.put("info", "您的订单已完成，感谢您的支持，有疑问请联系客服");

                        items.add(itemObject);
                    }
                }
                returnDatas.put("items",items);
                returnData.add(returnDatas);
                checkTrackOrAddData(number,items);

                return returnDatas;
            }

        }else {
            // 封装一个适配多单接口的查询
            returnDatas.put("statusCode","success");
            returnDatas.put("trackNumber",number);
            returnDatas.put("track",null);

            JSONArray items = new JSONArray();
            for ( int j = 0 ; j < a.size(); j ++ ){
                JSONObject o = a.getJSONObject(j);

                JSONObject itemObject = new JSONObject();
                itemObject.put("dateTime", o.get("Sdate"));
                itemObject.put("location", o.get("Place"));
                itemObject.put("info", o.get("Intro"));

                items.add(itemObject);
            }
            returnDatas.put("items",items);
            returnData.add(returnDatas);
            checkTrackOrAddData(number,items);

            return returnDatas;
        }

        // 如果数组为空，则还没有该订单信息
        returnDatas.put("statusCode","error");
        returnDatas.put("trackNumber",number);
        returnDatas.put("message","单号系统中不存在！");
        returnData.add(returnDatas);

        return returnDatas;
    }

    enum ZTOSTATUS{
        NEW("new","快递标签已创建，快递员尚未上门提前包裹"),
        CONSOLE_INBOUND("console-inbound", "在中转仓收到包裹，正在等待分拣"),
        PICKUP("pickup", "包裹已被快递员提取，等待派送"),
        ONDELIVERY("on-delivery","包裹正在派送，请您注意当面签收，以免联系不上出现包裹丢失或退回"),
        ATTEMPT_FAIL("attempt-fail","司机试图送货，交付未完成，原因可能：联系不上/收件方不在家/地址不完整，请及时联系客服处理"),
        WAREHOUSE("warehouse","包裹已退回海外仓，尝试派送不成功"),
        FAILED("failed","第三次尝试派送失败，投递无法完成"),
        RETURNED("returned","派送失败，返回发件方"),
        DESTROY("destroy","派送失败，包裹破损"),
        LOST("lost","派送失败，包裹丢失"),
        DEMAGED("damaged","派送失败，包裹销毁"),
        DELIVERED("delivered", "包裹已签收，订单完成，感谢您的支持"),
        ;
        ZTOSTATUS(String key, String value){
            this.key = key;
            this.value = value;
        }

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }



}
