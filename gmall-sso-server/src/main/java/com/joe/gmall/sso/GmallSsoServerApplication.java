package com.joe.gmall.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GmallSsoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallSsoServerApplication.class, args);
	}

}
