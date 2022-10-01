class pullUtil {
	//暂时是scrollview 
	//freshName （绑定）标注是否在刷新,triggeredName（绑定）//刷新后图标隐藏标注
	// static onPullDownRefresh(freshName, triggeredName,callback) {
	// 	if (this[freshName]) return;		
	// 	this[freshName] = true;
	// 	this[triggeredName] = true;
	// 	setTimeout(async () => {
	// 		await callback && callback();
	// 		console.log('刷新成功')
	// 		this[freshName] = false;
	// 		this[triggeredName] = false;

	// 	}, 10)

	// }
	static pros={};
	static onPullDownRefresh(freshName,callback) {
		// pros.self=this;
		!this.onPullDownRefresh&&(this.onPullDownRefresh={})
		this.onPullDownRefresh['freshName']=freshName;
		if (this[freshName]) return;
		  this[freshName]=true;
		setTimeout(async () => {
			await callback && callback();
			console.log('刷新成功')
			this[freshName] = false;
			if(this.scrolltolower)
			this[this.scrolltolower['freshName']]=false;
			uni.stopPullDownRefresh();	
		}, 10)
	
	}

	static scrolltolower(freshName, callback, lowercallback) {
		!this.scrolltolower&&(this.scrolltolower={})
		this.scrolltolower['freshName']=freshName;
		if (this[freshName]) return;
		this[freshName] = true;
		console.log("shuaxin")
		setTimeout(async () => {
			//是否到底
		    let lower=callback && (await callback())
			this[freshName] = false;
			if (lower) {
				// console.log("到底了。。。。。。。。")
				lowercallback && lowercallback();
				this[freshName] = true;
			}
		}, 10)
		// !this['scrolltolower']&&(this['scrolltolower']=[])
		// if(this['scrolltolower'].some(item=>item==freshName))
		//    this['scrolltolower'].push(freshName);	
	}
	static scrolltoupper(freshName, callback) {
		!this['scrolltolower']&&(this['scrolltolower']=[])
		this['scrolltolower'].push(freshName);	
		if (this[freshName]) return;
		this[freshName] = true;
		setTimeout(async () => {
			console.log("戳破天了。。。。。。")
			let lower = await callback && callback();
			this[freshName] = false;
		}, 10)
	}
	//系统onReachBottom触发不了  暂时封装的是scrollview
	//freshName （绑定）是否正在下拉 ，lowerName（绑定）无数据了加载 
	static onPullUpRefresh(freshName, lowerName) {
		this[lowerName]
	}

}
export default pullUtil;
