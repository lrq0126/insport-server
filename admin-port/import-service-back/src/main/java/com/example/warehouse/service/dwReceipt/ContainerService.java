package com.example.warehouse.service.dwReceipt;

import com.example.warehouse.entity.Container;
import com.example.warehouse.entity.ContainerNumber;

import java.util.List;
import java.util.Map;

public interface ContainerService {

    /**
     * 查找所有批次的扫描单号
     * @param map
     * @return
     */
    List<ContainerNumber> getContainerNumberList(Map<String,Object> map);
    int countContainerNumberList(Map<String,Object> map);

    /**
     * 查找所有批次
     * @param map
     * @return
     */
    List<Container> getContainerList(Map<String,Object> map);
    int countContainerList(Map<String,Object> map);

    /**
     * 删除批次，先判断是否关联了单号，有就不能删除
     * @param containerIds
     * @return
     */
    int deleteContainer(List<String> containerIds);

    /**
     * 保存批次
     * @param name 批次名称
     * @return
     */
    int saveContainer(String name);

    /**
     * 获取批次详情
     * @param id
     * @return
     */
    Container get(int id);

    /**
     * 保存扫描单号，长度大于24位的单号需要截取，保留12位
     * @param containerNumber
     * @return
     */
    ContainerNumber saveContainerNumber(ContainerNumber containerNumber);

    /**
     * 更新批次名称，需要更新语音文件
     * @param container
     * @return
     */
    int updateContainer(Container container);

    /**
     * 批量删除单号
     * @param containerNumberIds
     * @return
     */
    int deleteContainerNumbers(List<String> containerNumberIds);

    /**
     * 获取单号详情
     * @param id
     * @return
     */
    ContainerNumber getContainerNumber(int id);

    /**
     * 更新单号
     * @param containerNumber
     * @return
     */
    int updateContainerNumber(ContainerNumber containerNumber);

    /**
     * 获取批次的下拉菜单
     * @param isOut
     * @return
     */
    List<Container> getContainerItemList(String isOut);

    /**
     * 导出excel文件
     * @return
     */
    List<ContainerNumber> exprotList(Map<String,Object> map);

    /**
     * 批量出仓
     * @param ids
     * @return
     */
    int outContainers(List<String> ids);

    /**
     * 检查单号是否已经入库
     * @param containerNumber
     * @return
     */
    int checkNumberIsIn(ContainerNumber containerNumber);
}
