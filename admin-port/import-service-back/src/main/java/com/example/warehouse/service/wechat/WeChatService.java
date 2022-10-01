package com.example.warehouse.service.wechat;

import com.example.warehouse.model.ResultModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface WeChatService {

    void processRequest(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<ResultModel> getMaterial(String type, int offset, int count);

}
