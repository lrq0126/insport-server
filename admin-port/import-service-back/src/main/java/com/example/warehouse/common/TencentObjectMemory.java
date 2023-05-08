package com.example.warehouse.common;

import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.entity.image.Images;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@Slf4j
public class TencentObjectMemory {

    private static String APPID;
    private static String SECRET_ID;
    private static String SECRET_KEY;
    private static String BUCKET_NAME;
//    private static String APPID = "1259586164";
//    private static String SECRET_ID = "AKIDFI6duxkHXWDdmeFaeRyGJ5aomUx96mBJ";
//    private static String SECRET_KEY = "tVquhEUTUMyJNAiz0SiikVzDAqFOHsCG";
//    private static String BUCKET_NAME = "flycloud";
    /**
     * 上传文件路径
     */
    private static String UPLOAD_LOCATION;

    /**
     * 上传文件路径
     */
    @Value("${upload.location}")
    public void setUploadLocation(String uploadLocation) {
        TencentObjectMemory.UPLOAD_LOCATION = uploadLocation;
    }

    @Value("${tencent.appid}")
    public void setAppid(String appId) {
        TencentObjectMemory.APPID = appId;
    }
    @Value("${tencent.secret-id}")
    public void setSecretId(String secretId) {
        TencentObjectMemory.SECRET_ID = secretId;
    }
    @Value("${tencent.secret-key}")
    public void setSecretKey(String secretKey) {
        TencentObjectMemory.SECRET_KEY = secretKey;
    }
    @Value("${tencent.bucket-name}")
    public void setBucketName(String bucketName) {
        TencentObjectMemory.BUCKET_NAME = bucketName;
    }

