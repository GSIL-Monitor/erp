package com.stosz.order.engine;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


/**
 * 用于处理 multipart form data 提交
 *
 * @author wangqian
 */
@Component
public class MultipartConfig {

    @Bean("multipartResolver")
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver c = new  CommonsMultipartResolver();
        c.setDefaultEncoding("utf-8");
        return c;
    }
}
