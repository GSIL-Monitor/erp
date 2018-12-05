package com.stosz.plat.engine;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Lists;
import com.stosz.plat.common.*;
import com.stosz.plat.utils.DateJsonDeserializer;
import com.stosz.plat.utils.JsonEnumDeserializer;
import com.stosz.plat.utils.JsonEnumSerializer;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther carter
 * create time    2017-12-09
 * mvc容器的消息配置抽取
 */
public final class MvcConfig {

	public static void wrapperFormatterRegistry(FormatterRegistry registry) {
		registry.addConverter(new DateTimeConverter(Lists.newArrayList("yyyy-MM-dd HH:mm:ss","yyyy-MM-dd")));
		registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
		registry.addConverter(new LocalDateConverter("yyyy-MM-dd HH:mm:ss"));
		registry.addConverter(new LocalDateTimeConverter());

	}

	public static void wrapperHttpMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		converters.add(stringConverter);
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setDefaultCharset(Charset.forName("UTF-8"));
		jsonConverter.setPrettyPrint(true);
		SimpleModule module = new SimpleModule();
		module.addSerializer(LocalDateTime.class, new CustomDateSerializer());
		module.addDeserializer(LocalDateTime.class, new DateJsonDeserializer());
//		module.addSerializer(Date.class, new CustomDateTimeSerializer());

		module.addSerializer(Enum.class, new JsonEnumSerializer());
		module.addDeserializer(Enum.class, new JsonEnumDeserializer());
		jsonConverter.getObjectMapper().registerModule(module);
		jsonConverter.getObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		// 防xss
		jsonConverter.getObjectMapper().getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
		List<MediaType> jsonMediaTypes = new ArrayList<>();
		jsonMediaTypes.add(MediaType.TEXT_HTML);
		jsonMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		jsonConverter.setSupportedMediaTypes(jsonMediaTypes);
		converters.add(jsonConverter);

		Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter = new Jaxb2RootElementHttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_XML);
		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		supportedMediaTypes.add(MediaType.TEXT_XML);
		jaxb2RootElementHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		converters.add(jaxb2RootElementHttpMessageConverter);

	}
}
