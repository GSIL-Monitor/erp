package com.stosz.plat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract class DateUtils {

	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	private static final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyyMM");


	/**
	 * 当前时间Date
	 * 
	 * @return Date 当前时间
	 */
	public static Date date() {
		return new Date();
	}

	public static String getLastMonth() {
		LocalDate today = LocalDate.now();
		LocalDate lastMonthDay = today.plusMonths(-1);
		return lastMonthDay.format(monthFormatter);
	}

	/**
	 * 返回 6 位的 当前年月
	 * @return
	 */
	public static String getCurrentMonth() {
		return YearMonth.now().toString();
	}

	public static List<String> getMonthList() {
		YearMonth endMonth = YearMonth.of(2017, 2);
		LocalDate tmpDay = LocalDate.now();
		YearMonth tmpMonth = YearMonth.now();
		List<String> monthList = new ArrayList<>();
		for (; tmpMonth.compareTo(endMonth) >= 0; tmpMonth = tmpMonth.plusMonths(-1)) {
			monthList.add(tmpMonth.toString());
		}

		return monthList;
	}

	/**
	 * 返回格式:2017/08\20170829111311
	 * @return
	 */
	public static String getYearMonth() {
		String fileUploadDate = "";
		LocalDate tmpDay = LocalDate.now();
		fileUploadDate = tmpDay.getYear() + File.separator + tmpDay.getMonthValue();
		return fileUploadDate;
	}

	/**
	 * 获取指定格式的日期
	 * @return
	 */
	public static String getUploadFileName() {
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter dateFormate = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
		return today.format(dateFormate);
	}

	public static long getTime(Date date) {
		return date == null ? 0 : date.getTime();
	}

	public static String format(Date date, String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.format(date);
	}
	/**
	 * yyyy-MM-dd
	 */
	public static Date getDateFormat(Date date) {
		SimpleDateFormat   format = new SimpleDateFormat("yyyy-MM-dd");
		String dStr = format.format(date);
		Date d = null;
		try {
			d = format.parse(dStr);
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		return  d;
	}

	/**
	 * 计算n后天时间
	 *
	 * @return
	 */
	public static final Date getNlateDate(Integer n) {
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间
		ca.add(Calendar.DATE, n);//
		return ca.getTime();
	}

	/**
	 * 计算n后天时间
	 *
	 * @return
	 */
	public static final Date getNlateDate(Date startDate,Integer n) {
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(startDate); // 设置时间为当前时间
		ca.add(Calendar.DATE, n);//
		return ca.getTime();
	}

	public static Date str2Date(String str,String formate){
		SimpleDateFormat spd = new SimpleDateFormat(formate);
		try {
			Date date = spd.parse(str);
			return date;
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 泰文日期 转date
	 * @param str
	 * @param formate
	 * @return
	 */
	public static Date thStr2Date(String str, String formate){
		Locale locale = new Locale("th","TH");
		SimpleDateFormat spd = new SimpleDateFormat(formate,locale);
		try {
			Date date = spd.parse(str);
			return date;
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	/**
	 * 19 Dec 17 15:17 转date
	 * @param str
	 * @param formate
	 * @return
	 */
	public static Date enStr2Date(String str, String formate){
		SimpleDateFormat spd = new SimpleDateFormat(formate,Locale.ENGLISH);
		try {
			Date date = spd.parse(str);
			return date;
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 两个日期相差的天数
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public static int dateDiffDay(Date from, Date to) {
		return (int) ((to.getTime() - from.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static void main(String[] args) {

		Date date = DateUtils.str2Date("2017-02-23 18:27:01","yyyy-MM-dd HH:mm:ss");
		logger.info(date.toString());
	}

}
