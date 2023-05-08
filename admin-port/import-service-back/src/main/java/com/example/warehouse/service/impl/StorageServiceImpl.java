package com.example.warehouse.service.impl;

import com.example.warehouse.common.PdfUtils;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.Storage;
import com.example.warehouse.entity.User;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.StorageMapper;
import com.example.warehouse.mapper.UserMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.StorageService;
import com.example.warehouse.vo.storage.StorageCodeVo;
import com.example.warehouse.vo.storage.StorageLocationsRespVo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.warehouse.common.PdfUtils.getSystemPath;
import static com.example.warehouse.enums.ResultStatus.*;

@Service("storageService")
@Slf4j
public class StorageServiceImpl implements StorageService {
    /**
     * 上传文件路径
     */
    @Value("${upload.location}")
    private String UPLOAD_LOCATION;
    /**
     * 系统域名
     */
    @Value("${server.domain}")
    private String DOMAIN;

    @Autowired
    private PdfUtils pdfUtils;

    @Autowired
    private StorageMapper storageMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public int save(Storage storage) {
        // TODO 添加 storage_code
        String code = SequenceCode.gainSerialNo("STO");
        storage.setStorageCode(code);
        return storageMapper.insert(storage);
    }

    @Override
    public int update(Storage storage) {
        return storageMapper.updateByPrimaryKey(storage);
    }

