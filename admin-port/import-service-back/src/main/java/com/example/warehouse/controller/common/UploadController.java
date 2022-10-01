package com.example.warehouse.controller.common;

import com.example.warehouse.common.FileUtil;
import com.example.warehouse.entity.AgentOrder;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.Goods;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.sys.Trajectory;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.CustomerPackService;
import com.example.warehouse.service.GoodsService;
import com.example.warehouse.service.UserService;
import com.example.warehouse.service.account.AgentService;
import com.example.warehouse.service.sys.TrajectoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

import static com.example.warehouse.enums.ResultStatus.*;

/**
 * 系统上传相关功能
 */
@Controller
@RequestMapping("/Common/Upload")
@Slf4j
public class UploadController {

    private static final String[] IMG_SUFFIX = {"PNG", "JPG", "JPEG"};

    private static final String[] VIDEO_SUFFIX = {"MP4"};

    private static final String[] APP_SUFFIX = {"APK", "IPA"};

    private static final String[] EXCEL_SUFFIX = {"XLS", "XLSX"};

    private static final String[] LECTURER_SUFFIX = {"PNG", "JPG", "JPEG", "MP3", "MP4", "ZIP", "DOC", "DOCX", "PPT", "PPTX", "PDF"};

    private static final String[] ALL_SUFFIX = LECTURER_SUFFIX;

    @Autowired
    private AgentService agentService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TrajectoryService trajectoryService;

    @Autowired
    private CustomerPackService customerPackService;

    /**
     * 上传文件路径
     */
    @Value("${upload.location}")
    private String UPLOAD_LOCAION;

