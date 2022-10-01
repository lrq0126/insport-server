package com.dwgj.mlxydedicatedline.mapper.ping;

import com.dwgj.mlxydedicatedline.entity.Goods;
import com.dwgj.mlxydedicatedline.entity.ping.PingMember;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PingMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PingMember record);

    int insertSelective(PingMember record);

    PingMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingMember record);

    int updateByPrimaryKey(PingMember record);

    int insertPinMembers(List<PingMember> pingMemberList);

    List<PingMember> getMemberGoodsByPinId(PingMember record);

    List<String> getMemberGoodsListByOrderNumber(PingMember record);

    void deleteByGoodsIds(List<String> goodsIds);

    List<PingMember> getAllMembersGoodsSumByPinId(String pId);

    List<PingMember> getAllMembersGoodsSumExceptLeaderByPinId(String pId,String learderId);
    /**
     * 计算拼团下面多少个包裹
     * @param record
     * @return
     */
    int countGoodsNumByPinIdExceptLeader(PingMember record);

    /**
     * 获取所有拼团下成员的包裹信息
     * @param map  pId：拼团id  customerId： 成员id
     * @return
     */
    List<Goods> getAllGoodsByPinIdAndCustomerId(Map<String,Object> map);

    List<PingMember> getMembersByPinId(String pId);


    int selectPeopleNumByPid(int pid);

    int selectPackageSumByPid(int pid);

}