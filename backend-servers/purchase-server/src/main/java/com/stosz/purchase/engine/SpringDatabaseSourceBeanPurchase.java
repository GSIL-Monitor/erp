package com.stosz.purchase.engine;

import com.stosz.purchase.mapper.__PurchaseMapperInit__;
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
@MapperScan(basePackages = "com.stosz.purchase.mapper", sqlSessionTemplateRef = "purchaseSqlSessionTemplate")
@EnableTransactionManagement
public class SpringDatabaseSourceBeanPurchase {

    @Resource
    private SpringDatabaseConfigPurchase springDatabaseConfigpurchase;

    @Bean(name = "purchaseDataSource")
    @Resource
//    @Autowired
    @Primary
    public DataSource purchaseDataSource() throws PropertyVetoException{
        return springDatabaseConfigpurchase.createDataSource();
    }

    @Bean(name = "purchaseSqlSessionFactoryBean")
    @Resource
    @Primary
    public SqlSessionFactory purchaseSqlSessionFactoryBean(@Qualifier("purchaseDataSource") DataSource purchaseDataSource) throws Exception {
        return springDatabaseConfigpurchase.createSqlSessionFactoryBean(purchaseDataSource,__PurchaseMapperInit__.class);

    }


    @Resource
    @Bean(name = "purchaseTransactionManager")
    @Primary
    public DataSourceTransactionManager purchaseTransactionManager(@Qualifier("purchaseDataSource") DataSource purchaseDataSource) {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(purchaseDataSource);
        return tm;
    }


    @Bean(name = "purchaseSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate purchaseSqlSessionTemplate(@Qualifier("purchaseSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
