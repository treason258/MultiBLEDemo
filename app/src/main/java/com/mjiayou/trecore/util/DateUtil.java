package com.mjiayou.trecore.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by treason on 15/6/8.
 */
public class DateUtil {

    public enum FormatType {
        FORMAT_DEFAULT(0, "yyyy-MM-dd HH:mm:ss"), //

        FORMAT_101(101, "yyyy-MM-dd HH:mm:ss"), //
        FORMAT_102(102, "yyyy-MM-dd"), //

        FORMAT_201(201, "yyyy年MM月dd日 HH时mm分ss秒"), //

        FORMAT_301(301, "yyyyMMddHHmmss"), // 年年月日时分秒（完全）
        FORMAT_302(302, "yyMMddHHmmss"), // 年月日时分秒
        FORMAT_303(303, "yyMMddHHmm"), // 年月日时分
        FORMAT_304(304, "yyMMdd"), // 年月日
        FORMAT_305(305, "yyMM"); // 年月


        private int id;
        private String value;

        FormatType(int id, String value) {
            this.id = id;
            this.value = value;
        }

        static FormatType getFormatType(int id) {
            for (FormatType layoutType : values()) {
                if (layoutType.getId() == id) {
                    return layoutType;
                }
            }
            return FORMAT_DEFAULT;
        }

        public int getId() {
            return id;
        }

        public String getValue() {
            return value;
        }
    }

    // ******************************** getSimpleDateFormat ********************************

    /**
     * 获取 SimpleDateFormat 对象
     */
    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    // ******************************** parseString ********************************

