package com.stosz.product.engine;

import com.stosz.product.mapper.__PcMapperInit__;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@MapperScan(basePackages = "com.stosz.product.mapper", sqlSessionTemplateRef = "pcSqlSessionTemplate")
@EnableTransactionManagement
public class SpringDatabaseSourceBeanPC {

    @Resource
    SpringDatabaseConfigPC springDatabaseConfigPC;

    @Qualifier("pcDataSource")
    @Bean
    @Resource
    public DataSource pcDataSource() throws PropertyVetoException{
        return springDatabaseConfigPC.createDataSource();
    }

    @Qualifier("pcSqlSessionFactoryBean")
    @Bean
    @Resource
    public SqlSessionFactory pcSqlSessionFactoryBean(@Qualifier("pcDataSource") DataSource pcDataSource) throws Exception {
        return springDatabaseConfigPC.createSqlSessionFactoryBean(pcDataSource,__PcMapperInit__.class);
    }


    @Resource
    @Bean(name="pcTxManager")
    public DataSourceTransactionManager pcTransactionManager(@Qualifier("pcDataSource") DataSource pcDataSource) {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(pcDataSource);
        return tm;
    }


    @Bean(name = "pcSqlSessionTemplate")
    public SqlSessionTemplate pcSqlSessionTemplate(@Qualifier("pcSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
