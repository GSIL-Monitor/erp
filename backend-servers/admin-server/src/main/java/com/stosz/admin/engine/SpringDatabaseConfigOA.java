package com.stosz.admin.engine;

import com.stosz.plat.engine.SpringDatabaseConfigAbstract;
import com.stosz.plat.utils.DESUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringDatabaseConfigOA extends SpringDatabaseConfigAbstract {

    public static final Logger logger = LoggerFactory.getLogger(SpringDatabaseConfigOA.class);

    @Value("${mssql.jdbc.driver}")
    public String jdbcDriver;
    @Value("${oa.jdbc.url}")
    public String jdbcUrl;
    @Value("${oa.jdbc.username}")
    public String jdbcUsername;
    @Value("${oa.jdbc.password}")
    public String jdbcPassword;

    @Value("${oa.c3p0.minPoolSize}")
    public Integer c3p0_minPoolSize;
    @Value("${oa.c3p0.maxPoolSize}")
    public Integer c3p0_maxPoolSize;
    @Value("${oa.c3p0.initialPoolSize}")
    public Integer c3p0_initialPoolSize;
    @Value("${oa.c3p0.maxIdleTime}")
    public Integer c3p0_maxIdleTime;
    @Value("${oa.c3p0.acquireIncrement}")
    public Integer c3p0_acquireIncrement;
    @Value("${oa.c3p0.idleConnectionTestPeriod}")
    public Integer c3p0_idleConnectionTestPeriod;
    @Value("${oa.c3p0.acquireRetryAttempts}")
    public Integer c3p0_acquireRetryAttempts;
    @Value("${oa.c3p0.maxStatements}")
    public Integer c3p0_maxStatements;
    @Value("${oa.c3p0.maxStatementsPerConnection}")
    public Integer c3p0_maxStatementsPerConnection;
    @Value("${oa.c3p0.checkoutTimeout}")
    public Integer c3p0_checkoutTimeout;

//    @Value("${oa.mybatis.mapperLocations}")
//    public String mybatisLocations;
    
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

//    public String getMybatisLocations() {
//        return mybatisLocations;
//    }
//
//    public void setMybatisLocations(String mybatisLocations) {
//        this.mybatisLocations = mybatisLocations;
//    }
}
