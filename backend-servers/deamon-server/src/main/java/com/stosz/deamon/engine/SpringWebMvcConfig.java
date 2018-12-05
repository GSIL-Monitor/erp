package com.stosz.deamon.engine;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.stosz.plat.common.CustomDateSerializer;
import com.stosz.plat.common.HTMLCharacterEscapes;
import com.stosz.plat.engine.MvcConfig;
import com.stosz.plat.interceptor.ClientLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.stosz.*.web",includeFilters={
		@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class),
        @Filter(type = FilterType.ANNOTATION, value = Controller.class),
        @Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class),
})
public class SpringWebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        MvcConfig.wrapperFormatterRegistry(registry);
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();

        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);

        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


//    @Autowired
//    private LoginUserResolver loginUserResolver;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        super.addArgumentResolvers(argumentResolvers);
//        argumentResolvers.add(loginUserResolver);
//    }
    
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
    }
    
    
    @Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		converters.add(stringConverter);
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setDefaultCharset(Charset.forName("UTF-8"));
		jsonConverter.setPrettyPrint(true);
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new CustomDateSerializer());
        jsonConverter.getObjectMapper().registerModule(module);
        //防xss
        jsonConverter.getObjectMapper().getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
        List<MediaType> jsonMediaTypes = new ArrayList<MediaType>();
        jsonMediaTypes.add(MediaType.TEXT_HTML);
        jsonMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        jsonConverter.setSupportedMediaTypes(jsonMediaTypes);
        converters.add(jsonConverter);

        Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter = new Jaxb2RootElementHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        jaxb2RootElementHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(jaxb2RootElementHttpMessageConverter);
    }



}
