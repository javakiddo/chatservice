package com.mycompany.chatservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import redis.clients.jedis.Protocol;
import redis.embedded.RedisServer;


/**
 * Redis is required for {@link EnableRedisHttpSession} handling.
 */
@Configuration
@EnableRedisHttpSession
public class RedisConfig {
	
	
	 @Bean
	    public RedisServer redisServer() {
	        RedisServer.builder().reset();

	        return RedisServer.builder().port(Protocol.DEFAULT_PORT).build();
	    }
	 
	 @Bean
     public JedisConnectionFactory connectionFactory() throws Exception {
         return new JedisConnectionFactory();
     }


	 
	
//	@Bean
//	public static RedisServerBean redisServer() {
//		return new RedisServerBean();
//	}
//	
//	/**
//	 * Implements BeanDefinitionRegistryPostProcessor to ensure this Bean is
//	 * initialized before any other Beans. Specifically, we want to ensure that
//	 * the Redis Server is started before RedisHttpSessionConfiguration attempts
//	 * to enable Keyspace notifications.
//	 */
//	static class RedisServerBean implements InitializingBean, DisposableBean, BeanDefinitionRegistryPostProcessor {
//
//		private RedisServer redisServer;
//
//		@Override
//		public void afterPropertiesSet() throws Exception {
//
//			redisServer = new RedisServer(Protocol.DEFAULT_PORT);
//			redisServer.start();
//		}
//
//		@Override
//		public void destroy() throws Exception {
//			if (redisServer != null) {
//				redisServer.stop();
//			}
//		}
//
//		@Override
//		public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//		}
//
//		@Override
//		public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		}
//
//	}
}
