package com.stosz.plat.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 和币种金额相关的函数
 * 
 * @author hongzhaoyang
 *
 */
public class CurrencyUtil {

    /**
     * 中文数字正写
     */
    private static final char[] cnNumbers = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };

    private static final char[] stdNumbers = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * 货币单位
     */
    // 顺序不能变，否则下面的实现代码页需要同步修改
    private static final char[] units = { '厘', '分', '角', '元', '拾', '佰', '仟', '万', '拾', '佰', '仟',
            '亿', '拾', '佰', '仟' };

    /**
     * 是否输出货币单位元角分的完整格式，简单格式通常用于套打纸张上已印有货币单位的情形。
     */
    private static Boolean fullFormat = true;

    /**
     * 取得大写形式的字符串
     * 
     * @return 中文正写的数字字符串
     */
    public static String format(double d) {
        NumberFormat nf = new DecimalFormat("#.##");
        String original = String.valueOf(nf.format(d));
        return form(original);
    }

    public static String format(long ln) {
        return form(String.valueOf(ln));
    }

    public static String form(String original) {
        String integerPart = "";
        String floatPart = "";

        if (original.indexOf(".") > -1) {
            int dotIndex = original.indexOf(".");
            integerPart = original.substring(0, dotIndex);
            floatPart = original.substring(dotIndex + 1);
        } else {
            integerPart = original;
        }
        StringBuffer sb = new StringBuffer();

        // 整数部分处理
        for (int i = 0; i < integerPart.length(); i++) {
            int number = Integer.parseInt(String.valueOf(integerPart.charAt(i)));
            sb.append(cnNumbers[number]);
            if (fullFormat) {
                sb.append(units[integerPart.length() + 2 - i]);
            }
        }

        // 小数部分处理
        if (floatPart.length() >= 1) {
            for (int i = 0; i < floatPart.length(); i++) {
                int number = Integer.parseInt(String.valueOf(floatPart.charAt(i)));
                sb.append(cnNumbers[number]);
                if (fullFormat && i < 3) {
                    sb.append(units[2 - i]);
                }
            }
        } else if (fullFormat) {
            sb.append('整');
        }

        return sb.toString();

    }

    /**
     * 将中文大写数字字符串转换成对应的数字类型
     * 
     * @param cnNumStr
     * @return Java Number类型对象
     */
    public Number parse(String cnNumStr) {
        if (null == cnNumStr || "".equals(cnNumStr.trim())) {
            return null;
        }
        cnNumStr = cnNumStr.replaceAll("整", "");
        if (!cnNumStr.endsWith("元")) {
            cnNumStr = cnNumStr.replaceAll("元", ".");
        }
        for (int i = 0; i < cnNumbers.length; i++) {
            cnNumStr = cnNumStr.replace(cnNumbers[i], stdNumbers[i]);
        }
        for (int j = 0; j < units.length; j++) {
            cnNumStr = cnNumStr.replaceAll(units[j] + "", "");
        }
        BigDecimal b = new BigDecimal(cnNumStr);
        return b;
    }

}
