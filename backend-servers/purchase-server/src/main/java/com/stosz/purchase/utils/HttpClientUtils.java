package com.stosz.purchase.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

    public static String doPost(String postAction, Map<String, Object> parameters, String charset) throws Exception {
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                .build();
        HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(defaultRequestConfig).build();
        HttpPost httpPost = new HttpPost(postAction);

        List<NameValuePair> valuePairs = new ArrayList<>();
        parameters.entrySet().forEach(item -> {
            String value=item.getValue()==null? "":String.valueOf(item.getValue());
            valuePairs.add(new BasicNameValuePair(item.getKey(),value));
        });
        httpPost.setEntity(new UrlEncodedFormEntity(valuePairs, charset));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            return EntityUtils.toString(httpResponse.getEntity(), charset);
        }
        return null;
    }
}
