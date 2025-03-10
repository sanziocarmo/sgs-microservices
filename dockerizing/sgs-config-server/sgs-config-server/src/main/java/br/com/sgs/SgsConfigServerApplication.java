package br.com.sgs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SgsConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgsConfigServerApplication.class, args);
	}

}
