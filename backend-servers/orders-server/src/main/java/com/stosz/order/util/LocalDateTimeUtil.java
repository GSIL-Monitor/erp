package com.stosz.order.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * String 转 LocalDate/LocalDateTime
 * <p>
 * 支持解析的日期格式：
 * 2017/01/01，2017/1/1
 * 2017-01-01，2017-1-1
 * <p>
 * 支持解析的日期时间格式：
 * 2017/01/01 01:01:01，2017/01/01 1:1:1，2017/1/1 01:01:01，2017/1/1 1:1:1
 * 2017-01-01 01:01:01，2017-01-01 1:1:1，2017-1-1 01:01:01，2017-1-1 1:1:1
 *
 * @auther tangtao
 * create time  2018/1/19
 */
public class LocalDateTimeUtil {

    private static final DateTimeFormatter mainFmt = DateTimeFormatter.ofPattern("yyyy/M/d H:m:s");
    private static final DateTimeFormatter defaultFmt = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s");

    public static LocalDate toLocalDate(String dateStr) {
        if (dateStr.length() < 14)
            dateStr = dateStr.trim() + " 0:0:0";

        if (dateStr.indexOf("-") > 0) {
            return LocalDate.parse(dateStr, defaultFmt);
        } else if (dateStr.indexOf("/") > 0) {
            return LocalDate.parse(dateStr, mainFmt);
        }
        throw new RuntimeException("不支持当前时间格式解析: " + dateStr);
    }

    public static LocalDateTime toLocalDateTime(String dateTimeStr) {

        if (dateTimeStr.length() < 14)
            dateTimeStr = dateTimeStr.trim() + " 0:0:0";

        if (dateTimeStr.indexOf("-") > 0) {
            return LocalDateTime.parse(dateTimeStr, defaultFmt);
        } else if (dateTimeStr.indexOf("/") > 0) {
            return LocalDateTime.parse(dateTimeStr, mainFmt);
        }
        throw new RuntimeException("不支持当前时间格式解析: " + dateTimeStr);
    }

    /**
     * 测试用例
     *
     * @param args
     */
//    public static void main(String[] args) {
//
//        logger.info("--------------日期时间 -》 LocalDate------------------");
//
//        logger.info(LocalDateTimeUtil.toLocalDate("2017/01/01 01:01:01"));
//        logger.info(LocalDateTimeUtil.toLocalDate("2017/01/01 1:1:1"));
//        logger.info(LocalDateTimeUtil.toLocalDate("2017/1/1 01:01:01"));
//        logger.info(LocalDateTimeUtil.toLocalDate("2017/1/1 1:1:1"));
//
//        logger.info();
//
//        logger.info(LocalDateTimeUtil.toLocalDate("2017-01-01 01:01:01"));
//        logger.info(LocalDateTimeUtil.toLocalDate("2017-01-01 1:1:1"));
//        logger.info(LocalDateTimeUtil.toLocalDate("2017-1-1 01:01:01"));
//        logger.info(LocalDateTimeUtil.toLocalDate("2017-1-1 1:1:1"));
//
//        logger.info("--------------日期时间 -》 LocalDateTime------------------");
//
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017/01/01 01:01:01"));
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017/01/01 1:1:1"));
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017/1/1 01:01:01"));
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017/1/1 1:1:1"));
//
//
//        logger.info();
//
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017-01-01 01:01:01"));
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017-01-01 1:1:1"));
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017-1-1 01:01:01"));
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017-1-1 1:1:1"));
//
//
//        logger.info("-------------日期 -》 LocalDate-------------------");
//
//        logger.info(LocalDateTimeUtil.toLocalDate("2017/01/01"));
//        logger.info(LocalDateTimeUtil.toLocalDate("2017/1/1"));
//        logger.info(LocalDateTimeUtil.toLocalDate("2017-01-01"));
//        logger.info(LocalDateTimeUtil.toLocalDate("2017-1-1"));
//
//
//        logger.info("---------------日期 -》 LocalDateTime-----------------");
//
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017/01/01"));
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017/1/1"));
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017-01-01"));
//        logger.info(LocalDateTimeUtil.toLocalDateTime("2017-1-1"));
//
//        logger.info("---------------错误格式-----------------");
//
//        try {
//
//            logger.info(LocalDateTimeUtil.toLocalDateTime("2017年01月01日"));
//            logger.info(LocalDateTimeUtil.toLocalDateTime("2017-1-1 1:1"));
//            logger.info(LocalDateTimeUtil.toLocalDateTime("2017-01-35"));
//            logger.info(LocalDateTimeUtil.toLocalDateTime("2017-1-1"));
//
//        } catch (Exception e) {
//            logger.info(e.getMessage());
//        }
//
//    }

}
