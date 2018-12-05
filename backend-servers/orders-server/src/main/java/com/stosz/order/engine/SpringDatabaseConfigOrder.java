package com.stosz.order.engine;

import com.stosz.plat.engine.SpringDatabaseConfigAbstract;
import com.stosz.plat.utils.DESUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringDatabaseConfigOrder extends SpringDatabaseConfigAbstract {

    @Value("${mysql.jdbc.driver}")
    private String jdbcDriver;
    @Value("${order.jdbc.url}")
    private String jdbcUrl;
    @Value("${order.jdbc.username}")
    private String jdbcUsername;
    @Value("${order.jdbc.password}")
    private String jdbcPassword;

    @Value("${order.c3p0.minPoolSize}")
    private Integer c3p0_minPoolSize;
    @Value("${order.c3p0.maxPoolSize}")
    private Integer c3p0_maxPoolSize;
    @Value("${order.c3p0.initialPoolSize}")
    private Integer c3p0_initialPoolSize;
    @Value("${order.c3p0.maxIdleTime}")
    private Integer c3p0_maxIdleTime;
    @Value("${order.c3p0.acquireIncrement}")
    private Integer c3p0_acquireIncrement;
    @Value("${order.c3p0.idleConnectionTestPeriod}")
    private Integer c3p0_idleConnectionTestPeriod;
    @Value("${order.c3p0.acquireRetryAttempts}")
    private Integer c3p0_acquireRetryAttempts;
    @Value("${order.c3p0.maxStatements}")
    private Integer c3p0_maxStatements;
    @Value("${order.c3p0.maxStatementsPerConnection}")
    private Integer c3p0_maxStatementsPerConnection;
    @Value("${order.c3p0.checkoutTimeout}")
    private Integer c3p0_checkoutTimeout;

    @Value("${system.secretKey}")
    public String secretKey;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SpringDatabaseConfigOrder.class);

    public String getJdbcDriver() {
        return jdbcDriver;
    }


    public String getJdbcUrl() {
        return jdbcUrl;
    }


    public String getJdbcUsername() {
        return jdbcUsername;
    }


    public String getJdbcPassword() {
        try {
			return DESUtils.decrypt(secretKey,jdbcPassword);
		} catch (Exception e) {
            logger.error(e.getMessage(),e);
		}
        return null;
    }


    public Integer getC3p0_minPoolSize() {
        return c3p0_minPoolSize;
    }


    public Integer getC3p0_maxPoolSize() {
        return c3p0_maxPoolSize;
    }


    public Integer getC3p0_initialPoolSize() {
        return c3p0_initialPoolSize;
    }


    public Integer getC3p0_maxIdleTime() {
        return c3p0_maxIdleTime;
    }


    public Integer getC3p0_acquireIncrement() {
        return c3p0_acquireIncrement;
    }

    public Integer getC3p0_idleConnectionTestPeriod() {
        return c3p0_idleConnectionTestPeriod;
    }


    public Integer getC3p0_acquireRetryAttempts() {
        return c3p0_acquireRetryAttempts;
    }


    public Integer getC3p0_maxStatements() {
        return c3p0_maxStatements;
    }


    public Integer getC3p0_maxStatementsPerConnection() {
        return c3p0_maxStatementsPerConnection;
    }


    public Integer getC3p0_checkoutTimeout() {
        return c3p0_checkoutTimeout;
    }


}
