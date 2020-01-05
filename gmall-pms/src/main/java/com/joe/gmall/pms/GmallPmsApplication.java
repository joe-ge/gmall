package com.joe.gmall.pms;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@MapperScan("com.joe.gmall.pms.mapper")
@SpringBootApplication
public class GmallPmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallPmsApplication.class, args);
	}

}
