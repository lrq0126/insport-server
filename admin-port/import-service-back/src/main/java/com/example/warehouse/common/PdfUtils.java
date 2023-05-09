package com.example.warehouse.common;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author lrq
 */
@Configuration
@Slf4j
public class PdfUtils {


    /**
     * 获取项目路径
     * @return
     */
    public static String getSystemPath(){
        return System.getProperty("user.dir")+System.getProperty("file.separator");
    }


    public void pdfout(Map<String,Object> dateMap, int i, String newPath, List<String> loginNameList) throws IOException, DocumentException {


        String templatePath = getSystemPath()+"model/100_1501.pdf";

        String logoPath = getSystemPath()+"model/logo/AW.jpg";

        log.info("getSystemPath():{}",getSystemPath());
        Integer codeHeight = 65;
        log.info("modelFile:{}",templatePath);
        log.info("logoFile:{}",logoPath);
        ByteArrayOutputStream bos;
        // 输出流
        FileOutputStream response = new FileOutputStream(newPath);
        Document document = new Document();

        PdfCopy copy = new PdfCopy(document, response);
        String orderNumber = (String) dateMap.get("orderNumber");
        orderNumber = orderNumber.trim();
        String number = String.valueOf(dateMap.get("number"));
        int b = 0;

        //不确定数据有多少，所以要无限循环
        try {
            while (b <= i) {
                bos= new ByteArrayOutputStream();
                // 读取pdf模板
                PdfReader reader= new PdfReader(templatePath);
                PdfStamper ps= new PdfStamper(reader, bos);
                //回去填充pdf的对象
                AcroFields s = ps.getAcroFields();
                if(i == 1){
                    b=1;
                }
                dateMap.put("logo","");

                if(b == 0){
                    dateMap.put("sum","Total-"+number+" pcs");
                    dateMap.put("number","主单");
                    dateMap.put("picture",orderNumber);
                }else {
                    dateMap.put("sum","Total-"+number+" pcs");
                    dateMap.put("number","第"+b+"/"+number+"件");
                    if(i == 1){
                        dateMap.put("picture",orderNumber);
                    }else {
                        dateMap.put("picture",b+"~"+orderNumber);
                    }

                    if(loginNameList != null && !loginNameList.isEmpty()){
                        dateMap.put("pinOrderNumber",loginNameList.get(b-1));
                    }
                }
                b++;
                for(String key : dateMap.keySet()){




                    if("picture".equals(key)){
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
                                moduleWidth=1.5F;
                            }else if(strLength >=5 && strLength <= 7){
                                Float length = 7.0F/strLength;
                                moduleWidth=1.5F*length;
                            }else if(strLength > 7 && strLength <= 9){
                                Float length = 11.0F/strLength;
                                moduleWidth=1.5F*length;
                            }else if(strLength > 9 && strLength < 14){
                                Float length = 13.0F/strLength;
                                moduleWidth=1.4F*length;
                            }else if(strLength >= 14 && strLength < 18){
                                Float length = 14.0F/strLength;
                                moduleWidth=1.4F*length;
                            }else if(strLength >= 18 && strLength < 22){
                                Float length = 15.0F/strLength;
                                moduleWidth=1.5F*length;
                            } else{
                                Float length = 15.0F/strLength;
                                moduleWidth=1.5F*length;
                            }
                            barcode128.setX(moduleWidth);
                            //条码值
                            barcode128.setCode(value.toString());

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

                    if("logo".equals(key)){
                        //绘制在第b页
                        PdfContentByte cb = ps.getOverContent(1);
                        Image imageLogo = Image.getInstance(logoPath);
                        //设置image对象的输出位置pa.getAsNumber(pa.size()-1).floatValue() 是该页pdf坐标轴的y轴的最大值  0, 0, 841.92, 595.32
                        imageLogo.setAbsolutePosition(5,340);
                        //设置插入的图片大小
                        imageLogo.scaleToFit(100,100);
//                            dateMap.put("logo",imageLogo);
                        cb.addImage(imageLogo);
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
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("生成面单失败");
        }
        copy.close();
        response.close();

    }

    public static void main(String[] args) throws IOException {
//        long yinbi = 65000;
//        long allYinBi = 0;
//        for (int i = 0; i < 30; i++){
//            yinbi += 500;
//            allYinBi += yinbi;
//        }
//        System.out.println("还需要："+allYinBi);

//        Barcode128 barcode128 = new Barcode128();
//        barcode128.setCode("S-D-4,4");
////        java.awt.Image image = barcode128.createAwtImage(null,null);
//        BufferedImage bufferedImage = (BufferedImage) barcode128.createAwtImage(null,null);
//        ImageIO.write(bufferedImage,"jpg",new File("D:\\image\\a.jpg"));
    }

    /**
     * pdf表格字体样式设置
     *
     * @return
     * @throws Exception
     */
    public static Font getPdfChineseFont() throws Exception {
        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
                BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);
        return fontChinese;
    }

    /**
     * 创建文件夹 有则不创建
     *
     * @param filePath
     */
    public void generateFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("创建文件夹");

            file.mkdirs();
        } else {
            System.out.println("文件夹已存在");
            return;
        }
    }

}
