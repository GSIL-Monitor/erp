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
@MapperScan(basePackages = "com.stosz.olderp.mapper", sqlSessionTemplateRef = "oldErpSqlSessionTemplate")
//@EnableTransactionManagement
public class SpringDatabaseSourceBeanOldErp {

	@Resource
	private SpringDatabaseConfigOldErp springDatabaseConfigoldErp;

	@Bean(name = "oldErpDataSource")
	@Resource
	// @Autowired
	@Primary
	public DataSource oldErpDataSource() throws PropertyVetoException {
		return springDatabaseConfigoldErp.createDataSource();
	}

	@Bean(name = "oldErpSqlSessionFactoryBean")
	@Resource
	@Primary
	public SqlSessionFactory oldErpSqlSessionFactoryBean(@Qualifier("oldErpDataSource") DataSource oldErpDataSource,
			ApplicationContext applicationContext) throws Exception {
		org.springframework.core.io.Resource[] resources = applicationContext.getResources(springDatabaseConfigoldErp.getMybatisLocations());
		return springDatabaseConfigoldErp.createSqlSessionFactoryBean(oldErpDataSource, resources);

	}

//	@Resource
//	@Bean(name = "oldErpTransactionManager")
//	@Primary
//	public DataSourceTransactionManager oldErpTransactionManager(@Qualifier("oldErpDataSource") DataSource oldErpDataSource) {
//		DataSourceTransactionManager tm = new DataSourceTransactionManager();
//		tm.setDataSource(oldErpDataSource);
//		return tm;
//	}

	@Bean(name = "oldErpSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate oldErpSqlSessionTemplate(@Qualifier("oldErpSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory)
			throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
