package com.example.warehouse.service.sys;

import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.entity.sys.Trajectory;
import com.example.warehouse.entity.sys.TrajectoryLog;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.appDataService.Track17ReqVo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface TrajectoryService {
    int batchInsertToLog(List<TrajectoryLog> logs);

    int batchInsertToTrack(List<Trajectory> trajectoryList);

    int checkOrderNumberIsFirst(String orderNumber);

    int checkOrderNumberToday(String orderNumber);

    List<Trajectory> getTrackListByOrderNumber(String orderNumbers,int desc);

    int saveTrackLog(String number);

    JSONObject saveTrack(String number, HttpServletRequest req);

    TrajectoryLog getLogByOrderNumber(String orderNumber);

    int checkLogByOrdernumberIsIn(String orderNumber);

    int updateLogByOrderNumber(TrajectoryLog log);

    /**
     * 获取所有轨迹日志列表
     * @return
     */
    ResponseEntity<ResultModel> getTrajectoryLog(Map<String,Object> map);

    /**
     * 新增轨迹信息
     * @param trajectory
     * @return
     */
    ResponseEntity<ResultModel> insertTrajectory(Trajectory trajectory);

    /**
     * 检查是否有轨迹详情在内部系统
     * @param orderNumber
     * @return
     */
    int checkTrackHaveDatas(String orderNumber);

    JSONObject getTrack17OrderData(String orderNumber, HttpServletRequest req);
	/**
	* 17轨迹网订单轨迹更新
	*/
    void updateTrack17Data(String body);

    void batchInsertOrder(String orderData);

    ResponseEntity<ResultModel> registerTrack17OrderData(String orderData, HttpServletRequest req);

    ResponseEntity<PageResultModel> getTrack17List(Track17ReqVo track17ReqVo, HttpServletRequest request);

    ResponseEntity<ResultModel> getTrack17OrderTracking(String orderNumber, HttpServletRequest req);

    ResponseEntity<PageResultModel> getTrackingUpdate(Map<String, Object> requestMap);


    JSONObject getTrack17Tracking(String number, HttpServletRequest req);

    JSONObject joiningTracking(JSONObject returnDatas, String number);

    /**
     * 手动更新订单轨迹信息
     * @param orderNumber
     * @return
     */
    ResponseEntity<ResultModel> updateOrderTrack(String orderNumber, HttpServletRequest request);

    ResponseEntity<ResultModel> updateTrack(String orderNumber, HttpServletRequest request);

}
