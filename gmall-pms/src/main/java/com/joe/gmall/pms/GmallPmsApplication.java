package com.joe.gmall.pms;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/**
 * 缓存使用的场景：
 * 	1.一些固定的数据，不太变化的数据，高频访问的数据（基本不变），变化频率低的数据
 *
 *redisTemplate 模板模式
 */
@EnableCaching
@EnableDubbo
@MapperScan("com.joe.gmall.pms.mapper")
@SpringBootApplication
public class GmallPmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallPmsApplication.class, args);
	}

}
