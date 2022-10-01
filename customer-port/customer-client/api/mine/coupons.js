/**
 * 优惠券接口
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import {
	getToken,
	getCustomerNo
} from "@/utils/auth";

/**
 * 领取优惠券
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function receiveCoupons({
	centerCouponsId,
	sysCouponsId,
	customerGroupId,
	couponsImgUrl
}) {
	return uni.request({
		url: config.baseUrl.dev + "/customerCoupons/receiveCoupons",
		method: "post",
		header: {
			"Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
		},
		data: {
			centerCouponsId: centerCouponsId,
			sysCouponsId: sysCouponsId,
			customerGroupId: customerGroupId,
			couponsImgUrl: couponsImgUrl
		}
	});
}


/**
 * 查看领券中心数据
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function getCouponsCenterList() {
	return uni.request({
		url: config.baseUrl.dev + "/customerCoupons/getCouponsCenterList",
		method: "get",
		header: {
			"Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
			"Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
		}
	});
}

/**
 * 查看我的优惠券数据
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function getMyCouponsList() {
	return uni.request({
		url: config.baseUrl.dev + "/customerCoupons/getMyCouponsList",
		method: "get",
		header: {
			"Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
			"Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
		}
	});
}
