package com.example.warehouse.mapper;

import com.example.warehouse.entity.User;
import com.example.warehouse.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
@Repository
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    User selectByUserCode(String userCode);

    List<User> selectAll(Map<String, Object> map);  //查询所有用户

    int countSelectAll(Map<String, Object> map);  //记录总数

    int updateByPrimaryKey(User record);

    int del(@Param("userId") int userId);

    User login(String loginName);

    List<User> find(Map<String, Object> map);

    User findByCode(String usercode);

    User findByUserName(String username);

    int updateLastLoginTime(User user);

    List<User> getChild(String userId);

    Set<String> findRoleByUserId(int id);

    Set<String> findByUserId(int id);

    int setRole(@Param("userId") int userId, @Param("roleId")int roleId, @Param("level")int level);

    List<User> findUserSelectList();

    void bingCommercialArea(@Param("userId") int userId, @Param("commercialAreaId") int commercialAreaId,
                            @Param("commercialAreaName") String commercialAreaName);
}
