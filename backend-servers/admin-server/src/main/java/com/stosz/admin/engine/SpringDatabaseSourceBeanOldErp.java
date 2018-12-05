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
@MapperScan(basePackages = "com.stosz.admin.mapper.olderp", sqlSessionTemplateRef = "oldErpSqlSessionTemplate")
@EnableTransactionManagement
public class SpringDatabaseSourceBeanOldErp {

    @Resource
    private SpringDatabaseConfigOldErp springDatabaseConfigoldErp;

    @Bean(name = "oldErpDataSource")
    @Resource
    public DataSource oldErpDataSource() throws PropertyVetoException {
        return springDatabaseConfigoldErp.createDataSource();
    }

    @Bean(name = "oldErpSqlSessionFactoryBean")
    @Resource
    public SqlSessionFactory oldErpSqlSessionFactoryBean(@Qualifier("oldErpDataSource") DataSource oldErpDataSource) throws Exception {
        return springDatabaseConfigoldErp.createSqlSessionFactoryBean(oldErpDataSource, __AdminMapperInit__.class);
    }


    @Resource
    @Bean(name = "oldErpTransactionManager")
    public DataSourceTransactionManager oldErpTransactionManager(@Qualifier("oldErpDataSource") DataSource oldErpDataSource) {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(oldErpDataSource);
        return tm;
    }


    @Bean(name = "oldErpSqlSessionTemplate")
    public SqlSessionTemplate oldErpSqlSessionTemplate(@Qualifier("oldErpSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
