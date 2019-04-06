package org.stockws.config;


import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataAccessConfig {

	private @Resource Environment env;
	
	@Autowired
	private DataSource dataSource;

	@Value("${custom.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource2.url}")
	private String url2;

	@Value("${spring.datasource2.username}")
	private String username2;

	@Value("${spring.datasource2.password}")
	private String password2;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlAuto;
	
	@Value("${spring.jpa.hibernate.packageToScan}")
	private String packageToScan;

	@Bean
	HibernateJpaVendorAdapter hibernateJpaVendorAdapter(){
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setShowSql(true);
		return hibernateJpaVendorAdapter;
	}
	
//	@Bean
//	public EntityManagerFactory entityManagerFactory() {
//		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//		emf.setDataSource(dataSource);
//		emf.setPackagesToScan(packageToScan);
//		emf.setJpaVendorAdapter(hibernateJpaVendorAdapter());
//
//		Properties properties = new Properties();
//		properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
//		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
////		properties.setProperty("hibernate.physical_naming_strategy", "org.stockws.context.DevPhysicalNamingStrategyImpl");
//		properties.setProperty("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
//		
//		emf.setJpaProperties(properties);
//		emf.afterPropertiesSet();
//		return emf.getObject();
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionManager() {
//
//		JpaTransactionManager txManager = new JpaTransactionManager();
//		txManager.setEntityManagerFactory(entityManagerFactory());
//		return txManager;
//	}
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(driverClassName);
		basicDataSource.setUrl(url2);
		basicDataSource.setUsername(username2);
		basicDataSource.setPassword(password2);
		return basicDataSource;
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setPackagesToScan(packageToScan);
		emf.setJpaVendorAdapter(hibernateJpaVendorAdapter());

		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
//		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//		properties.setProperty("hibernate.physical_naming_strategy", "org.stockws.context.ArchivePhysicalNamingStrategyImpl");

		emf.setJpaProperties(properties);
		emf.afterPropertiesSet();
		EntityManagerFactory result = emf.getObject();
		return result;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}
	

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

}
