package com.zkteco.bionest.samples.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NacosApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(NacosApplication.class, args);
		String userName = applicationContext.getEnvironment().getProperty("spring.datasource.url");
		String userAge = applicationContext.getEnvironment().getProperty("user.age");
		System.err.println("spring.datasource.url :"+userName+"; age: "+userAge);
	}
}