    /**
     * 成本结算
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadSettleMentExcel",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> uploadSettleMentExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        UploadResult result = new UploadResult();
        String originalFilename = file.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        if (null == suffix || !ArrayUtils.contains(EXCEL_SUFFIX, suffix.toUpperCase())) {
            result.setMessage("文件格式不正确");
            return new ResponseEntity<>(ResultModel.error(FILE_TYPE_ERROR), HttpStatus.OK);
        }
        String [][] excelData;
        excelData = FileUtil.excelToArray(file,5);

        // 验证表格格式是否符合要求
        ExcelCheckMessage excelCheckMessage = new SettleMentExcelTemplate().check(excelData);
        String authorization = request.getHeader("Authorization");
        authorization = authorization.substring(authorization.indexOf("USER"));
        User user = userService.getUserByUserCode(authorization);
        if (excelCheckMessage.getMessage().isEmpty()) {
            int userCount = excelCheckMessage.getObjects().size();

            // 业务流程处理
            int i = agentService.batchImport((List<AgentOrder>) excelCheckMessage.getObjects(),user);

            if ( i < 0 ){
                excelCheckMessage.getMessage().add("成本结算错误");
                return  new ResponseEntity<>(ResultModel.error(FILE_DATA_ERROR,excelCheckMessage), HttpStatus.OK);
            }

            if (excelCheckMessage.getMessage().isEmpty()) {
                return new ResponseEntity<>(ResultModel.ok("成功结算" + userCount + "条代理商成本价!"), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(ResultModel.error(FILE_DATA_ERROR,excelCheckMessage), HttpStatus.OK);
    }

    /**
     * 包裹预录入
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadPreOrderExcel",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> uploadPreOrderExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        UploadResult result = new UploadResult();
        String originalFilename = file.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        if (null == suffix || !ArrayUtils.contains(EXCEL_SUFFIX, suffix.toUpperCase())) {
            result.setMessage("文件格式不正确");
            return new ResponseEntity<>(ResultModel.error(FILE_TYPE_ERROR), HttpStatus.OK);
        }
        String [][] excelData;
        excelData = FileUtil.excelToArray(file,4);

        // 验证表格格式是否符合要求
        ExcelCheckMessage excelCheckMessage = new PreOrderExcelTemplate().check(excelData);
        String authorization = request.getHeader("Authorization");
        authorization = authorization.substring(authorization.indexOf("USER"));
        User user = userService.getUserByUserCode(authorization);
        if (excelCheckMessage.getMessage().isEmpty()) {
            int userCount = excelCheckMessage.getObjects().size();

            // 业务流程处理
            int i = goodsService.batchInsertPreOrder((List<Goods>) excelCheckMessage.getObjects(),user);

            if ( i < 0 ){
                excelCheckMessage.getMessage().add("批量预报出现错误");
                return  new ResponseEntity<>(ResultModel.error(FILE_DATA_ERROR,excelCheckMessage), HttpStatus.OK);
            }

            if (excelCheckMessage.getMessage().isEmpty()) {
                return new ResponseEntity<>(ResultModel.ok("成功预报" + userCount + "条信息!"), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(ResultModel.error(FILE_DATA_ERROR,excelCheckMessage), HttpStatus.OK);
    }

    /**
     * 轨迹导入
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadTrajectoryExcel",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> uploadTrajectoryExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        UploadResult result = new UploadResult();
        String originalFilename = file.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        if (null == suffix || !ArrayUtils.contains(EXCEL_SUFFIX, suffix.toUpperCase())) {
            result.setMessage("文件格式不正确");
            return new ResponseEntity<>(ResultModel.error(FILE_TYPE_ERROR), HttpStatus.OK);
        }
        String [][] excelData;
        excelData = FileUtil.excelToArray(file,5);

        // 验证表格格式是否符合要求
        ExcelCheckMessage excelCheckMessage = new TrajectoryExcelTemplate().check(excelData);
        String authorization = request.getHeader("Authorization");
        authorization = authorization.substring(authorization.indexOf("USER"));
//        User user = userService.getUserByUserCode(authorization);
        if (excelCheckMessage.getMessage().isEmpty()) {
            int userCount = excelCheckMessage.getObjects().size();

            // 业务流程处理
            int i = trajectoryService.batchInsertToTrack((List<Trajectory>) excelCheckMessage.getObjects());

            if ( i < 0 ){
                excelCheckMessage.getMessage().add("批量导入轨迹出现错误");
                return  new ResponseEntity<>(ResultModel.error(FILE_DATA_ERROR,excelCheckMessage), HttpStatus.OK);
            }

            if (excelCheckMessage.getMessage().isEmpty()) {
                return new ResponseEntity<>(ResultModel.ok("成功导入" + userCount + "条轨迹!"), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(ResultModel.error(FILE_DATA_ERROR,excelCheckMessage), HttpStatus.OK);
    }

    /**
     * 转单号批量导入
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadAgentNumberExcel",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> uploadAgentNumberExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        UploadResult result = new UploadResult();
        String originalFilename = file.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        if (null == suffix || !ArrayUtils.contains(EXCEL_SUFFIX, suffix.toUpperCase())) {
            result.setMessage("文件格式不正确");
            return new ResponseEntity<>(ResultModel.error(FILE_TYPE_ERROR), HttpStatus.OK);
        }
        String [][] excelData;
        excelData = FileUtil.excelToArray(file,3);

        // 验证表格格式是否符合要求
        ExcelCheckMessage excelCheckMessage = new AgentNumberExcelTemplate().check(excelData);
        String authorization = request.getHeader("Authorization");
        authorization = authorization.substring(authorization.indexOf("USER"));
        //        User user = userService.getUserByUserCode(authorization);
        if (excelCheckMessage.getMessage().isEmpty()) {
            int userCount = excelCheckMessage.getObjects().size();
            // 业务流程处理
            customerPackService.batchUpdateAgentNumberByOrderNumber((List<CustomerPack>) excelCheckMessage.getObjects(), request);

            if (excelCheckMessage.getMessage().isEmpty()) {
                return new ResponseEntity<>(ResultModel.ok("成功导入" + userCount + "条转单号"), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(ResultModel.error(FILE_DATA_ERROR,excelCheckMessage), HttpStatus.OK);
    }


    /**
     * 公共的图片上传
     */
    @RequestMapping(value = "/uploadImg")
    public UploadResult uploadImg(MultipartFile uploadFile) {
        return saveUploadFile(uploadFile, IMG_SUFFIX);
    }

    /**
     * 上传 excel文件
     *
     * @param uploadFile
     * @return
     */
    @RequestMapping(value = "uploadExcel")
    public UploadResult uploadExcel(MultipartFile uploadFile) {
        return saveUploadFile(uploadFile, EXCEL_SUFFIX);
    }

