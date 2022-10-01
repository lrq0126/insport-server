package com.dwgj.mlxydedicatedline.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author guoshengwen
 */
public class DateHelper {

    public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDD_HHMMSS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YYYYMMDD_HHMMSS_S = "yyyy-MM-dd HH:mm:ss.S";
    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String MMDD = "MMdd";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY = "yyyy";

    public static String getYmdhmsStr() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
    }

    public static String getYMStr() {
        return DateFormatUtils.format(new Date(), "yyyyMM");
    }

    public static String formatDate(Date date, String targetformat) {
        return DateFormatUtils.format(date, targetformat);
    }

    public static String formatDate(long date, String targetformat) {
        return DateFormatUtils.format(date, targetformat);
    }

    public static String formatDate(Long date) {
        if (date == null) {
            return "";
        }
        return DateFormatUtils.format(date.longValue(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDateYYYYMM(Long date) {
        if (date == null) {
            return "";
        }
        return DateFormatUtils.format(date.longValue(), "yyyyMM");
    }

    public static String formatDateYYYY(Long date) {
        if (date == null) {
            return "";
        }
        return DateFormatUtils.format(date.longValue(), "yyyy");
    }

    public static Date toDate(String dateStr, String sourceformat) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sourceformat);
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {

        }
        return date;
    }

    public static int getSysYear() {
        return Calendar.getInstance().get(1);
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static Timestamp getCurrentTimestamp() {
        long date = getCurrentDate().getTime();
        return new Timestamp(date);
    }

    public static Date getCurrentOnlyDate() {
        Date now = getCurrentDate();
        String onlyDateStr = formatDate(now, "yyyy-MM-dd");
        return toDate(onlyDateStr, "yyyy-MM-dd");
    }

    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(7);
    }

    public static int dateSub(Date startDate, Date endDate) {
        long start = startDate.getTime();
        long end = endDate.getTime();
        return (int)((end - start) / 86400000L);
    }

    public static Calendar toCalendar(Date date) {
        return DateUtils.toCalendar(date);
    }

}