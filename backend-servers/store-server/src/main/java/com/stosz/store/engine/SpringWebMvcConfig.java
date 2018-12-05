package com.stosz.store.engine;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.stosz.*.web",includeFilters={
        @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class),
        @Filter(type = FilterType.ANNOTATION, value = Controller.class),
        @Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class),
})
public class SpringWebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ClientLoginInterceptor clientLoginInterceptor;

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
//    private LoginInterceptor loginInterceptor;



//    @Autowired
//    private LoginUserResolver loginUserResolver;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

       registry.addInterceptor(clientLoginInterceptor).addPathPatterns("/**").excludePathPatterns("/", "/static/**", "/*/remote/*","/store/wms/api/*");
    }

    /*@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(loginUserResolver);
    }*/
    
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
    }
    
    
    @Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MvcConfig.wrapperHttpMessageConverters(converters);
    }



}
