package com.example.warehouse.controller.ping;

import com.example.warehouse.entity.Goods2pack;
import com.example.warehouse.enums.ping.PingTypeEnums;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.goods2pack.Goods2packMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.ping.PinListingService;
import com.example.warehouse.service.ping.PingMainOrderService;
import com.example.warehouse.service.ping.PingService;
import com.example.warehouse.vo.packVo.PackedReqVo;
import com.example.warehouse.vo.ping.requestVo.AuditRequestVo;
import com.example.warehouse.vo.ping.requestVo.PingRequestVo;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.warehouse.enums.ping.PingTypeEnums.WAITING_FOR_AUDIT;

@ApiOperation(value = "拼团控制层")
@RestController
@RequestMapping("/ping")
@Slf4j
public class PingController {

    @Autowired
    private PingService pingService;
    @Autowired
    private PingMainOrderService pingMainOrderService;
    @Autowired
    private PinListingService pinListingService;


    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private Goods2packMapper goods2packMapper;
    @GetMapping(value = "tt")
    public void tt(@RequestParam("goodsNos") String goodsNos, @RequestParam("packId") int packId){

        List<String> goodsNoList = Arrays.asList(goodsNos.split(","));

        List<Integer> goodsIds = goodsMapper.selectIdByGoodsNos(goodsNoList);

        goodsIds.forEach(goodsId -> System.out.println("goodsIds:" + goodsId));
        List<Goods2pack> goods2packList = new ArrayList<>();
        for (Integer goodsId : goodsIds) {
            Goods2pack goods2pack = new Goods2pack();
            goods2pack.setPackId(packId);
            goods2pack.setGoodsId(goodsId);
            goods2pack.setStatus(1);
            goods2packList.add(goods2pack);
        }
        goods2packMapper.insertList(goods2packList);
    }

    //获取所有拼团列表
    @PostMapping(value = "/pingMainList")
    public ResponseEntity<ResultModel> pingMainList(PingRequestVo pingRequestVo) {
        log.info("获取所有拼团列表------>>>>>>");
        return pingService.getPingMainList(pingRequestVo);
    }

    //获取所有拼团列表
    @PostMapping(value = "/applyPinList")
    public ResponseEntity<ResultModel> applyPinList(PingRequestVo pingRequestVo) {
        log.info("获取申请拼团拼团列表------>>>>>>");
        pingRequestVo.setPingType(WAITING_FOR_AUDIT.getValue());
        return pingService.getPingMainList(pingRequestVo);
    }

    // 审核
    @PostMapping(value = "/operationTeamAudit")
    public ResponseEntity<ResultModel> operationTeamAudit(AuditRequestVo auditRequestVo) {
        return pingService.operationAudit(auditRequestVo);
    }

    // 成团
    @GetMapping(value = "/operationTeamComplete")
    public ResponseEntity<ResultModel> operationTeamComplete(@RequestParam("id") int id) {
        return pingService.operationTeamType(id, PingTypeEnums.TEAM_COMPLETE.getValue());
    }

    // 解散（拆团）
    @GetMapping(value = "/operationTeamDissolution")
    public ResponseEntity<ResultModel> operationTeamDissolution(@RequestParam("id") int id) {
        return pingService.operationTeamType(id, PingTypeEnums.TEAM_DISSOLUTION.getValue());
    }

    //重新激活
    @GetMapping(value = "/operationTeamReactivation")
    public ResponseEntity<ResultModel> operationTeamReactivation(@RequestParam("id") int id) {
        return pingService.operationTeamType(id, PingTypeEnums.WAITING_FOR_TEAM_COMPLETE.getValue());
    }

    //确认取消
    @GetMapping(value = "/operationTeamCancel")
    public ResponseEntity<ResultModel> operationTeamCancel(@RequestParam("id") int id) {
        return pingService.operationTeamType(id, PingTypeEnums.TEAM_DISSOLUTION.getValue());
    }

    // 删除拼团订单信息
    @PostMapping(value = "/deletePin")
    public ResponseEntity<ResultModel> deletePin(@RequestParam("pIds") List<Integer> pIds) {
        return pingService.deletePin(pIds);
    }

    @PostMapping(value = "/topOperation")
    public ResponseEntity<ResultModel> topOperation(@RequestParam("id") int id, @RequestParam("isTop") int isTop){
        return pingService.topOperation(id, isTop);
    }

    // 根据id查询拼团订单详情
    @PostMapping(value = "/getPingMainDetailed")
    public ResponseEntity<ResultModel> getPingMainDetailed(@RequestParam("id") int id) {
        return pingService.getPingMainDetailed(id);
    }

    // 踢出某个成员
    @PostMapping(value = "/kickedMember")
    public ResponseEntity<ResultModel> kickedMember(@RequestParam("pid") int pid, @RequestParam("memberId") int memberId) {
        return pingService.kickedMember(pid, memberId);
    }
    // 踢出某个成员的包裹
    @PostMapping(value = "/kickedMemberGoods")
    public ResponseEntity<ResultModel> kickedMemberGoods(@RequestParam("pid") int pid, @RequestParam("memberId") int memberId,
                                                         @RequestParam("goodsId") int goodsId) {
        return pingService.kickedMemberGoods(pid, memberId, goodsId);
    }

    // 获取拼团子订单详情
    @PostMapping(value = "/getPingMainOrderDetailed")
    public ResponseEntity<ResultModel> getPingMainOrderDetailed(@RequestParam("pmoId") int pmoId) {
        return pingMainOrderService.getPingMainOrderDetailed(pmoId);
    }

    // 拼团子订单打包
    @PostMapping(value = "/pingMainOrderPacked")
    public ResponseEntity<ResultModel> pingMainOrderPacked(PackedReqVo packedReqVo) {
        return pingMainOrderService.pingMainOrderPacked(packedReqVo);
    }

    @GetMapping(value = "/packComplete")
    public ResponseEntity<ResultModel> packComplete(@RequestParam("id") int id) {
        return pingService.packComplete(id);
    }

    @GetMapping(value = "/shipped")
    public ResponseEntity<ResultModel> shipped(@RequestParam("id") int id) {
        return pingService.shipped(id);
    }

    // 打印包裹标记
    @GetMapping(value = "/printPackTa")
    public ResponseEntity<ResultModel> printPackTa(@RequestParam("pmoId") int pmoId) {
        return pinListingService.printPackTa(pmoId);
    }

    // 打印所有货物清单
    @GetMapping(value = "/printAllGoods")
    public ResponseEntity<ResultModel> printAllGoods(@RequestParam("pId") int pId) {
        return pinListingService.printAllGoods(pId);
    }

    // 打印子订单货物清单 跟 普通打包的货物清单打印同一接口

    // TODO 打印订单号
    @GetMapping(value = "/printPingMain")
    public ResponseEntity<ResultModel> printPingMain(@RequestParam("pid") int pid) {
        return pinListingService.printPingMain(pid);
    }

}
