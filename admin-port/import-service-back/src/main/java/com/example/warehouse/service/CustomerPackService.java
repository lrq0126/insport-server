package com.example.warehouse.service;

import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.customerPack.CustomerPackVo;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customer.OrderAmountChangeReqVo;
import com.example.warehouse.vo.customer.OrderPayReqVo;
import com.example.warehouse.vo.customerPack.RouteCustomerPackReqVo;
import com.example.warehouse.vo.mobile.PackSortReqVo;
import com.example.warehouse.vo.packVo.PackConditionsQueryReqVo;
import com.example.warehouse.vo.packVo.PackedReqVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author lrq
 */
public interface CustomerPackService {

    // 手持终端接口 start
    ResponseEntity<ResultModel> getWaitPackList(PackSortReqVo packSortReqVo);

    ResponseEntity<ResultModel> getOrderDetail(String businessNumber);

    ResponseEntity<ResultModel> overPicking(String businessNumber);

    ResponseEntity<ResultModel> getByOrderNumber(String orderNumber);

    ResponseEntity<ResultModel> orderOutWare(String orderNumber, HttpServletRequest request);
    // 手持终端接口 end


    ResponseEntity<ResultModel> getProductValueDetail(Integer packId);

    /**
     * 查询总共的数据
     * @param paramMap
     * @return
     */
    int countSelectAll(Map<String,Object> paramMap);

    /**
     * 根据包裹状态的 打包包裹查询
     * 参数：packType  包裹状态 1、待打包  2、已打包  3、确认发货  4、已出库
     *       cutomerName  收件人姓名   ；   phoneNumber   手机号码；
     * @param paramMap
     * @return
     */
    ResponseEntity<ResultModel> findByPackType(Map<String,Object> paramMap);
    int countSelectByPackType(Map<String,Object> paramMap);

    /**
     * 已发货列表
     * @param
     * @return
     */
    ResponseEntity<ResultModel> findOutStorageList(PackConditionsQueryReqVo queryReqVo);
//    List<CustomerPackVo> findOutStorageList(Map<String,Object> paramMap);
    int countOutStorageList(PackConditionsQueryReqVo queryReqVo);
//    int countOutStorageList(Map<String,Object> paramMap);

    /**
     * 查询 待确认 和 待发货 的包裹
     * @param paramMap
     * @return
     */
    List<CustomerPackVo> findToBeShipped(Map<String,Object> paramMap);

    /**
     * 查询 已发货 的包裹
     * @param paramMap
     * @return
     */
    List<CustomerPackVo> findShipped(Map<String,Object> paramMap);


    /**
     * 根据 流水号 查询 包裹
     * @param businessNumber
     * @return
     */
    List<GoodsVo> findByBusinessNumber(String businessNumber);

    /**
     * 根据 业务号 更新包裹状态
     * @param paramMap
     * @return
     */
//    int updatePacking(Map<String, Object> paramMap);
////    ResponseEntity<ResultModel> updatePacking(Map<String, Object> paramMap);

    /**
     * 根据 流水号 发货
     * @param
     * @return
     */
    ResponseEntity<ResultModel> updateDeliver(Map<String, Object> paramMap, HttpServletRequest request);

    /**
     * 查询 待拆包 的包裹
     * @param paramMap
     * @return
     */
    List<CustomerPackVo> findByToUnpacking(Map<String, Object> paramMap);
    /**
     * 拆包操作
     * @param map
     * @return
     */
    int delete(Map<String,Object> map);

    /**
     * 根据 流水号获取 包裹明细
     * @param businessNumber
     * @return
     */
    CustomerPackVo findPackByBusinessNumber(String businessNumber);

    ResponseEntity<ResultModel> getWaitPackDetail(String businessNumber);

    /**
     * 取消拆包操作
     * @param packId
     * @return
     */
    int cancelUnpack(Integer packId);

