package org.stockws.config;

import org.business.models.Cert;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment=WebEnvironment.DEFINED_PORT,
		classes = {TestEmbeddTomcatConfig.class},
		properties={
				"eureka.client.register-with-eureka=false",
				"eureka.client.fetch-registry=false",
				"spring.application.name=api",
				"spring.cloud.config.uri=${CONFIG_SERVER_URL:http://localhost:8888}",
				"spring.profiles.active=api_dev, api_error, api_cert",
				"redis.switch-on=false", 
})
public class TestConfig {

	
	@Bean
	@ConfigurationProperties(prefix="keystore.init")
	Cert initCert(){
		return new Cert();
	}
	
}
