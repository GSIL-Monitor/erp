package com.stosz.plat.engine;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected final Logger logger = LoggerFactory.getLogger(SpringDatabaseConfigAbstract.class);

    public abstract String getJdbcDriver() ;


    public abstract String getJdbcUrl() ;


    public abstract String getJdbcUsername() ;


    public abstract String getJdbcPassword() ;


    public abstract Integer getC3p0_minPoolSize();


    public abstract Integer getC3p0_maxPoolSize() ;


    public abstract Integer getC3p0_initialPoolSize() ;


    public abstract Integer getC3p0_maxIdleTime() ;


    public abstract Integer getC3p0_acquireIncrement() ;


    public abstract Integer getC3p0_idleConnectionTestPeriod() ;


    public abstract Integer getC3p0_acquireRetryAttempts() ;


    public abstract Integer getC3p0_maxStatements() ;


    public abstract Integer getC3p0_maxStatementsPerConnection() ;


    public abstract Integer getC3p0_checkoutTimeout() ;


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
        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);
        ds.setPoolPreparedStatements(true);
        ds.setMaxPoolPreparedStatementPerConnectionSize(20);
        return ds;
    }

    public SqlSessionFactory createSqlSessionFactoryBean(DataSource dataSource, Class mapperPackageClass) throws Exception {
        return  createSqlSessionFactoryBean(dataSource, mapperPackageClass,null);
    }


    public SqlSessionFactory createSqlSessionFactoryBean(DataSource dataSource, Class mapperPackageClass,Interceptor[]interceptors) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

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

        cfg.getTypeAliasRegistry().registerAliases(mapperPackageClass.getPackage().getName());

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

        registerAllEnumToMybatis(cfg);

        //用于添加分页插件
        if(interceptors != null){
            sessionFactory.setPlugins(interceptors);
        }

        sessionFactory.setConfiguration(cfg);

        return  sessionFactory.getObject();

    }


    private void registerAllEnumToMybatis(org.apache.ibatis.session.Configuration cfg) throws Exception{
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
            if (!classname.startsWith(pack)) {
                continue;
            }
            try {
                Class<?> cls = Class.forName(classname);

                if (Enum.class.isAssignableFrom(cls)) {
                    cfg.getTypeHandlerRegistry().register(cls, EnumOrdinalTypeHandler.class);
                }
            } catch (NoClassDefFoundError ex) {
                //just handle find class todo
//                logger.error("没有找到类：{} ", classname, ex);
            }
        }

    }


}
