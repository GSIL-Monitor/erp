package com.stosz.tms.engine;

import com.stosz.plat.utils.DESUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringDatabaseConfigTms extends SpringDatabaseConfigAbstract {

    public static final Logger logger = LoggerFactory.getLogger(SpringDatabaseConfigTms.class);

    @Value("${tms.jdbc.driver}")
    private String jdbcDriver;
    @Value("${tms.jdbc.url}")
    private String jdbcUrl;
    @Value("${tms.jdbc.username}")
    private String jdbcUsername;
    @Value("${tms.jdbc.password}")
    private String jdbcPassword;

    @Value("${tms.c3p0.minPoolSize}")
    private Integer c3p0_minPoolSize;
    @Value("${tms.c3p0.maxPoolSize}")
    private Integer c3p0_maxPoolSize;
    @Value("${tms.c3p0.initialPoolSize}")
    private Integer c3p0_initialPoolSize;
    @Value("${tms.c3p0.maxIdleTime}")
    private Integer c3p0_maxIdleTime;
    @Value("${tms.c3p0.acquireIncrement}")
    private Integer c3p0_acquireIncrement;
    @Value("${tms.c3p0.idleConnectionTestPeriod}")
    private Integer c3p0_idleConnectionTestPeriod;
    @Value("${tms.c3p0.acquireRetryAttempts}")
    private Integer c3p0_acquireRetryAttempts;
    @Value("${tms.c3p0.maxStatements}")
    private Integer c3p0_maxStatements;
    @Value("${tms.c3p0.maxStatementsPerConnection}")
    private Integer c3p0_maxStatementsPerConnection;
    @Value("${tms.c3p0.checkoutTimeout}")
    private Integer c3p0_checkoutTimeout;

//    @Value("${tms.mybatis.mapperLocations}")
//    private String mybatisLocations;
//
    @Value("${system.secretKey}")
    public String secretKey;

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        try {
			return DESUtils.decrypt(secretKey,jdbcPassword);
		} catch (Exception e) {
            logger.error(e.getMessage(),e);
		}
        return null;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public Integer getC3p0_minPoolSize() {
        return c3p0_minPoolSize;
    }

    public void setC3p0_minPoolSize(Integer c3p0_minPoolSize) {
        this.c3p0_minPoolSize = c3p0_minPoolSize;
    }

    public Integer getC3p0_maxPoolSize() {
        return c3p0_maxPoolSize;
    }

    public void setC3p0_maxPoolSize(Integer c3p0_maxPoolSize) {
        this.c3p0_maxPoolSize = c3p0_maxPoolSize;
    }

    public Integer getC3p0_initialPoolSize() {
        return c3p0_initialPoolSize;
    }

    public void setC3p0_initialPoolSize(Integer c3p0_initialPoolSize) {
        this.c3p0_initialPoolSize = c3p0_initialPoolSize;
    }

    public Integer getC3p0_maxIdleTime() {
        return c3p0_maxIdleTime;
    }

    public void setC3p0_maxIdleTime(Integer c3p0_maxIdleTime) {
        this.c3p0_maxIdleTime = c3p0_maxIdleTime;
    }

    public Integer getC3p0_acquireIncrement() {
        return c3p0_acquireIncrement;
    }

    public void setC3p0_acquireIncrement(Integer c3p0_acquireIncrement) {
        this.c3p0_acquireIncrement = c3p0_acquireIncrement;
    }

    public Integer getC3p0_idleConnectionTestPeriod() {
        return c3p0_idleConnectionTestPeriod;
    }

    public void setC3p0_idleConnectionTestPeriod(Integer c3p0_idleConnectionTestPeriod) {
        this.c3p0_idleConnectionTestPeriod = c3p0_idleConnectionTestPeriod;
    }

    public Integer getC3p0_acquireRetryAttempts() {
        return c3p0_acquireRetryAttempts;
    }

    public void setC3p0_acquireRetryAttempts(Integer c3p0_acquireRetryAttempts) {
        this.c3p0_acquireRetryAttempts = c3p0_acquireRetryAttempts;
    }

    public Integer getC3p0_maxStatements() {
        return c3p0_maxStatements;
    }

    public void setC3p0_maxStatements(Integer c3p0_maxStatements) {
        this.c3p0_maxStatements = c3p0_maxStatements;
    }

    public Integer getC3p0_maxStatementsPerConnection() {
        return c3p0_maxStatementsPerConnection;
    }

    public void setC3p0_maxStatementsPerConnection(Integer c3p0_maxStatementsPerConnection) {
        this.c3p0_maxStatementsPerConnection = c3p0_maxStatementsPerConnection;
    }

    public Integer getC3p0_checkoutTimeout() {
        return c3p0_checkoutTimeout;
    }

    public void setC3p0_checkoutTimeout(Integer c3p0_checkoutTimeout) {
        this.c3p0_checkoutTimeout = c3p0_checkoutTimeout;
    }

    public String getMybatisLocations() {
        return "";/*mybatisLocations;*/
    }

    public void setMybatisLocations(String mybatisLocations) {
//        this.mybatisLocations = mybatisLocations;
    }
}
