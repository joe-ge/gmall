package com.joe.gmall.pms;

import com.joe.gmall.pms.entity.Brand;
import com.joe.gmall.pms.entity.Product;
import com.joe.gmall.pms.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
@Slf4j
@SpringBootTest
class GmallPmsApplicationTests {

	@Autowired
	ProductService productService;

	@Autowired
	RedisTemplate<Object, Object> redisTemplate;

	@Test
	void contextLoads() {
		Product product = productService.getById(1);
		System.out.println(product);
		System.out.println(product.getName());
	}

	@Test
	void test() {
		Brand brand = new Brand();
		brand.setId(1L);
		brand.setName("tttt");
		redisTemplate.opsForValue().set("test",brand);
		log.debug("redis添加object成功");
		Object test = redisTemplate.opsForValue().get("test");
		log.debug("redis获取object:{}",test );
	}

}
