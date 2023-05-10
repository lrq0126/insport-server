import config from "@/config/apiUrl.config";
import { getToken, getUserCode } from "@/utils/auth";
let base = config.baseUrl.dev;
class http {
	static request (option, url) {
		return new Promise((resolve, reject) => {
			uni.request({
				url: option.url ? option.url : base + url, //仅为示例，并非真实接口地址。
				data: option.data ? option.data : {},
				header: {
					// "Content-Type": option.ContentType ? option.ContentType : "application/x-www-form-urlencoded", //自定义请求头信息
					Authorization: getToken()//自定义请求头信息
				},
				dataType: "json",
				method: option.type ? option.type : "post",
				success: (res) => {
					if (res.data.code == 100 ) {
						let data = res.content || res.data;
						if (data.code == '401') {
							var page = (getCurrentPages()[getCurrentPages().length - 1]).route;
							uni.setStorageSync('page', page);
							// uni.navigateTo({
							uni.redirectTo({
								url: '/pages/login/login'
							})
						}
						resolve(res.content || res.data);
					} else {
						uni.showToast({
							icon: 'none',
							title: '服务器繁忙'
						})
						resolve({ code: 500, msg: '操作失败' });
						throw new Error("错误信息:" + JSON.stringify(res));
					}
				},
				fail (err) {
					uni.showToast({
						title: "网络故障了。。。"
					})
					resolve([])
					// reject(err)
				}
			});
		})
	}
	static getBaseUrl () {
		return base;
	}
	static uploadFile (formData, url, files, mintor) {
		return new Promise((resolve, reject) => {
			const uploadTask = uni.uploadFile({
				url: base + url,
				files,
				name: 'flie',
				formData: formData,
				header: {
					Authorization: getToken() //自定义请求头信息
				},
				method: 'post',
				success: function (res) {
					// console.log(obj)
					resolve(res)
				},
				fail: function (res) {
					reject();
				}
			})
			uploadTask.onProgressUpdate((res) => {
				mintor(res);
			})
		})
	}
	
	static uploadImage (imageType, url, file, mintor) {
		return new Promise((resolve, reject) => {
			const uploadTask = uni.uploadFile({
				url: base + url,
				file,
				name: 'flie',
				header: {
					Authorization: getToken() //自定义请求头信息
				},
				method: 'post',
				success: function (res) {
					resolve(res)
				},
				fail: function (res) {
					reject();
				}
			})
			uploadTask.onProgressUpdate((res) => {
				mintor(res);
			})
		})
	}
}
export default http
