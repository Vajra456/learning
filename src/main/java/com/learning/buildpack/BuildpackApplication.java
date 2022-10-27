package com.learning.buildpack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class BuildpackApplication {

	public static void main(String[] args) {

//		new SpringApplicationBuilder(BuildpackApplication.class)
//				.profiles("dev")
//				.run(args);

		SpringApplication.run(BuildpackApplication.class, args);
	}

}
