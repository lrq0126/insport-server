package com.dwgj.mlxydedicatedline.service.sys;

import com.dwgj.mlxydedicatedline.entity.sys.SysNotice;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SysNoticeService {
    /**
     * 获取某个客户的消息列表
     * @param param
     * @return
     */
    List<SysNotice> selectNoticeList(Map<String,Object> param);

    int countSelectNoticeList(Map<String,Object> param);

    int updateNoticeRead(Map<String,Object> param);

    ResponseEntity<ResultModel> getNotReadMessage();

    ResponseEntity<ResultModel> updateRead(String noticeIds);
}
