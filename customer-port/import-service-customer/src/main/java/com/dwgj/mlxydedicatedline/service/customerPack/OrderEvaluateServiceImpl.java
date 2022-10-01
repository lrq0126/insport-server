package com.dwgj.mlxydedicatedline.service.customerPack;

import com.dwgj.mlxydedicatedline.commons.SequenceCode;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.CustomerPack;
import com.dwgj.mlxydedicatedline.entity.customerPack.OrderEvaluate;
import com.dwgj.mlxydedicatedline.entity.image.ImageType;
import com.dwgj.mlxydedicatedline.entity.image.Images;
import com.dwgj.mlxydedicatedline.enums.customerPack.PackTypeEnums;
import com.dwgj.mlxydedicatedline.mapper.CustomerPackMapper;
import com.dwgj.mlxydedicatedline.mapper.customerPack.OrderEvaluateMapper;

import com.dwgj.mlxydedicatedline.mapper.image.ImagesMapper;
import com.dwgj.mlxydedicatedline.resultType.PageData;
import com.dwgj.mlxydedicatedline.resultType.PageHelp;
import com.dwgj.mlxydedicatedline.resultType.PageResultModel;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.utils.DateUtil;
import com.dwgj.mlxydedicatedline.utils.FileUtil;
import com.dwgj.mlxydedicatedline.vo.orderEvaluate.OrderEvaluateReqVo;
import com.dwgj.mlxydedicatedline.vo.orderEvaluate.OrderEvaluateRespVo;
import com.dwgj.mlxydedicatedline.vo.pack.CustomerPackRespVo;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.ERROR;
import static com.dwgj.mlxydedicatedline.enums.ResultStatus.FILE_MAX;
import static com.dwgj.mlxydedicatedline.enums.ResultStatus.SYS_ERROR;

@Service
@Slf4j
public class OrderEvaluateServiceImpl implements OrderEvaluateService {

    @Autowired
    private OrderEvaluateMapper orderEvaluateMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private ImagesMapper imagesMapper;

    // 域名；IP地址
    @Value("${server.domain}")
    private String domain;
    @Value("${upload.location}")
    private String UPLOAD_LOCATION;
    @Value("${upload.root}")
    private String Upload_Root;

    @Override
    public ResponseEntity<PageResultModel> getEvaluateList(OrderEvaluateReqVo orderEvaluateReqVo) {
        PageData pageData = PageHelp.editPage(orderEvaluateReqVo.getPageNumber().toString(),
                orderEvaluateReqVo.getPageSize().toString());
        Map<String, Object> pageResult = new HashMap<>();
        List<OrderEvaluateRespVo> resultList = new ArrayList<>();

        // evaluateStatus 根据评价状态查询数据，1是查询已评价 2是未评价
        if(orderEvaluateReqVo.getEvaluateStatus() == 1){
            int total = orderEvaluateMapper.getEvaluateCount(orderEvaluateReqVo);
            pageResult.put("total", total);
            pageResult.put("number", pageData.getNumber());
            int totalPages = 1;

            if(total < 1){
                pageResult.put("totalPages", totalPages);
                return new ResponseEntity<>(PageResultModel.ok(resultList, pageResult), HttpStatus.OK);
            }

            double ceil = (double) total / (double) orderEvaluateReqVo.getPageSize();
            totalPages = (int) Math.ceil(ceil);
            pageResult.put("totalPages", totalPages);

            orderEvaluateReqVo.setPageNumber(pageData.getPageNumber());

            List<OrderEvaluateRespVo> orderEvaluateList = orderEvaluateMapper.getEvaluateList(orderEvaluateReqVo);
            resultList.addAll(orderEvaluateList);
        }

        if(orderEvaluateReqVo.getEvaluateStatus() == 2){
            List<OrderEvaluateRespVo> orderEvaluateList = orderEvaluateMapper.getEvaluateList(orderEvaluateReqVo);
            List<Integer> evaluateOrderIdList = orderEvaluateList.stream().map(OrderEvaluateRespVo::getPackId).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(evaluateOrderIdList)){
                evaluateOrderIdList = null;
            }
            List<CustomerPackRespVo> customerPackList
                    = customerPackMapper.getUnEvaluatePackByCustomer(orderEvaluateReqVo.getCustomerId(), evaluateOrderIdList);

            if(!CollectionUtils.isEmpty(customerPackList)){
                List<OrderEvaluateRespVo> waitEvaluateOrderList = new ArrayList<>();
                for (CustomerPackRespVo customerPackRespVo : customerPackList) {

                    OrderEvaluateRespVo orderEvaluate = new OrderEvaluateRespVo();

                    orderEvaluate.setOrderNumber(customerPackRespVo.getOrderNumber());
                    orderEvaluate.setPackId(customerPackRespVo.getId());
                    orderEvaluate.setCustomerId(customerPackRespVo.getCustomerId());

                    orderEvaluate.setRouteName(customerPackRespVo.getRouteName());
                    orderEvaluate.setActualPrice(customerPackRespVo.getActualPrice());
                    orderEvaluate.setOrderTime(customerPackRespVo.getCreateTime());
                    orderEvaluate.setReceiptTime(customerPackRespVo.getReceiptTime());
                    orderEvaluate.setBusinessNumber(customerPackRespVo.getBusinessNumber());

                    waitEvaluateOrderList.add(orderEvaluate);
                }
                resultList.addAll(waitEvaluateOrderList);
            }
        }

