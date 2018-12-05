package com.stosz.plat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * EncryptUtils
 *
 * @ClassName EncryptUtils
 * @author kelvem
 * @version 1.0
 */
public abstract class EncryptUtils {

    public static final Logger logger = LoggerFactory.getLogger(EncryptUtils.class);
    /**
     * 对字符串加密,加密算法使用SHA-256
     * 
     * @param content
     *            要加密的字符串
     * @return
     */
    public static String sha256(String content) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = content.getBytes();
        try {
            String encName = "SHA-256";
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * 对字符串加密,加密算法使用MD5
     * 
     * @param content
     *            要加密的字符串
     * @return
     */
    public static String md5(String content) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = content.getBytes();
        try {
            String encName = "MD5";
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * <p>
     * salt MD5 加密
     * </p>
     * 
     * @param text
     *            加密原文
     * @return
     */
    public static String saltMD5(String text) {
        StringBuffer content = new StringBuffer();
        content.append("92051859bd0fb79155-");
        content.append(text);
        return md5(content.toString());
    }

    /**
     * main
     *
     * @param args
     * @return void @throws
     */
    public static void main(String[] args) {
        String content = "458972847@qq.com";
        String s = EncryptUtils.sha256(content);
        logger.info(content + " sha256 : " + s);
        String s1 = EncryptUtils.md5(content);
        logger.info(content + " md5 : " + s1);
        System.err.println(saltMD5("123456"));
    }

}
