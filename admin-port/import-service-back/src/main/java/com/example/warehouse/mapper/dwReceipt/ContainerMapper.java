package com.example.warehouse.mapper.dwReceipt;

import com.example.warehouse.entity.Container;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ContainerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Container record);

    int insertSelective(Container record);

    Container selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Container record);

    int updateByPrimaryKey(Container record);

    List<Container> getContainerList(Map<String,Object> map);

    int countContainerList(Map<String,Object> map);

    int checkIsRalateById(int id);

    /**
     * 查找所有批次，可以筛选是否出仓，主要用于扫描的时候下拉菜单
     * @param map isout : 是否出
     * @return
     */
    List<Container> getContainerItemList(Map<String,Object> map);

    int deleteContainers(List<String> ids);

    /**
     * 批量出仓
     * @param ids
     * @return
     */
    int outContainers(List<String> ids);
}
