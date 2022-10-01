package com.example.warehouse.service;

import com.example.warehouse.entity.User;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.UserVo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    int save(User user);

    int update(User user,String type);

    int del(String usercode);

    ResponseEntity<ResultModel> findUserSelectList();
    /**
     * 根据id查询
     * @param id
     * @return
     */
    User findById(int id);

    User getUserByUserCode(String userCode);

    List<UserVo> findAll(Map<String, Object> map,Integer loginId);

    int countSelectAll(Map<String, Object> map);

    List<User> find(Map<String, Object> map);

    User findName(String loginName);

    User login(String username, String password);

    int updateLastLogin(User user);

    List<User> getChild(String userId);

    /**
     * 修改 用户角色
     * @param userId
     * @param roleId
     * @return
     */
    int updateUserToRole(int userId, int roleId);

    int setRole(int userId, int roleId);

    UserVo findUserVoById(Integer id);

    ResponseEntity<ResultModel> generateShareLink();

    ResponseEntity<ResultModel> bingCommercialArea(int userId, int commercialAreaId, String commercialAreaName);
}
