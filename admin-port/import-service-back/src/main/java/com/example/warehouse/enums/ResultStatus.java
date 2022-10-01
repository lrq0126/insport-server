package com.example.warehouse.enums;

import com.example.warehouse.config.CodeEnum;

/**
 * 状态码
 * @author lrq
 */
public enum ResultStatus implements CodeEnum {
    /**
     * 代码&错误信息
     */
    SUCCESS(100, "成功"),
    ERROR(-100,"操作失败"),
    // 登录类型错误
    USER_NOT_LOGIN(-99, "未登录或者当前用户已过期，请登录"),
    USER_NOT_COMMERCIAL_AREA(-99, "当前用户暂未分配所属仓库，请向管理员联系分配"),
    PERMISSION_ERROR(-101,"该账户暂无此权限"),
    OPERATION_ERROR(-102,"该数据无法删除，请确认是否有关联数据"),
    USERNAME_ERROR(-1001, "用户名错误"),
    USER_REPEAT_ERROR(-1002, "该登录名已存在"),
    PASSWORD_ERROR(-1003,"密码错误"),
    USERNAME_REPEAT_ERROR(-1004, "该用户名已存在"),
    CUSTOMER_HAVE_GOODS_ERROR(-1005, "该客户存在包裹，无法删除"),
    CUSTOMER_GOODS_ERROR(-1006, "该包裹不属于此客户"),
    ROLE_REPEAT_ERROR(-1007, "该角色已存在，请重新输入角色名"),
    CUSTOMER_NULL_ERROR(-1008,"客户不存在"),
    CUSTOMER_ADDRESS_NULL_ERROR(-1009,"该收件地址已失效"),

    ENABLE_WAREHOUSE_ADDRESS_NULL_ERROR(-1010, "暂无启用中的仓库收货地址，请启用或添加一条仓库收货地址"),
    WAREHOUSE_ADDRESS_LOGIN_NAME_NULL_ERROR(-1011, "LOGIN_NAME参数不能为必填字符，请在收件人/收件地址上添加LOGIN_NAME"),

    SYS_ERROR(500,"服务器错误，请联系管理员"),

    SYS_TOKEN_OUT_OF_DATE_ERROR(601, "请求Token不存在或已经过期，请重新获取"),
    SYS_SCHEDULE_NULL_ERROR(602, "当前任务不存在，请刷新页面重试"),

    // 货架错误类型
    SHELVES_AREA_REPETITION_ERROR(701, "货架区域名称重复，请修改"),
    SHELVES_AREA_ROW_REPETITION_ERROR(702, "当前货架区域已存在当这个排号，请更换排号"),
    SHELVES_ROWS_REPETITION_ERROR(703, "当前货架区域存在重复排号，请修改"),
    SHELVES_ROW_NULL_ERROR(704, "货架不能有为空的排，请添加货架排号"),

    SHELVES_AREA_HAVE_GOODS(705, "当前货架区内还存在客户包裹，请将包裹下架再进行删除操作"),
    SHELVES_ROW_HAVE_GOODS(706, "当前货架排上还存在客户包裹，请将包裹下架再进行删除操作"),
    SHELVES_AREA_ROW_NULL_ERROR(707, "当前货架不存在"),
    // 包裹类型错误
    PACK_ACCEPTED(-1100, "包裹已经由别的用户开始处理了"),

    PACK_SURPLUS_ERROR(-1101,"存在未拣货的货物，请核查"),
    PACK_ERROR(-1102,"包裹状态错误"),
    PACK_EXISTENCE_ERROR(-1103,"该订单包裹已存在"),
    PACK_NULL_ERROR(-1104,"包裹订单有误，请核实"),
    PACK_LENGTH_ERROR(-1105,"有包裹的长宽高未填写，请输入后再提交"),
    PACK_THERE_ERROR(-1106,"该货物已申请打包，无法删除"),
    COURIER_NOT_SHELVES_ERROR(-1107,"有快递包裹未上架，请核查"),
    SON_PACK_NULL_ERROR(-1108, "子包裹订单为空，请添加子包裹数据后在提交"),
    GOODS_LOCATION_NULL_ERROR(-1109, "有包裹货架号为空，请核查"),
    PACK_GOODS_NULL_ERROR(-1110, "当前订单没有货物，请核查或者联系管理员"),
    THIS_PACK_IS_PIN_ERROR(-1111, "此订单是拼团订单，不能进行当前操作"),
    SON_PACK_GOODS_NULL_ERROR(-1112, "箱子数据中存在空货物箱子，请核查"),

    PLEASE_ADD_GOODS_IN_PACK(-1113, "请选择货物装箱"),
    PACK_ADD_GOODS_ERROR(-1114, "货物包裹关联错误"),
    PACK_CAN_NOT_PAYMENT(-1115, "当前订单不可付款或已付款"),

    PACK_TYPE_NOT_UPDATE_ADDRESS_ERROR(-1116,"包裹已发出，不能再进行地址修改"),

