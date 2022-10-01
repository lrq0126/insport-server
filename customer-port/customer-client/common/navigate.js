class navigate{
	constructor(...arg) {
	    
	}
	static param(data) {
	  let url = ''
	  for (var k in data) {
	    let value = data[k] !== undefined ? data[k] : ''
	    url += '&' + k + '=' + encodeURIComponent(value)
	  }
	  return url ? url.substring(1) : ''
	}
	static navigateTo(url,data){
		if(data)
		url += (url.indexOf('?') < 0 ? '?' : '&') + this.param(data)
		uni.navigateTo({
			url
		})
	}
	static navigateBack(delta=1,data){
		var pages = getCurrentPages();
		var index=pages.length-(delta+1);
		var prevPage = pages[index]; //上一个页面
		// prevPage.setData({
		// userdata: 
		// })
		//#ifdef MP-WEIXIN
		 prevPage.setData({
		     preData:data
		 })
		//#endif
		//#ifdef H5
		
		prevPage.$vm.preData=data;
	    //#endif
		uni.navigateBack({//返回
		 delta: delta
		})
	}
}
export default navigate;