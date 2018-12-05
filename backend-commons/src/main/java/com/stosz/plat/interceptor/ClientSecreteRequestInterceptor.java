//package com.stosz.plat.interceptor;
//
//import com.stosz.plat.common.MBox;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//public class ClientSecreteRequestInterceptor implements ClientHttpRequestInterceptor{
//
//    private  final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Override
//    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//        request.getHeaders().set(MBox.REQUEST_HEADER_PROJECT_ID,getProjectName());
//        String nonce = RandomStringUtils.randomAlphanumeric(8);
//        String timestamp = System.currentTimeMillis()+"";
//        String secret = getProjectSecret();
//        String sign = secret + timestamp + nonce;
//        String signMd5 = DigestUtils.md5Hex(sign);
//        request.getHeaders().set(MBox.REQUEST_HEADER_AUTH_TIMESTAMP, timestamp);
//        request.getHeaders().set(MBox.REQUEST_HEADER_AUTH_NONCE, nonce);
//        request.getHeaders().set(MBox.REQUEST_HEADER_AUTH_SIGNATURE, signMd5);
//        logger.trace("发送请求前增加头信息，url:{} project_id:{} , timestamp:{}, nonce:{}, util:{}, signMd5:{}" , request.getURI(),getProjectName() , timestamp,nonce,sign,signMd5);
//        return execution.execute(request,body);
//    }
//
//    //todo
//    private String getProjectSecret() {
//        return null;
//    }
//
//    //todo
//    private String getProjectName() {
//        return null;
//    }
//}
