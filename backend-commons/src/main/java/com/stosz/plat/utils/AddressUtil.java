/**
 * 
 */
package com.stosz.plat.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * 地址工具类
 * 
 * @author kelvem
 */
public class AddressUtil {

    protected static final Logger logger = LoggerFactory.getLogger(AddressUtil.class);

    private AddressUtil() {
    }

    /**
     * 验证Email
     * 
     * @param email
     *            email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证邮政信箱(PostOfficeBox)
     * 
     * @param address
     * @param city
     * @return 检查Pobox
     */
    public static boolean checkPobox(String address, String city) {
        if (address.toLowerCase().indexOf(" apo ") >= 0
                || address.toLowerCase().indexOf(" dpo ") >= 0
                || address.toLowerCase().indexOf(" pod ") >= 0
                || address.toLowerCase().indexOf(" fpo ") >= 0
                || address.toLowerCase().indexOf("pobox") >= 0
                || address.toLowerCase().indexOf("p.o.box") >= 0
                || address.toLowerCase().indexOf("po box") >= 0
                || city.toLowerCase().equals("dpo")) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * 地址编码
     * </p>
     */
    public static String encodeUrl(String url) {
        try {
            if (StringUtils.isNotEmpty(url)) {
                return URLEncoder.encode(url, "UTF-8").replaceAll("\\+", "%20").replaceAll("%2F",
                        "/");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("地址：{} 转换错误：{}", url, e.getMessage());
        }
        return null;
    }

}
