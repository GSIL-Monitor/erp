package com.stosz.plat.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * yanghuaqing
 * 17/4/16
 */
public class IpUtils {

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 判断IP地址的合法性，这里采用了正则表达式的方法来判断
     * return true，合法,false 不合法
     * */
    public static boolean ipCheck(String str) {
        if (str != null && !str.isEmpty()) {
            // 定义正则表达式
            String regex="^(((\\d{1,2})|(1\\d{2})|(2[2-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[2-4]\\d)|(25[0-5]))$";
            // 判断ip地址是否与正则表达式匹配
            if (str.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }
}
