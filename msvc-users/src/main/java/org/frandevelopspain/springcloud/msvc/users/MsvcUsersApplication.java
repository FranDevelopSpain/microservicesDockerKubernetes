package org.frandevelopspain.springcloud.msvc.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcUsersApplication.class, args);
	}

}
