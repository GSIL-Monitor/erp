package com.stosz.plat.utils;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;

/**
 * <p>
 * 注入自定义 JsonSerializer
 * </p>
 * 
 * @author hubin
 * @date 2016-07-12
 */
public class JacksonMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public JacksonMappingJackson2HttpMessageConverter() {
        super();
        /*
         * 注入自定义 JsonSerializer
         */
        SimpleModule module = new SimpleModule();
        module.addSerializer(Enum.class, new JsonEnumSerializer());
        module.addDeserializer(Enum.class, new JsonEnumDeserializer());
        objectMapper.registerModule(module);
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return super.readInternal(clazz, inputMessage);
    }

}
