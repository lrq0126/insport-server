package com.dwgj.mlxydedicatedline.controller.timeLimitActivity;

import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.TimeLimitActivity;
import com.dwgj.mlxydedicatedline.entity.TransportationRoute;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.TransportationRouteService;
import com.dwgj.mlxydedicatedline.service.timeLimitActivity.TimeLimitActivityService;
import com.dwgj.mlxydedicatedline.vo.timeLimitActivityVo.ActivityRouteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.*;

/**
 * @author 梁榕清
 * @date 2020年04月05日 14:59
 * 限时活动接口
 */
@RestController
@RequestMapping("/timeLimitActivity")
public class TimeLimitActivityController {

    @Autowired
    private TimeLimitActivityService timeLimitActivityService;

    // 活动名称
    private static String ACTIVITY_NAME = "ReceiveActivityOfAmerican-LianHuaQingWen";

    @PostMapping("/create")
    public ResponseEntity<ResultModel> timeLimitActivity(@RequestParam Map<String, Object> paramMap) {
        try {
            Customer customer = UserThreadContext.getUser();
            // 取消 活动不能重复参与的限制
            /* Map<String, Object> selectMap = new HashMap<>(4);
            selectMap.put("customerId", customer.getId());
            selectMap.put("activityName", ACTIVITY_NAME);
            TimeLimitActivity timeLimitActivity = timeLimitActivityService.selectByCustomerId(selectMap);
            if (timeLimitActivity != null) {
                return new ResponseEntity<>(ResultModel.error(ACTIVITY_REPEAT_ERROR), HttpStatus.OK);
            }*/
            TimeLimitActivity timeLimitActivity = new TimeLimitActivity();
            timeLimitActivity.setCustomerId(Long.valueOf(customer.getId()));
            if (!paramMap.containsKey("addressId")) {
                return new ResponseEntity<>(ResultModel.error(ADDRESS_CHOICE_ERROR), HttpStatus.OK);
            }
            timeLimitActivity.setTransportRouteId(Long.valueOf(paramMap.get("routeId").toString()));
            timeLimitActivity.setActivityName(ACTIVITY_NAME);
            int i = timeLimitActivityService.insert(timeLimitActivity, paramMap.get("addressId").toString());
            if (i == -1) {
                return new ResponseEntity<>(ResultModel.error(ROUTE_NULL_ERROR), HttpStatus.OK);
            }
            if (i == -2) {
                return new ResponseEntity<>(ResultModel.error(ADDRESS_NULL_ERROR), HttpStatus.OK);
            }
            Map<String, Object> resultMap = new HashMap<>(4);
            resultMap.put("msg", "领取成功，请到 个人中心-待付款 进行下一步操作");
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    @GetMapping("/get/route")
    public ResponseEntity<ResultModel> getRoute() {
        try {
            List<ActivityRouteVO> activityRouteVOList = timeLimitActivityService.getRoute();
            if (activityRouteVOList.size() <= 0) {
                return new ResponseEntity<>(ResultModel.error(ROUTE_NULL_ERROR), HttpStatus.OK);
            }
            Map<String, Object> resultMap = new HashMap<>(4);
            resultMap.put("route", activityRouteVOList);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    @GetMapping("/get/timeLimitActivity")
    public ResponseEntity<ResultModel> getTimeLimitActivity() {
        try {
            // 取消 活动不能重复参与的限制
            /*Customer customer = UserThreadContext.getUser();
            Map<String, Object> selectMap = new HashMap<>(4);
            selectMap.put("customerId", customer.getId());
            selectMap.put("activityName", ACTIVITY_NAME);
            TimeLimitActivity timeLimitActivity = timeLimitActivityService.selectByCustomerId(selectMap);
            if (timeLimitActivity != null) {
                return new ResponseEntity<>(ResultModel.error(ACTIVITY_REPEAT_ERROR), HttpStatus.OK);
            }*/
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

}
