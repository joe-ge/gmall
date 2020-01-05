package com.joe.gmall.pms;

import com.joe.gmall.pms.entity.Product;
import com.joe.gmall.pms.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GmallPmsApplicationTests {

	@Autowired
	ProductService productService;

	@Test
	void contextLoads() {
		Product product = productService.getById(1);
		System.out.println(product);
		System.out.println(product.getName());
	}

}
