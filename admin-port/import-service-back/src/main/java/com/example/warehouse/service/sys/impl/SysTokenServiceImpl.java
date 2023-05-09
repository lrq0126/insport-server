package com.example.warehouse.service.sys.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.entity.sys.SysToken;
import com.example.warehouse.mapper.sys.SysTokenMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.SysTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service("sysTokenService")
public class SysTokenServiceImpl implements SysTokenService {

    @Autowired
    private SysTokenMapper sysTokenMapper;

    @Override
    public void generateSysToken() {
        String tokenStr = generateRandom(82);
        SysToken sysToken = new SysToken();
        sysToken.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        sysToken.setStatus(1);
        sysToken.setTimeLimit(9000);
        sysToken.setSysToken(tokenStr);

        sysTokenMapper.insertSelective(sysToken);
    }

    @Override
    public ResponseEntity<ResultModel> getSysToken() {
        SysToken sysToken = sysTokenMapper.getUltramodernToken();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", sysToken.getSysToken());
        resultMap.put("expire", sysToken.getTimeLimit());
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @Override
    public Boolean checkToken(String token) {
        SysToken sysToken = sysTokenMapper.getSysTokenByToken(token);
        if(sysToken == null){
            return false;
        }
        Date dateTime = DateUtil.string2Date(sysToken.getCreateTime(), DateUtil.DEFAULT_TIMESTAMP_FORMAT);
        Date newDataTime = new Date();
        if(DateUtil.getDifTwoTime(newDataTime, dateTime, "S") > sysToken.getTimeLimit()){
            return false;
        }
        return true;
    }


    private static String generateRandom(int len) {
        char[] str = {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'B', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_', '-', '+'
        };
        int maxNum = 65;
        int count = 0;
        int i;
        StringBuilder randomStr = new StringBuilder();
        Random random = new Random();
        while (count < len) {
            i = random.nextInt(maxNum);
            if (i >= 0 && i < str.length) {
                randomStr.append(str[i]);
                count++;
            }
        }
        return randomStr.toString();
    }

}
