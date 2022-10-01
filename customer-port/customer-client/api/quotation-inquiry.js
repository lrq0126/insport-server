/**
 * 报价查询
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 报价查询列表数据【重量】
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function weightQuotationInquiryList ({
    page,
    limit,
    weight,
    routeType,
    transportType,
    countryId
}) {
    let data = ''
    if (!weight) {
        data = {
            pageNumber: page,
            pageSize: limit,
            routeType: routeType,
            transportType: transportType,
            countryId: countryId
        };
    } else {
        data = {
            pageNumber: page,
            pageSize: limit,
            weight: weight,
            routeType: routeType,
            transportType: transportType,
            countryId: countryId
        };
    }

    return uni.request({
        url: config.baseUrl.dev + "/routePrice/price/quotation",
        method: "post",
        header: {
            "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
            "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
        },
        data: { ...data }
    });
}

/**
 * 拼团报价查询列表数据【重量】
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function pinWeightQuotationInquiryList ({
    page,
    limit,
    weight,
    routeType,
    transportType,
    countryId,
	isPin
}) {
    let data = ''
    if (!weight) {
        data = {
            pageNumber: page,
            pageSize: limit,
            routeType: routeType,
            transportType: transportType,
            countryId: countryId,
			isPin: isPin
        };
    } else {
        data = {
            pageNumber: page,
            pageSize: limit,
            weight: weight,
            routeType: routeType,
            transportType: transportType,
            countryId: countryId,
			isPin: isPin
        };
    }

    return uni.request({
        url: config.baseUrl.dev + "/routePrice/price/quotation",
        method: "post",
        header: {
            "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
            "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
        },
        data: { ...data }
    });
}

/**
 * 报价查询列表数据【体积】
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function volumeQuotationInquiryList ({
    page,
    limit,
    volume,
    routeType,
    transportType,
    countryId
}) {
    let data = ''
    if (!volume) {
        data = {
            pageNumber: page,
            pageSize: limit,
            routeType: routeType,
            transportType: transportType,
            countryId: countryId
        };
    } else {
        data = {
            pageNumber: page,
            pageSize: limit,
            volume: volume,
            routeType: routeType,
            transportType: transportType,
            countryId: countryId
        };
    }
    return uni.request({
        url: config.baseUrl.dev + "/routePrice/price/quotation",
        method: "post",
        header: {
            "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
            "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
        },
        data: { ...data }
    });
}

/**
 * 获取国家地址
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function getCountriesList () {
    return uni.request({
        url: config.baseUrl.dev + "/sysDictDetail/find/country/list",
        method: "get",
        header: {
            "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
        }
    });
}

/**
 * 获取国家内容（简易版）
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function getCountriesSimpleList () {
    return uni.request({
        url: config.baseUrl.dev + "/sysDictDetail/findCountrySimpleList",
        method: "get",
        header: {
            "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
        }
    });
}


