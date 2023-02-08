package com.example.warehouse.controller.customerPack;

import com.example.warehouse.entity.customerPack.PinSpellMail;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.customerPack.PinSpellMailService;
import com.example.warehouse.vo.customerPack.PinSpellMailReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pinSpellMail")
public class PinSpellMailController {

    @Autowired
    private PinSpellMailService pinSpellMailService;

    // 保存
    @PostMapping("/savePinSpellMail")
    public ResponseEntity<ResultModel> savePinSpellMail(PinSpellMail pinSpellMail){
        return pinSpellMailService.savePinSpellMail(pinSpellMail);
    }

    // 获取列表
    @PostMapping("/getPinSpellMailList")
    public ResponseEntity<PageResultModel> getPinSpellMailList(PinSpellMailReqVo pinSpellMailReqVo){
        return pinSpellMailService.getPinSpellMailList(pinSpellMailReqVo);
    }

    // 获取订单明细
    @PostMapping("/getPinSpellMailInfo")
    public ResponseEntity<ResultModel> getPinSpellMailInfo(int id){
        return pinSpellMailService.getPinSpellMailInfo(id);
    }

    // 确认打包
    @PostMapping("/completeTeamComplete")
    public ResponseEntity<ResultModel> completeTeamComplete(@RequestParam("id") int id, @RequestParam("customerPackIds") String customerPackIds){
        return pinSpellMailService.completeTeamComplete(id, customerPackIds);
    }

    // 发货
    @PostMapping("/deliver")
    public ResponseEntity<ResultModel> deliver(int id){
        return pinSpellMailService.deliver(id);
    }

    // 到货
    @PostMapping("/arrival")
    public ResponseEntity<ResultModel> arrival(int id){
        return pinSpellMailService.arrival(id);
    }

    // 删除
    @PostMapping("/delete")
    public ResponseEntity<ResultModel> delete(int id){
        return pinSpellMailService.delete(id);
    }
}
