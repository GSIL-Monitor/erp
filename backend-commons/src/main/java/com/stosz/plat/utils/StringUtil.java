/**
 * 
 */
package com.stosz.plat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

/**
 * @author kelvem
 *
 */
public class StringUtil {

	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	private StringUtil() {
		// do nothing
	}

	public static String valueOf(InputStream is) {

		try {
			int i = -1;
			byte[] b = new byte[1024 * 100];
			StringBuffer sb = new StringBuffer();
			while ((i = is.read(b)) != -1) {
				sb.append(new String(b, 0, i));
			}
			String content = sb.toString();
			return content;

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	// ###
	public static String toUpperFirstChar(String str) {

		if (str == null || str.isEmpty()) {
			return str;
		}

		StringBuffer buf = new StringBuffer(str);
		String first = String.valueOf(buf.charAt(0)).toUpperCase();
		buf.setCharAt(0, first.charAt(0));

		return buf.toString();
	}

	// ###
	public static String toLowerFirstChar(String str) {

		if (str == null || str.isEmpty()) {
			return str;
		}

		StringBuffer buf = new StringBuffer(str);
		String first = String.valueOf(buf.charAt(0)).toLowerCase();
		buf.setCharAt(0, first.charAt(0));

		return buf.toString();
	}

	// columnModel -> COLUMN_MODEL
	public static String fieldToColumnName(String name) {

		if (name == null || name.isEmpty()) {
			return name;
		}

		StringBuffer buf = new StringBuffer(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			if ('_' != buf.charAt(i - 1) && Character.isUpperCase(buf.charAt(i)) && !Character.isUpperCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toUpperCase();
	}

	public static String toHTMLString(String in) {
		if (in == null || in.length() <= 0) {
			return "";
		}

		StringBuffer out = new StringBuffer();
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\'')
				out.append("'");
			else if (c == '\"')
				out.append("");
			else if (c == '<')
				out.append("&lt;");
			else if (c == '>')
				out.append("&gt;");
			else if (c == '&')
				out.append("&amp;");
			else if (c == ' ')
				out.append("&nbsp;");
			else if (c == '\n')
				out.append("<br>");
			else if (c == '\r')
				continue;
			else
				out.append(c);
		}
		return out.toString();
	}

	/**
	 * List通过split拼接成 字符串
	 *
	 * @param list
	 * @param delimiter
	 * @return String
	 * @throws
	 */
	public static String toString(List<String> list, String delimiter) {

		if (list == null || delimiter == null) {
			return "";
		}
		String result = String.join(delimiter, list.toArray(new String[0]));
		return result;
	}

	/**
	 * trim
	 *
	 * @param str
	 * @return String
	 * @throws
	 */
	public static String trim(String str) {

		if (str == null) {
			return null;
		} else {
			return str.trim();
		}
	}

	public static String formatDecimal(BigDecimal decimal, int scale) {
		if (decimal == null) {
			return "null";
		}
		StringBuilder sb = new StringBuilder("0");
		if (scale > 0) {
			sb.append(".");
		}
		for (int i = 0; i < scale; i++) {
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		df.setRoundingMode(RoundingMode.FLOOR);
		return df.format(decimal);
	}

	/**
	 * 生成字母加数字的随机数
	 * @param length(生成的随机数的位数)
	 * @return
	 */
	public static String getRandomCharAndNum(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) // 字符串
			{
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
			{
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	public static <T> T nvl(T value, T defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value;
	}
	
	public static <T, X> X nvl(T value, X dValue, X defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (dValue == null) {
			return defaultValue;
		}
		return dValue;
	}

	public static String concat(Object... params) {
		if (params.length <= 0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (Object item : params) {
			builder.append(item);
		}
		return builder.toString();
	}

	public static String concat(String... params) {
		if (params.length <= 0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (Object item : params) {
			builder.append(item);
		}
		return builder.toString();
	}

	public static String concatExcludeNull(Object... params) {
		if (params.length <= 0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (Object item : params) {
			if (item != null) {
				builder.append(item);
			}
		}
		return builder.toString();
	}

	public static String concatBySplit(String split, String... params) {
		if (params.length <= 0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (String item : params) {
			if (builder.length() != 0) {
				builder.append(split);
			}
			builder.append(item);
		}
		return builder.toString();
	}

	public static String concatBySplitExcludeNull(String split, Object... params) {
		if (params.length <= 0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (Object item : params) {
			if (builder.length() != 0) {
				builder.append(split);
			}
			if (item != null) {
				builder.append(item);
			}
		}
		return builder.toString();
	}

	public static String objToStr(Object dest) {
		if (dest == null) {
			return null;
		}
		return dest.toString();
	}

	public static String generateNo(String prefix, Integer id) {
		String currentDay = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		return StringUtil.concat(prefix, currentDay, Integer.toString(id));
	}

	public static Integer strToInt(String str, Integer defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static boolean isAnyEmpty(String... items) {
		if (items.length == 0) {
			return false;
		}
		for (String item : items) {
			if (!org.springframework.util.StringUtils.hasText(item)) {
				return false;
			}
		}
		return true;
	}
}
