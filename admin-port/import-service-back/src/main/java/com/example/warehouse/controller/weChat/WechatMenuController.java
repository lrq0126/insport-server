package com.example.warehouse.controller.weChat;

import com.example.warehouse.common.HttpUtilManager;
import com.example.warehouse.entity.wx.WechatMenu;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.wechat.WeChatService;
import com.example.warehouse.service.wechat.WechatMenuService;
import com.example.warehouse.vo.wechat.WechatMenuReqVo;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("wechatMenu")
public class WechatMenuController {

    @Autowired
    private WechatMenuService wechatMenuService;
    @Autowired
    private WeChatService weChatService;

    @GetMapping("getAccessToken")
    @ResponseBody
    public String getAccessToken(){
        HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx41baa8dfc1dae72b&secret=8d5b1a036d81714ac5b5e3d29fff1419";
        String request = null;
        try {
            request = httpUtilManager.requestHttpGet(url);
            System.out.println("request: "+ request);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return request;
    }

    @PostMapping("getMenuList")
    public ResponseEntity<PageResultModel> getMenuList(WechatMenuReqVo wechatMenuReqVo){
        return wechatMenuService.getMenuList(wechatMenuReqVo);
    }

    @PostMapping("getMenuInfo")
    public ResponseEntity<ResultModel> getMenuInfo(int id){
        return wechatMenuService.getMenuInfo(id);
    }

    /**
     *
     * @param type      素材类型
     * @param offset    起始标记
     * @param count     查询数量
     * @return
     */
    @PostMapping("getMaterial")
    public ResponseEntity<ResultModel> getMaterial(String type, int offset, int count){
        return weChatService.getMaterial(type, offset, count);
    }

    @GetMapping("getFirstMenu")
    public ResponseEntity<ResultModel> getFirstMenu(){
        return wechatMenuService.getFirstMenu();
    }

    /**
     * 查询菜单设置结构的数据
     * @return
     */
    @GetMapping("getSettingMenu")
    public ResponseEntity<ResultModel> getSettingMenu(){
        return wechatMenuService.getSettingMenu();
    }

    @PostMapping("saveMenu")
    public ResponseEntity<ResultModel> saveMenu(WechatMenu wechatMenu){
        return wechatMenuService.saveWechatMenu(wechatMenu);
    }

    @PostMapping("deleteMenu")
    public ResponseEntity<ResultModel> saveMenu(int id){
        return wechatMenuService.deleteWechatMenu(id);
    }

    @PostMapping("enableMenu")
    public ResponseEntity<ResultModel> enableMenu(String ids){
        return wechatMenuService.enableMenu(ids);
    }


}
