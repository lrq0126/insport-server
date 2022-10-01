package com.example.warehouse.service.ping;

import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.ping.requestVo.AuditRequestVo;
import com.example.warehouse.vo.ping.requestVo.PingRequestVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface PingService {

    /**
     * 获取拼团列表
     * @param pingRequestVo
     * @return
     */
    ResponseEntity<ResultModel>  getPingMainList(PingRequestVo pingRequestVo);


    /**
     * 审核操作
     * @param auditRequestVo
     * @return
     */
    ResponseEntity<ResultModel> operationAudit(AuditRequestVo auditRequestVo);

    /**
     * 更改拼团信息
     * @param pid
     * @param pingType
     * @return
     */
    ResponseEntity<ResultModel> operationTeamType(int pid, int pingType);

    /**
     * 踢出某一个成员
     * @param pId
     * @param customerId
     * @return
     */
    ResponseEntity<ResultModel> kickedMember(int pId,int customerId);

    /**
     * 踢出成员下的某一个包裹
     * @param pId
     * @param customerId
     * @param goodsId
     * @return
     */
    ResponseEntity<ResultModel> kickedMemberGoods(int pId,int customerId,int goodsId);

    /**
     * 删除拼团
     * @param pIds  拼团id族
     * @return
     */
    ResponseEntity<ResultModel> deletePin(List<Integer> pIds);

    ResponseEntity<ResultModel> topOperation(int id, int isTop);

    /**
     * 打包完成，发送通知给所有人
     * @param id
     * @return
     */
    ResponseEntity<ResultModel> packComplete(int id);

    /**
     * 获取拼单详情
     * @param pid
     * @return
     */
    ResponseEntity<ResultModel> getPingMainDetailed(@RequestParam("pid") int pid);

    ResponseEntity<ResultModel> shipped(int id);

}
