package com.stosz.plat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpClient {

	private static final Logger logger = LoggerFactory.getLogger("HttpClient");

	public void pub(String serviceURL,String param)
	{
		pub2(serviceURL,param);
	}

	public String pub2(String serviceURL,String param)
	{
		URL url;
		HttpURLConnection connection = null;
		StringBuffer buffer = new StringBuffer();
		logger.info("request:" + serviceURL + "?" + param);
		try {
			url = new URL(serviceURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Length",param.length()+"");

			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(param.getBytes("UTF-8"));

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
		} catch (IOException e) {
			logger.error("发起http请求发生错误:", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		logger.info("response:"+buffer.toString());
		return buffer.toString();
	}
}