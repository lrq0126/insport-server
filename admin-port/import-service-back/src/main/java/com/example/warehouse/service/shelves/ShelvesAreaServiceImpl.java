package com.example.warehouse.service.shelves;

import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.Goods;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.shelves.GoodsShelvesRow;
import com.example.warehouse.entity.shelves.ShelvesArea;
import com.example.warehouse.entity.shelves.ShelvesGoodsCapacity;
import com.example.warehouse.entity.shelves.ShelvesRow;
import com.example.warehouse.enums.goods.GoodsStatus;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.shelves.GoodsShelvesRowMapper;
import com.example.warehouse.mapper.shelves.ShelvesAreaMapper;
import com.example.warehouse.mapper.shelves.ShelvesGoodsCapacityMapper;
import com.example.warehouse.mapper.shelves.ShelvesRowMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.shelves.ShelvesReqVo;
import com.example.warehouse.vo.shelves.ShelvesRespVo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.warehouse.common.PdfUtils.getSystemPath;
import static com.example.warehouse.enums.ResultStatus.*;

@Service("shelvesAreaService")
@Slf4j
public class ShelvesAreaServiceImpl implements ShelvesAreaService {
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
    private ShelvesAreaMapper shelvesAreaMapper;
    @Autowired
    private ShelvesRowMapper shelvesRowMapper;
    @Autowired
    private ShelvesGoodsCapacityMapper shelvesGoodsCapacityMapper;

