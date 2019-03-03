package org.stockws.config;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestEmbeddTomcatConfig {

	@Bean
	public TomcatEmbeddedServletContainerFactory tomcatFactory() {
		
		
	    return new TomcatEmbeddedServletContainerFactory() {
	        @Override
	        protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
	                Tomcat tomcat) {
	            tomcat.enableNaming();
	            return super.getTomcatEmbeddedServletContainer(tomcat);
	        }

			@Override
			protected void postProcessContext(Context context) {
				super.postProcessContext(context);
				ContextResource mydatasource = new ContextResource();
                 mydatasource.setName("jdbc/mysql");
                 mydatasource.setAuth("Container");
                 mydatasource.setType("javax.sql.DataSource");
                 mydatasource.setScope("Sharable");
                 mydatasource.setProperty("driverClassName", "com.mysql.jdbc.Driver");
                 mydatasource.setProperty("url", "jdbc:mysql://localhost:3306/dev");
                 mydatasource.setProperty("username", "NU_ES");
                 mydatasource.setProperty("password", "NU_ES");
                 context.getNamingResources().addResource(mydatasource);
				
			}
	        
	        
	    };
	}

	
}
