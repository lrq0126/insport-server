package com.example.warehouse.mapper.dwReceipt;

import com.example.warehouse.entity.ContainerNumber;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ContainerNumberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ContainerNumber record);

    int insertSelective(ContainerNumber record);

    ContainerNumber selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContainerNumber record);

    int updateByPrimaryKey(ContainerNumber record);

    List<ContainerNumber> getContainerNumberList(Map<String,Object> map);

    int countContainerNumberList(Map<String,Object> map);

    int deleteContainerNumbers(List<String> ids);

    List<ContainerNumber> exprotList(Map<String,Object> map);

    int checkNumberIsIn(ContainerNumber record);
}
