package com.mycompany.chatservice;

import static java.util.stream.Collectors.joining;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.WebSocketTraceChannelInterceptorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.mycompany.chatservice.security.RememberMeServices;

import redis.embedded.RedisServer;
@SpringBootApplication
@Import(WebSocketTraceChannelInterceptorAutoConfiguration.class)
public class ChatServiceApplication {
	
//	private final Logger log = LoggerFactory.getLogger(RememberMeServices.class);

	public static void main(String[] args) {
		SpringApplication.run(ChatServiceApplication.class, args);
	}
	
	
//	@Autowired RedisServer redisServer;
//
//	@PostConstruct
//	public void start() {
//
//		log.info("starting redis...");
//		if (!redisServer.isActive()) redisServer.start();
//		log.info("redis listen ports: {}", redisServer.ports().stream()
//				.map(Object::toString).collect(joining(",")));
//	}
//
//	@PreDestroy
//	public void stop() {
//
//		log.info("shutting down redis...");
//		redisServer.stop();
//		log.info("bye!");
//	}
}
