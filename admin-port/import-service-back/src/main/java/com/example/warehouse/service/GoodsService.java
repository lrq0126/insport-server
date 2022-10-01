package com.example.warehouse.service;


import com.example.warehouse.entity.CustomerPackNumber;
import com.example.warehouse.entity.Goods;
import com.example.warehouse.entity.User;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.applyPack.GoodsRequestVo;
import com.example.warehouse.vo.goods.GoodsReqVo;
import com.example.warehouse.vo.goods.OcrInWareSortingReqVo;
import com.example.warehouse.vo.goods.ShelvesReqVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface GoodsService {

    ResponseEntity<ResultModel> selectGoodsInfoByDeliveryOrderNo(String deliveryOrderNo);

    ResponseEntity<ResultModel> updateShelves(ShelvesReqVo shelvesReqVo, HttpServletRequest request);

    ResponseEntity<ResultModel> getCustomerGoodsDetail(String deliveryOrderNo);

    ResponseEntity<ResultModel> getCustomerGoods(String deliveryOrderNo, String customerNo);

    int judgeGoods(Goods goods, String type);

    int save(Goods goods,String type);

    int update(Goods good);

    int del(String goodsNo, String usercode);

    int batchDelete(String goodsNo, String usercode);

//    List<GoodsVo> findAll(Map<String,Object> map);
//    int countFindAll(Map<String,Object> map);

    ResponseEntity<PageResultModel> findAll(GoodsReqVo goodsReqVo);

    ResponseEntity<ResultModel> findAllRe(Map<String,Object> map);

    int countFindAllRe(Map<String,Object> map);

    List<GoodsVo> find(Map<String, Object> map);

    List<GoodsVo> findByCustomerNo(String customerNo);

    List<GoodsVo> conditionQuery(Map<String, Object> map);

    int outWarehouse(String goodNos, String operatorName);

    int inWarehouse(String goodNos, String operatorName);

    int outAllWarehouse(String goodNos, String operatorName);

    /**
     * 扫码出库
     * @author guoshengwen 2019年9月30日23:43:09
     * @param map
     * @return
     */
    int scanOutWare(Map<String,Object> map);

    ResponseEntity<ResultModel> findByDeliveryOrderNo(String deliveryOrderNo);

    ResponseEntity<ResultModel> getCustomerOrderLocation(String customerNo);

    GoodsVo searchDeliveryOrderNo(String deliveryOrderNo);

    Goods findById(int id);

    /**
     * 扫码拣货
     * @param goodsVoList
     * @param deliverOrderNo
     * @return
     */
    int scanPicking(List<GoodsVo> goodsVoList, String deliverOrderNo);

    /**
     * 查询 未入包 包裹
     * @return
     */
    List<GoodsVo> selectPack(Map<String,Object> paramMap);

    /**
     * 入包
     * @param paramMap
     * @return
     */
    CustomerPackNumber inPacked(Map<String,Object> paramMap);

    void initInPacked(String businessNumber);

    /**
     * 根据 订单号查询 快递包裹
     * @param deliveryOrderNo
     * @return
     */
    List<GoodsVo> findGoodsByDeliveryOrderNo(String deliveryOrderNo);

    int deletePacked(Map<String, Object> paramMap);

    /**
     * 获取客户最新的仓库位置
     * @param customerNo
     * @return
     */
    String getLocationByCustomerNo(String customerNo);

    /**
     * 根据快递单号获取货物
     * @param delieverOrderNo
     * @return
     */
    List<Goods> getGoodsBydeliveryOrderNo(String delieverOrderNo);

    int updateByPrimaryKey(Goods goods, User user);

    /**
     * 回去
     * @param list
     * @param user
     * @return
     */
    int batchInsertPreOrder(List<Goods> list,User user);

    ResponseEntity<ResultModel> findGoodsLocalhost(int customerId);

    ResponseEntity<ResultModel> getGoodsByCustomerId(GoodsRequestVo goodsRequestVo);

    ResponseEntity<ResultModel> findByPackId(int packId);

    /**
     * 异常件入库
     * @param goods 货物信息
     * @return
     */
    ResponseEntity<ResultModel> abnormalInStorage(Goods goods);

    ResponseEntity<ResultModel> warehouseSorting(Map<String, Object> params);

    ResponseEntity<ResultModel> ocrInWareSorting(OcrInWareSortingReqVo ocrInWareSortingReqVo, HttpServletRequest request);

    ResponseEntity<ResultModel> inWareSorting(OcrInWareSortingReqVo ocrData);

    ResponseEntity<ResultModel> abnormalInWareSorting(OcrInWareSortingReqVo ocrData);

    ResponseEntity<ResultModel> ocrInWareSortingPicture(String deliveryOrderNo, String systemName, MultipartFile file);

    ResponseEntity<ResultModel> printDeliveryOrder(String deliveryOrderNo, String loginName);

}
