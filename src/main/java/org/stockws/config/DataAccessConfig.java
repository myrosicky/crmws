package org.stockws.config;


import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataAccessConfig {
	
	private final static Logger log = LoggerFactory.getLogger(DataAccessConfig.class);
	
	
	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlAuto;
	
	@Value("${spring.jpa.hibernate.packageToScan}")
	private String packageToScan;

	@Value("${spring.jpa.hibernate.dialect}")
	private String dialect;
	
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
	EntityManagerFactory entityManagerFactory() {
		log.info("hibernate packageToScan:" + packageToScan);
		log.info("hibernate hibernate.hbm2ddl.auto:" + ddlAuto);
		log.info("hibernate hibernate.dialect:" + dialect);
		
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan(packageToScan);
		emf.setJpaVendorAdapter(hibernateJpaVendorAdapter());

		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
		properties.setProperty("hibernate.dialect", dialect);
//		properties.setProperty("hibernate.physical_naming_strategy", "org.stockws.context.ArchivePhysicalNamingStrategyImpl");

		emf.setJpaProperties(properties);
		emf.afterPropertiesSet();
		EntityManagerFactory result = emf.getObject();
		return result;
	}
	
	@Bean
	PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}
	

	@Bean
	HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

}
