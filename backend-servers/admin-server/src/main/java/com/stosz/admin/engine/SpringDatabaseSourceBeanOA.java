package com.stosz.admin.engine;

import com.stosz.admin.mapper.__AdminMapperInit__;
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
@MapperScan(basePackages = "com.stosz.admin.mapper.oa", sqlSessionTemplateRef = "oaSqlSessionTemplate")
@EnableTransactionManagement
public class SpringDatabaseSourceBeanOA {

    @Resource
    SpringDatabaseConfigOA springDatabaseConfigOA;

    @Bean
    @Resource
    public DataSource oaDataSource() throws PropertyVetoException{
        return springDatabaseConfigOA.createDataSource();
    }

    @Bean
    @Resource
    public SqlSessionFactory oaSqlSessionFactoryBean(@Qualifier("oaDataSource") DataSource oaDataSource) throws Exception {
        return springDatabaseConfigOA.createSqlSessionFactoryBean(oaDataSource, __AdminMapperInit__.class);
    }


    @Resource
    @Bean
    public DataSourceTransactionManager oaTransactionManager(@Qualifier("oaDataSource") DataSource oaDataSource) {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(oaDataSource);
        return tm;
    }


    @Bean(name = "oaSqlSessionTemplate")
    public SqlSessionTemplate oaSqlSessionTemplate(@Qualifier("oaSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
