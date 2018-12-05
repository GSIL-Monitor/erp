package com.stosz.tms.engine;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

public  abstract class SpringDatabaseConfigAbstract {


    public abstract String getJdbcDriver() ;

    public abstract void setJdbcDriver(String jdbcDriver) ;

    public abstract String getJdbcUrl() ;

    public abstract void setJdbcUrl(String jdbcUrl) ;

    public abstract String getJdbcUsername() ;

    public abstract void setJdbcUsername(String jdbcUsername) ;

    public abstract String getJdbcPassword() ;

    public abstract void setJdbcPassword(String jdbcPassword);

    public abstract Integer getC3p0_minPoolSize();

    public abstract void setC3p0_minPoolSize(Integer c3p0_minPoolSize);

    public abstract Integer getC3p0_maxPoolSize() ;

    public abstract void setC3p0_maxPoolSize(Integer c3p0_maxPoolSize);

    public abstract Integer getC3p0_initialPoolSize() ;

    public abstract void setC3p0_initialPoolSize(Integer c3p0_initialPoolSize);

    public abstract Integer getC3p0_maxIdleTime() ;

    public abstract void setC3p0_maxIdleTime(Integer c3p0_maxIdleTime);

    public abstract Integer getC3p0_acquireIncrement() ;

    public abstract void setC3p0_acquireIncrement(Integer c3p0_acquireIncrement);

    public abstract Integer getC3p0_idleConnectionTestPeriod() ;

    public abstract void setC3p0_idleConnectionTestPeriod(Integer c3p0_idleConnectionTestPeriod);

    public abstract Integer getC3p0_acquireRetryAttempts() ;

    public abstract void setC3p0_acquireRetryAttempts(Integer c3p0_acquireRetryAttempts);

    public abstract Integer getC3p0_maxStatements() ;

    public abstract void setC3p0_maxStatements(Integer c3p0_maxStatements) ;

    public abstract Integer getC3p0_maxStatementsPerConnection() ;

    public abstract void setC3p0_maxStatementsPerConnection(Integer c3p0_maxStatementsPerConnection);

    public abstract Integer getC3p0_checkoutTimeout() ;

    public abstract void setC3p0_checkoutTimeout(Integer c3p0_checkoutTimeout) ;

//    public abstract String getMybatisLocations() ;
//
//    public abstract void setMybatisLocations(String mybatisLocations) ;

    public  DataSource createDataSource() throws PropertyVetoException {
        DruidDataSource ds = new DruidDataSource();

        ds.setUrl(this.getJdbcUrl());
        ds.setUsername(this.getJdbcUsername());
        ds.setPassword(this.getJdbcPassword());
        ds.setMaxActive(this.getC3p0_maxPoolSize());
        ds.setInitialSize(this.getC3p0_initialPoolSize());
        ds.setMinIdle(this.getC3p0_minPoolSize());
        ds.setDriverClassName(this.getJdbcDriver());
        //<!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        ds.setTimeBetweenEvictionRunsMillis(this.getC3p0_idleConnectionTestPeriod());
        ds.setMinEvictableIdleTimeMillis(300000);
        ds.setValidationQuery("select 1");
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(true);
        ds.setTestOnReturn(false);
        ds.setPoolPreparedStatements(true);
        ds.setMaxPoolPreparedStatementPerConnectionSize(20);
//        ds.setAcquireRetryAttempts(this.getC3p0_acquireRetryAttempts());
//        ds.setMaxStatements(this.getC3p0_maxStatements());
//        ds.setMaxStatementsPerConnection(this.getC3p0_maxStatementsPerConnection());
//        ds.setCheckoutTimeout(this.getC3p0_checkoutTimeout());
        return ds;
    }
//    public  DataSource createDataSource() throws PropertyVetoException {
//        ComboPooledDataSource ds = new ComboPooledDataSource();
//        ds.setDriverClass(this.getJdbcDriver());
//        ds.setJdbcUrl(this.getJdbcUrl());
//        ds.setUser(this.getJdbcUsername());
//        ds.setPassword(this.getJdbcPassword());
//        ds.setMaxPoolSize(this.getC3p0_maxPoolSize());
//        ds.setMinPoolSize(this.getC3p0_minPoolSize());
//        ds.setInitialPoolSize(this.getC3p0_initialPoolSize());
//        ds.setMaxIdleTime(this.getC3p0_maxIdleTime());
//        ds.setAcquireIncrement(this.getC3p0_acquireIncrement());
//        ds.setIdleConnectionTestPeriod(this.getC3p0_idleConnectionTestPeriod());
//        ds.setAcquireRetryAttempts(this.getC3p0_acquireRetryAttempts());
//        ds.setMaxStatements(this.getC3p0_maxStatements());
//        ds.setMaxStatementsPerConnection(this.getC3p0_maxStatementsPerConnection());
//        ds.setCheckoutTimeout(this.getC3p0_checkoutTimeout());
//        return ds;
//    }


