package org.stockws.config;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableWebSecurity
@EnableDiscoveryClient
public class UnderGateWayWebSecurityContext extends WebSecurityConfigurerAdapter {

	private final static Logger log = LoggerFactory.getLogger(UnderGateWayWebSecurityContext.class);
	
	public UnderGateWayWebSecurityContext(){
		log.info("init UnderGateWayWebSecurityContext");
	}
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServer extends ResourceServerConfigurerAdapter {

		@Value("${security.resourceID}")
		private String API_RESOURCE_ID;
		
		@Value("${security.trustStore}")
		private String trustStore;
		
		@Value("${security.storepass}")
		private String storepass;
		
		@Value("${security.trustKeyAlias}")
		private String trustKeyAlias;
		
		@Value("${security.storeType}")
		private String storeType;
		
		@Value("${security.sigAlg}")
		private String sigAlg;
		
		@Autowired
		ResourceServerTokenServices tokenServices;
		
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(API_RESOURCE_ID).tokenServices(tokenServices).stateless(false);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				// Since we want the protected resources to be accessible in the UI as well we need 
				// session creation to be allowed (it's disabled by default in 2.0.6)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests()
					.antMatchers("/menus/**", "/applys/**").access("hasAnyRole('ROLE_API_USER')")
					;
			// @formatter:on
		}
		
		
		JwtAccessTokenConverter tokenConverter(){
			JwtAccessTokenConverter jwtTokenEnhancer = new JwtAccessTokenConverter();
			jwtTokenEnhancer.setVerifier(new SignatureVerifier(){

				@Override
				public String algorithm() {
					return sigAlg;
				}

				@Override
				public void verify(byte[] content, byte[] signature) {
					try {
						KeyStore keystore = KeyStore.getInstance(storeType);
						keystore.load(new ClassPathResource(trustStore).getInputStream(), storepass.toCharArray());
						Certificate cert = keystore.getCertificate(trustKeyAlias);
						Signature sig = Signature.getInstance(algorithm());
						sig.initVerify(cert);
						sig.update(content);
						if(!sig.verify(signature)){
							throw new InvalidSignatureException("Signature did not match content");
						}
					} catch (KeyStoreException
							| NoSuchAlgorithmException | CertificateException
							| IOException | InvalidKeyException | SignatureException e) {
						log.error("", e);
					}
					
				}
				
			});
		
			return jwtTokenEnhancer;
		}
		
		@Bean
		TokenStore jwtTokenStore(){
			return new JwtTokenStore(tokenConverter());
		}
		
		@Bean
		ResourceServerTokenServices tokenServices(){
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setTokenStore(jwtTokenStore());
			return tokenServices;
		}
		
	}
	
	
	
    @Override
	public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.requestMatchers().antMatchers("/menus")
//		.and()
//		.authorizeRequests()
//		.antMatchers("/menus").permitAll();
//		;
//	}

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//         http
//            .requestMatchers().antMatchers("/login", "/oauth/authorize", "/test")
//                .and()
//            .authorizeRequests()
//                .antMatchers("/login", "/test").permitAll()
//                .anyRequest().hasRole("USER")
//                .and()
//            .exceptionHandling()
//                .accessDeniedPage("/login.jsp?authorization_error=true")
//                .and()
//            .csrf()
//                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
//                .disable()
//            .logout()
//                .and()
//            .formLogin()
//            ;
//        // @formatter:on
//    }
	
	

	

//	protected static class Stuff {
//
//		@Autowired
//		private ClientDetailsService clientDetailsService;
//
//		@Autowired
//		private TokenStore tokenStore;
//
//		@Bean
//		public ApprovalStore approvalStore() throws Exception {
//			TokenApprovalStore store = new TokenApprovalStore();
//			store.setTokenStore(tokenStore);
//			return store;
//		}
//
//		@Bean
//		@Lazy
//		@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
//		public SparklrUserApprovalHandler userApprovalHandler() throws Exception {
//			SparklrUserApprovalHandler handler = new SparklrUserApprovalHandler();
//			handler.setApprovalStore(approvalStore());
//			handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//			handler.setClientDetailsService(clientDetailsService);
//			handler.setUseApprovalStore(true);
//			return handler;
//		}
//	}
	
	

}
