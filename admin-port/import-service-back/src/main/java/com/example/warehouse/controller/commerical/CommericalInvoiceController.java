package com.example.warehouse.controller.commerical;

import com.example.warehouse.entity.commerical.CommericalInvoice;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.commerical.CommericalInvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.*;

/**
 * (CommericalInvoice)表控制层
 *
 * @author makejava
 * @since 2020-07-29 19:10:42
 */
@RestController
@RequestMapping("commericalInvoice")
public class CommericalInvoiceController {
    /**
     * 服务对象
     */
    @Resource
    private CommericalInvoiceService commericalInvoiceService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public CommericalInvoice selectOne(Long id) {
        return this.commericalInvoiceService.queryById(id);
    }

    // customerPackId 761

    /**
     * 通过包裹id查询订单号和转运单号
     *
     * @param customerPackId 主键
     * @return 单条数据
     */
    @GetMapping("selectByCustomerPackId")
    public ResponseEntity<ResultModel> selectByCustomerPackId(Integer customerPackId) {
        CommericalInvoice commericalInvoice = commericalInvoiceService.selectByCustomerPackId(customerPackId);
        if(commericalInvoice == null){
            return new ResponseEntity<>(ResultModel.error(ORDER_NUMBER_SELECT_ERROR), HttpStatus.OK);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("commericalInvoice",commericalInvoice);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }



    /**
     * 发票打印
     *
     * 基本参数：commericalInvoice:
     *        {conCompanyName  (发货)公司名； conSenderName  发件人；     recCompanyName  （收货）公司名；   recReceiverName  收件人；
     *         conTel  发件人电话；           conCode 发件人邮编；         recTel  收件人电话；              recCode  收件人邮编；
     *         conContry  发件人国家；       conAddress  发件人地址；     recContry 收件人国家；           recAddress 收件人地址；
     *         totalCtns 总件数；            exportDate 出口日期         signature   签名；               usd   USD；
     *         orderNumber  订单号；        agentNumber   转运单号}
     * 明细参数：goods:[{
     *                descriptionOfGoods  货物描述；  hsCode  海关编码；  material  材质；  purpose  用途；
     *                qty  数量；  unitValue  单价；  totalValue 总价；
     *              }]
     *          requestMap：{
     *              commericalInvoice:{"conCompanyName":"德威国际货运公司","conSenderName":"梁榕清","recCompanyName":"德威国际货运-马来西亚分公司","recReceiverName":"梁榕清分身","conTel":"13676216939","conCode":"516300","recTel":"080-13676216938","recCode":"516311",
     *              "conContry":"中国广东省广州市","conAddress":"广东省广州市白云区嘉禾望岗108号","recContry":"MLXY 马来西亚","recAddress":"马来西亚-首都嘉禾望岗108号",
     *              "totalCtns":"15","exportDate":"2020-08-06","signature":"梁榕清","usd":"1750"},
     *              goods:[{"descriptionOfGoods":"塑料玩具","hsCode":"516300","material":"塑料","purpose":"儿童玩具","qty":"5","unitValue":"50","totalValue":"250"},
     *              {"descriptionOfGoods":"硅胶玩具","hsCode":"516300","material":"硅胶","purpose":"玩具","qty":"200","unitValue":"5","totalValue":"1000"},
     *              {"descriptionOfGoods":"钢铁玩具","hsCode":"516300","material":"钢材","purpose":"玩具","qty":"100","unitValue":"5","totalValue":"500"}]
     *          }
     *
     * @param requestMap
     * @return
     */
    @PostMapping("createInvoice")
    public ResponseEntity<ResultModel> createInvoice(@RequestParam Map<String, Object> requestMap){
        Map<String, Object> resultMap = new HashMap<>();
        commericalInvoiceService.createInvoice(requestMap);
        // TODO 发票打印逻辑
        if(resultMap.containsKey("errorCode")){
            String errorCode = requestMap.get("errorCode").toString();
            if(errorCode.equals("1501")){
                return new ResponseEntity<>(ResultModel.error(INVOICE_INSERT_ERROR), HttpStatus.OK);
            }else if(errorCode.equals("1502")){
                return new ResponseEntity<>(ResultModel.error(INVOICEITEMS_INSERT_ERROR), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
            }
        }
        resultMap.put("url",requestMap.get("url").toString());
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @PostMapping("saveInvoice")
    public ResponseEntity<ResultModel> saveInvoice(@RequestParam Map<String, Object> requestMap){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap = commericalInvoiceService.saveInvoice(requestMap);

        if(resultMap.containsKey("errorCode")){
            String errorCode = requestMap.get("errorCode").toString();
            if(errorCode.equals("1501")){
                return new ResponseEntity<>(ResultModel.error(INVOICE_INSERT_ERROR), HttpStatus.OK);
            }else if(errorCode.equals("1502")){
                return new ResponseEntity<>(ResultModel.error(INVOICEITEMS_INSERT_ERROR), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

}