package com.example.warehouse.controller.common;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.sys.Trajectory;
import com.example.warehouse.entity.sys.TrajectoryLog;
import com.example.warehouse.service.sys.TrajectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class TrajectoryExcelTemplate extends AbstractExcelTemplate {

    private static final String[] TITLES = {"转单号","轨迹内容","轨迹站点","轨迹时间","所属系统"};

    public TrajectoryExcelTemplate() {
        super(TITLES);
    }

    static TrajectoryExcelTemplate trajectoryExcelTemplate;

    @Autowired
    private TrajectoryService trajectoryService;


    @PostConstruct
    public void init(){
        trajectoryExcelTemplate = this;
        trajectoryExcelTemplate.trajectoryService = this.trajectoryService;
    }


    @Override
    ExcelCheckMessage doCheck(ExcelCheckMessage excelCheckMessage) {
        List<Trajectory> trajectoryList = new ArrayList<>();
        List<TrajectoryLog> trajectoryLogs = new ArrayList<>();

        Trajectory trajectory;
        List<String> messages = excelCheckMessage.getMessage();
        String[][] excel = excelCheckMessage.getExcel();

        Map<String,String> orderNumberMaps = new HashMap<>();
        Map<String,String> systemMap = new HashMap<>();

        int rowNum;


        if ( excel.length > 402 ){
            messages.add("一次导入不能超过400条");
            return excelCheckMessage;
        }
        for (int i = 1; i < excel.length; i++) {
            rowNum = i + 1;
            trajectory = new Trajectory();

            // 转单号
            if( StringUtils.hasText(excel[i][0])){
                trajectory.setOrderNumber(excel[i][0].trim());
                orderNumberMaps.put(excel[i][0].trim(),excel[i][0].trim());
                if(StringUtils.hasText(excel[i][4])){
                    systemMap.put("SYS"+excel[i][0].trim(), excel[i][4].trim().toUpperCase());
                }
            }else {
                messages.add(emptyMessage(rowNum, TITLES[0]));
            }

            // 轨迹内容
            if( StringUtils.hasText(excel[i][1])){
                trajectory.setMessage(excel[i][1].trim());
            }else {
                messages.add(emptyMessage(rowNum, TITLES[1]));
            }

            // 轨迹站点
            if( StringUtils.hasText(excel[i][2])){
                trajectory.setLocation(excel[i][2].trim());
            }

            // 轨迹时间
            if( StringUtils.hasText(excel[i][3])){
                trajectory.setTrackTime(excel[i][3].trim());
            }else {
                messages.add(emptyMessage(rowNum, TITLES[3]));
            }

            trajectory.setStatus(1);
            trajectory.setCreateTime(new Date());

            trajectoryList.add(trajectory);

        }

        if ( !CollectionUtils.isEmpty(orderNumberMaps) ){
            Iterator iter = orderNumberMaps.keySet().iterator();
            while (iter.hasNext()) {
                Object key = iter.next();
                TrajectoryLog log = new TrajectoryLog();
                log.setOrderNumber(key.toString());
                log.setSystemCode(systemMap.get("SYS"+key.toString()));
                log.setCheckNum(1);  // 只要是导入的，每次都会更新
                log.setStatus(1);
                log.setIsInside(1);
                log.setCheckTime(DateUtil.timestamp2String(new Date()));
                log.setCreateTime(DateUtil.timestamp2String(new Date()));

                int i = trajectoryExcelTemplate.trajectoryService.checkLogByOrdernumberIsIn(log.getOrderNumber());

                if ( i > 0 ){
                    // 如果存在轨迹日志，则不需要进行改变
//                    trajectoryService.updateLogByOrderNumber(log);
                    continue;
                }
                // 首次导入的，对轨迹日志新增一条数据，用于后期查询是否是内部数据用
                trajectoryLogs.add(log);
            }
            if ( trajectoryLogs.size() > 0 ) {
                // 批量导入轨迹日志
                trajectoryExcelTemplate.trajectoryService.batchInsertToLog(trajectoryLogs);
            }
        }
        excelCheckMessage.setObjects(trajectoryList);
        return excelCheckMessage;
    }

}
