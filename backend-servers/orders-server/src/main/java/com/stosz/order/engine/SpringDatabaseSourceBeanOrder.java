package com.stosz.order.engine;

import com.github.pagehelper.PageHelper;
import com.stosz.order.mapper.__OrderMapperInit__;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"com.stosz.order.mapper.order","com.stosz.order.mapper.crm"}, sqlSessionTemplateRef = "orderSqlSessionTemplate")
@EnableTransactionManagement
public class SpringDatabaseSourceBeanOrder {

    @Resource
    private SpringDatabaseConfigOrder springDatabaseConfigorder;

    @Bean(name = "orderDataSource")
    @Resource
    @Primary
    public DataSource orderDataSource() throws PropertyVetoException{
        return springDatabaseConfigorder.createDataSource();
    }

    @Bean(name = "orderSqlSessionFactoryBean")
    @Resource
    @Primary
    public SqlSessionFactory orderSqlSessionFactoryBean(@Qualifier("orderDataSource") DataSource orderDataSource) throws Exception {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(p);
        return springDatabaseConfigorder.createSqlSessionFactoryBean(orderDataSource, __OrderMapperInit__.class,new Interceptor[]{pageHelper});
    }


    @Resource
    @Bean(name = "orderTxManager")
    @Primary
    public DataSourceTransactionManager orderTxManager(@Qualifier("orderDataSource") DataSource orderDataSource) {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(orderDataSource);
        return tm;
    }


    @Bean(name = "orderSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate orderSqlSessionTemplate(@Qualifier("orderSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
