package com.dwgj.mlxydedicatedline.enums;

import com.dwgj.mlxydedicatedline.config.CodeEnum;

/**
 *
 * @author 梁榕清
 * 2019年11月8日16:03:17
 */
public enum ResultStatus implements CodeEnum {
    /**
     * 返回状态码 和 信息
     */
    SUCCESS(200, "成功"),
    ERROR(-100,"操作失败"),
    USER_ERROR(-102,"用户不匹配"),
    USER_NULL_ERROR(-103,"当前用户不存在"),
    NOT_LOGIN_ERROR(-202,"请登录"),
    USER_NOT_COMMERCIAL_AREA(-203,"请联系管理员为您绑定相关的仓库"),
    USER_NOT_COUNTRY(-204,"请联系管理员为您绑定相关的国家"),

    SYS_ERROR(501,"服务器错误，请联系管理员"),
    BALANCE_INSUFFICIENT_ERROR(-999, "账户余额不足，请充值"),
    USERNAME_ERROR(-1001, "用户名错误"),
    PASSWORD_ERROR(-1002,"密码错误"),
    PASSWORD_NULL_ERROR(-10021,"请输入密码"),
    NEW_PASSWORD_NULL_ERROR(-10022,"请输入新密码"),
    EDIT_TIME_PASSWORD_ERROR(-10023,"原密码错误，请重新输出入"),
    USER_REPEAT_ERROR(-1003, "用户名已存在"),
    PHONE_REPEAT_ERROR(-1004, "该手机用户已存在"),
    EMAIL_REPEAT_ERROR(-10041, "该邮箱已存在，请重新输入或者找回密码"),
    LOGIN_NAME_REPEAT_ERROR(-1005, "登录名已存在"),
    GOODS_PACK_TYPE_ERROR(-1101,"只有未打包和已打包的状态的包裹能执行拆包操作"),
    GOODS_CONFIRM_ERROR(-1102,"当前状态的包裹不能发货"),
    GOODS_UPDATE_ADDRESS_ERROR(-1103,"当前状态的包裹不能修改地址"),
    GOODS_WEIGHT_EXCEEDING_ERROR(-1104, "订单中某件货物重量超标，无法空运"),
    GOODS_LENGTH_EXCEEDING_ERROR(-1105, "订单中某件货物长度超标，无法空运"),
    GOODS_WEIGHT_OR_VOL_NULL_ERROR(-1106, "您选中的包裹中，有【重量】或者【体积】未核算，故【预报价】不能计算，请联系客服核实"),
    ROUTE_NUMBER_ERROR(-1107, "请选择路线"),
    ADDRESS_CHOICE_ERROR(-1108, "请选择地址"),
    ROUTE_NULL_ERROR(-1109, "该路线已被删除，请联系管理人员"),
    ADDRESS_NULL_ERROR(-1110, "该订单的收货地址已被删除，请重新选择"),
	GOODS_TYPE_PACKED_ERROR(-1111, "申请打包的货物中，有已经申请打包的或已打包好的货物，请退出申请界面，重新选择打包货物"),
    DELIVERY_NO_REPEAT_ERROR(-1114, "订单号有重复，请检查单号是否已经录入"),
    DELIVERY_HAS_IN_ERROR(-1115, "该包裹已入库"),
    ROUTE_PRICE_NULL_ERROR(-1116, "当前路线价格为空，请联系【管理员】或【客服】"),
    ROUTE_PRICE_BEYOND_THR_LIMIT_ERROR(-1117, "获取不到该路线的价格，可能是货物总重量区不在此路线的重量范围，请联系【管理员】或【客服】"),

    ENABLE_WAREHOUSE_ADDRESS_NULL_ERROR(-1010, "暂无启用中的仓库收货地址，请联系客服人员"),

    ROUTE_IS_TARIFFS_PLEASE_INPUT_PACK_VALUATION(-1150, "当前渠道存在预付关税，请先填写产品货值"),
    PLEASE_SELECTED_IDENTITY(-1151, "请选择身份证信息"),

    ACTIVITY_REPEAT_ERROR(-1201, "您已参与过该活动"),
    ACTIVITY_PACK_UNPACK_ERROR(-1202, "活动包裹无法拆包，敬请谅解"),
    NULL_POINTER_ERROR(-500,"空指针异常"),
    NULL_ADDRESS_ERROR(-301,"请先选择收获地址"),
    FILE_MAX(-202,"文件过大"),
    ORDERNUMBER_NOT_EXSIT(-203,"该订单不存在！"),
    GOODS_IDS_NULL(-204,"包裹不能为空"),
    INPUT_NULL(-205,"输入值不能为空"),
    NULL_USER(-206,"未获取到用户信息"),

    // 拼团包裹异常信息
    IS_IN(101,"有权限进入"),
    IS_NOT_IN(102,"没权限进入，需要输入密码"),
    IS_MAX_PEOPLE(103,"已达上限人数"),
    IS_TIME_OFF(104,"该团已过期"),
    ORDER_HAVE_MEMBER(105,"该拼团下有客户添加包裹"),
    PIN_IS_CHECKING(106,"该订单还在审核，暂时不能添加包裹"),
    WEIGHT_NOT_ENOUGH(107,"该订单的实际重量还没到达预期值，是否按当前重量提交？"),
    WEIGHT_ENOUGH(108,"该订单重量已足够"),
    PACK_ABNORMAL_ERROR(109, "包裹异常错误，请联系管理人员"),
    TEAM_COMPLETED_ERROR(110, "当前包裹已成团，无需再次成团操作"),
    PIN_MAIN_IS_NOTP_ACKED_ERROR(111, "拼团订单包裹暂未打包完成，请等待拼团订单中的其他包裹打包完成再进行付款"),
    PIN_LEADER_NOT_THIS_USERID(112,"当前客户不是这个拼团的创建人，无权限进行此操作"),
    // 拼团包裹异常信息

    // 优惠券异常 1250 ~ 1299
    HAVE_TO_RECEIVED(1250, "您已经领取过该优惠券，不能重复领取"),
    THIS_COUPONS_RECEIVED_ERROR(1251, "优惠券领取错误，请刷新界面重新领取"),
    THIS_CUSTOMER_LOGIN_ERROR(1252, "账户信息错误，请重新登录后再领取"),
    THIS_COUPONS_USED_ERROR(1253, "该优惠券已使用，请重新选择优惠券"),
    THIS_PRICE_NOT_SATISFY_COUPONS_MINIMUM_AMOUNT_ERROR(1254, "货物价格不满足该优惠券的使用价格，请重新选择优惠券"),



    // 微信请求异常  1300 - 1399
    WECHAT_ERROR(1300, "微信请求数据异常"),
    WECHAT_USER_CODE_NULL_ERROR(1301, "微信请求数据异常, 用户CODE为空"),

    // 图片请求异常 1401~1450
    IMAGES_NULL_ERROR(1401,"暂未发现当前订单图片"),

    IMAGES_NUMBER_NIMIETIES_ERROR(1402,"当前订单图片数据过多，请联系客服查询"),
    ;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
