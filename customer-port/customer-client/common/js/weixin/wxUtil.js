// import http from '../../net/request.js'
var baseurl = 'http://vai66w.natappfree.cc/jsapi'
export function wxConfig () {
	// let url = location.href.split('#')[0];		
	// console.log(url)
	// this.$http.request({
	//  url:baseurl,
	//  data:{
	// 	url 
	//  }
	// }).then(res => {
	//    console.log(res)
	// 	let {
	// 		noncestr,
	// 		signature,
	// 		timestamp,
	// 		appId
	// 	} = res;
	//    this.$wx.config({
	// 		debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	// 		appId: appId, // 必填，公众号的唯一标识
	// 		timestamp: timestamp, // 必填，生成签名的时间戳
	// 		nonceStr: noncestr, // 必填，生成签名的随机串  
	// 		signature: signature, // 必填，签名
	// 		jsApiList: [
	// 			'scanQRCode',
	// 			'updateAppMessageShareData', ,
	// 			'showMenuItems', 'showAllNonBaseMenuItem',
	// 			'hideOptionMenu', 'hideMenuItems', 'showOptionMenu'
	// 		] // 必填，需要使用的JS接口列表
	// 	});
	// })
}

export function wxShare (options) {
	let {
		title,
		desc,
		link,
		imgUrl
	} = options
	return new Promise((reslove, reject) => {
		this.$wx.ready(() => { //需在用户可能点击分享按钮前就先调用
			this.$wx.updateAppMessageShareData({
				title, // 分享标题
				desc, // 分享描述
				link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
				imgUrl, // 分享图标
				success: function (res) {
					// 设置成功
					reslove(res);
					// console.log(res)
				}
			})
		});
	})

}