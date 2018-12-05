package com.stosz.tms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalUtils {

	public static final Logger logger = LoggerFactory.getLogger(DecimalUtils.class);

	public static String formateDecimal(BigDecimal decimal, String formate) {
		DecimalFormat format = new DecimalFormat(formate);
		return format.format(decimal);
	}

	/**
	 * 提供精确的除法运算方法div
	 * @param value1 被除数
	 * @param value2 除数
	 * @param scale 精确范围
	 * @return 两个参数的商
	 * @throws IllegalAccessException
	 */
	public static double div(double value1,double value2,int scale) throws IllegalAccessException{
		//如果精确范围小于0，抛出异常信息
		if(scale<0){
			throw new IllegalAccessException("精确度不能小于0");
		}
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.divide(b2, scale).doubleValue();
	}

	/**
	 * 提供精确乘法运算的mul方法
	 * @param value1 被乘数
	 * @param value2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double value1,double value2){
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.multiply(b2).doubleValue();
	}
	
	public static void main(String[] args) {
		
		String str=DecimalUtils.formateDecimal(new BigDecimal(10.136), "#.##");
		logger.info(str);
	}
}
