package com.stosz.plat.service;

import com.stosz.plat.enums.SystemEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;


/**
 * 所有项目的公共配置都应该放到这里
 * 这里的值来自 global.project.properties 中
 */
public class ProjectConfigService implements InitializingBean, ServletContextAware, EnvironmentAware {
    private ServletContext servletContext;


    /*************    project self config   ****************/
    //当前项目的rest请求秘钥
    @Value("${project.rest.secret}")
    public String projectRestSecret;
    //当前项目自己的id 的定义
    @Value("${project.id}")
    public String projectSelfId;


    /*************    redis config   ****************/
    @Value("${redis.host}")
    public String projectRedisHost;
    @Value("${redis.port}")
    public String projectRedisPort;
    @Value("${redis.database}")
    public String projectRedisDatabase;
    @Value("${redis.password}")
    public String projectRedisPassword;
    @Value("${redis.pool.max.active}")
    public String projectRedisPoolMaxActive;
    @Value("${redis.pool.max.wait}")
    public String projectRedisPoolMaxWait;
    @Value("${redis.pool.max.idle}")
    public String projectRedisPoolMaxIdle;
    @Value("${redis.pool.min.idle}")
    public String projectRedisPoolMinIdle;
    @Value("${redis.pool.timeout}")
    public String projectRedisTimeout;
    @Value("${redis.pool.testOnBorrow}")
    public String projectRedisPoolTestOnBorrow;

    @Value("${mail.host}")
    public String mailHost;

    @Value("${mail.port}")
    public String mailPort;

    @Value("${mail.username}")
    public String mailUsername;

    @Value("${mail.password}")
    public String mailPassword;

    protected Environment environment;
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getSystemHost(SystemEnum systemEnum){
        return environment.getProperty( systemEnum.name()+".host","127.0.0.1");
    }


    public int getSystemPort(SystemEnum systemEnum){
        String portStr = environment.getProperty( systemEnum.name()+".port","80");
        return Integer.parseInt(portStr);
    }

    public String getSystemHttp(SystemEnum systemEnum){
        String httpStr = "http://";
        httpStr +=getSystemHost(systemEnum);
        int port = getSystemPort(systemEnum);
        if(port!=80){
            httpStr += ":" + port;
        }
        return httpStr;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (servletContext == null) {
            return;
        }
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public String getProjectRestSecret() {
        return projectRestSecret;
    }

    public String getProjectSelfId() {
        return projectSelfId;
    }

}
