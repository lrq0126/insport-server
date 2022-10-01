package com.dwgj.mlxydedicatedline.service.ping;

import com.dwgj.mlxydedicatedline.entity.ping.PingMain;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PingMainService {

    /**
     * 自己发起的拼团列表
     * @param map
     * @return
     */
    ResponseEntity<ResultModel> ownList(Map<String,Object> map);

    /**
     * 参加过的拼团列表
     * @param map
     * @return
     */
    ResponseEntity<ResultModel> takePartInList(Map<String,Object> map);

    /**
     * 主页的拼团列表
     * @param map
     * @return
     */
    ResponseEntity<ResultModel> pinList(Map<String,Object> map);

    /**
     * 首页的置顶拼团列表
     * @return
     */
    ResponseEntity<ResultModel> homeList();

    /**
     * 创建拼团
     * @return
     */
    ResponseEntity<ResultModel> savePingtuan(MultipartFile[] multipartFiles, PingMain pin, boolean isUpdate);

    /**
     * 重新激活
     * @return
     */
    ResponseEntity<ResultModel> reactivatePin(PingMain pin);

    /**
     * 根据拼团orderNumber订单号获取拼团信息
     * @param orderNumber
     * @return
     */
    ResponseEntity<ResultModel> getPingtuan(String orderNumber);

    /**
     * 参加拼团
     * @param orderNumber
     * @param goodsIds
     * @param takeIn 是添加包裹还是删除包裹
     * @return
     */
    ResponseEntity<ResultModel> takePartIn(String orderNumber,String goodsIds,boolean takeIn);

    /**
     * 退出该拼团
     * @param orderNumber
     * @return
     */
    ResponseEntity<ResultModel> quitPin(String orderNumber);

    /**
     * 检查成员是否有几个进入拼团
     * @param orderNumber
     * @param pId
     * @return
     */
    ResponseEntity<ResultModel> checkIsIn(String orderNumber,int pId)throws Exception;

    /**
     * 检查密码是否正确
     * @param orderNumber
     * @param password
     * @return
     */
    ResponseEntity<ResultModel> checkPassword(String orderNumber,String password);

    /**
     * 踢出某个成员
     * @param pId
     * @param memberId
     * @return
     */
    ResponseEntity<ResultModel> kickMember(String pId,String memberId);

    /**
     * 踢出某个成员
     * @param pId
     * @param orderNumber
     * @return
     */
    ResponseEntity<ResultModel> deletePin(String pId,String orderNumber);

    /**
     * 提醒某个成员加快拼团速度
     * @param pId
     * @param orderNumber
     * @param memberId
     * @return
     */
    ResponseEntity<ResultModel> messgeMember(String pId,String orderNumber,String memberId);

    /**
     * 检查是否有成团资格
     * @param pId
     * @param orderNumber
     * @return
     */
    ResponseEntity<ResultModel> checkConfirm(String pId,String orderNumber);

    /**
     * 提醒某个成员加快拼团速度
     * @param pId
     * @param orderNumber
     * @return
     */
    ResponseEntity<ResultModel> confirm(String pId,String orderNumber);
}
