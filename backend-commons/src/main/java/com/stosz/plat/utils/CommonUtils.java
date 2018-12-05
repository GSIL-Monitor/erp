/*
 * Author		: Soholife
 * Description 	: 常规参数转换
 * Date			: 2015-05-23
 */

package com.stosz.plat.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class CommonUtils
{

	public static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	/*
	 * 获取字符串类型
	 */
	public static String getStringValue(Object value)	
	{
		if(value == null)	
			return "";
		else
			return value.toString().trim();
	}
	public static String getStringValue(Object value,String defaultValue)
	{
		if(null == value)
			return defaultValue.trim();
		else
			return  value.toString().trim();
	}
	
	/*
	 * 获取数字类型
	 */
	public static int getIntValue(String value)	
	{
		return getIntValue(value,0);
	}
	public static int getIntValue(Object value)	
	{
		if(value == null)	return 0;
		return getIntValue(value.toString(),0);
	}
	public static int getIntValue(String value,int defaultValue)
	{	
		try
		{

				return Integer.parseInt(value);

		}
		catch(Exception e)
		{

			return defaultValue;
		}
	}

	/*
	 * 获取数字类型
	 */
	public static long getLongValue(String value)
	{
		return getLongValue(value, 0);
	}
	public static long getLongValue(Object value)
	{
		if(value == null)	return 0;
		return getLongValue(value.toString(), 0);
	}
	public static long getLongValue(String value,int defaultValue)
	{
		try
		{
			return Long.parseLong(value);
		}
		catch(Exception e)
		{
			return defaultValue;
		}
	}
//	/*
//	 * 获取日期类型(这批方法，目前还是存在一些问题的)
//	 */
//	public static java.util.Date getDateTimeValue(String value)
//	{
//		return getDateTimeValue(value, new java.util.Date());
//	}
//	/*
// * 获取日期类型(这批方法，目前还是存在一些问题的)
// */
//	public static java.util.Date getDateTime2Value(String value)
//	{
//		return getDateTime2Value(value, new java.util.Date());
//	}

	public static java.util.Date getDateTimeValue(Object value)	
	{
		if(value != null)
		{
			return getDateTimeValue(value.toString());
		}
		else
		{
			return new java.util.Date();
		}

//		if(value instanceof java.util.Date)
//			return (java.util.Date)value;
//		else
//			return new java.util.Date();
		
	}

//	/**
//	 * 通过参数获取时间 为了和老的兼容
//	 * @param value
//	 * @param defaultValue
//	 * @return
//	 */
//	public static java.util.Date getDateTimeValue(String value,java.util.Date defaultValue) {
//		return  getDateTimeValue(value,defaultValue,"yyyy-MM-dd");
//	}
//	/**
//	 *  通过参数获取时间 新方法
//	 * @param value
//	 * @param defaultValue
//	 * @return
//	 */
//	public static java.util.Date getDateTime2Value(String value,java.util.Date defaultValue) {
//		return  getDateTimeValue(value,defaultValue,"yyyy-MM-dd HH:mm:ss");
//	}

//	public static java.util.Date getDateTimeValue(String value,java.util.Date defaultValue,String timeformat)
//	{
//
//		if(StringUtils.isEmpty(value)) {
//			return defaultValue;
//		}
//
//		try
//		{
//			return DateUtils.String2Date(value,timeformat,defaultValue);
//		}
//		catch(Exception e)
//		{
//			return defaultValue;
//		}
//	}
	
	
	
	
	/*
	 * 获取逻辑类型
	 */
	public static boolean getBooleanValue(String value)	
	{
		return getBooleanValue(value,false);
	}
	public static boolean getBooleanValue(Object value)	
	{
		if(value == null)	return false;
		
		return getBooleanValue(value.toString(),false);
	}
	public static boolean getBooleanValue(String value,boolean defaultValue)
	{				
		if("".equals(value) || value == null )	return defaultValue;
		try
		{
			return Boolean.parseBoolean(value);
		}
		catch(Exception e)
		{
			return defaultValue;
		}
	}

	/*
	 * 获取逻辑类型
	 */
	public static Double getDoubleValue(String value)
	{
		return getDoubleValue(value, 0.0);
	}
	public static Double getDoubleValue(Object value)
	{
		if(value == null)	return 0.0;

		return getDoubleValue(value.toString(), 0.0);
	}
	public static Double getDoubleValue(String value,Double defaultValue)
	{
		if(StringUtils.isEmpty(value)) 	return defaultValue;
		try
		{
			//return Double.valueOf(value).doubleValue();
			return Double.parseDouble(value);
		}
		catch(Exception e)
		{
			return defaultValue;
		}
	}

	public static String fen2yuan(String fen) {

		BigDecimal bd = new BigDecimal(fen);

		bd = bd.movePointLeft(2);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.toString();

	}

	public static String yuan2fen(String yuan) {
		BigDecimal bd = new BigDecimal(yuan);
		bd = bd.movePointRight(2);
		bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}




	public static String[] getStrArray(String[] value)
	{
		String[] str = null;
		if(value == null)
		{
			return new String[]{};
		}
		else
		{
			str = new String[value.length];
			for (int i=0;i<str.length;i++)
			{
				str[i] = value[i];
			}
		}
			return str==null?new String[]{}:str;
	}

	public static void main(String[] args) {
		String flag = "true";
		if(CommonUtils.getBooleanValue(flag))
		{
			logger.info("it's work");
		}
	}

	public static LocalDateTime getLocalDateTime(String parameter) {
		try {
			return LocalDateTime.parse(parameter, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}catch (Exception ex)
		{
			return  LocalDateTime.now();
		}
	}
}
