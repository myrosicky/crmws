package org.stockws.context;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@Configuration
//@EnableWebSecurity
public class WebSecurityContext extends WebSecurityConfigurerAdapter {

	
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth
        	.inMemoryAuthentication()
        	.withUser("marissa").password("koala").roles("USER")
        	.and()
        	.withUser("paul").password("emu").roles("USER");
    }

    @Override
	public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
	}

	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
         http
            .requestMatchers().antMatchers("/login", "/oauth/authorize", "/test")
                .and()
            .authorizeRequests()
                .antMatchers("/login", "/test").permitAll()
                .anyRequest().hasRole("USER")
                .and()
            .exceptionHandling()
                .accessDeniedPage("/login.jsp?authorization_error=true")
                .and()
            .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable()
            .logout()
                .and()
            .formLogin()
            ;
        // @formatter:on
    }
	
	private static final String API_RESOURCE_ID = "sparklr";

//	@Configuration
//	@EnableResourceServer
	protected static class ResourceServer extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(API_RESOURCE_ID).stateless(false);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				// Since we want the protected resources to be accessible in the UI as well we need 
				// session creation to be allowed (it's disabled by default in 2.0.6)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()
				.requestMatchers().antMatchers("/photos/**", "/oauth/users/**", "/oauth/clients/**","/me")
			.and()
				.authorizeRequests()
					.antMatchers("/me").access("#oauth2.hasScope('read')")					
					.antMatchers("/photos").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")                                        
					.antMatchers("/photos/trusted/**").access("#oauth2.hasScope('trust')")
					.antMatchers("/photos/user/**").access("#oauth2.hasScope('trust')")					
					.antMatchers("/photos/**").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
					.regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
						.access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
					.regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
						.access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
					.regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
						.access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");
			// @formatter:on
		}

	}

//	@Configuration
//	@EnableAuthorizationServer
	protected static class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private TokenStore tokenStore;

//		@Autowired
//		private UserApprovalHandler userApprovalHandler;

		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

			// @formatter:off
			clients.inMemory()
//					.withClient("tonr")
//			 			.resourceIds(API_RESOURCE_ID)
//			 			.authorizedGrantTypes("authorization_code", "implicit")
//			 			.authorities("ROLE_CLIENT")
//			 			.scopes("read", "write")
//			 			.secret("secret")
//			 		.and()
//			 		.withClient("tonr-with-redirect")
//			 			.resourceIds(API_RESOURCE_ID)
//			 			.authorizedGrantTypes("authorization_code", "implicit")
//			 			.authorities("ROLE_CLIENT")
//			 			.scopes("read", "write")
//			 			.secret("secret")
////			 			.redirectUris(tonrRedirectUri)
//			 		.and()
//		 		    .withClient("my-client-with-registered-redirect")
//	 			        .resourceIds(API_RESOURCE_ID)
//	 			        .authorizedGrantTypes("authorization_code", "client_credentials")
//	 			        .authorities("ROLE_CLIENT")
//	 			        .scopes("read", "trust")
//	 			        .redirectUris("http://anywhere?key=value")
//		 		    .and()
//	 		        .withClient("my-trusted-client")
// 			            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
// 			            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
// 			            .scopes("read", "write", "trust")
// 			            .accessTokenValiditySeconds(60)
//		 		    .and()
//	 		        .withClient("my-trusted-client-with-secret")
// 			            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
// 			            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
// 			            .scopes("read", "write", "trust")
// 			            .secret("somesecret")
//	 		        .and()
// 		            .withClient("my-less-trusted-client")
//			            .authorizedGrantTypes("authorization_code", "implicit")
//			            .authorities("ROLE_CLIENT")
//			            .scopes("read", "write", "trust")
//     		        .and()
//		            .withClient("my-less-trusted-autoapprove-client")
//		                .authorizedGrantTypes("implicit")
//		                .authorities("ROLE_CLIENT")
//		                .scopes("read", "write", "trust")
//		                .autoApprove(true)
//		             .and()
		            .withClient("test-client")
		            	.resourceIds(API_RESOURCE_ID)
		            	.secret("secret")
		                .authorizedGrantTypes("authorization_code")
		                .authorities("ROLE_CLIENT")
		                .scopes("read")
		                .autoApprove(true)
		                .accessTokenValiditySeconds(600)
		                .refreshTokenValiditySeconds(900)
		                ;
			
			// @formatter:on
		}

		@Bean
		public TokenStore tokenStore() {
			return new InMemoryTokenStore();
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.tokenStore(tokenStore)
//					.userApprovalHandler(userApprovalHandler)
					.authenticationManager(authenticationManager)
					;
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer.realm("sparklr2/client");
		}

	}

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