    /**
     * 修改包裹信息操作
     * @param paramMap
     * @return
     */
    int updateCustomerPackMessage(Map<String,Object> paramMap);

    /**
     * 更新转单号
     * @param customerPack
     * @return
     */
    int updateAgentNumber(CustomerPack customerPack, HttpServletRequest request);

    /**
     * 编辑打包的备注信息
     * @param customerPack
     * @return
     */
    ResponseEntity<ResultModel> updatePackMessage(CustomerPack customerPack, HttpServletRequest request);

    /**
     * 更新产品货值内容
     * @param packId
     * @param packValuations
     * @return
     */
    ResponseEntity<ResultModel> updatePackValuations(int packId, String packValuations);

    /**
     * 变更订单的渠道
     * @param customerPack
     * @return
     */
    ResponseEntity<ResultModel> updateChannel(CustomerPack customerPack);

    /**
     * 订单作废
     * @param customerPack
     * @return
     */
    ResponseEntity<ResultModel> updateOrderVoided(CustomerPack customerPack);

    /**
     * 撤销申请打包
     * @param packId
     * @return
     */
    ResponseEntity<ResultModel> cancleApply(int packId);

    /**
     * 撤销申请打包
     * @param packId
     * @return
     */
    ResponseEntity<ResultModel> handleOrder(int packId,int accepted);

    /**
     *
     * @param packedReqVo
     * @param isEditPack 是否编辑包裹
     * @return
     */
    ResponseEntity<ResultModel> packing(PackedReqVo packedReqVo, boolean isEditPack);

    /**
     * 拣货/扫码拣货
     * @param businessNumber
     * @param deliveryOrderNo
     * @return
     */
    ResponseEntity<ResultModel> sort(String businessNumber, String deliveryOrderNo);

    ResponseEntity<ResultModel> updateAllSort(String businessNumber);

    ResponseEntity<ResultModel> addGoodsInPack(int id, String goodsIds, int isPinPage);

    /**
     * 批量更新转单号
     * @param customerPacks
     */
    void batchUpdateAgentNumberByOrderNumber(List<CustomerPack> customerPacks, HttpServletRequest request);

    /**
     * 检查单号是否已发货或者存在数据库中
     * @param orderNumber
     * @return
     */
    int checkOrderNumberIsOutWare(String orderNumber);

    /**
     * 创建亚美订单
     * @param orderNumber
     * @return
     */
    ResponseEntity<ResultModel> buildOrderForYamei(String orderNumber);

    //订单付款操作
    ResponseEntity<ResultModel> getPayOrderByOrderNumber(String orderNumber);

    ResponseEntity<ResultModel> paymentOrder(OrderPayReqVo orderPayReqVo);
    // 订单付款操作结束

    ResponseEntity<ResultModel> updateOrderReceiverAddress( Map<String, Object> requestMap);

    void exportCustomerPack(Map requestMap, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<ResultModel> cancelDelivery(int packId);

    ResponseEntity<ResultModel> ocrUploadOrderPicture(String orderNumber, MultipartFile file);

    ResponseEntity<ResultModel> orcUpdateOrderRe(String body, HttpServletRequest request);

    ResponseEntity<ResultModel> orcUpdateOrder(String body);

    ResponseEntity<ResultModel> rejectionDelivery(int packId);

    ResponseEntity<ResultModel> updateWeighed(int packId);

    ResponseEntity<ResultModel> orderAmountChange(OrderAmountChangeReqVo orderAmountChangeReqVo);


    void printInvoice(Map<String, Object> requestMap, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<ResultModel> getCustomerPackIdentity(Integer customerPackId);

    ResponseEntity<ResultModel> getGoodsList(Integer customerPackId);

    ResponseEntity<PageResultModel> getRouteCustomerPackList(RouteCustomerPackReqVo routeCustomerPackReqVo);

    ResponseEntity<ResultModel> orderSplit(int id);
}
