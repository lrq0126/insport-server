package com.example.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.entity.DwReceipt;
import com.example.warehouse.entity.DwReciptReject;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.dwReceipt.DwReceiptService;
import com.example.warehouse.vo.receipt.DwReceiptVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.*;

/**
 * (DwReceipt)表控制层
 *
 * @author makejava
 * @since 2020-05-22 13:31:56
 */
@RestController
@RequestMapping("/dwReceipt")
@Slf4j
public class DwReceiptController {
    /**
     * 服务对象
     */
    @Resource
    private DwReceiptService dwReceiptService;

    /**
     * 上传文件路径
     */
    @Value("${upload.location}")
    private String UPLOAD_LOCATION;

    /**
     * 查询
     * @param map
     * @return
     */
    @PostMapping(value = "/queryAll")
    public ResponseEntity<ResultModel> queryAll(@RequestParam Map<String, Object> map) {
        return dwReceiptService.queryAll(map);

    }

    /**
     * 查询
     * @param map
     * @return
     */
    @PostMapping(value = "/queryAllReject")
    public ResponseEntity<ResultModel> queryAllReject(@RequestParam Map<String, Object> map) {

        return dwReceiptService.queryAllReject(map);

    }

    /**
     * 新增拒收
     * deliveryOrderNo SF888522263
     * deliveryNo
     * remarks
     */
    @PostMapping(value = "/insertReject")
    @RequiresPermissions("dwReceipt:create")
    public ResponseEntity<ResultModel> insertReject(DwReciptReject reject){
        return dwReceiptService.insertReject(reject);

    }

    /**
     * 修改拒收数据
     */
    @PostMapping(value = "/updateReject")
    @RequiresPermissions("dwReceipt:update")
    public ResponseEntity<ResultModel> updateReject(DwReciptReject reject){
        int result = dwReceiptService.updateReject(reject);
        if(1 == result){
            return new ResponseEntity<>(PageResultModel.error(NOT_NULL),HttpStatus.OK);
        }else if(2 == result){
            return new ResponseEntity<>(PageResultModel.error(REJECT_HAVEADD),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(PageResultModel.ok(SUCCESS),HttpStatus.OK);
        }
    }

    /**
     * 修改拒收数据
     */
    @PostMapping(value = "/deleteReject")
    @RequiresPermissions("dwReceipt:update")
    public ResponseEntity<ResultModel> deleteReject(DwReciptReject reject){
        int result = dwReceiptService.deleteReject(reject);
        return new ResponseEntity<>(PageResultModel.ok(SUCCESS),HttpStatus.OK);
    }

    /**
     * 新增签收数据
     * deliveryOrderNo SF888522263
     * deliveryNo
     * remarks
     */
    @PostMapping(value = "/insert")
    @RequiresPermissions("dwReceipt:create")
    public ResponseEntity<ResultModel> insert(@RequestParam Map<String, Object> map){
        return dwReceiptService.insertList(map);
    }

    /**
     * 修改签收数据
     */
    @PostMapping(value = "/update")
    @RequiresPermissions("dwReceipt:update")
    public ResponseEntity<ResultModel> update(@RequestParam Map<String, Object> map){
        DwReceipt dwReceipt = JSONObject.parseObject(JSONObject.toJSONString(map),DwReceipt.class);
        int result = dwReceiptService.update(dwReceipt);
        if(1 == result){
            return new ResponseEntity<>(PageResultModel.ok(SUCCESS),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(PageResultModel.error(ERROR),HttpStatus.OK);
        }
    }
    /**
     * 查询单个
     * @param id
     * @return
     */
    @PostMapping(value = "/queryById")
    public ResponseEntity<ResultModel> queryById(@RequestParam int id) {
        Map<String, Object> resultMap = new HashMap<>();
        DwReceiptVo dwReceipt = dwReceiptService.queryById(id);
        resultMap.put("dwReceipt", dwReceipt);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    /**
     * 签收列表导出
     * ids id集合
     */
    @GetMapping(value = "/export")
    public void exportTable(@RequestParam Map<String, Object> map, HttpServletResponse response, HttpServletRequest request) {
        dwReceiptService.export(map, response, request);
    }
}
