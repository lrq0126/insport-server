package com.example.warehouse.service.dwReceipt.impl;

import com.baidu.speech.restapi.common.DemoException;
import com.baidu.speech.restapi.ttsdemo.TtsMain;
import com.example.warehouse.entity.Container;
import com.example.warehouse.entity.ContainerNumber;
import com.example.warehouse.entity.User;
import com.example.warehouse.mapper.dwReceipt.ContainerMapper;
import com.example.warehouse.mapper.dwReceipt.ContainerNumberMapper;
import com.example.warehouse.service.dwReceipt.ContainerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ContainerServiceImpl implements ContainerService {

    @Autowired
    private ContainerNumberMapper containerNumberMapper;

    @Autowired
    private ContainerMapper containerMapper;

    @Override
    public List<ContainerNumber> getContainerNumberList(Map<String, Object> map) {
        return containerNumberMapper.getContainerNumberList(map);
    }

    @Override
    public int countContainerNumberList(Map<String, Object> map) {
        return containerNumberMapper.countContainerNumberList(map);
    }

    @Override
    public List<Container> getContainerList(Map<String, Object> map) {
        return containerMapper.getContainerList(map);
    }

    @Override
    public int countContainerList(Map<String, Object> map) {
        return containerMapper.countContainerList(map);
    }

    @Override
    public int deleteContainer(List<String> containerIds) {
        for ( String id : containerIds ){
            int count = containerMapper.checkIsRalateById(Integer.parseInt(id));
            if ( count > 0 ){
                // 存在关联单号的批次
                return 101;
            }
        }
        containerMapper.deleteContainers(containerIds);
        return 1;
    }



    @Override
    public int saveContainer(String name) {
        Container container = new Container();
        container.setName(name);
        container.setStatus(1);
        container.setIsout(0);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        container.setCreateId(user == null ? 1 : user.getId());
        container.setOperator(user == null ? "管理员" : user.getUsername());
        try {
            String url = new TtsMain().run(name);
            container.setVioceUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DemoException e) {
            e.printStackTrace();
        }
        return containerMapper.insert(container);
    }

    @Override
    public Container get(int id) {
        return containerMapper.selectByPrimaryKey(id);
    }

    @Override
    public ContainerNumber saveContainerNumber(ContainerNumber containerNumber) {
        String number = containerNumber.getAgentNumber().trim();
        containerNumber.setAgentNumber(number);
        if (StringUtils.isNotBlank(number) ){
            int len = number.length();
            // 长度大于24位的单号，截取保留后12位
            if ( len >= 24 ){
                containerNumber.setAgentNumber(number.substring(len - 12 ,len));
            }
        }
        containerNumber.setStatus(1);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        containerNumber.setCreateId(user == null ? 1 : user.getId());
        containerNumber.setOpreator(user == null ? "管理员" : user.getUsername());
        containerNumberMapper.insert(containerNumber);
        return containerNumber;
    }

    @Override
    public int updateContainer(Container container) {
        try {
            container.setLastUpdateTime(new Date());
            String url = new TtsMain().run(container.getName());
            container.setVioceUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DemoException e) {
            e.printStackTrace();
        }
        return containerMapper.updateByPrimaryKeySelective(container);
    }

    @Override
    public int deleteContainerNumbers(List<String> containerNumberIds) {
        return containerNumberMapper.deleteContainerNumbers(containerNumberIds);
    }

    @Override
    public ContainerNumber getContainerNumber(int id) {
        return containerNumberMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateContainerNumber(ContainerNumber containerNumber) {
        containerNumber.setLastUpdateTime(new Date());
        return containerNumberMapper.updateByPrimaryKeySelective(containerNumber);
    }

    @Override
    public List<Container> getContainerItemList(String isOut) {
        Map<String,Object> map = new HashMap<>(1);
        if ( StringUtils.isNotBlank(isOut) )
            map.put("isout",Integer.parseInt(isOut));
        return containerMapper.getContainerItemList(map);
    }

    @Override
    public List<ContainerNumber> exprotList(Map<String, Object> map) {
        return containerNumberMapper.exprotList(map);
    }

    @Override
    public int outContainers(List<String> ids) {
        return containerMapper.outContainers(ids);
    }

    @Override
    public int checkNumberIsIn(ContainerNumber containerNumber) {
        String number = containerNumber.getAgentNumber().trim();
        containerNumber.setAgentNumber(number);
        if (StringUtils.isNotBlank(number) ){
            int len = number.length();
            // 长度大于24位的单号，截取保留后12位
            if ( len >= 24 ){
                containerNumber.setAgentNumber(number.substring(len - 12 ,len));
            }
        }
        int i = containerNumberMapper.checkNumberIsIn(containerNumber);
        return i;
    }
}
