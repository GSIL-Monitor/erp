package com.stosz.store.engine.erpOld;

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
@MapperScan(basePackages = "com.stosz.store.erpOld", sqlSessionTemplateRef = "erpOldSqlSessionTemplate")
@EnableTransactionManagement
public class SpringDatabaseSourceBeanErpOld {

    @Resource
    private SpringDatabaseConfigErpOld springDatabaseConfigErpOld;

    @Bean(name = "erpOldDataSource")
    @Resource
    public DataSource erpOldDataSource() throws PropertyVetoException{
        return springDatabaseConfigErpOld.createDataSource();
    }

    @Bean(name = "erpOldSqlSessionFactoryBean")
    @Resource
    public SqlSessionFactory erpOldSqlSessionFactoryBean(@Qualifier("erpOldDataSource") DataSource erpOldDataSource) throws Exception {
        return springDatabaseConfigErpOld.createSqlSessionFactoryBean(erpOldDataSource,__StoreMapperInit__.class);

    }

    @Resource
    @Bean(name = "erpOldTransactionManager")
    public DataSourceTransactionManager erpOldTransactionManager(@Qualifier("erpOldDataSource") DataSource erpOldDataSource) {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(erpOldDataSource);
        return tm;
    }


    @Bean(name = "erpOldSqlSessionTemplate")
    public SqlSessionTemplate erpOldSqlSessionTemplate(@Qualifier("erpOldSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
