package net.softbell.bsh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import net.softbell.bsh.service.NettyServer;

@SpringBootApplication
public class BellsmarthomeApplication {
	//@Autowired
    //private ApplicationContext context;
	private static ConfigurableApplicationContext context;
	public static NettyServer nettyServer;

	public static void main(String[] args) {	
		context = SpringApplication.run(BellsmarthomeApplication.class, args);
        nettyServer = context.getBean(NettyServer.class);
        nettyServer.start();
	}
}