    public static COSClient cosClient(){
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    public static PutObjectResult putObjectResult(String bucketName, String key, File localFile){
        COSClient cosClient = cosClient();
        // 指定要上传的文件
//        File localFile = new File("D:\\Futari No Hanabi.png");
        // 指定文件将要存放的存储桶
//        String bucketName = "fly-"+APPID;
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
//        String key = "test/Futari No Hanabi.png";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return putObjectResult;
    }

    public static Images uploadObject(String bucketName, String type, MultipartFile multipartFile) throws IOException {
        String imageName = multipartFile.getOriginalFilename();
//        String suffix = FileUtil.getSuffix2(originalFilename);
//        String imageName = originalFilename;
        // 去掉后缀中包含的.png字符串
        if(imageName.contains(".png")){
            imageName = imageName.replace(".png", ".jpg");
        }
        String date = DateUtil.FormatDate(new Date(), "yyyy-MM");
        // key ☞指在对象存储器中的桶的路径
        String key = type + "/" + date + "/" + imageName;

        String outPathFile = UPLOAD_LOCATION + "/template/orc/" +imageName;
        // 先把文件存储到本地服务器，在进行上传
        File file = new File(outPathFile);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        InputStream inputStream = multipartFile.getInputStream();
        IOUtils.copyLarge(inputStream, fileOutputStream);
        inputStream.close();
        fileOutputStream.close();

        bucketName = bucketName + "-" + APPID;
        PutObjectResult putObjectResult = putObjectResult(bucketName, key, file);

        System.out.println("toJSONString：" + JSONObject.toJSONString(putObjectResult));
        // 操作完成删除图片
        file.delete();

//        "https://flycloud-1253561272.cos.ap-guangzhou.myqcloud.com";
        String fileUrl = "https://" + bucketName + ".cos.ap-guangzhou.myqcloud.com/"+key;
        Images images = new Images();

        images.setImageName(imageName);
        images.setPicType(type);

        images.setPicUrl(fileUrl);
        images.setImageSize(String.valueOf(multipartFile.getSize()));
        images.setSortNo(1);
        images.setPath(file.getPath());
        images.setCreateTime(new Date());
        images.setStatus(1);

        return images;
    }

    public static Images uploadObject(String bucketName, String type, File uploadFile) throws IOException {
        String imageName = uploadFile.getName();
//        // 去掉后缀中包含的.png字符串
        if(imageName.contains(".png")){
            imageName = imageName.replace(".png", ".jpg");
        }

//        String date = DateUtil.FormatDate(new Date(), "yyyy-MM");
        // key ☞指在对象存储器中的桶的路径
        String key = type + "/" + imageName;

        bucketName = bucketName + "-" + APPID;
        PutObjectResult putObjectResult = putObjectResult(bucketName, key, uploadFile);

        System.out.println("toJSONString：" + JSONObject.toJSONString(putObjectResult));
        // 操作完成删除图片
        uploadFile.delete();

        String fileUrl = "https://" + bucketName + ".cos.ap-guangzhou.myqcloud.com/"+key;
        Images images = new Images();

        images.setImageName(imageName);
        images.setPicType(type);

        images.setPicUrl(fileUrl);
        images.setSortNo(1);
        images.setPath(fileUrl);
        images.setCreateTime(new Date());
        images.setStatus(1);

        return images;
    }

    // 删除腾讯云对象存储文件夹：1、确定需要删除的文件夹；2、删除文件夹下的所有文件对象
    public static void deleteObject(String type, List<String> imageNameList){
        log.info("------>>>>>开始删除腾讯云文件<<<<<------");

        COSClient cosClient = cosClient();
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = BUCKET_NAME + "-" + APPID;

        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<>();
        for (String imageName : imageNameList) {
            keyList.add(new DeleteObjectsRequest.KeyVersion(imageName));
        }
        deleteObjectsRequest.setKeys(keyList);
        try {
            DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
            List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
            System.out.println("文件删除成功");
        } catch (MultiObjectDeleteException mde) {
            // 如果部分删除成功部分失败, 返回 MultiObjectDeleteException
            List<DeleteObjectsResult.DeletedObject> deleteObjects = mde.getDeletedObjects();
            System.out.println("部分文件删除失败：" + deleteObjects);
            List<MultiObjectDeleteException.DeleteError> deleteErrors = mde.getErrors();
            System.out.println("部分文件删除失败，原因：" + deleteErrors);
        } catch (CosClientException e) {
            e.printStackTrace();
        }

// 确认本进程不再使用 cosClient 实例之后，关闭之
        cosClient.shutdown();
        log.info("------>>>>>删除腾讯云文件夹完毕<<<<<------");
    }

    // 删除腾讯云对象存储文件夹：1、确定需要删除的文件夹；2、删除文件夹下的所有文件对象
    public static void timingDeleteObject(){
        log.info("------>>>>>开始删除腾讯云文件夹<<<<<------");
        List<String> retainMonth = retainMonth();
        // 删除入库图片
        deleteObjectDir("GoodsInWarePicture/", retainMonth);
        // 删除订单图片
        deleteObjectDir("CustomerPackPicture/", retainMonth);
        log.info("------>>>>>删除腾讯云文件夹完毕<<<<<------");
    }

    private static void deleteObjectDir(String prefix, List<String> retainMonth){
        COSClient cosClient = cosClient();
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = BUCKET_NAME + "-" + APPID;
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置bucket名称
        listObjectsRequest.setBucketName(bucketName);
        // prefix表示列出的object的key以prefix开始
        listObjectsRequest.setPrefix(prefix);
        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter("/");
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(10);
        // 所有文件夹对象
        ObjectListing allObjectDirs = null;
        // 确定需要删除的文件夹信息
        List<String> delObjectDirs = new ArrayList<>();
        do {
            try {
                allObjectDirs = cosClient.listObjects(listObjectsRequest);
            } catch (CosClientException e) {
                e.printStackTrace();
                return;
            }
            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
            List<String> commonPrefixs = allObjectDirs.getCommonPrefixes();
            for (String commonPrefix : commonPrefixs) {
                String filesName = commonPrefix.split(prefix)[1];
                if(!retainMonth.contains(filesName)){
                    System.out.println("需要删除的文件夹："+ prefix+filesName);
                    delObjectDirs.add(prefix+filesName);
                }
            }
        } while (allObjectDirs.isTruncated());


        // 如果要删除的对象不为空，则进行删除操作
        if(!CollectionUtils.isEmpty(delObjectDirs)){
            for (String delObjectDir : delObjectDirs) {
                listObjectsRequest = new ListObjectsRequest();
                // 设置bucket名称
                listObjectsRequest.setBucketName(bucketName);
                // prefix表示列出的object的key以prefix开始
                listObjectsRequest.setPrefix(delObjectDir);
                // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
                listObjectsRequest.setDelimiter("/");
                // 设置最大遍历出多少个对象, 一次listobject最大支持1000
                listObjectsRequest.setMaxKeys(1000);

                // 保存每次列出的结果
                ObjectListing objectList = null;

                do {
                    try {
                        objectList = cosClient.listObjects(listObjectsRequest);
                    } catch (CosClientException e) {
                        e.printStackTrace();
                        return;
                    }
//                    // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
//                    List<String> commonPrefixs = objectList.getCommonPrefixes();
//                    for (String commonPrefix : commonPrefixs) {
//                        System.out.println("需要删除的文件：" + commonPrefix);
//                    }
                    // 这里保存列出的对象列表
                    List<COSObjectSummary> cosObjectSummaries = objectList.getObjectSummaries();

                    ArrayList<DeleteObjectsRequest.KeyVersion> delObjects = new ArrayList<>();

                    for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                        delObjects.add(new DeleteObjectsRequest.KeyVersion(cosObjectSummary.getKey()));
                    }

                    DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);

                    deleteObjectsRequest.setKeys(delObjects);

                    try {
                        DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
                        List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
                    } catch (MultiObjectDeleteException mde) {
                        // 如果部分删除成功部分失败, 返回 MultiObjectDeleteException
                        List<DeleteObjectsResult.DeletedObject> deleteObjects = mde.getDeletedObjects();
                        System.out.println("deleteObjects:" + JSONObject.toJSONString(deleteObjects));
                        List<MultiObjectDeleteException.DeleteError> deleteErrors = mde.getErrors();
                        System.out.println("deleteErrors:" + JSONObject.toJSONString(deleteErrors));
                    } catch (CosClientException e) {
                        e.printStackTrace();
                        return;
                    }
                    // 标记下一次开始的位置
                    String nextMarker = objectList.getNextMarker();
                    listObjectsRequest.setMarker(nextMarker);

                }while (objectList.isTruncated());

            }
        }
        cosClient.shutdown();
    }

    private static List<String> retainMonth(){
        List<String> retainMonthList = new ArrayList<>();
        for (int i = 0; i < 3; i ++){
            // 按 年-月 间隔存放海报内容，每隔一个月清理一次海报图片
            String month = DateUtil.getDateFormate(new Date(), "MM");
            String year = DateUtil.getDateFormate(new Date(), "yyyy");
            int mon = Integer.parseInt(month) - i;
            if(mon < 10){
                month = String.valueOf("0"+mon);
            }else {
                month = String.valueOf(mon);
            }
            String dateStr = year + "-" + month;
            System.out.println("保留的月份：" + dateStr);
            retainMonthList.add(dateStr + "/");
        }

        return retainMonthList;
    }

}
