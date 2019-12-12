package cn.gydata.zckb.zckbinterfaceorder.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 类描述：时间操作定义类
 *
 * @version 1.0
 * @author: jeecg @date： 日期：2012-12-8 时间：下午12:15:03
 */
public class DateUtils extends PropertyEditorSupport {


    public static Date getDate() {
        return new Date();
    }


    /**
     * 字符串转换成日期
     *
     * @param str
     * @param f
     * @return
     */
    public static Date str2Date(String str, String f) {
        SimpleDateFormat sdf = new SimpleDateFormat(f);

        if (null == str || "".equals(str)) {
            return null;
        }
        Date date = null;
        try {
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期转换为字符串
     *
     * @param date   日期
     * @param format 日期格式
     * @return 字符串
     */
    public static String date2Str(Date date, String format) {
        try {
            SimpleDateFormat date_sdf = new SimpleDateFormat(format);
            return date_sdf.format(date);
        } catch (Exception e) {
            return "";
        }


    }

    /**
     * 获取指定日期之前的天数
     */
    public static Date getBeforDays(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 0 - num);
        date = calendar.getTime();
        return date;
    }

    public static Date localDateToDate(LocalDate localDate) {
        ZonedDateTime zdt = localDate.atStartOfDay(ZoneId.systemDefault());
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Long date1, Long date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date(date1));

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(date2));
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            return day2 - day1;
        }
    }
}
