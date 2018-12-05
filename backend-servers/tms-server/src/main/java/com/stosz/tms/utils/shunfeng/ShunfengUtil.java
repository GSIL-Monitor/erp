package com.stosz.tms.utils.shunfeng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class ShunfengUtil {
	public static final Logger logger = LoggerFactory.getLogger(
			ShunfengUtil.class);
	public static String loadFile(String fileName) {
		InputStream fis;
		try {
			fis = new FileInputStream(fileName);
			byte[] bs = new byte[fis.available()];
			fis.read(bs);
			String res = new String(bs, "utf8");
			fis.close();
			return res;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String md5EncryptAndBase64(String str) {
		return encodeBase64(md5Encrypt(str));
	}

	private static byte[] md5Encrypt(String encryptStr) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(encryptStr.getBytes("utf8"));
			return md5.digest();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String encodeBase64(byte[] b) {
		BASE64Encoder base64Encode = new BASE64Encoder();
		String str = base64Encode.encode(b);
		return str;
	}

	public static void main(String[] args) {
		String xml = loadFile(args[0]);
		String checkword = loadFile(args[1]);
		logger.info(md5EncryptAndBase64(xml + checkword));
		logger.info(md5EncryptAndBase64("abc"));
		logger.info(md5EncryptAndBase64("中"));
	}
}

