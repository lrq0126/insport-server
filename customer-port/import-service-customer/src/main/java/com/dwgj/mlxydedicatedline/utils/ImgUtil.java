package com.dwgj.mlxydedicatedline.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dwgj.mlxydedicatedline.entity.activityReward.ActivityPoster;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ImgUtil {


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

    private static String QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
    private static String CREATE_QRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";


    public static void main(String[] args) {

    }


    /**
     * @param bigImg    大图片路径
     * @param smallImg  大图片路径
     * @param newImg    新图片路径
     * @param bigHeight 小图片在大图片的高度
     * @param bigWidth  小图片在大图片的宽度
     * @throws Exception:
     * @备注：
     */

    public static void posterImage(String bigImg, String smallImg, String newImg, int bigWidth, int bigHeight) throws Exception {
        File file = new File(newImg);
        if (file.exists()) {
            file.delete();
        }
        //读入大图片到内存
//        InputStream is = new FileInputStream(bigImg);
        //创建JPEG解码器
//        JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);
        //获取缓冲数据流
//        BufferedImage bufferImg = jpegDecoder.decodeAsBufferedImage();
        //得到画笔对象
//        Graphics g = bufferImg.getGraphics();
        /*创建附加的小图片对象*/
//        ImageIcon imgIcon = new ImageIcon(smallImg);
        //得到Image对象
//        Image img = imgIcon.getImage();

        //将小图片绘到大图片上,位置信息:(x,y)
//        g.drawImage(img, bigWidth, bigHeight, null);

        //设置字体颜色
//        g.setColor(Color.pink);

        //字体、字体大小
//        Font f = new Font("宋体", Font.BOLD, 50);
//        g.setFont(f);

        //图片上绘制字符串内容,位置信息（x,y）
//        g.drawString(text, height, width);
//        g.dispose();

        //解析内存中的图像数据
//        JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(out);
//        en.encode(bufferImg);

        // 原图
        BufferedImage bufferImg = ImageIO.read(new File(bigImg));
        // 插入图
        BufferedImage insertImg = ImageIO.read(new File(smallImg));

        Graphics2D g = bufferImg.createGraphics();

        g.drawImage(insertImg, bigWidth, bigHeight, insertImg.getWidth(), insertImg.getHeight(),null);

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

}