    PACK_OUT_STORAGE_LONG_ERROR(-1117,"包裹已发出超过1个小时，不能在进行取消发货操作"),

    // 包裹轨迹错误类型
    PACK_ORDER_NUMBER_CAN_NOT_NULL(-1130, "申请注册轨迹的订单号不能为空"),
    PACK_ORDER_NUMBER_REGISTER_FAILURE(-1131, "注册失败"),
    PACK_OTHER_ORDER_NUMBER_REGISTER_FAILURE(-1132, "部分订单号注册失败"),
    PACK_ORDER_NUMBER_MORE_THAN_ERROR(-1133, "一次最多只能注册400个订单号"),
    PACK_ORDER_TRACK_MESSAGE_NULL_ERROR(-1134, "当前订单号暂无轨迹"),
    // 拼团类型错误  暂定 -1150~-1199
    PIN_NULL_ERROR(-1150, "当前拼团订单已被删除，请刷新界面重试"),
    PIN_MEMBER_NULL_ERROR(-1151, "暂无拼团成员"),
    PIN_ORDER_PACK_NULL(-1152, "拼团包裹为空"),
    PIN_ORDER_NULL_ERROR(-1153, "当前拼团子包裹已被删除，请退出当前界面重进"),
    PIN_ORDER_PACK_NULL_ERROR(-1154, "还存在有客户未打包，请打包后在确认打包完成"),
    PIN_WEIGHT_LESS_THAN_TARGET(-1155, "拼团订单总体打包后的重量小于目标重量，请提醒客户继续拼团"),
    PIN_VOL_LESS_THAN_TARGET(-1156, "拼团订单总体打包后的体积小于设定目标"),

    A_PIN_MEMBER_NOT_PAID(-1157, "还有客户没有支付货款，不能发货"),


    ROUTE_UPDATE_ERROR(-601,"请至少保留一条价格数据"),
    ROUTE_PRICE_NULL_ERROR(-602,"有部分明细未单价，请输入单价"),
    BALANCE_INSUFFICIENT_ERROR(-999, "该账户余额不足，请提醒充值！"),


    NOTICE_CONTENT_NULL(-10001, "消息内容不能为空"),
    USERID_NULL(-10002, "获取当前操作员信息无效，请重新刷新页面"),

//    路线
    TRANSPORTROUTE_ERROR(-1200, "渠道信息错误或当前渠道已经被删除，请联系管理员"),
    TRANSPORT_ROUTE_PRICE_ERROR(-1201, "暂未发现渠道价格，请联系管理员"),
    ROUTE_WEIGHT_OR_VOLUME_LIMIT_ERROR(-1202, "打包后的包裹将超出限制，请通知客户重新打包"),
    TRANSPORT_ROUTE_USE_ERROR(-1203, "该路线正在使用，无法删除"),
    TRANSPORT_ROUTE_NULL_ERROR(-1204, "该路线不存在"),
    WEIGHT_CARRY_ERROR(-1205, "进位换算规则的重量数据不合法，请修改"),
    ROUTE_PRICE_VOL_NULL_ERROR(-1206, "部分体积单位数据不正确，请检查"),
    ROUTE_PRICE_WEIGHT_NULL_ERROR(-1207, "部分重量单位数据不正确，请检查"),

    ROUTE_IS_TARIFFS_PLEASE_INPUT_PACK_VALUATION(-1250, "当前渠道存在预付关税，请先填写产品货值"),
    PACK_VALUATION_NULL_ERROR(1251, "产品货值为空"),
    PLEASE_INPUT_PACK_VALUATION(1252, "当前渠道存在预付关税，请先填写产品货值"),

    DELIVERY_ORDER_NO_NULL(-1301, "请输入快递单号"),
    DELIVERY_ORDER_NO_REJECT(-1302, "此包裹拒收"),
    DELIVERY_ORDER_NO_HAD_RECEIPT(-1303, "已签收"),
    NOT_NULL(500,"请检查输入值是否为空"),
    NULL_ERROR(500,"空指针异常"),

    FILE_TYPE_ERROR(-1401,"文件类型错误"),
    FILE_DATA_ERROR(1001,"文件上传出错"),
    FILE_DATA_UPPER_ERROR(1002,"文件包含的单据不能超过500单"),

    ORDER_OUT_OF_DATE(-1402,"订单超过3天，无法撤单，请使用超级管理员账号进行强制撤单"),
    INVOICE_TYPE_NULL_ERROR(1500, "发票类型为空，请选择发票类型"),
    INVOICE_INSERT_ERROR(1501,"发票主数据插入数据库失败"),
    INVOICEITEMS_INSERT_ERROR(1502,"发票子数据插入数据库失败"),
    ORDER_NUMBER_SELECT_ERROR(1503,"订单号出现错误，请联系管理员"),


