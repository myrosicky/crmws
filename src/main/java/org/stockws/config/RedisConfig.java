package org.stockws.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {
	
	private final static Logger log = LoggerFactory.getLogger(RedisConfig.class);
	
	private @Value("${redis.host}") String host;
	private @Value("${redis.port}") String port;
	private @Value("${redis.password}") String password;
	private @Value("${redis.salt}") String salt;
	private @Value("${redis.timeout}") int timeout;
	

//	@Bean
//	public JedisConnectionFactory jedisConnectionFactory() {
//		// JedisConnectionFactory jedisConnectionFactory = new
//		// JedisConnectionFactory(redisSentinelConfiguration());
//		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//		jedisConnectionFactory.setUsePool(true);
//		jedisConnectionFactory.setHostName(hostName);
//		jedisConnectionFactory.setPort(Integer.parseInt(port));
//		jedisConnectionFactory.setPassword(new Md5PasswordEncoder()
//				.encodePassword(password, salt));
//		return jedisConnectionFactory;
//	}
//
//	// @Bean
//	public RedisSentinelConfiguration redisSentinelConfiguration() {
//		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//				.master("mymaster").sentinel("127.0.0.1", 26379)
//				.sentinel("127.0.0.1", 26380);
//		return sentinelConfig;
//	}
//
//	@Bean
//	public RedisTemplate redisTemplate() {
//		RedisTemplate redisTemplate = new RedisTemplate();
//		redisTemplate.setConnectionFactory(jedisConnectionFactory());
//		// redisTemplate.afterPropertiesSet();
//		return redisTemplate;
//	}
//
//	@Bean
//	CacheManager cacheManager() {
//		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
//		return cacheManager;
//	}
	
	
	@Bean
	public Jedis jedis() {
		log.info("redis server [host:"+host+",port:"+port+"]");
		Jedis jedis = new Jedis(host, Integer.parseInt(port), timeout);
	    jedis.connect();
	    jedis.auth(new Md5PasswordEncoder().encodePassword(password, salt));
		return jedis;
	}
	
}