    public SqlSessionFactory createSqlSessionFactoryBean(DataSource dataSource) throws Exception
    {
        return createSqlSessionFactoryBean(dataSource,null);
    }

    public SqlSessionFactory createSqlSessionFactoryBean(DataSource dataSource,
                                                      Resource[] resources) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //TODO 先把resource的xml关闭
//        sessionFactory.setMapperLocations(resources);


        org.apache.ibatis.session.Configuration cfg = new org.apache.ibatis.session.Configuration();
        cfg.setMapUnderscoreToCamelCase(true);
        cfg.setCacheEnabled(true);
        cfg.setMultipleResultSetsEnabled(true);
        cfg.setUseColumnLabel(true);
        cfg.setUseGeneratedKeys(true);
        cfg.setDefaultExecutorType(ExecutorType.SIMPLE);
        cfg.setDefaultStatementTimeout(10000);

        cfg.setLazyLoadingEnabled(false);
        //指定MyBatis如何自动映射列到字段/属性。PARTIAL只会自动映射简单，没有嵌套的结果。FULL会自动映射任意复杂的结果（嵌套的或其他情况）。
        cfg.setAutoMappingBehavior(AutoMappingBehavior.FULL);

        cfg.getTypeAliasRegistry().registerAliases(SpringDatabaseConfigAbstract.class.getPackage().getName());

        cfg.getTypeHandlerRegistry().register(org.apache.ibatis.type.InstantTypeHandler.class);
        cfg.getTypeHandlerRegistry().register(org.apache.ibatis.type.LocalDateTimeTypeHandler.class);
        cfg.getTypeHandlerRegistry().register(org.apache.ibatis.type.LocalDateTypeHandler.class);
        cfg.getTypeHandlerRegistry().register(org.apache.ibatis.type.LocalTimeTypeHandler.class);
        cfg.getTypeHandlerRegistry().register(org.apache.ibatis.type.OffsetDateTimeTypeHandler.class);
        cfg.getTypeHandlerRegistry().register(org.apache.ibatis.type.OffsetTimeTypeHandler.class);
        cfg.getTypeHandlerRegistry().register(org.apache.ibatis.type.ZonedDateTimeTypeHandler.class);
        cfg.getTypeHandlerRegistry().register(org.apache.ibatis.type.YearTypeHandler.class);
        cfg.getTypeHandlerRegistry().register(org.apache.ibatis.type.MonthTypeHandler.class);
        cfg.getTypeHandlerRegistry().register(org.apache.ibatis.type.YearMonthTypeHandler.class);
        cfg.getTypeHandlerRegistry().register(org.apache.ibatis.type.JapaneseDateTypeHandler.class);

//        cfg.getTypeHandlerRegistry().register(Enum.class, EnumOrdinalTypeHandler.class);

        registAllEnumToMybatis(cfg);

        sessionFactory.setConfiguration(cfg);

        return (SqlSessionFactory) sessionFactory.getObject();
    }


    private void registAllEnumToMybatis(org.apache.ibatis.session.Configuration cfg) throws Exception{
        String pack = "com.stosz";
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        String pkg = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(pack) + "/**/*.class";
        Resource[] resources = resolver.getResources(pkg);
        for (Resource resource : resources) {
            if (!resource.isReadable()) {
                continue;
            }
            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
            String classname = metadataReader.getClassMetadata().getClassName();
            Class<?> cls = Class.forName(classname);

            if (Enum.class.isAssignableFrom(cls)) {
                cfg.getTypeHandlerRegistry().register(cls , EnumOrdinalTypeHandler.class);
            }
        }

    }


}
