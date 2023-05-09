package com.example.warehouse.common;

import org.springframework.context.annotation.Configuration;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 获取累增流水号
 * @author:wenguosheng
 * @date:2019年4月14日11:09:34
 * @version:1.0
 */
@Configuration
public class SequenceCode {
	/**
	 * 限制位数
	 */
	private static final int mumIntegerDigits = 5;
	/**
     * 计数器
     */
    private static short SerialNocounter = (short) 0;
    /**
     * 累加
     * @author:wenguosheng
     * @date:2019年4月14日11:09:53
     * @return
     */
    private static synchronized short getSerialNoCount() {
    	if (SerialNocounter < 0){
        	SerialNocounter = 0;
    	}
        return SerialNocounter++;
    }
	/**
	 * 流水号
	 *
	 * 规则说明：
	 *  编码规则制定如下，请查看。（说明：单号不存在空格，空格只为了展示说明设计规则）
     *  1、[货物号]规则：GOODS 20190101 180558 00001，GOODS+20190101是年月日+180558(时分秒)+4位数（按次序增加）；
     *  2、[客户号] CUM 20190101 180558 00001，CUM+20190101是年月日+180558(时分秒)+4位数（按次序增加）；
     *  3、[用户号] USER 20190101 180558 00001，USER+20190101是年月日+180558(时分秒)+4位数（按次序增加）；
     *  4、[操作员号] OPR 20190101 180558 00001 ，OPR+20190101是年月日+180558(时分秒)+4位数（按次序增加）。
	 * @author:wenguosheng
	 * @date:2019年4月14日11:09:53
	 * @param serialNoType 流水类别
	 * @return
	 * @throws Exception
	 */
	public static String gainSerialNo(String serialNoType) {
		StringBuffer sbStr = new StringBuffer("");
		sbStr.append(serialNoType).append("").append(getPk()).append("").append(formatNum(getSerialNoCount()));
		return sbStr.toString();
	}

	/**
	 * 获取10位数的唯一编号
	 * @param serialNoType
	 * @return
	 */
	private static int inc = 0;
	public static String getId(String serialNoType){
		long id = Long.parseLong(String.valueOf(System.currentTimeMillis())
			.substring(1,10)
			.concat(String.valueOf(inc)));
		inc = (inc+1)%10;
		return serialNoType + id;
	}


	/**
	 * 获取时间戳（精确至秒）
	 * @author:wenguosheng
	 * @date:2019年4月14日11:10:30
	 * @return
	 */
	private static String getPk(){
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateformat.format(new Date());
	}
	/**
	 * 将整型数字左补0使获得5位字符串
	 * @author:wenguosheng
	 * @date:2019年4月14日11:10:30
	 * @param intval 需要填充的整数
	 * @return
	 */
	private static String formatNum(int intval) {
    	NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);//设置是否使用分组
        nf.setMaximumIntegerDigits(mumIntegerDigits);//设置最大整数位数
        nf.setMinimumIntegerDigits(mumIntegerDigits);//设置最小整数位数
        return nf.format(intval);
    }

	private static String formatNum(int intval, int minDigits) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);//设置是否使用分组
		nf.setMaximumIntegerDigits(minDigits);//设置最大整数位数
		nf.setMinimumIntegerDigits(minDigits);//设置最小整数位数
		return nf.format(intval);
	}

	// 发货任务单号生成
	public static String deliveryTaskOrder(int count) {
		String str = DateUtil.dateToString(new Date(), "yyyyMMdd");
		String number = formatNum(count, 3);
		return "DT" + str + number;
	}

	// 流水号生成
	public static String businessNumber() {
		Long str = System.currentTimeMillis();
		String s = str.toString();
		s = s.substring(1, s.length() - 2);
		int i = 0;
		while (i < 4) {
			int d = (int) (Math.random() * 10);
			s += d;
			i++;
		}
		return s + "-" + (char) (Math.random() * 26 + 'A');
	}

	public static String insuranceNo(int number){
		StringBuilder countStr = new StringBuilder(String.valueOf(number));
		while (countStr.length() < 5){
			countStr.insert(0, "0");
		}
		return "BX-"+DateUtil.FormatDate(new Date(), DateUtil.DATESTYLE_8)+countStr;
	}

	/**
	 * 拼团单号生成
	 * P + 3年为一个周期的时间戳 + 1位随机数
	 * @param
	 * @return
	 */
	public static String pMainOrderNumber() {
		long current = System.currentTimeMillis();//当前时间毫秒数
		String count = String.valueOf(current);
		count = count.substring(2, count.length() - 3);
		return "P" + count + (int)(Math.random()*10);
	}


	public static String generateSigningKey() {
		char [] en = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		StringBuilder salt = new StringBuilder();
		Random random = new Random();
		int i;
		while (salt.length() < 12){
			i = random.nextInt(35);
			salt.append(en[i]);
		}
		return salt.toString();
	}
}
