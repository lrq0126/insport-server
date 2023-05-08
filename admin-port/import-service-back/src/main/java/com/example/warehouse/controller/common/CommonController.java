package com.example.warehouse.controller.common;

import com.example.warehouse.common.CommonUtil;
import com.example.warehouse.common.FileUtil;
import com.example.warehouse.model.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 此公共类暂时没区分，可以任意扩展
 */
@Controller
@RequestMapping("/common")
@Slf4j
public class CommonController {

    /**
     * 下载成本批量结算模板文件
     */
    @RequestMapping(value = "/downloadettleMentTemplateExcel", method = RequestMethod.GET)
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 为了适配物流公司的低配版系统，采用xls后缀
            FileUtil.downloadExcel("成本批量结算模板.xls", "settleMentTemplate.xls", request, response);
        } catch (Exception e1) {
            log.error("下载出错，未知异常：{}",e1.getMessage());
            e1.printStackTrace();
        }
    }

    /**
     * 下载成本批量预报模板文件
     */
    @RequestMapping(value = "/downloadPreOrderTemplateExcel", method = RequestMethod.GET)
    public void downloadPreOrderExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 为了适配物流公司的低配版系统，采用xls后缀
            FileUtil.downloadExcel("批量预报模板.xls", "preOrderTemplate.xls", request, response);
        } catch (Exception e1) {
            log.error("下载出错，未知异常：{}",e1.getMessage());
            e1.printStackTrace();
        }
    }

    /**
     * 下载成本批量预报模板文件
     */
    @RequestMapping(value = "/downloadTrajectoryTemplateExcel", method = RequestMethod.GET)
    public void downloadTrajectoryTemplateExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 为了适配物流公司的低配版系统，采用xls后缀
            FileUtil.downloadExcel("批量导入轨迹模板.xls", "trajectoryTemplate.xls", request, response);
        } catch (Exception e1) {
            log.error("下载出错，未知异常：{}",e1.getMessage());
            e1.printStackTrace();
        }
    }

    /**
     * 下载成本批量预报模板文件
     */
    @RequestMapping(value = "/downloadAgentNumberTemplateExcel", method = RequestMethod.GET)
    public void downloadAgentNumberTemplateExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 为了适配物流公司的低配版系统，采用xls后缀
            FileUtil.downloadExcel("批量导入转单号模板.xls", "agentNumberTemplate.xls", request, response);
        } catch (Exception e1) {
            log.error("下载出错，未知异常：{}",e1.getMessage());
            e1.printStackTrace();
        }
    }

    /**
     * 下载渠道编辑模板文件
     */
    @RequestMapping(value = "/downloadChannelTemplateExcel", method = RequestMethod.GET)
    public void downloadChannelTemplateExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 为了适配物流公司的低配版系统，采用xls后缀
            FileUtil.downloadExcel("系统渠道编辑模板.xls", "channelTemplate.xls", request, response);
        } catch (Exception e1) {
            log.error("下载出错，未知异常：{}",e1.getMessage());
            e1.printStackTrace();
        }
    }

    /**
     * 下载渠道编辑模板文件
     */
    @RequestMapping(value = "/downloadTrack17RegisterExcel", method = RequestMethod.GET)
    public void downloadTrack17RegisterExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 为了适配物流公司的低配版系统，采用xls后缀
            FileUtil.downloadExcel("17轨迹网订单注册模板.xls",
                    "track17Register.xls", request, response);
        } catch (Exception e1) {
            log.error("下载出错，未知异常：{}",e1.getMessage());
            e1.printStackTrace();
        }
    }

    @Value("${tencent.appid}")
    private String APPID;
    @Value("${tencent.secret-id}")
    private String SECRET_ID;
    @Value("${tencent.secret-key}")
    private String SECRET_KEY;
    @Value("${tencent.bucket-name}")
    private String BUCKET_NAME;

    @Autowired
    private CommonUtil commonUtil;

    @PostMapping("/getJSAPI")
    public ResponseEntity<ResultModel> getJSAPI(@RequestParam("requestUrl") String requestUrl){

        return new ResponseEntity<>(ResultModel.ok(commonUtil.getJsApi(requestUrl)), HttpStatus.OK);
    }

}
