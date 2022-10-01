package com.example.warehouse.service.webLog;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.NetWorkUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.webLog.WebClickLog;
import com.example.warehouse.mapper.webLog.WebClickLogMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.vo.webLog.WebClickReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WebClickLogServiceImpl implements WebClickLogService {
    @Autowired
    private WebClickLogMapper webClickLogMapper;
    @Override
    public void create(WebClickLog webClickLog, HttpServletRequest httpServletRequest) throws IOException {
            String host = httpServletRequest.getRemoteHost();

            webClickLog.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

            webClickLog.setIpLocation(NetWorkUtil.getIpAddress(httpServletRequest));
            webClickLogMapper.insertSelective(webClickLog);
    }

    @Override
    public ResponseEntity<PageResultModel> get(WebClickReqVo webClickReqVo) {
        PageData pageData = PageHelp.editPage(webClickReqVo);
        int count = webClickLogMapper.getCount(webClickReqVo);
        pageData.setTotal(count);
        if(count > 0){
            webClickReqVo.setPageNumber(pageData.getPageNumber());
            List<WebClickLog> webClickLogList = webClickLogMapper.getList(webClickReqVo);
            return new ResponseEntity<>(PageResultModel.ok(webClickLogList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }
}
