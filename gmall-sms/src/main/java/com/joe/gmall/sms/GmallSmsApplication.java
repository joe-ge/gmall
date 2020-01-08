package com.joe.gmall.sms;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@MapperScan("com.joe.gmall.sms.mapper")
@SpringBootApplication
public class GmallSmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallSmsApplication.class, args);
	}

}
