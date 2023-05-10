class dateUtils {
	static transToBJ(timeStr) {

		var data = timeStr.substr(0, 19);

		var newDate = new Date(new Date(data.replace(/T/g, ' ').replace(/-/g, '/')).getTime() + 8 * 60 * 60 * 1000);
		return newDate
	}

	static getRemainTime(str) {
		if(str){
			var time = new Date(str.replace(/-/g, '/')).getTime() - new Date().getTime();
			if (time < 0)
				return `0/0/0/0`;
			let seconds = time / 1000
			let day = Math.floor(seconds / (60 * 60 * 24))
			let hour = Math.floor(seconds / (60 * 60)) - (day * 24)
			let minute = Math.floor(seconds / 60) - (day * 24 * 60) - (hour * 60)
			let second = Math.floor(seconds) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60)
			
			return `${day}/${hour}/${minute}/${second}`
		}else{
			return `0/0/0/0`;
		}
		
	}
	static compareTo(t1, t2) {
		if (t1 instanceof Date)
			t1 = t1.getTime();
		else
			t1 = new Date(t1).getTime()

		if (t2 instanceof Date)
			t2 = t2.getTime();
		else
			t2 = new Date(t2).getTime()
		return t1 - t2

	}

}
export default dateUtils;