    private UploadResult saveUploadFile(MultipartFile multipartFile, String[] suffixes){
        UploadResult result = new UploadResult();
//        String originalFilename = multipartFile.getOriginalFilename();
//        String suffix = FileUtil.getSuffix(originalFilename);
//        if (null == suffix || !ArrayUtils.contains(suffixes, suffix.toUpperCase())) {
//            result.setMessage("文件格式不正确");
//        } else {
//            String fileName = FileUtil.buildNewName(originalFilename, SequenceCode.gainSerialNo("DWSM"));
//            String tmpPath = FileUtil.getTempPath(null);
//            String pscPath = FileUtil.getPhysicalPath(tmpPath);
//            FileUtil.exitDirs(pscPath);
//            File file = new File(pscPath + fileName);
//            try {
//                multipartFile.transferTo(file);
//                System.out.println(tmpPath.concat(fileName));
//                result.setPath(filePath);
//                result.setSuccess(true);
//            } catch (Exception  e) {
//                log.error().error(e.getMessage());
//                result.setMessage("保存上传文件失败");
//            }
//        }
        return result;
    }



    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len = 0;
            while ((len = inBuff.read(b)) > 0) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null) {
                inBuff.close();
            }
            if (outBuff != null) {
                outBuff.close();
            }
        }
    }


    /**
     * 批量上传图片
     *
     * @param request request
     */
    @RequestMapping(value = "/batchUploadImg", method = RequestMethod.POST)
    public UploadResult uploadImages(HttpServletRequest request) {
        UploadResult result = new UploadResult();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> multiMap = multipartRequest.getMultiFileMap();
        List<String> paths = new ArrayList<>();
        Set<String> set = multiMap.keySet();
        for (String key : set) {
            List<MultipartFile> fileList = multiMap.get(key);
            for (MultipartFile multipartFile : fileList) {
                UploadResult singleFile = saveUploadFile(multipartFile, IMG_SUFFIX);
                if (singleFile.isSuccess()) {
                    paths.add(singleFile.getPath());
                } else {
                    result.setMessage(singleFile.getMessage());
                    return result;
                }
            }
        }
        result.setSuccess(true);
        result.setPaths(paths);
        return result;
    }

    /**
     *
     * @param base64Data 图片
     * @param isSigned 是否已经签收
     * @param isDesign 是否拒收的包裹
     * @return
     */
    @PostMapping("/base64Upload")
    @ResponseBody
    public ResponseEntity<ResultModel> uploadImage(String base64Data,int isSigned,int isDesign ,String deliveryOrderNo){
        log.info("==上传图片==");

        String dataPrix = ""; //base64格式前头
        String data = "";//实体部分数据
        if(base64Data==null||"".equals(base64Data)){
            return new ResponseEntity<>(ResultModel.error(NOT_NULL), HttpStatus.OK);
        }else {
            String [] d = base64Data.split("base64,");//将字符串分成数组
            if(d != null && d.length == 2){
                dataPrix = d[0];
                data = d[1];
            }else {
                return new ResponseEntity<>(ResultModel.error(FILE_TYPE_ERROR), HttpStatus.OK);
            }
        }
        String suffix = "";//图片后缀，用以识别哪种格式数据
        //data:image/jpeg;base64,base64编码的jpeg图片数据
        if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){
            suffix = ".jpg";
        }else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){
            //data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        }else if("data:image/gif;".equalsIgnoreCase(dataPrix)){
            //data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        }else if("data:image/png;".equalsIgnoreCase(dataPrix)){
            //data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }else {
            return new ResponseEntity<>(ResultModel.error(FILE_TYPE_ERROR), HttpStatus.OK);
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String tempFileName=uuid+suffix;
        String imgFilePath = UPLOAD_LOCAION + "/" +tempFileName;//新生成的图片
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(data);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0) {
                    //调整异常数据
                    b[i]+=256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            System.out.println("imgFilePath:"+imgFilePath);
            // 暂时先写死域名路径
            String imgurl="https://taomao138.com/images/"+tempFileName;
            System.out.println("imgurl:"+imgurl);
            //imageService.save(imgurl);
//            return "200";
            return new ResponseEntity<>(ResultModel.ok(imgurl), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
//            return "上传图片失败401";
            return new ResponseEntity<>(ResultModel.error(FILE_DATA_ERROR), HttpStatus.OK);
        }

    }

    public static void main(String[] args) throws Exception {
//        String[] datas = BarcodeScanner.scan("C:\\Users\\10375\\Desktop\\83d51a5f30acc33c4a393503e19adf8.jpg",BarCodeType.Code_128);
//        System.out.println("图片识别条形码结果："+datas[0]);
    }
}
