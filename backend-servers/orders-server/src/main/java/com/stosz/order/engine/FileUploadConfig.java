package com.stosz.order.engine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;

@Configuration
public class FileUploadConfig {

    @Value("${file.maxUploadSize:1048576}")
    public String maxUploadSize;

    @Value("${file.defaultEncodind:UTF-8}")
    private String defaultEncoding;

    @Bean
    public CommonsMultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        //设置文件上传的最大大小是1M
        resolver.setMaxUploadSize(Long.parseLong(maxUploadSize));
        //设置编码格式
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

}