        return new ResponseEntity<>(PageResultModel.ok(resultList, pageResult), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> saveEvaluate(OrderEvaluate orderEvaluate, MultipartFile[] multipartFiles) {
        List<Images> imagesList = new ArrayList<>(3);
        try {
            ResponseEntity<ResultModel> responseEntity = imagesUpload(multipartFiles, imagesList);
            if (responseEntity == null) {
                orderEvaluate.setCreateTime(DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
                orderEvaluate.setStatus(1);
                orderEvaluateMapper.insertSelective(orderEvaluate);

                if (!imagesList.isEmpty()) {
                    imagesList.forEach(images -> {
                        images.setContentId(orderEvaluate.getOrderNumber());
                        images.setPicType(ImageType.OrderEvaluatePicture.toString());
                    });
                    imagesMapper.insertImagesList(imagesList);
                }

                return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);

            }
            return responseEntity;
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, "图片上传错误"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> getEvaluateByPackId(int packId) {
        OrderEvaluateRespVo orderEvaluate = orderEvaluateMapper.selectEvaluateByPackId(packId);

        if(orderEvaluate == null){
            CustomerPackRespVo customerPack = customerPackMapper.selectPackByPackId(packId);
            orderEvaluate = new OrderEvaluateRespVo();

            orderEvaluate.setCreateId(customerPack.getCustomerId());
            orderEvaluate.setStatus(0);
            orderEvaluate.setVersion(1);

            orderEvaluate.setPackId(customerPack.getId());
            orderEvaluate.setOrderNumber(customerPack.getOrderNumber());
            orderEvaluate.setCustomerId(customerPack.getCustomerId());
            orderEvaluate.setCustomerName(customerPack.getCustomerName());
            orderEvaluate.setRouteName(customerPack.getRouteName());
            orderEvaluate.setOutStorageTime(customerPack.getOutStorageTime());
            orderEvaluate.setTransportationTime(customerPack.getTransportationTime());

            orderEvaluate.setActualPrice(customerPack.getActualPrice());
            orderEvaluate.setOrderTime(customerPack.getCreateTime());
            orderEvaluate.setReceiptTime(customerPack.getReceiptTime());
        }
//        else {
//            Images images = new Images();
//            images.setPicType(ImageType.OrderEvaluatePicture.toString());
//            images.setContentId(orderEvaluate.getOrderNumber());
//            List<Images> imagesList = imagesMapper.getImagesByContentId(images);
//            orderEvaluate.setImages(imagesList);
//        }

        return new ResponseEntity<>(ResultModel.ok(orderEvaluate), HttpStatus.OK);
    }


    // 图片上传保存
    private ResponseEntity<ResultModel> imagesUpload(MultipartFile[] multipartFiles, List<Images> imagesList) throws IOException {
        // 上传文件的地址
        String filePath = UPLOAD_LOCATION + "/" +ImageType.OrderEvaluatePicture.toString() + "/" + DateUtil.getYMStr();

        File files = new File(filePath);
        if (files.getParentFile() != null) {
            //创建文件
            files.mkdirs();
        }

        int i = 0;
        for (MultipartFile f : multipartFiles) {

            if (f.getSize() >= 10*1024*1024) {
                System.out.println("文件不能大于10M");
            }

            String originalFilename = f.getOriginalFilename();
            String suffix = FileUtil.getSuffix2(originalFilename);
            String fileSize = f.getSize() + "";

            if (f.getSize() > FileUtil.IMAGE_MAX_SIZE) {
                return new ResponseEntity<>(ResultModel.error(FILE_MAX), HttpStatus.OK);
            }
            String imageName = SequenceCode.gainSerialNo("IMG_") + suffix;

            // 创建文件路径
            File file = FileUtil.buildFileName(ImageType.OrderEvaluatePicture.toString(), imageName);

            try {
                f.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
            }
            String picurl = domain + "/" + file.getPath().substring(file.getPath().indexOf("upload"));
            picurl = picurl.replaceAll("\\\\", "/");


            pictureZip(f, picurl, imageName);

            Images images = new Images();
            images.setImageName(originalFilename);
            images.setPicType(ImageType.OrderEvaluatePicture.toString());


            images.setPicUrl(picurl);
            images.setImageSize(fileSize);
            images.setSortNo(i);
            images.setPath(file.getPath());
            images.setCreateTime(new Date());
            images.setStatus(1);
            imagesList.add(images);
            i++;
        }
        return null;
    }

    /**
     *
     * @param imageFile 输入文件
     * @param outPath 输出文件完整路径
     * @param outFileName 输出文件名称
     */
    private void pictureZip(MultipartFile imageFile, String outPath, String outFileName) {

        // 去掉后缀中包含的.png字符串
        if(outFileName.contains(".png")){
            outFileName = outFileName.replace(".png", ".jpg");
        }

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        // 输出文件完整路径
        String outPathFile = outPath + "/" + outFileName;

        File file = new File(outPathFile);
        if(file.exists()){
            file.delete();
        }
        try {
            inputStream = imageFile.getInputStream();
            fileOutputStream = new FileOutputStream(outPathFile);
            IOUtils.copyLarge(inputStream, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long size = imageFile.getSize();
        double scale = 1.0d ;
        // 修改
        if(size > 500*1024){
            scale = (500*1024f) / size ;
        }

        try {
            if(size > 500*1024){
                Thumbnails.of(outPathFile).scale(1f).outputQuality(scale).outputFormat("jpg").toFile(outPathFile);
            }else {
                Thumbnails.of(outPathFile).scale(1f).outputFormat("jpg").toFile(outPathFile);
            }
        } catch (Exception e1) {
           e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        String outFileName = "BBBBB.png";
//        String outPath = "D:\\App\\upload\\OrderEvaluatePicture\\202201";
//        File file = new File("D:\\BBBBB.jpg");
//        try {
//            InputStream inputStream = new FileInputStream(file);
//            MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
//            pictureZip(multipartFile, outPath, outFileName);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
    }

}
