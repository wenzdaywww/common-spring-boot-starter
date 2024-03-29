package com.www.common.utils;

import com.www.common.data.enums.DateFormatEnum;
import com.www.common.data.enums.Weeknum;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>@Description Java8日期时间工具类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/3/22 09:58 </p>
 */
public class LocalDateUtils {
    /** 年 */
    private static final String YEAR = "year";
    /** 月 */
    private static final String MONTH = "month";
    /** 周 */
    private static final String WEEK = "week";
    /** 日 */
    private static final String DAY = "day";
    /** 时 */
    private static final String HOUR = "hour";
    /** 分 */
    private static final String MINUTE = "minute";
    /** 秒 */
    private static final String SECOND = "second";
    /**
     * <p>@Description 获取当前日期和时间字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:17 </p>
     * @return java.lang.String 日期时间字符串，例如 2015-08-11 09:51:53
     */
    public static String getLocalDateTimeStr() {
        return format(LocalDateTime.now(), DateFormatEnum.YYYYMMDDHHMMSS1.getFormat());
    }
    /**
     * <p>@Description 获取当前日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:17 </p>
     * @return java.lang.String 日期字符串，例如2015-08-11
     */
    public static String getLocalDateStr() {
        return format(LocalDate.now(), DateFormatEnum.YYYYMMDD1.getFormat());
    }
    /**
     * <p>@Description 获取当前时间字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:17 </p>
     * @return java.lang.String 时间字符串，例如 09:51:53
     */
    public static String getLocalTimeStr() {
        return format(LocalTime.now(), DateFormatEnum.HHMMSS.getFormat());
    }
    /**
     * <p>@Description 获取当前星期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:17 </p>
     * @return java.lang.String 当前星期字符串，例如 星期二
     */
    public static String getDayOfWeekStr() {
        return format(LocalDate.now(), "E");
    }
    /**
     * <p>@Description 获取指定日期是星期几 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:18 </p>
     * @param localDate 日期
     * @return java.lang.String 星期几
     */
    public static String getDayOfWeekStr(LocalDate localDate) {
        String[] weekOfDays = {Weeknum.MONDAY.getName(), Weeknum.TUESDAY.getName(), Weeknum.WEDNESDAY.getName(),
                Weeknum.THURSDAY.getName(), Weeknum.FRIDAY.getName(), Weeknum.SATURDAY.getName(), Weeknum.SUNDAY.getName()};
        int dayOfWeek = localDate.getDayOfWeek().getValue() - 1;
        return weekOfDays[dayOfWeek];
    }
    /**
     * <p>@Description 获取日期时间字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:19 </p>
     * @param temporal 需要转化的日期时间
     * @param pattern 时间格式
     * @return java.lang.String 日期时间字符串，例如 2015-08-11 09:51:53
     */
    public static String format(TemporalAccessor temporal, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(temporal);
    }
    /**
     * <p>@Description 日期时间字符串转换为日期时间(java.time.LocalDateTime) </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:19 </p>
     * @param localDateTimeStr 日期时间字符串
     * @param pattern 日期时间格式 例如DateFormatEnum.YYYYMMDDHHMMSS1.format
     * @return java.time.LocalDateTime 日期时间
     */
    public static LocalDateTime parseLocalDateTime(String localDateTimeStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(localDateTimeStr, dateTimeFormatter);
    }
    /**
     * <p>@Description 日期字符串转换为日期(java.time.LocalDate) </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:19 </p>
     * @param localDateStr 日期字符串
     * @param pattern 日期格式 例如DATE_PATTERN
     * @return java.time.LocalDate 日期
     */
    public static LocalDate parseLocalDate(String localDateStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(localDateStr, dateTimeFormatter);
    }
    /**
     * <p>@Description 获取指定日期时间加上指定数量日期时间单位之后的日期时间 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:20 </p>
     * @param localDateTime 日期时间
     * @param num 数量
     * @param chronoUnit 日期时间单位
     * @return java.time.LocalDateTime 新的日期时间
     */
    public static LocalDateTime plus(LocalDateTime localDateTime, int num, ChronoUnit chronoUnit) {
        return localDateTime.plus(num, chronoUnit);
    }
    /**
     * <p>@Description 获取指定日期时间减去指定数量日期时间单位之后的日期时间 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:20 </p>
     * @param localDateTime 日期时间
     * @param num           数量
     * @param chronoUnit    日期时间单位
     * @return java.time.LocalDateTime 新的日期时间
     */
    public static LocalDateTime minus(LocalDateTime localDateTime, int num, ChronoUnit chronoUnit) {
        return localDateTime.minus(num, chronoUnit);
    }
    /**
     * <p>@Description 根据ChronoUnit计算两个日期时间之间相隔日期时间 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:21 </p>
     * @param start      开始日期时间
     * @param end        结束日期时间
     * @param chronoUnit 日期时间单位
     * @return long 相隔日期时间
     */
    public static long getChronoUnitBetween(LocalDateTime start, LocalDateTime end, ChronoUnit chronoUnit) {
        return Math.abs(start.until(end, chronoUnit));
    }
    /**
     * <p>@Description 根据ChronoUnit计算两个日期之间相隔年数或月数或天数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:21 </p>
     * @param start      开始日期
     * @param end        结束日期
     * @param chronoUnit 日期时间单位,(ChronoUnit.YEARS,ChronoUnit.MONTHS,ChronoUnit.WEEKS,ChronoUnit.DAYS)
     * @return long 相隔年数或月数或天数
     */
    public static long getChronoUnitBetween(LocalDate start, LocalDate end, ChronoUnit chronoUnit) {
        return Math.abs(start.until(end, chronoUnit));
    }
    /**
     * <p>@Description 获取本年第一天的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:21 </p>
     *
     * @return java.lang.String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfYearStr() {
        return getFirstDayOfYearStr(LocalDateTime.now());
    }
    /**
     * <p>@Description 获取本年最后一天的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:21 </p>
     * @return java.lang.String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfYearStr() {
        return getLastDayOfYearStr(LocalDateTime.now());
    }
    /**
     * <p>@Description 获取指定日期当年第一天的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:22 </p>
     * @param localDateTime 指定日期时间
     * @return java.lang.String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfYearStr(LocalDateTime localDateTime) {
        return getFirstDayOfYearStr(localDateTime, DateFormatEnum.YYYYMMDDHHMMSS1.getFormat());
    }
    /**
     * <p>@Description 获取指定日期当年最后一天的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:22 </p>
     * @param localDateTime 指定日期时间
     * @return java.lang.String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfYearStr(LocalDateTime localDateTime) {
        return getLastDayOfYearStr(localDateTime, DateFormatEnum.YYYYMMDDHHMMSS1.getFormat());
    }
    /**
     * <p>@Description 获取指定日期当年第一天的日期字符串,带日期格式化参数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:22 </p>
     * @param localDateTime 指定日期时间
     * @param pattern 日期时间格式
     * @return java.lang.String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfYearStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0), pattern);
    }
    /**
     * <p>@Description 获取指定日期当年最后一天的日期字符串,带日期格式化参数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:22 </p>
     * @param localDateTime 指定日期时间
     * @param pattern 日期时间格式
     * @return java.lang.String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfYearStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.with(TemporalAdjusters.lastDayOfYear()).withHour(23).withMinute(59).withSecond(59), pattern);
    }
    /**
     * <p>@Description 获取本月第一天的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:23 </p>
     * @return java.lang.String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfMonthStr() {
        return getFirstDayOfMonthStr(LocalDateTime.now());
    }
    /**
     * <p>@Description 获取本月最后一天的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:23 </p>
     * @return java.lang.String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfMonthStr() {
        return getLastDayOfMonthStr(LocalDateTime.now());
    }
    /**
     * <p>@Description 获取指定日期当月第一天的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:23 </p>
     * @param localDateTime 指定日期时间
     * @return java.lang.String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getFirstDayOfMonthStr(LocalDateTime localDateTime) {
        return getFirstDayOfMonthStr(localDateTime, DateFormatEnum.YYYYMMDDHHMMSS1.getFormat());
    }
    /**
     * <p>@Description 获取指定日期当月最后一天的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:24 </p>
     * @param localDateTime 指定日期时间
     * @return java.lang.String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfMonthStr(LocalDateTime localDateTime) {
        return getLastDayOfMonthStr(localDateTime, DateFormatEnum.YYYYMMDDHHMMSS1.getFormat());
    }
    /**
     * <p>@Description 获取指定日期当月第一天的日期字符串,带日期格式化参数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:24 </p>
     * @param localDateTime 指定日期时间
     * @param pattern 日期时间格式
     * @return java.lang.String 日期字符串
     */
    public static String getFirstDayOfMonthStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0), pattern);
    }
    /**
     * <p>@Description 获取指定日期当月最后一天的日期字符串,带日期格式化参数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:24 </p>
     * @param localDateTime 指定日期时间
     * @param pattern       日期时间格式
     * @return java.lang.String 日期字符串
     */
    public static String getLastDayOfMonthStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59), pattern);
    }
    /**
     * <p>@Description 获取本周第一天的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:25 </p>
     * @return java.lang.String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfWeekStr() {
        return getFirstDayOfWeekStr(LocalDateTime.now());
    }
    /**
     * <p>@Description 获取本周最后一天的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:25 </p>
     * @return java.lang.String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfWeekStr() {
        return getLastDayOfWeekStr(LocalDateTime.now());
    }
    /**
     * <p>@Description 获取指定日期当周第一天的日期字符串,这里第一天为周一 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:26 </p>
     * @param localDateTime 指定日期时间
     * @return java.lang.String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfWeekStr(LocalDateTime localDateTime) {
        return getFirstDayOfWeekStr(localDateTime, DateFormatEnum.YYYYMMDDHHMMSS1.getFormat());
    }
    /**
     * <p>@Description 获取指定日期当周最后一天的日期字符串,这里最后一天为周日 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:26 </p>
     * @param localDateTime 指定日期时间
     * @return java.lang.String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfWeekStr(LocalDateTime localDateTime) {
        return getLastDayOfWeekStr(localDateTime, DateFormatEnum.YYYYMMDDHHMMSS1.getFormat());
    }
    /**
     * <p>@Description 获取指定日期当周第一天的日期字符串,这里第一天为周一,带日期格式化参数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:26 </p>
     * @param localDateTime 指定日期时间
     * @param pattern       日期时间格式
     * @return java.lang.String 日期字符串
     */
    public static String getFirstDayOfWeekStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0), pattern);
    }
    /**
     * <p>@Description 获取指定日期当周最后一天的日期字符串,这里最后一天为周日,带日期格式化参数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:26 </p>
     * @param localDateTime 指定日期时间
     * @param pattern       日期时间格式
     * @return java.lang.String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfWeekStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.with(DayOfWeek.SUNDAY).withHour(23).withMinute(59).withSecond(59), pattern);
    }
    /**
     * <p>@Description 获取今天开始时间的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:27 </p>
     * @return java.lang.String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getStartTimeOfDayStr() {
        return getStartTimeOfDayStr(LocalDateTime.now());
    }
    /**
     * <p>@Description 获取今天结束时间的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:27 </p>
     * @return java.lang.String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getEndTimeOfDayStr() {
        return getEndTimeOfDayStr(LocalDateTime.now());
    }
    /**
     * <p>@Description 获取指定日期开始时间的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:27 </p>
     * @param localDateTime 指定日期时间
     * @return java.lang.String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getStartTimeOfDayStr(LocalDateTime localDateTime) {
        return getStartTimeOfDayStr(localDateTime, DateFormatEnum.YYYYMMDDHHMMSS1.getFormat());
    }
    /**
     * <p>@Description 获取指定日期结束时间的日期字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:27 </p>
     * @param localDateTime 指定日期时间
     * @return java.lang.String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getEndTimeOfDayStr(LocalDateTime localDateTime) {
        return getEndTimeOfDayStr(localDateTime, DateFormatEnum.YYYYMMDDHHMMSS1.getFormat());
    }
    /**
     * <p>@Description 获取指定日期开始时间的日期字符串,带日期格式化参数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:27 </p>
     * @param localDateTime 指定日期时间
     * @param pattern 日期时间格式
     * @return java.lang.String 日期字符串
     */
    public static String getStartTimeOfDayStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.withHour(0).withMinute(0).withSecond(0), pattern);
    }
    /**
     * <p>@Description 获取指定日期结束时间的日期字符串,带日期格式化参数 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:28 </p>
     * @param localDateTime 指定日期时间
     * @param pattern 日期时间格式
     * @return java.lang.String 日期字符串
     */
    public static String getEndTimeOfDayStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.withHour(23).withMinute(59).withSecond(59), pattern);
    }
    /**
     * <p>@Description 切割日期。按照周期切割成小段日期段。例如： </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 10:28 </p>
     * @param startDate 开始日期（yyyy-MM-dd）
     * @param endDate   结束日期（yyyy-MM-dd）
     * @param period    周期（天，周，月，年）
     * @return 切割之后的日期集合
     * <li>startDate="2019-02-28",endDate="2019-03-05",period="day"</li>
     * <li>结果为：[2019-02-28, 2019-03-01, 2019-03-02, 2019-03-03, 2019-03-04, 2019-03-05]</li><br>
     * <li>startDate="2019-02-28",endDate="2019-03-25",period="week"</li>
     * <li>结果为：[2019-02-28,2019-03-06, 2019-03-07,2019-03-13, 2019-03-14,2019-03-20,
     * 2019-03-21,2019-03-25]</li><br>
     * <li>startDate="2019-02-28",endDate="2019-05-25",period="month"</li>
     * <li>结果为：[2019-02-28,2019-02-28, 2019-03-01,2019-03-31, 2019-04-01,2019-04-30,
     * 2019-05-01,2019-05-25]</li><br>
     * <li>startDate="2019-02-28",endDate="2020-05-25",period="year"</li>
     * <li>结果为：[2019-02-28,2019-12-31, 2020-01-01,2020-05-25]</li><br>
     */
    public static List<String> listDateStrs(String startDate, String endDate, String period) {
        List<String> result = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormatEnum.YYYYMMDD1.getFormat());
        LocalDate end = LocalDate.parse(endDate, dateTimeFormatter);
        LocalDate start = LocalDate.parse(startDate, dateTimeFormatter);
        LocalDate tmp = start;
        switch (period) {
            case DAY:
                while (start.isBefore(end) || start.isEqual(end)) {
                    result.add(start.toString());
                    start = start.plusDays(1);
                }
                break;
            case WEEK:
                while (tmp.isBefore(end) || tmp.isEqual(end)) {
                    if (tmp.plusDays(6).isAfter(end)) {
                        result.add(tmp.toString() + "," + end);
                    } else {
                        result.add(tmp.toString() + "," + tmp.plusDays(6));
                    }
                    tmp = tmp.plusDays(7);
                }
                break;
            case MONTH:
                while (tmp.isBefore(end) || tmp.isEqual(end)) {
                    LocalDate lastDayOfMonth = tmp.with(TemporalAdjusters.lastDayOfMonth());
                    if (lastDayOfMonth.isAfter(end)) {
                        result.add(tmp.toString() + "," + end);
                    } else {
                        result.add(tmp.toString() + "," + lastDayOfMonth);
                    }
                    tmp = lastDayOfMonth.plusDays(1);
                }
                break;
            case YEAR:
                while (tmp.isBefore(end) || tmp.isEqual(end)) {
                    LocalDate lastDayOfYear = tmp.with(TemporalAdjusters.lastDayOfYear());
                    if (lastDayOfYear.isAfter(end)) {
                        result.add(tmp.toString() + "," + end);
                    } else {
                        result.add(tmp.toString() + "," + lastDayOfYear);
                    }
                    tmp = lastDayOfYear.plusDays(1);
                }
                break;
            default:
                break;
        }
        return result;
    }

}
