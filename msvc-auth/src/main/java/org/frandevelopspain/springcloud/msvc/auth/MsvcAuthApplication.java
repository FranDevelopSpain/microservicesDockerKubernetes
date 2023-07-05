package org.frandevelopspain.springcloud.msvc.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsvcAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAuthApplication.class, args);
	}

}
