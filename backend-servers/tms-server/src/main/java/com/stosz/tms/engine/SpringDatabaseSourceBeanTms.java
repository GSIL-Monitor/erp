package com.stosz.tms.engine;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@MapperScan(basePackages = "com.stosz.tms.mapper", sqlSessionTemplateRef = "tmsSqlSessionTemplate")
@EnableTransactionManagement
public class SpringDatabaseSourceBeanTms {

	@Resource
	private SpringDatabaseConfigTms springDatabaseConfigtms;

	@Bean(name = "tmsDataSource")
	@Resource
	// @Autowired
	@Primary
	public DataSource tmsDataSource() throws PropertyVetoException {
		return springDatabaseConfigtms.createDataSource();
	}

	@Bean(name = "tmsSqlSessionFactoryBean")
	@Resource
	@Primary
	public SqlSessionFactory tmsSqlSessionFactoryBean(@Qualifier("tmsDataSource") DataSource tmsDataSource,
			ApplicationContext applicationContext) throws Exception {
		org.springframework.core.io.Resource[] resources = applicationContext
				.getResources(springDatabaseConfigtms.getMybatisLocations());
		return springDatabaseConfigtms.createSqlSessionFactoryBean(tmsDataSource, resources);

	}

	@Resource
	@Bean(name = "tmsTransactionManager")
	@Primary
	public DataSourceTransactionManager tmsTransactionManager(@Qualifier("tmsDataSource") DataSource tmsDataSource) {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(tmsDataSource);
		return tm;
	}

	@Bean(name = "tmsSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate tmsSqlSessionTemplate(
			@Qualifier("tmsSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
