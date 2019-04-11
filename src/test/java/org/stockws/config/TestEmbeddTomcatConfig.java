package org.stockws.config;

import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestEmbeddTomcatConfig {

//	@Bean
//	public TomcatEmbeddedServletContainerFactory tomcatFactory() {
//		
//		
//	    return new TomcatEmbeddedServletContainerFactory() {
//	        @Override
//	        protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
//	                Tomcat tomcat) {
//	            tomcat.enableNaming();
//	            return super.getTomcatEmbeddedServletContainer(tomcat);
//	        }
//
//			@Override
//			protected void postProcessContext(Context context) {
//				super.postProcessContext(context);
//				ContextResource mydatasource = new ContextResource();
//                 mydatasource.setName("jdbc/mysql");
//                 mydatasource.setAuth("Container");
//                 mydatasource.setType("javax.sql.DataSource");
//                 mydatasource.setScope("Sharable");
//                 mydatasource.setProperty("driverClassName", "com.mysql.jdbc.Driver");
//                 mydatasource.setProperty("url", "jdbc:mysql://localhost:3306/dev");
//                 mydatasource.setProperty("username", "");
//                 mydatasource.setProperty("password", "");
//                 context.getNamingResources().addResource(mydatasource);
//				
//			}
//	        
//	        
//	    };
//	}

	
}
