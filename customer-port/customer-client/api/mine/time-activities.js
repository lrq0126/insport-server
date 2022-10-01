/**
 * 限时活动
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 获取活动渠道
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function getactivitiesChannel () {
    return uni.request({
        url: config.baseUrl.dev + "/timeLimitActivity/get/route",
        method: "get",
        header: {
            "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
        },
    });
}

/**
 * 检查是否参与过该活动
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function checkParticipatedActivity () {
    return uni.request({
        url: config.baseUrl.dev + "/timeLimitActivity/get/timeLimitActivity",
        method: "get",
        header: {
            "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
        },
    });
}

/**
 * 确认下单
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function confirmActivitiesOrder ({ routeId, addressId }) {
    const data = {
        routeId: routeId,
        addressId: addressId
    };
    return uni.request({
        url: config.baseUrl.dev + "/timeLimitActivity/create",
        method: "post",
        header: {
            "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
            "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
        },
        data: { ...data }
    });
}
