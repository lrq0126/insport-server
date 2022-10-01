import http from "@/common/net/request.js";
import spellGroupUrls from "./config.js";
//发起拼团
export  function  spellGroup(data,files,mintor){	
 	return http.uploadFile(data,spellGroupUrls.spellGroupUrl,files,mintor)	
}
export function  getUrl(name){
	return  http.getBaseUrl()+urls[name];
}
//检查是否有成团条件
export function spellCheckConfirm(data){
	let option={
		type:'get',
		data,
	}
	return new Promise(function (resolve, reject) {
		http.request(option,spellGroupUrls.spellCheckConfirm).then((res)=>{
			if (res.code == '200') {
				resolve()
			} else {
				reject(res.message)
			}
		})	
	})
}

//检查是否需要密码
export  function isNeedPas(data){	
	let option={
		type:'get',
		data,
		// ContentType:'application/json'
		}
	// data.type='get'
	return http.request(option,spellGroupUrls.isNeedPas)
	// return
} 
//检查密码是否正确
export  function validatePas(data){	
	let option={
		type:'get',
		data,
		// ContentType:'application/json'
		}
	return http.request(option,spellGroupUrls.validatePas)
	// console.log(res);
}
//提醒成员添加包裹
export  function tipMsg(paramMap){		
	// memberId 成员的id
	// orderNumber 拼团订单号
	var data;
	var param=Object.assign({},paramMap)
	return http.request({data:param},spellGroupUrls.tipMsg)
	// return data;
}
//获取所有列表
export async function getAllList(paramMap){		
	var data;
	var param=Object.assign({'pageNumber':0,'pageSize':10},paramMap)
	await http.request({type:'post',data:param},spellGroupUrls.allList).then((res)=>{
		data=res.content;
	})	
	return data;
}
//获取我发起的
export async function getOwnList(paramMap){
	var data;	
	var param=Object.assign({'pageNumber':1,'pageSize':10},paramMap);
	await http.request({type:'post',data:param},spellGroupUrls.spellList).then((res)=>{
		data=res.content;
	})	
	return data;
}
//获取我参与的
export async function getJionList(paramMap){
	var data;	
	var param=Object.assign({'pageNumber':1,'pageSize':10},paramMap);
	await http.request({type:'post',data:param},spellGroupUrls.jionList).then((res)=>{
		data=res.content;
	})	
	return data;
}
//根据订单号获取详情
export async function getPinTuanByOrderNumber(paramMap){
	var data;
	let  {orderNumber}=paramMap;	
	await http.request({type:'get',data:{orderNumber}},spellGroupUrls.getListByOrderNum).then((res)=>{
		data=res.content;
	})	
	return data;
}
//删除 添加包囊
export async function jionAndDelPackge(paramMap){
	var data;
	 // goodsIds 包裹id族，用逗号隔开，例如：14581,4512
	 // type 操作类型：add添加 delete删除
     // orderNumber 拼团订单号
	await http.request({type:'post',data:paramMap},spellGroupUrls.jionAndDelPackge).then((res)=>{
		data=res;
	})	
	return data;
}
//首页拼团置顶列表，最多12条，每次显示3条，向上循环滚动
export async function getHomeList(){
	var data;

	await http.request({type:'get'},spellGroupUrls.homeList).then((res)=>{
		data=res;
	})	
	return data;	
}
//删除拼团信息
export  function delPin(paramMap){
	var data;
   // orderNumber 拼团订单号
   // pId 拼团id
	return http.request({type:'get',data:paramMap},spellGroupUrls.delPin)
	// return data;	
}
//退出拼团
export  function quit(paramMap){
	var data;
     // orderNumber 拼团订单号
	return http.request({type:'get',data:paramMap},spellGroupUrls.quit)
	// .then((res)=>{
	// 	data=res.content;
	// })	
	// return data;
}

//确认成团
export function spellConfirm(data){
	let option={
		type:'get',
		data,
	}
	return new Promise(function (resolve, reject) {
		http.request(option,spellGroupUrls.spellConfirmUrl).then((res)=>{
			if (res.code == '200') {
				resolve(res)
			} else {
				reject(res.message)
			}
		})	
	})
}

// //确认成团
// export  function confirm(paramMap){
// 	var data;
//      // orderNumber 拼团订单号
// 	return http.request({type:'get',data:paramMap},spellGroupUrls.confirm)
// 	// .then((res)=>{
// 	// 	data=res.content;
// 	// })	
// 	// return data;
// }

//踢人
export function kickMember(paramMap){
	//memberId 成员id
	//pId 拼团id
	return http.request({type:'get',data:paramMap},spellGroupUrls.kickMember)
}
//拼团状态转化
export function  getPtype(type){
	let str;
	switch (type) {
		case 0:
			str = '未审核';
			break;
		case 1:
			str = '未成团';
			break;
		case 2:
			str = '已成团';
			break;
		case 3:
			str = '已解散';
			break;
		case 4:
			str = '未通过';
			break;
		case 5:
			str = '未成团待确认';
			break;
		default:
			str = '已完成';
	}
	return str;
}
