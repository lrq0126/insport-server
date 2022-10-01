package com.example.warehouse.service.commerical.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PdfUtils;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.CustomerPackNumber;
import com.example.warehouse.entity.commerical.CommericalInvoiceItems;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.commerical.CommericalInvoiceDao;
import com.example.warehouse.entity.commerical.CommericalInvoice;
import com.example.warehouse.mapper.commerical.CommericalInvoiceItemsDao;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.service.commerical.CommericalInvoiceService;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.detailedListVo.GoodsDetailedListVo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (CommericalInvoice)表服务实现类
 *
 * @author makejava
 * @since 2020-07-29 19:10:41
 */
@Service("commericalInvoiceService")
@Slf4j
public class CommericalInvoiceServiceImpl implements CommericalInvoiceService {
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private PdfUtils pdfUtils;

    @Resource
    private CommericalInvoiceDao commericalInvoiceDao;
    @Resource
    private CommericalInvoiceItemsDao commericalInvoiceItemsDao;
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 系统域名
     */
    @Value("${server.domain}")
    private String DOMAIN;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CommericalInvoice queryById(Long id) {
        return this.commericalInvoiceDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<CommericalInvoice> queryAllByLimit(int offset, int limit) {
        return this.commericalInvoiceDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param commericalInvoice 实例对象
     * @return 实例对象
     */
    @Override
    public CommericalInvoice insert(CommericalInvoice commericalInvoice) {
        this.commericalInvoiceDao.insert(commericalInvoice);
        return commericalInvoice;
    }

    /**
     * 修改数据
     *
     * @param commericalInvoice 实例对象
     * @return 实例对象
     */
    @Override
    public CommericalInvoice update(CommericalInvoice commericalInvoice) {
        this.commericalInvoiceDao.update(commericalInvoice);
        return this.queryById(commericalInvoice.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.commericalInvoiceDao.deleteById(id) > 0;
    }

    /**
     * 根据包裹id获取订单号和转运单号
     *
     * @param customerPackId
     * @return
     */
    @Override
    public CommericalInvoice selectByCustomerPackId(Integer customerPackId) {
        try {
            CommericalInvoice commericalInvoice = commericalInvoiceDao.selectOldInvoiceByPackId(customerPackId);
            if (commericalInvoice == null) {
                // 获取收件人信息并赋值
                commericalInvoice = commericalInvoiceDao.selectByPackId(customerPackId);
            }
            // 获取包裹的货物明细信息
            List<GoodsDetailedListVo> goodsVoList = goodsMapper.findGoodsDetailedVoByPackId(customerPackId);
            commericalInvoice.setGoodsList(goodsVoList);
            return commericalInvoice;
        } catch (Exception e) {
            log.info("订单查询错误：{}" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Map<String, Object> createInvoice(Map<String, Object> requestMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("发票打印");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            log.info("发票打印开始执行： =========================》》");
            String goodsListStr = requestMap.get("goods").toString();
            CommericalInvoice commericalInvoice = JSON.parseObject(requestMap.get("commericalInvoice").toString(), CommericalInvoice.class);
            if ( commericalInvoice.getExportDate() == null || "".equals(commericalInvoice.getExportDate())){
                commericalInvoice.setExportDate(new Date());
            }
            int i;
            if(commericalInvoice.getId() == null){
                i = commericalInvoiceDao.insert(commericalInvoice);
            }else {
                i = commericalInvoiceDao.update(commericalInvoice);
            }

            if (i < 1) {
                requestMap.put("errorCode", 1501);
                return requestMap;
            }
            List<CommericalInvoiceItems> commericalInvoiceItemsList = JSON.parseArray(goodsListStr, CommericalInvoiceItems.class);
            // 子包裹操作
            insertInvoiceItems(commericalInvoiceItemsList, commericalInvoice.getId(), requestMap);

            // 生成的pdf的    TODO 后续修改PDF的生成地址
            String outPath = pdfUtils.getSystemPath() + "upload/pdf/invoice"; //这是本地的
            //outPath = "/App/upload/pdf/invoice";
            pdfUtils.generateFile(outPath);
            String pdfFileName = SequenceCode.gainSerialNo("invoice");
            String lastPdfName = outPath + "/" + pdfFileName + ".pdf";
            createInvoice(commericalInvoice, commericalInvoiceItemsList, lastPdfName);

            //requestMap.put("url",lastPdfName);

            lastPdfName = lastPdfName.replaceAll("\\\\", "/");
            lastPdfName = lastPdfName.substring(lastPdfName.indexOf("upload"));
            log.info("发票打印完成1：{}", lastPdfName);
            lastPdfName = DOMAIN + "/" + lastPdfName;
            requestMap.put("url", lastPdfName);

            transactionManager.commit(status);
            log.info("发票打印完成2：{}", lastPdfName);
            return requestMap;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("发票打印错误：{}" + e.getMessage());
            transactionManager.rollback(status);
            requestMap.put("errorCode", -100);
            return requestMap;
        }
    }

    @Override
    public Map<String, Object> saveInvoice(Map<String, Object> requestMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("暂存发票信息");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            log.info("暂存发票信息开始执行： =========================》》");
            String goodsListStr = requestMap.get("goods").toString();
            CommericalInvoice commericalInvoice = JSON.parseObject(requestMap.get("commericalInvoice").toString(), CommericalInvoice.class);
            int i;
            if (commericalInvoice.getId() == null) {
                i = commericalInvoiceDao.insert(commericalInvoice);
            } else {
                i = commericalInvoiceDao.update(commericalInvoice);
            }

            if (i == 0) {
                requestMap.put("errorCode", 1501);
                return requestMap;
            }
            List<CommericalInvoiceItems> commericalInvoiceItemsList = JSON.parseArray(goodsListStr, CommericalInvoiceItems.class);

            // 子包裹操作
            insertInvoiceItems(commericalInvoiceItemsList, commericalInvoice.getId(), requestMap);

            log.info("暂存发票信息保存完成： 《《《=========================");
            transactionManager.commit(status);
            return requestMap;
        } catch (Exception e) {
            log.info("暂存发票信息错误： 《《《=========================");
            e.printStackTrace();
            transactionManager.rollback(status);
            requestMap.put("errorCode", -100);
            return requestMap;
        }

    }


    private void createInvoice(CommericalInvoice commericalInvoice, List<CommericalInvoiceItems> commericalInvoiceItemsList,
        String newPath) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String templatePath = pdfUtils.getSystemPath() + "model/invoice_2.pdf";
        BaseFont bf;
        Font font = null;
        try {
            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                BaseFont.NOT_EMBEDDED);//创建字体
            font = new Font(bf, 12);//使用字体
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos;
        // 输出流
        FileOutputStream response = new FileOutputStream(newPath);
        Document document = new Document();

        PdfCopy copy = new PdfCopy(document, response);
        Map<String, Object> dateMainMap = new HashMap<>();
        Map<String, Object> dateSonMap = new HashMap<>();
        int b = 0;

        bos = new ByteArrayOutputStream();
        // 读取pdf模板
        PdfReader reader = new PdfReader(templatePath);
        PdfStamper ps = new PdfStamper(reader, bos);
        //回去填充pdf的对象
        AcroFields s = ps.getAcroFields();

        //填充发票主数据
        if(commericalInvoice.getConCompanyName() != null){
            dateMainMap.put("conCompanyName", commericalInvoice.getConCompanyName());
        }
        if(commericalInvoice.getConSenderName() != null){
            dateMainMap.put("conSenderName", commericalInvoice.getConSenderName());
        }
        if(commericalInvoice.getConContry() != null){
            dateMainMap.put("conContry", commericalInvoice.getConContry());
        }
        if(commericalInvoice.getConTel() != null){
            dateMainMap.put("conTel", commericalInvoice.getConTel());
        }
        if(commericalInvoice.getConAddress() != null){
            dateMainMap.put("conAddress", commericalInvoice.getConAddress());
        }
        if(commericalInvoice.getConCode() != null){
            dateMainMap.put("conCode", commericalInvoice.getConCode());
        }
        if(commericalInvoice.getRecCompanyName() != null){
            dateMainMap.put("recCompanyName", commericalInvoice.getRecCompanyName());
        }
        if(commericalInvoice.getRecReceiverName() != null){
            dateMainMap.put("recReceiverName", commericalInvoice.getRecReceiverName());
        }
        if(commericalInvoice.getRecTel() != null){
            dateMainMap.put("recTel", commericalInvoice.getRecTel());
        }
        if(commericalInvoice.getRecContry() != null){
            dateMainMap.put("recContry", commericalInvoice.getRecContry());
        }
        if(commericalInvoice.getRecAddress() != null){
            dateMainMap.put("recAddress", commericalInvoice.getRecAddress());
        }
        if(commericalInvoice.getRecCode() != null){
            dateMainMap.put("recCode", commericalInvoice.getRecCode());
        }
        if(commericalInvoice.getExportDate() != null){
            dateMainMap.put("exportDate", sdf.format(commericalInvoice.getExportDate()));
        }
        if(commericalInvoice.getTotalCtns() != null){
            dateMainMap.put("totalCtns", commericalInvoice.getTotalCtns());
        }
        if(commericalInvoice.getSignature() != null){
            dateMainMap.put("signature", commericalInvoice.getSignature());
        }
        if(commericalInvoice.getOrderNumber() != null){
            dateMainMap.put("orderNumber", commericalInvoice.getOrderNumber());
        }
        if(commericalInvoice.getAgentNumber() != null){
            dateMainMap.put("agentNumber", commericalInvoice.getAgentNumber());
        }

        double usd = 0;
        // 填充 发票子数据
        while (b < commericalInvoiceItemsList.size() && b < 5) {
            if(commericalInvoiceItemsList.get(b).getDescriptionOfGoods() != null){
                dateSonMap.put("descriptionOfGoods" + (b + 1), commericalInvoiceItemsList.get(b).getDescriptionOfGoods().trim());
            }
            if(commericalInvoiceItemsList.get(b).getHsCode() != null){
                dateSonMap.put("hsCode" + (b + 1), commericalInvoiceItemsList.get(b).getHsCode().trim());
            }
            if(commericalInvoiceItemsList.get(b).getMaterial() != null){
                dateSonMap.put("material" + (b + 1), commericalInvoiceItemsList.get(b).getMaterial().trim());
            }
            if(commericalInvoiceItemsList.get(b).getPurpose() != null){
                dateSonMap.put("purpose" + (b + 1), commericalInvoiceItemsList.get(b).getPurpose().trim());
            }
            if(commericalInvoiceItemsList.get(b).getQty() != null){
                dateSonMap.put("qty" + (b + 1), commericalInvoiceItemsList.get(b).getQty().trim());
            }
            if(commericalInvoiceItemsList.get(b).getUnitValue() != null){
                dateSonMap.put("unitValue" + (b + 1), commericalInvoiceItemsList.get(b).getUnitValue().trim());
            }
            if(commericalInvoiceItemsList.get(b).getTotalValue() != null){
                dateSonMap.put("totalValue" + (b + 1), commericalInvoiceItemsList.get(b).getTotalValue().trim());
                usd += Double.parseDouble(commericalInvoiceItemsList.get(b).getTotalValue()) ;
            }
            b++;
        }
        dateMainMap.put("usd", usd);

        // 主数据信息填写
        for (String key : dateMainMap.keySet()) {
            String value = String.valueOf(dateMainMap.get(key));
            s.setField(key, value);
        }
        // 子数据填写
        for (String key : dateSonMap.keySet()) {
            String value = String.valueOf(dateSonMap.get(key));
            s.setField(key, value);
        }

        ps.setFormFlattening(true);// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
        ps.close();
        PdfReader pdfReader = new PdfReader(bos.toByteArray());
        document.open();
        document.newPage();
        copy.addDocument(pdfReader);
        pdfReader.close();
        bos.close();

        copy.flush();
        reader.close();

        copy.close();
        response.close();
    }


    private void insertInvoiceItems(List<CommericalInvoiceItems> commericalInvoiceItemsList, Long commericalInvoiceId, Map<String,Object> requestMap) {
        if(commericalInvoiceItemsList != null && !commericalInvoiceItemsList.isEmpty()){
            commericalInvoiceItemsDao.deleteByInvoiceId(commericalInvoiceId);
            for (CommericalInvoiceItems commericalInvoiceItems : commericalInvoiceItemsList) {
                commericalInvoiceItems.setCommericalId(commericalInvoiceId);
            }
            int j = commericalInvoiceItemsDao.insertList(commericalInvoiceItemsList);
            if (j < 1) {
                requestMap.put("errorCode", 1502);
            }
        }

    }

}