    @Autowired
    private GoodsShelvesRowMapper goodsShelvesRowMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public ResponseEntity<PageResultModel> getShelvesAreaList(ShelvesReqVo shelvesReqVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
//            map.put("commercialAreaId", user.getCommercialAreaId());
            shelvesReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        PageData pageData = PageHelp.editPage(shelvesReqVo);
        int count = shelvesAreaMapper.getShelvesAreaCount(shelvesReqVo);
        pageData.setTotal(count);
        List<ShelvesRespVo> shelvesRespVoList = new ArrayList<>();
        if(count > 0){
            shelvesReqVo.setPageNumber(pageData.getPageNumber());
            shelvesRespVoList = shelvesAreaMapper.getShelvesAreaList(shelvesReqVo);
        }
        return new ResponseEntity<>(PageResultModel.ok(shelvesRespVoList, pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getShelvesAreaDropdownList() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        Integer commercialAreaId = null;
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
            commercialAreaId = user.getCommercialAreaId();
        }
        List<ShelvesRespVo> shelvesRespVoList = shelvesAreaMapper.getShelvesAreaDropdownList(commercialAreaId);

        List<Map<String, Object>> newStorage = new ArrayList<>();
        for (ShelvesRespVo storage : shelvesRespVoList) {
            Map<String, Object> map = new HashMap<>();
            map.put("storageName",storage.getShelvesArea());
            map.put("storageCode",storage.getId());
            map.put("storageArea",storage.getShelvesArea());
            map.put("storageRow",storage.getShelvesRowList());
            newStorage.add(map);
        }
        Map<String, Object> storageMap = new HashMap();
        storageMap.put("storage",newStorage);
        return new ResponseEntity<>(ResultModel.ok(storageMap), HttpStatus.OK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResultModel> saveShelves(ShelvesRespVo shelves) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        int repetitionArea = shelvesAreaMapper.selectRepetitionArea(shelves);
        if(repetitionArea > 0){
            return new ResponseEntity<>(ResultModel.error(SHELVES_AREA_REPETITION_ERROR), HttpStatus.OK);
        }
        if(shelves.getId() != null){
            shelves.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            shelves.setUpdateId(user.getId());
            shelvesAreaMapper.updateByPrimaryKeySelective(shelves);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else{
            List<ShelvesRow> shelvesRowList = JSONObject.parseArray(shelves.getShelvesRows(), ShelvesRow.class);
            if(CollectionUtils.isEmpty(shelvesRowList)){
                return new ResponseEntity<>(ResultModel.error(SHELVES_ROW_NULL_ERROR), HttpStatus.OK);
            }
            shelves.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            shelves.setCreateId(user.getId());
            shelvesAreaMapper.insertSelective(shelves);
            ShelvesArea shelvesArea = shelvesAreaMapper.selectArea(shelves.getShelvesArea());

            for (ShelvesRow shelvesRow : shelvesRowList) {
                shelvesRow.setParentId(shelvesArea.getId());
                shelvesRow.setCreateId(user.getId());
            }
            shelvesRowMapper.insertList(shelvesRowList);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResultModel> saveShelvesRow(ShelvesRow shelvesRow) {
        int i = shelvesRowMapper.selectCountShelvesAreaRowByRow(shelvesRow.getId(),
                shelvesRow.getParentId(), shelvesRow.getShelvesRow());
        if(i > 0){
            return new ResponseEntity<>(ResultModel.error(SHELVES_AREA_ROW_REPETITION_ERROR), HttpStatus.OK);
        }
        shelvesRowMapper.updateByPrimaryKeySelective(shelvesRow);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResultModel> saveShelvesRowList(String shelvesRowStr) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        List<ShelvesRow> shelvesRowList = JSONObject.parseArray(shelvesRowStr, ShelvesRow.class);
        if(CollectionUtils.isEmpty(shelvesRowList)){
            return new ResponseEntity<>(ResultModel.error(SHELVES_ROW_NULL_ERROR), HttpStatus.OK);
        }
        for (int i = 0; i < shelvesRowList.size(); i++) {
            for (int j = i+1; j < shelvesRowList.size(); j++) {
                if(shelvesRowList.get(i).getShelvesRow().equals(shelvesRowList.get(j).getShelvesRow())){
                    return new ResponseEntity<>(ResultModel.error(SHELVES_ROWS_REPETITION_ERROR), HttpStatus.OK);
                }
            }
        }
        List<ShelvesRow> insertShelvesRows = new ArrayList<>();
        List<ShelvesRow> updateShelvesRows = new ArrayList<>();
        for (ShelvesRow shelvesRow : shelvesRowList) {
            if(shelvesRow.getId() != null){
                shelvesRow.setUpdateId(user.getId());
                shelvesRow.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                updateShelvesRows.add(shelvesRow);
            }else {
                shelvesRow.setCreateId(user.getId());
                shelvesRow.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                insertShelvesRows.add(shelvesRow);
            }
        }
        if(!CollectionUtils.isEmpty(updateShelvesRows)){
            log.info("-------------->>>>>>货架排号执行更新语句<<<<<<-----------------");
            shelvesRowMapper.updateList(updateShelvesRows);
        }
        if(!CollectionUtils.isEmpty(insertShelvesRows)){
            log.info("-------------->>>>>>货架排号执行插入语句<<<<<<-----------------");
            shelvesRowMapper.insertList(insertShelvesRows);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> deleteShelvesArea(int shelvesAreaId) {
        int goodsSum = shelvesAreaMapper.selectShelvesAreaGoodsSum(shelvesAreaId);
        if(goodsSum > 0){
            return new ResponseEntity<>(ResultModel.error(SHELVES_AREA_HAVE_GOODS), HttpStatus.OK);
        }
        shelvesAreaMapper.deleteByPrimaryKey(shelvesAreaId);
        shelvesRowMapper.deleteByParentId(shelvesAreaId);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> deleteShelvesRow(int shelvesRowId) {
        int goodsSum = shelvesRowMapper.selectShelvesRowGoodsSum(shelvesRowId);
        if(goodsSum > 0){
            return new ResponseEntity<>(ResultModel.error(SHELVES_ROW_HAVE_GOODS), HttpStatus.OK);
        }
        shelvesRowMapper.deleteByPrimaryKey(shelvesRowId);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ResultModel> getShelvesArea(int shelvesAreaId) {
        ShelvesArea shelvesArea = shelvesAreaMapper.selectByPrimaryKey(shelvesAreaId);
        return new ResponseEntity<>(ResultModel.ok(shelvesArea), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getShelvesRowList(int shelvesAreaId) {
        List<ShelvesRow> shelvesRowList =  shelvesRowMapper.selectByParentId(shelvesAreaId);
        return new ResponseEntity<>(ResultModel.ok(shelvesRowList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getShelvesRow(int shelvesRowId) {
        ShelvesRow shelvesRow = shelvesRowMapper.selectByPrimaryKey(shelvesRowId);
        return new ResponseEntity<>(ResultModel.ok(shelvesRow), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> codePrint(String type, int id) {
        ShelvesArea shelvesArea = null;
        List<ShelvesRow> shelvesRowList = new ArrayList<>();
        if("area".equals(type)){
            shelvesArea = shelvesAreaMapper.selectByPrimaryKey(id);
            shelvesRowList = shelvesRowMapper.selectByParentId(id);
        }else{
            ShelvesRow shelvesRow = shelvesRowMapper.selectByPrimaryKey(id);
            shelvesArea = shelvesAreaMapper.selectByPrimaryKey(shelvesRow.getParentId());
            shelvesRowList.add(shelvesRow);
        }
        if(shelvesArea == null){
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        if (CollectionUtils.isEmpty(shelvesRowList)){
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
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
        generateCode(shelvesArea, shelvesRowList, outFile);
        String outFile1 = DOMAIN + fileLocation.substring(4) + fileName;
        return new ResponseEntity<>(ResultModel.ok(outFile1), HttpStatus.OK);
    }

    @Override
    public void updateOptimalShelves() {
        ShelvesGoodsCapacity shelves = null;

        List<ShelvesGoodsCapacity> shelvesAreaRowList = shelvesRowMapper.selectFreeShelvesRow();
        if(CollectionUtils.isEmpty(shelvesAreaRowList)){
            System.out.println("请添加货架信息");
            return;
        }
        // 计算货架容量
        Double capacity = null;

        for (ShelvesGoodsCapacity areaRow : shelvesAreaRowList) {
            if(areaRow.getGoodsSum() == 0){
                shelves = areaRow;

                shelves.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                shelves.setStatus(1);
                break;
            }else{
                double thisCapacity =  new BigDecimal(areaRow.getGoodsSum()/areaRow.getShelvesLimit())
                        .setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
                if(capacity == null){
                    capacity = thisCapacity;

                    shelves = areaRow;
                    shelves.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                    shelves.setStatus(1);
                }

                if(thisCapacity < capacity){
                    capacity = thisCapacity;
                    shelves = areaRow;
                    shelves.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                    shelves.setStatus(1);
                }
            }
        }
        if(shelves == null){
            System.out.println("没有可推荐的货架");
            return;
        }
        shelvesGoodsCapacityMapper.insertSelective(shelves);
    }

    @Override
    public String generateShelvesAreaRowSQL(String shelvesAreas, Integer rowNumber, String sortingExport) {
        List<String> areaList = JSONObject.parseArray(shelvesAreas, String.class);
        String sql = "";
        for (String shelvesArea : areaList) {
            String shelvesAreaSQL = "insert into shelves_area (create_time, shelves_area, shelves_type, sorting_export)";

            String dateTime = DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT);
            shelvesAreaSQL = shelvesAreaSQL + " values ('"+dateTime+"', '"+shelvesArea+"', 'general', '"+sortingExport+"')";
            String areaId = "select id from shelves_area where `status` = 1 and  shelves_area = '"+shelvesArea+"'";
            String shelvesRowSQL = "insert into shelves_row (create_time, shelves_row, shelves_limit, parent_id)";
            int i = 1;
            sql += shelvesAreaSQL + ";";
            while (i <= rowNumber){
                sql = sql + shelvesRowSQL + " values ('"+ dateTime +"', "+i+", 10, ("+areaId+"));";
                i ++;
            }

        }

        return sql;
    }

    @Override
    public void uploadGoodsShelvesRowData() {
        List<Goods> goodsList = goodsMapper.selectGoodsByGoodsType(GoodsStatus.INTO_WARE.getStatus());
        List<GoodsShelvesRow> goodsShelvesRowList = new ArrayList<>();
        ShelvesReqVo shelvesReqVo = new ShelvesReqVo();
        List<ShelvesRespVo> shelvesRespVoList = shelvesAreaMapper.getShelvesAreaList(shelvesReqVo);

        String area;
        String row;
        GoodsShelvesRow goodsShelvesRow;
        for (ShelvesRespVo shelvesRespVo : shelvesRespVoList) {
            for (Goods goods : goodsList) {
                if(StringUtils.isEmpty(goods.getLocation()) || ",".equals(goods.getLocation()) ||
                        "，".equals(goods.getLocation())){
                    continue;
                }
                if(goods.getLocation().contains(",")){
                    area = goods.getLocation().split(",")[0];
                    row = goods.getLocation().split(",")[1];
                }else{
                    area = goods.getLocation().split("，")[0];
                    row = goods.getLocation().split("，")[1];
                }
                if(area.equals(shelvesRespVo.getShelvesArea())){
                    for (ShelvesRow shelvesRow : shelvesRespVo.getShelvesRowList()) {
                        if(row.equals(shelvesRow.getShelvesRow())){
                            goodsShelvesRow = new GoodsShelvesRow();
                            goodsShelvesRow.setStatus(1);
                            goodsShelvesRow.setVersion(1);
                            goodsShelvesRow.setIsPicking(2);

                            goodsShelvesRow.setShelvesAreaId(shelvesRow.getParentId());
                            goodsShelvesRow.setShelvesRowId(shelvesRow.getId());
                            goodsShelvesRow.setGoodsId(goods.getId());
                            goodsShelvesRowList.add(goodsShelvesRow);
                        }
                    }
                }
            }
        }

        if(!CollectionUtils.isEmpty(goodsShelvesRowList)){
            goodsShelvesRowMapper.insertList(goodsShelvesRowList);
        }
    }

    @Override
    public ResponseEntity<ResultModel> beAlreadyFull(String area, String row) {
        ShelvesRow shelvesRow = shelvesRowMapper.selectAreaRow(area, row);
        if(shelvesRow == null){
            return new ResponseEntity<>(ResultModel.error(SHELVES_AREA_ROW_NULL_ERROR), HttpStatus.OK);
        }
        shelvesRowMapper.updateRowFull(shelvesRow.getId());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 打印二维码
     * @param shelvesArea
     * @param shelvesRowList
     * @param newPath
     */
    private void generateCode(ShelvesArea shelvesArea, List<ShelvesRow> shelvesRowList, String newPath) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        String templatePath = getSystemPath() + "model/StorageCode.pdf";

        //不确定数据有多少，所以要无限循环
        try {
            ByteArrayOutputStream bos;
            // 输出流
            FileOutputStream response = new FileOutputStream(newPath);
            Document document = new Document();

            PdfCopy copy = new PdfCopy(document, response);

            String area = shelvesArea.getShelvesArea();
            int rowNumber = shelvesRowList.size();

            String printArea;

            int b = 1;
            Map<String, Object> dateMap = new HashMap<>();

            while (b <= rowNumber) {

                printArea = area + "区" + shelvesRowList.get(b-1).getShelvesRow() + "排";
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
