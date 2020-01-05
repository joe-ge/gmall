package com.joe.gmall.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 如果导入一个依赖引入一个自动配置场景
 * 1）这个场景自动配置默认生效，我们就必须配置他
 * 2）不想配置：
 * 		1）排除依赖
 * 		2)排除这个场景的自动配置类
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GmallAdminWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallAdminWebApplication.class, args);
	}

}
