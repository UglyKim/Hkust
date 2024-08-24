package com.hkust.utils;

import cn.hutool.core.date.DatePattern;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    /**
     * 获取当前时间，包含时分秒，去掉毫秒，类型为LocalDateTime;
     *
     * @return
     */
    public static LocalDateTime getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MS_PATTERN);
        String strDateTime = now.format(formatter);
        return LocalDateTime.parse(strDateTime, formatter);
    }

    /**
     * 获取当前日期
     * @return 当前日期
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前日期时间
     *
     * @return 当前日期时间
     */
    /*public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }*/

    /**
     * 将字符串转换为 LocalDate
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return 转换后的 LocalDate
     */
    public static LocalDate parseDate(String dateStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * 将字符串转换为 LocalDateTime
     *
     * @param dateTimeStr 日期时间字符串
     * @param pattern     日期时间格式
     * @return 转换后的 LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    /**
     * 将 LocalDate 转换为字符串
     *
     * @param date    LocalDate 对象
     * @param pattern 日期格式
     * @return 转换后的字符串
     */
    public static String formatDate(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    /**
     * 将 LocalDateTime 转换为字符串
     *
     * @param dateTime LocalDateTime 对象
     * @param pattern  日期时间格式
     * @return 转换后的字符串
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param startDate 起始日期
     * @param endDate   结束日期
     * @return 两个日期之间的天数
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

}
