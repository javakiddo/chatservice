package com.mycompany.chatservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.WebSocketTraceChannelInterceptorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.mycompany.chatservice.domain.Authority;
import com.mycompany.chatservice.domain.User;
import com.mycompany.chatservice.repository.AuthortiyRepository;
@SpringBootApplication
@Import(WebSocketTraceChannelInterceptorAutoConfiguration.class)
public class ChatServiceApplication  implements CommandLineRunner{
	
	private AuthortiyRepository authortiyRepository;
	
//	private final Logger log = LoggerFactory.getLogger(RememberMeServices.class);

	public static void main(String[] args) {
		SpringApplication.run(ChatServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Authority a= new Authority();
		a.setName("admin");
		
		
		
		User user= new User();
		

		
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
