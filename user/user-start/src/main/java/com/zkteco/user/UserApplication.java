package com.zkteco.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.zkteco")
@EntityScan("com.zkteco")
@EnableJpaRepositories(basePackages = "com.zkteco.bionest.user")
public class UserApplication {

	public static void main(String[] args) {
		new SpringApplication(UserApplication.class).run(args);
	}

}