    @Override
    public int del(String storageNo, String usercode) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("storageNo",storageNo);
//        List<Goods> goodsList = goodsMapper.find(map);
//        if(!goodsList.isEmpty()){ //不为空，说明货物表里面有引用该仓库，则不可以删除
//            return 500;
//        }
        User user = userMapper.findByCode(usercode);
        return storageMapper.del(storageNo, user.getId());
    }

    @Override
    public List<Storage> findAll(Map<String, Object> map) {
        return storageMapper.selectAll(map);
    }

    @Override
    public int countFindAll(Map<String, Object> map) {
        return storageMapper.countSelectAll(map);
    }

    @Override
    public List<Storage> find(Map<String, Object> map) {
        return storageMapper.find(map);
    }

    @Override
    public ResponseEntity<ResultModel> codePrint(StorageCodeVo storageCodeVo) {
        if (storageCodeVo.getStorageRow() == null) {
            return new ResponseEntity<>(ResultModel.error(STORAGE_ROW_NULL_ERROR), HttpStatus.OK);
        }


        String fileLocation = UPLOAD_LOCATION + "/template/storage/";

        File parent = new File(fileLocation);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        //文件名
        String fileName = "DW_" + SequenceCode.gainSerialNo("storage") + ".pdf";

        // 生成的文件路径和名
        String outFile = fileLocation + fileName;
        generateCode(storageCodeVo, outFile);
        String outFile1 = DOMAIN + fileLocation.substring(4) + fileName;
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("url",outFile);
        return new ResponseEntity<>(ResultModel.ok(outFile1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getWareLocations() {
        List<StorageLocationsRespVo> storageLocations = storageMapper.getWareLocations();
        for (StorageLocationsRespVo storageLocation : storageLocations) {
            Integer rowNum = storageLocation.getStorageRow();
            List<String> storageRows = new ArrayList<>();
            int i = 1;
            while (storageRows.size() < rowNum){
                storageRows.add(String.valueOf(i));
                i++ ;
            }
            storageLocation.setStorageRows(storageRows);
        }
        return new ResponseEntity<>(ResultModel.ok(storageLocations), HttpStatus.OK);
    }


    private void generateCode(StorageCodeVo storageCodeVo, String newPath) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        String templatePath = getSystemPath() + "model/StorageCode.pdf";

        log.info("getSystemPath():{}", getSystemPath());

        log.info("modelFile:{}", templatePath);
        //不确定数据有多少，所以要无限循环
        try {
            ByteArrayOutputStream bos;
            // 输出流
            FileOutputStream response = new FileOutputStream(newPath);
            Document document = new Document();

            PdfCopy copy = new PdfCopy(document, response);

            String area = storageCodeVo.getStorageArea();
            int rowNumber = storageCodeVo.getStorageRow();

            String printArea;

            int b = 1;
            Map<String, Object> dateMap = new HashMap<>();

            while (b <= rowNumber) {
                printArea = area + "区" + b + "排";
                bos = new ByteArrayOutputStream();
                // 读取pdf模板
                PdfReader reader = new PdfReader(templatePath);
                PdfStamper ps = new PdfStamper(reader, bos);
                //回去填充pdf的对象
                AcroFields s = ps.getAcroFields();

                dateMap.put("date", sdf.format(new Date()));
                dateMap.put("location", printArea);
                dateMap.put("code","DW_"+area+","+b);
                System.out.println(dateMap.get("location"));
                b++;
                Integer codeHeight = 75;
                for(String key : dateMap.keySet()){
                    if("code".equals(key)){
                        Object value = dateMap.get(key);
                        // 获取属性的类型
                        if(value != null && s.getField(key) != null){
                            //获取位置(左上右下)
                            AcroFields.FieldPosition fieldPosition = s.getFieldPositions(key).get(0);
                            //绘制条码
                            Barcode128 barcode128 = new Barcode128();
                            //字号
                            barcode128.setSize(22);
                            //条码与数字间距
                            barcode128.setBaseline(20);
                            //条码高度
                            barcode128.setBarHeight(codeHeight);
                            //设置长宽\
                            final Float moduleWidth;
                            int strLength = value.toString().length();
                            if(strLength < 5){
                                moduleWidth=1.6F;
                            }else if(strLength >=5 && strLength <= 7){
                                Float length = 7.0F/strLength;
                                moduleWidth=1.6F*length;
                            }else if(strLength > 7 && strLength <= 9){
                                Float length = 11.0F/strLength;
                                moduleWidth=1.6F*length;
                            }else if(strLength > 9 && strLength < 14){
                                Float length = 13.0F/strLength;
                                moduleWidth=1.5F*length;
                            }else if(strLength >= 14 && strLength < 18){
                                Float length = 14.0F/strLength;
                                moduleWidth=1.5F*length;
                            }else if(strLength >= 18 && strLength < 22){
                                Float length = 15.0F/strLength;
                                moduleWidth=1.6F*length;
                            } else{
                                Float length = 15.0F/strLength;
                                moduleWidth=1.6F*length;
                            }
                            barcode128.setX(moduleWidth);

                            // 条码形状
                            barcode128.setCode(value.toString());
                            // 条码下方文字
                            barcode128.setAltText("");

                            barcode128.setStartStopText(false);
                            barcode128.setExtended(true);

                            //绘制在第b页
                            PdfContentByte cb = ps.getOverContent(1);
                            //生成条码图片
                            Image image128 = barcode128.createImageWithBarcode(cb, null, null);
                            //左边距(居中处理)
                            float marginLeft = (fieldPosition.position.getRight() - fieldPosition.position.getLeft() - image128.getWidth()) / 2;
                            //条码位置
                            image128.setAbsolutePosition(fieldPosition.position.getLeft() + marginLeft, fieldPosition.position.getBottom());
                            //加入条码
                            cb.addImage(image128);
                        }
                    }else {
                        String value = (String) dateMap.get(key);
                        s.setField(key, value);
                    }
                }



                // 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
                ps.setFormFlattening(true);
                ps.close();
                PdfReader pdfReader = new PdfReader(bos.toByteArray());
                document.open();
                document.newPage();
                copy.addDocument(pdfReader);
                pdfReader.close();
                bos.close();

                copy.flush();
                reader.close();
                System.out.println("----------------------------------------");
            }

            copy.close();
            response.close();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("仓库条码打印失败");
        }

    }

}
