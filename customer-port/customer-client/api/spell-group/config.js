//拼团路径
let spellGroupUrls={
	spellGroupUrl:'/pin/addPin',//创建拼团
	spellCheckConfirm:'/pin/checkConfirm', //检查是否有成团条件
	spellConfirmUrl:'/pin/confirm',//确认拼团
	isNeedPas:'/pin/checkIsInPin',//检查是否资格进入拼团
	validatePas:'/pin/checkPassword',//判断密码正确性
	getListByOrderNum:'/pin/getPinTuanByOrderNumber',//根据订单号获取详情
	kick:'/pin/kickMember',//踢出某一个成员
	exit:'/pin/quitPin',//退出拼团
	jionAndDelPackge:'/pin/takePartInPin',	//根据拼团订单参加拼团或者删除该团下的某些包裹
	allList:'/pin/pinList',//获取所有拼团,
	jionList:'/pin/myTakePartInlist',//我参与的拼团列表
	spellList:'/pin/ownList',//我发起的拼团列表,
	delPin:'/pin/deletePin', //orderNumber 拼团订单号 pId 拼团id
    homeList:'/pin/homeList',
	kickMember:'/pin/kickMember',//memberId 成员id pId拼团id
	quit:'/pin/quitPin',//退出拼团
	tipMsg:'/pin/messageMember'
}
export default spellGroupUrls;