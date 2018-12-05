package com.stosz.admin.engine;

import com.stosz.admin.mapper.__AdminMapperInit__;
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

@Configuration
@MapperScan(basePackages = "com.stosz.admin.mapper.admin", sqlSessionTemplateRef = "adminSqlSessionTemplate")
@EnableTransactionManagement
public class SpringDatabaseSourceBeanAdmin {

    @Resource
    private SpringDatabaseConfigAdmin springDatabaseConfigAdmin;

    @Bean(name = "adminDataSource")
    @Resource
    @Primary
    public DataSource adminDataSource() throws PropertyVetoException{
        return springDatabaseConfigAdmin.createDataSource();
    }

    @Bean(name = "adminSqlSessionFactoryBean")
    @Resource
    @Primary
    public SqlSessionFactory adminSqlSessionFactoryBean(@Qualifier("adminDataSource") DataSource adminDataSource) throws Exception {
        return springDatabaseConfigAdmin.createSqlSessionFactoryBean(adminDataSource, __AdminMapperInit__.class);
    }


    @Resource
    @Bean(name = "adminTransactionManager")
    @Primary
    public DataSourceTransactionManager adminTransactionManager(@Qualifier("adminDataSource") DataSource adminDataSource) {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(adminDataSource);
        return tm;
    }


    @Bean(name = "adminSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate adminSqlSessionTemplate(@Qualifier("adminSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
