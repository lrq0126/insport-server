package com.dwgj.mlxydedicatedline.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.dwgj.mlxydedicatedline.commons.DateUtil.date2String;

/**
 * 日期辅助类
 *
 * @author <a href="mailto:p.zhang@wunding.com">ZhangPan</a>
 */
@Slf4j public class DateUtil {

    /**
     * @return
     * @description 生成当前时间的年月日时分秒字符串
     * @author wlq
     * @date 2015年4月14日下午2:15:39
     */
    public static String getYmdhmsStr() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * @return
     * @description 生成当前时间的年月字符串
     * @author wlq
     * @date 2015年4月15日上午9:09:12
     */
    public static String getYMStr() {
        return DateFormatUtils.format(new Date(), "yyyyMM");
    }

    /**
     * 生成当前日期的中文描述（2018年08月08日）
     *
     * @return 日期
     */
    public static String getChinaDate() {
        return DateFormatUtils.format(new Date(), "yyyy年MM月dd日");
    }

    /**
     * 格式化时间 yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getYMD() {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
    }

    public static Date parseDate(String date) {
        try {
            return DateUtils.parseDate(date, "yyyy-MM-dd");
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 格式化时间 yyyy-MM-dd HH:mm:ss
     */
    public static Date formatToDate(String date) {
        try {
            return formatToDate(date, null);
        } catch (Exception e) {
            log.error("An error occurred when formatToDate", e);
        }
        return null;
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param format 指定日期格式 如："yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String formatToStr(Date date, String format) {
        return DateFormatUtils.format(date, format);
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param format 指定日期格式 如："yyyy-MM-dd HH:mm:ss"
     * @return
     * @throws ParseException
     */
    public static Date formatToDate(String date, String format) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        if (null == format) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            log.error("An error occurred when formatToDate", e);
        }
        return null;
    }

    /**
     * 获取当前时间
     *
     * @param format 指定日期格式 如："yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getCurrentTime(String format) {
        return DateFormatUtils.format(new Date(), format);
    }

    /**
     * 获取当月的第一天
     *
     * @param format 指定返回日期格式 如："yyyy-MM-dd HH:mm:ss"
     * @return
     * @author Sunjf
     */
    public static String getCurrentMonthFirstDay(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(calendar.getTime());
    }

    /**
     * @param
     * @return
     * @description
     * @author wlq
     * @date 2015年8月17日下午6:46:55
     */
    public static Date getExpiryDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 2);
        return calendar.getTime();
    }

    /**
     * 返回按指定格式格式化后的当前日期
     *
     * @param format yyyy-MM-dd ...
     * @return
     * @throws ParseException
     * @author jb.fu
     */
    public static Date parseFormatForCurrDate(String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(sdf.format(new Date()));
    }

    public static String convertTimeRules(Long time) {
        String result = "0秒";
        if (time != null && time > 0) {
            Long ss = time % 60;
            Long mi = time / 60;
            if (mi > 60) {
                Long hh = mi / 60;
                mi = mi % 60;
                if (hh > 24) {
                    Long day = hh / 24;
                    hh = hh % 24;
                    result = day + "天" + hh + "时" + mi + "分" + ss + "秒";
                } else {
                    result = hh + "时" + mi + "分" + ss + "秒";
                }
            } else {
                result = mi + "分" + ss + "秒";
            }
        }
        return result;
    }

    /**
     * @param str
     * @return
     * @Description: 验证字符串是否为"YYYY-MM-DD"格式的日期
     * @date 2016年3月11日下午3:43:02
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 将秒数转换为日时分秒，
     *
     * @param second
     * @return
     */
    public static String secondToTime(long second) {
        // 转换天数
        long days = second / 86400;
        // 剩余秒数
        second = second % 86400;
        // 转换小时
        long hours = second / 3600;
        // 剩余秒数
        second = second % 3600;
        // 转换分钟
        long minutes = second / 60;
        // 剩余秒数
        second = second % 60;
        if (days > 0) {
            return days + "天" + hours + ":" + minutes + ":" + second;
        } else {
            return hours + ":" + minutes + ":" + second;
        }
    }

    /**
     * 获取指定时间
     *
     * @param calendar
     * @param hour     时
     * @param minute   分
     * @param second   秒
     * @return
     */
    public static Date getSettingDate(Calendar calendar, Integer hour, Integer minute, Integer second) {
        if (hour != null) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != null) {
            calendar.set(Calendar.MINUTE, minute);
        }
        if (second != null) {
            calendar.set(Calendar.SECOND, second);
        }
        return calendar.getTime();
    }


    /**
     * 获取指定日期月份的第一天
     *
     * @param date date
     * @return 当月第一天
     */
    public static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        return calendar.getTime();
    }

    /**
     * 获取指定日期月份的最后一天
     *
     * @param date date
     * @return 当月最后一天
     */
    public static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (-index));
        return calendar.getTime();
    }

    /**
     * 指定日期的后一天
     *
     * @param date date
     * @return 指定日期的下一天时间
     */
    public static Date getNext(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 指定日期的前一天
     *
     * @param date date
     * @return 指定日期的上一天时间
     */
    public static Date getLast(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime   知道日期
     * @param beginTime 开始日期
     * @param endTime   结束日期
     * @return boolean
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 日期时间处理：年月日 + 时分 --> 年月日时分秒,如：2019-03-18 + 8：30 ---> 2019-03-18 08:30:00
     *
     * @param date 年月日
     * @param time 时分
     * @return
     */
    public static String concatDateAndTime(String date, String time) {
        String str = "";
        if (StringUtils.isNotBlank(date) && StringUtils.isNotBlank(time)) {
            if (time.length() == 4) {
                str = date.concat(" ").concat("0").concat(time).concat(":00");
            } else {
                str = date.concat(" ").concat(time).concat(":00");
            }
        }
        return str;
    }

    /**
     * 返回指定分钟数后的日期
     *
     * @param date    日期
     * @param minutes 分钟数
     * @return
     */
    public static Date addTime(Date date, int minutes) {
        Date afterDate = new Date(date.getTime() + minutes * 60 * 1000);
        return afterDate;
    }

    public static String getYmdhmStr(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * @return
     * @description 生成当前时间的年月日字符串
     * @author xyc
     * @date 2017年11月24日上午9:39:12
     */
    public static String getYMDStr() {
        return DateHelper.getYmdhmsStr().substring(0, 8);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1 较小日期
     * @param date2 较大日期
     * @return 相差天数
     */
    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 判断是否是周末
     * @param bDate
     * @return
     * @throws ParseException
     */
    public static boolean isWeekend(String bDate) throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = format1.parse(bDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else{
            return false;
        }

    }

    /**
     * 判断今天是否是周末
     * @return
     * @throws ParseException
     */
    public static boolean isWeekendToday() throws ParseException {
        return isWeekend(date2String(new Date()));
    }

}
