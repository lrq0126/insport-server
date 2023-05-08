package com.example.warehouse.service.activityReward;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.ImgUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.activityReward.ActivityPoster;
import com.example.warehouse.mapper.activityReward.ActivityPosterMapper;
import com.example.warehouse.mapper.user.WxAccessTokenMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.activity.ActivityPosterReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.warehouse.enums.ResultStatus.*;


/**
* 活动海报(ActivityPoster)表服务实现类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/3/23 11:18
*/
@Service("activityPosterService")
@Slf4j
public class ActivityPosterServiceImpl implements ActivityPosterService {

    @Autowired
    private ActivityPosterMapper activityPosterMapper;
    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;
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

    @Override
    public ResponseEntity<PageResultModel> getActivityPosterList(ActivityPosterReqVo activityPosterReqVo) {
        PageData pageData = PageHelp.editPage(activityPosterReqVo);
        int count = activityPosterMapper.selectPosterCount(activityPosterReqVo);
        pageData.setTotal(count);
        if(count > 0){
            activityPosterReqVo.setPageNumber(pageData.getPageNumber());
            List<ActivityPoster> activityPosterList = activityPosterMapper.getActivityPosterList(activityPosterReqVo);
            return new ResponseEntity<>(PageResultModel.ok(activityPosterList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getActivityPoster(int id) {
        ActivityPoster activityPoster = activityPosterMapper.selectByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(activityPoster), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveActivityPoster(ActivityPoster activityPoster) {
        try{
            if(activityPoster.getFiles() == null){
                return new ResponseEntity<>(ResultModel.error(PLEASE_ADD_POSTER_PICTURE), HttpStatus.OK);
            }
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if(user == null){
                return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
            }

            if(activityPoster.getIsChangImg() == 1){
                // 图片上传处理，并返回相关路径
                String posterPath = ImgUtil.imageProcess(activityPoster.getFiles(), activityPoster.getPosterName());
                activityPoster.setLocationPath(posterPath);
                posterPath = "http://" + DOMAIN + posterPath.substring(4, posterPath.length());
                activityPoster.setPosterUrl(posterPath);
            }

            if(activityPoster.getId() == null){
                ActivityPoster enablePoster = activityPosterMapper.selectEnablePoster(activityPoster.getPosterType());
                // 如果当前类型的海报没有启动的海报，则将新增的海报设置为启用状态
                if(enablePoster == null){
                    activityPoster.setIsEnable(1);
                }else {
                    activityPoster.setIsEnable(-1);
                }

                activityPoster.setStatus(1);
                activityPoster.setVersion(1);
                activityPoster.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                activityPoster.setCreateId(user.getId());

                activityPosterMapper.insertSelective(activityPoster);
                return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
            }else{
                activityPoster.setStatus(1);
                activityPosterMapper.updateByPrimaryKey(activityPoster);
                return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<ResultModel> checkPosterModel(String posterType) {
        ActivityPoster activityPoster = activityPosterMapper.selectEnablePoster(posterType);
        if(activityPoster == null){
            return new ResponseEntity<>(ResultModel.error(IS_NOT_ENABLE_POSTER), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> generatePoster(int id) {
        try{
            ActivityPoster activityPoster = activityPosterMapper.selectByPrimaryKey(id);
            // 按 年-月 间隔存放海报内容，每隔一个月清理一次海报图片
            String dateStr = DateUtil.getDateFormate(new Date(), "yyyy-MM");
            String timeStr = DateUtil.getDateFormate(new Date(), "HHmmss");
            String qrcodePath = "";
            // 如果需要插入二维码才进行二维码申请
            if(activityPoster.getIsPlaceQrcode() == 1){
                // 生成二维码图片
                String QRPath = UPLOAD_LOCATION + "/template/poster/qrcode/"+dateStr;
                qrcodePath = QRPath + "/QR_test.jpg";
                File qrcode = new File(qrcodePath);
                if(qrcode.exists()){
                    qrcode.delete();
                }
                double multiple = activityPoster.getQrcodeScaling();
                String accessToken = wxAccessTokenMapper.getAccessToken();
                ImgUtil.generateWeChatQrcode(QRPath, accessToken, "QRtest", qrcodePath, multiple);
            }

            String fileName = activityPoster.getPosterName() +  timeStr + "Test.jpg";
            // 合成图片的路径
            String fileLocation = UPLOAD_LOCATION + "/template/poster/" + dateStr;
            File posterDir = new File(fileLocation);
            if (!posterDir.exists()) {
                posterDir.mkdirs();
            }
            String posterPath = fileLocation + "/" + fileName;
            // 合成图片
            ImgUtil.posterImage(activityPoster, qrcodePath, posterPath);

            posterPath = DOMAIN + posterPath.substring(4, posterPath.length());

            return new ResponseEntity<>(ResultModel.ok(posterPath), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> deleteActivityPoster(int id) {
        activityPosterMapper.deleteByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> enableActivityPoster(int id) {
        ActivityPoster activityPoster = activityPosterMapper.selectByPrimaryKey(id);
        // 先停用所有相同的海报
        activityPosterMapper.unEnableAll(activityPoster.getPosterType());
        // 再启用选中的海报
        activityPosterMapper.enableActivityPoster(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> unEnableActivityPoster(int id) {
        activityPosterMapper.unEnableActivityPoster(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public void deletePosterTask() {
        // 按 年-月 间隔存放海报内容，每隔一个月清理一次海报图片
        String month = DateUtil.getDateFormate(new Date(), "MM");
        String year = DateUtil.getDateFormate(new Date(), "yyyy");
        int mon = Integer.parseInt(month) - 1;
        if(mon < 10){
            month = String.valueOf("0"+mon);
        }else {
            month = String.valueOf(mon);
        }

        String dateStr = year + "-" + month;
        System.out.println("删除月份：" + dateStr);
        String QRPath = UPLOAD_LOCATION + "/template/poster/qrcode/"+dateStr;
        File qrDir = new File(QRPath);
        if (qrDir.exists() && qrDir.isDirectory()) {
            log.info("二维码：删除{}的文件", QRPath);
            qrDir.delete();
        }

        String fileLocation = UPLOAD_LOCATION + "/template/poster/" + dateStr;
        File posterDir = new File(fileLocation);
        if (posterDir.exists() && posterDir.isDirectory()) {
            log.info("海报：删除{}的文件", fileLocation);
            posterDir.delete();
        }

    }

}