    /**
     * Date TO String
     *
     * @param formatType {@link com.mjiayou.trecore.util.DateUtil.FormatType FormatType}
     */
    public static String parseString(Date date, FormatType formatType) {
        try {
            return getSimpleDateFormat(formatType.getValue()).format(date);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    /**
     * Calendar TO String
     */
    public static String parseString(Calendar calendar, FormatType formatType) {
        try {
            return getSimpleDateFormat(formatType.getValue()).format(calendar.getTime());
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    /**
     * long TO String
     */
    public static String parseString(long timestamp, FormatType formatType) {
        try {
            return getSimpleDateFormat(formatType.getValue()).format(new Date(timestamp));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    /**
     * Timestamp TO String
     */
    public static String parseString(Timestamp timestamp, FormatType formatType) {
        try {
            return getSimpleDateFormat(formatType.getValue()).format(new Date(timestamp.getTime()));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    // ******************************** parseDate ********************************

    /**
     * String TO Date
     */
    public static Date parseDate(String str, FormatType formatType) {
        try {
            return getSimpleDateFormat(formatType.getValue()).parse(str);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    /**
     * Calendar TO Date
     */
    public static Date parseDate(Calendar calendar) {
        return calendar.getTime();
    }

    /**
     * Timestamp TO Date
     */
    public static Date parseDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    /**
     * long TO Date
     */
    public static Date parseDate(long timestamp) {
        try {
            return new Date(timestamp);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    // ******************************** parseCalendar ********************************

    /**
     * String TO Calendar
     */
    public static Calendar parseCalendar(String str, FormatType formatType) {
        try {
            Date date = getSimpleDateFormat(formatType.getValue()).parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    /**
     * Date TO Calendar
     */
    public static Calendar parseCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Timestamp TO Calendar
     */
    public static Calendar parseCalendar(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        return calendar;
    }

    /**
     * long TO Calendar
     */
    public static Calendar parseCalendar(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            return calendar;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    // ******************************** parseLong ********************************

    /**
     * String TO long
     */
    public static long parseLong(String str, FormatType formatType) {
        try {
            Date date = getSimpleDateFormat(formatType.getValue()).parse(str);
            return date.getTime();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return 0;
        }
    }

    /**
     * Date TO long
     */
    public static long parseLong(Date date) {
        return date.getTime();
    }

    /**
     * Calendar TO long
     */
    public static long parseLong(Calendar calendar) {
        return calendar.getTimeInMillis();
    }

    /**
     * Timestamp TO long
     */
    public static long parseLong(Timestamp timestamp) {
        return timestamp.getTime();
    }

    // ******************************** parseTimestamp ********************************

    /**
     * String TO Timestamp
     */
    public static Timestamp parseTimestamp(String str, FormatType formatType) {
        try {
            Date date = getSimpleDateFormat(formatType.getValue()).parse(str);
            return new Timestamp(date.getTime());
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    /**
     * Date TO Timestamp
     */
    public static Timestamp parseTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * Calendar TO Timestamp
     */
    public static Timestamp parseTimestamp(Calendar calendar) {
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * long TO Timestamp
     */
    public static Timestamp parseTimestamp(long timestamp) {
        try {
            return new Timestamp(timestamp);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    // ******************************** getAge & getZodica ********************************

    /**
     * 获取年龄
     */
    public static int getAge(Calendar calendar) {
        int age = 0;

        Calendar today = Calendar.getInstance();
        if (today.get(Calendar.YEAR) > calendar.get(Calendar.YEAR)) {
            age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        }

        return age;
    }

    /**
     * 根据日期获取生肖
     */
    public static String getZodica(Calendar calendar) {
        String[] zodiacArr = new String[]{"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
        int index = calendar.get(Calendar.YEAR) % 12;
        return zodiacArr[index];
    }

    /**
     * test
     */
    public static void getAgeAndZodicaTest() {
        for (int i = 1900; i <= 2100; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(i, 0, 1);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            StringBuilder builder = new StringBuilder();
            builder.append(year + "年" + month + "月" + day + "日");
            builder.append(" | ");
            builder.append("年龄 -> " + getAge(calendar));
            builder.append(" | ");
            builder.append("属相 -> " + getZodica(calendar));
            System.out.println(builder.toString());
        }
    }

    // ******************************** getConstellation ********************************

    /**
     * 获取星座
     */
    public static String getConstellation(Calendar calendar) {
        // 魔羯座 12.22-1.19
        // 水瓶座 1.20-2.18
        // 双鱼座 2.19-3.20
        // 白羊座 3.21-4.19
        // 金牛座 4.20-5.20
        // 双子座 5.21-6.21
        // 巨蟹座 6.22-7.22
        // 狮子座 7.23-8.22
        // 处女座 8.23-9.22
        // 天秤座 9.23-10.23
        // 天蝎座 10.24-11.22
        // 射手座 11.23-12.21
        String[] constellationArr = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
        int[] dayIndex = new int[]{19, 18, 20, 19, 20, 21, 22, 22, 22, 23, 22, 21}; // 两个星座分割日

        int month = calendar.get(Calendar.MONTH); // 月份默认从0开始
        int day = calendar.get(Calendar.DAY_OF_MONTH); // 日默认从1开始
        int index = month;
        // 所查询日期在分割日之后，索引+1，否则不变
        if (day > dayIndex[month]) {
            index = index + 1;
        }
        // 返回索引指向的星座string
        return constellationArr[index];
    }

    /**
     * test
     */
    public static void getConstellationTest() {
        for (int i = 0; i <= 11; i++) {
            for (int j = 1; j <= 31; j++) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(1990, i, j);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                StringBuilder builder = new StringBuilder();
                builder.append(year + "年" + month + "月" + day + "日");
                builder.append(" | ");
                builder.append("星座 -> " + getConstellation(calendar));
                System.out.println(builder.toString());
            }
        }
    }
    // ******************************** other ********************************

    /**
     * 距离现在时刻-毫秒
     */
    public static long getPassedMillisecond(Calendar calendar) {
        // 获取系统现在时间
        Calendar today = Calendar.getInstance();
        // 距离毫秒
        return today.getTimeInMillis() - calendar.getTimeInMillis();
    }

    /**
     * 距离现在时刻-秒
     */
    public static long getPassedSecond(Calendar calendar) {
        return getPassedMillisecond(calendar) / 1000;
    }

    /**
     * 距离现在时刻的-天
     */
    public static int getPassedDay(Calendar calendar) {
        return (int) (getPassedMillisecond(calendar) / (1000 * 60 * 60 * 24));
    }
}
