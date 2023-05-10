// import http from '../../net/request.js'

import config from "@/config/apiUrl.config";

import http from "@/common/net/request.js"
import wx from "@/common/js/weixin/jweixin.js"

export function wxConfig (requestUrl) {
	http.request({
	 url:config.baseUrl.dev+"/common/getJSAPI?requestUrl=" + requestUrl,
	}).then(res => {
		let resp = res.content;
		wx.config({
			debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId: resp.appId, // 必填，公众号的唯一标识
			timestamp: resp.timestamp, // 必填，生成签名的时间戳
			nonceStr: resp.noncestr, // 必填，生成签名的随机串  
			signature: resp.signature, // 必填，签名
			jsApiList: ['checkJsApi', 'scanQRCode'] // 必填，需要使用的JS接口列表
		});
	})
}