    GOODS_HAVE_IN_ERROR(1504,"该单号已入库"),
    GOODS_HAVE_REIN_ERROR(1505,"该单号已预报"),
    CONTAINER_HAVE_NUMBER_ERROR(1506,"该批次名称下包含关联的单号，请先删除单号再删除批次"),
    CONTAINER_ID_NULL(1507,"该选择批次名称"),
    GOODS_REIN_NUMBER_TOO_MUCH(1508,"存在多个相同订单的预录入包裹，请在快递单号/唛头中回车选择其中一条"),

    ACCOUNT_RESERFAIL(1509,"清零失败，该客户余额已经为零"),

    STORAGE_ROW_NULL_ERROR(1601, "当前仓库区域的排数为空"),
    LOCATION_NULL_ERROR(1602, "请选择要上架的货架【区号】和【排数】"),
    GOODS_DELIVERY_ORDER_NO_ERROR(1603, "请输入要上架的货物"),
    GOODS_TYPE_NOT_INTO_WARE_ERROR(1604, "只有已入库的货物才能上架"),
    THIS_GOODS_NULL_ERROR(1605, "这个货物不存在"),
    THIS_GOODS_IS_REPEAT(1606, "快递单号存在重复，请核查"),

    // 代理商错误 301 - 350
    ONLY_CUSTOMER_PACKING_ERROR(301, "目前只支持一个客户的申请打包操作"),
    ADDRESS_NULL_ERROR(302, "收货地址不能为空"),
    CUSTOMER_ID_NULL_ERROR(303, "客户信息不能为空"),
    ROUTE_ID_NULL_ERROR(304, "路线不能为空"),
    GOODS_NULL_ERROR(305, "货物不能为空"),

    THIS_AGENT_IS_HAVE(351, "当前供应商已存在"),
    REJECT_HAVEADD(1510, "该单号已加入拒收列表"),

    // 优惠券错误 1700 - 1750
    COUPONS_NAME_LONGED_ERROR(1700, "优惠券名称最多可输入6位"),
    THIS_COUPONS_NAME_IS_EXISTING(1701, "这个优惠券名称已存在，请换一个"),

    THIS_COUPONS_IS_DELETE(1702, "这个优惠券已经被删除"),
    THIS_COUPONS_IS_DISABLED(1703, "这个优惠券已经被禁用"),

    PLEASE_SELECT_THE_CUSTOMER_GROUP(1706, "请选择要分发的分组"),
    PLEASE_SELECT_THE_CUSTOMER(1707, "请选择要分发的客户"),
    PLEASE_DELETE_THE_CUSTOMER(1708, "请选择要删除的客户"),


    // 客户分组错误1751 - 1799
    THIS_CUSTOMER_GROUP_IS_EXISTING(1751, "这个客户分组已存在"),

    PLEASE_SELECT_THE_CUSTOMER_TO_OPERATE(1753, "请选择要操作的客户"),

    // 任务中心错误
    TASK_DETAIL_IS_NULL_ERROR(1801,"请添加任务订单明细"),

    // 数据字典类型错误 2001 - 2099
    THIS_DICT_REPEAT_ERROR(2001, "字典已存在，无需重复添加"),
    THIS_DICT_NAME_REPEAT_ERROR(2002, "当前字典名已被使用"),
    THIS_DICT_REMARKS_REPEAT_ERROR(2003, "当前类型代码已被使用"),

    CHANNEL_NOT_FIXED(3001,"该渠道不适合打印亚美面单"),
    CODE_NULL(3002,"请确保邮编不为空"),


    // 微信类型错误 3101 - 3300
    WECHAT_MENU_SETTING_NULL(3101, "微信菜单内容为空，请重新选择菜单"),
    WECHAT_MENU_EXCESS(3102, "微信菜单最多只能有3个一级菜单"),
    WECHAT_MENU_CAN_NOT_SETTING_SECONDARY(3103, "当前菜单中存在二级菜单，所以不能把这个菜单变更为二级菜单"),

    WECHAT_MENU_SETTING_ERROR(3105, "微信菜单设置失败"),

    WECHAT_REPLY_RULE_REPEAT_ERROR(3106, "自动回复的规则名称重复，请修改后再提交"),
    WECHAT_KEY_WORD_REPEAT_ERROR(3107, "自动回复的关键词重复，请修改后再提交"),

    // 积分规则3301 - 3400
    INTEGRAL_RULE_TYPE_REPEAT_ERROR(3301, "该类型的积分规则已存在"),

    // 活动
    PLEASE_ADD_POSTER_PICTURE(3401, "请选择活动海报图片"),
    ACTIVITY_NAME_REPEAT(3402, "当前活动名已存在，请修改"),
    ACTIVITY_TYPE_REWARD_REPEAT(3403, "此奖励的活动类型已存在，请更换奖励内容"),

    IS_NOT_ENABLE_POSTER(3451, "暂未发现有启用的海报，请添加海报并启动"),

    EMAIL_IS_DELETED(3501, "该邮件记录已被删除"),

    OTHER_SYSTEM_ERROR(-9999, "")
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
