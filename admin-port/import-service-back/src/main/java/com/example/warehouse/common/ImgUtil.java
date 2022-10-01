package com.example.warehouse.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.entity.activityReward.ActivityPoster;
import com.example.warehouse.entity.image.ImageType;
import com.example.warehouse.entity.image.Images;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ImgUtil {


    /**
     * 上传文件路径
     */
    private static String UPLOAD_LOCATION;
    /**
     * 系统域名
     */
    private static String DOMAIN;

    /**
     * 上传文件路径
     */
    @Value("${upload.location}")
    public void setUploadLocation(String uploadLocation) {
        ImgUtil.UPLOAD_LOCATION = uploadLocation;
    }

    @Value("${server.domain}")
    public void setDomain(String domain) {
        ImgUtil.DOMAIN = domain;
    }

    private static String QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
    private static String CREATE_QRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

    public static void main(String[] args) {

    }


    /**
     * @param bigImg    大图片路径
     * @param smallImg  大图片路径
     * @param newImg    新图片路径
     * @param bigWidth  小图片在大图片的宽度
     * @param bigHeight 小图片在大图片的高度
     * @throws Exception:
     * @备注：
     */

    public static void posterImage(String bigImg, String smallImg, String newImg, int bigWidth, int bigHeight) throws Exception {

        File file = new File(newImg);
        // 如果这个图片已经存在，则删除旧的
        if (file.exists()) {
            file.delete();
        }
        // 原图
        BufferedImage bufferImg = ImageIO.read(new File(bigImg));
        // 插入图
        BufferedImage insertImg = ImageIO.read(new File(smallImg));

        Graphics2D g = bufferImg.createGraphics();

        g.drawImage(insertImg, bigWidth, bigHeight, insertImg.getWidth(), insertImg.getHeight(), null);

        //输出新图片
        OutputStream out = new FileOutputStream(newImg);

        ImageIO.write(bufferImg, "jpg", out);
        //关闭流
//        is.close();
//        out.close();
    }

    public static void posterImage(ActivityPoster activityPoster, String smallImg, String newImg) throws Exception {

        File file = new File(newImg);
        // 如果这个图片已经存在，则删除旧的
        if (file.exists()) {
            file.delete();
        }
        //输出新图片
        OutputStream out = new FileOutputStream(newImg);
        // 原图
        BufferedImage bufferImg = ImageIO.read(new File(activityPoster.getLocationPath()));

        // 是否需要插入二维码
        if(activityPoster.getIsPlaceQrcode() == 1){
            // 插入图
            BufferedImage insertImg = ImageIO.read(new File(smallImg));
            Graphics2D graphics2D = bufferImg.createGraphics();
            Integer widthPosition = activityPoster.getWidthPosition() == null ? 0 : activityPoster.getWidthPosition();
            Integer heightPosition = activityPoster.getHeightPosition() == null ? 0 : activityPoster.getHeightPosition();
            graphics2D.drawImage(insertImg, widthPosition, heightPosition,
                    insertImg.getWidth(), insertImg.getHeight(), null);

        }

        ImageIO.write(bufferImg, "jpg", out);
    }

    public static boolean imageSetW_H(Image img, String outputFilePath_Name, double multiple) {
        try {
            int width = (int) (img.getWidth(null) * multiple);
            int height = (int) (img.getWidth(null) * multiple);
            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = buffImg.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            g.drawImage(img, 0, 0, width, height, null);
            g.dispose();
            //建立输出文件对象
            File file = new File(outputFilePath_Name);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
//            encoder.encode(buffImg);
            ImageIO.write(buffImg, "jpg", fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("压缩异常");
            return false;
        }
    }

    /**
     * 生成二维码
     *
     * @param content
     * @param file
     */
    private static void generateQrcode(String content, File file) {
        try {
            // 定义二维码参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            // 设置编码
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 设置容错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            // 设置边距，默认为5
            hints.put(EncodeHintType.MARGIN, 2);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 200, 200, hints);
            Path path = file.toPath();
            // 保存到项目跟目录中
            MatrixToImageWriter.writeToPath(bitMatrix, "png", path);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成微信二维码
     *
     * @param QRPath      二维码存放文件夹
     * @param accessToken 微信密匙
     * @param content     二维码内容
     * @param qrcodePath  二维码地址
     * @param multiple    二维码缩放大小
     */
    public static void generateWeChatQrcode(String QRPath, String accessToken, String content, String qrcodePath, double multiple) throws Exception {
        File QRFiles = new File(QRPath);
        if (!QRFiles.exists()) {
            QRFiles.mkdirs();
        }

        // 生成微信分享二维码
        String data = "{" +
                "\"action_name\": \"QR_LIMIT_STR_SCENE\"," +
                "\"action_info\":{\"scene\": {\"scene_str\": \"" + content + "\"}" +
                "}";
        HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
        String ticketRespStr = httpUtilManager.post(QRCODE_URL + accessToken, data);
        JSONObject json = JSON.parseObject(ticketRespStr);
        String respTicket = json.getString("ticket");

        // 获取到图片，并进行存储
        URL url = new URL(CREATE_QRCODE_URL + respTicket);
        Image image = ImageIO.read(url);

        // 处理图片大小
        imageSetW_H(image, qrcodePath, multiple);

    }


    // 图片上传保存
    public static Images imagesUpload(String filesName, String systemName, MultipartFile multipartFiles) throws IOException {
        // 上传文件的地址
        String filePath = UPLOAD_LOCATION + "/template/orc/"
                + systemName + "/" + filesName + "/"  + DateUtil.getDateFormate(new Date(), "yyyy-MM");

        File files = new File(filePath);
        if (files.getParentFile() != null) {
            //创建文件
            files.mkdirs();
        }

        int i = 0;

        String originalFilename = multipartFiles.getOriginalFilename();
        String suffix = FileUtil.getSuffix2(originalFilename);
        String fileSize = multipartFiles.getSize() + "";

        String imageName = SequenceCode.gainSerialNo("IMG_") + suffix;

        // 去掉后缀中包含的.png字符串
        if(imageName.contains(".png")){
            imageName = imageName.replace(".png", ".jpg");
        }

        String outPathFile = filePath +"/" +imageName;

        // 创建文件路径
        File file = new File(outPathFile);
//        File file = new File("D:\\" + outPathFile);
//        try {
//            multipartFiles.transferTo(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if(file.exists()){
            file.delete();
        }
        InputStream inputStream = multipartFiles.getInputStream();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        IOUtils.copyLarge(inputStream, fileOutputStream);
        inputStream.close();
        fileOutputStream.close();

        long size = multipartFiles.getSize();
        double scale = 1.0d;
        // 修改
        if (size > 300 * 1024) {
            scale = (300 * 1024f) / size;
        }
        if (size > 300 * 1024) {
            Thumbnails.of(outPathFile).scale(1f).outputQuality(scale).outputFormat("jpg").toFile(outPathFile);
        } else {
            Thumbnails.of(outPathFile).scale(1f).outputFormat("jpg").toFile(outPathFile);
        }

        String picurl = DOMAIN + "/" + file.getPath().substring(file.getPath().indexOf("upload"));
//        String picurl = file.getPath().substring(file.getPath().indexOf("upload"));
        picurl = picurl.replaceAll("\\\\", "/");
        Images images = new Images();

        images.setImageName(originalFilename);
        images.setPicType(filesName);

        images.setPicUrl(picurl);
        images.setImageSize(fileSize);
        images.setSortNo(i);
        images.setPath(file.getPath());
        images.setCreateTime(new Date());
        images.setStatus(1);

        return images;
    }


    /**
     * 处理文件
     * @param files
     * @param posterName
     * @return
     * @throws IOException
     */
    public static String imageProcess(String files, String posterName) throws IOException {
//        log.info("插入海报base图片: {}", posterName);
        int index = files.indexOf(",");
        int index2 = files.indexOf("/");
        int index3 = files.indexOf(";");
        String type = files.substring(index2+1,index3);
        files = files.substring(index+1);
        /**用base64解码，decode方法将其转成图片*/
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(files.replace("\r\n", ""));

        String posterPath = UPLOAD_LOCATION + "/template/poster/basePoster";

        /**指定保存的路径*/
        File path = new File(posterPath);
        if(!path .exists()) {
            path.mkdirs();
        }
        String filesName = posterName+"."+type;
        File basePoster = new File(posterPath + "/" + filesName);
        // 如果这个名称的图片存在，则删除
        if(basePoster.exists()){
            basePoster.delete();
        }
        FileOutputStream outputStream = new FileOutputStream(basePoster);
        outputStream.write(bytes);
        outputStream.close();

        return posterPath+ "/" +filesName;
    }
}