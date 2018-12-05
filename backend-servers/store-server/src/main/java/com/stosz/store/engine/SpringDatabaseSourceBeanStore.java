package com.stosz.store.engine;

import com.stosz.store.mapper.__StoreMapperInit__;
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
@MapperScan(basePackages = "com.stosz.store.mapper", sqlSessionTemplateRef = "storeSqlSessionTemplate")
@EnableTransactionManagement
public class SpringDatabaseSourceBeanStore {

    @Resource
    private SpringDatabaseConfigStore springDatabaseConfigstore;

    @Bean(name = "storeDataSource")
    @Resource
    @Primary
    public DataSource storeDataSource() throws PropertyVetoException{
        return springDatabaseConfigstore.createDataSource();
    }

    @Bean(name = "storeSqlSessionFactoryBean")
    @Resource
    @Primary
    public SqlSessionFactory storeSqlSessionFactoryBean(@Qualifier("storeDataSource") DataSource storeDataSource) throws Exception {
        return springDatabaseConfigstore.createSqlSessionFactoryBean(storeDataSource,__StoreMapperInit__.class);

    }

    @Resource
    @Bean(name = "storeTransactionManager")
    @Primary
    public DataSourceTransactionManager storeTransactionManager(@Qualifier("storeDataSource") DataSource storeDataSource) {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(storeDataSource);
        return tm;
    }


    @Bean(name = "storeSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate storeSqlSessionTemplate(@Qualifier("storeSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
