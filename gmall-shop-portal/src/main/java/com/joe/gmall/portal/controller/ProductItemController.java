package com.joe.gmall.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.joe.gmall.pms.service.ProductService;
import com.joe.gmall.to.es.EsProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-12
 */

@CrossOrigin
@RestController
public class ProductItemController {

    @Reference(version = "1.0")
    ProductService productService;

    @Qualifier("mainThreadPoolExecutor")
    @Autowired
    ThreadPoolExecutor mainThreadPoolExecutor;

    @Qualifier("otherThreadPoolExecutor")
    @Autowired
    ThreadPoolExecutor otherThreadPoolExecutor;


    public EsProduct complexProductInfo(Long id) {

        //第一种：Thread.start()
//        Thread thread = new Thread(() -> {
//        });
//        thread.start();
        //第二种：交给线程池
//        mainThreadPoolExecutor.submit(() -> {
//            System.out.println("查看基本信息");
//        });
        //第三种：线程池+返回结果
        CompletableFuture.supplyAsync(() -> {
            return "jieguo";
        }, mainThreadPoolExecutor).whenComplete((r, e) -> {
            System.out.println("处理结果：" + r);
            System.out.println("处理异常" + e);
        });


        return null;
    }

//    @GetMapping("/detail/{id}")
    @GetMapping("/item/{id}.html")
    public EsProduct productInfo(@PathVariable("id") Long id) {
        EsProduct esProduct = productService.productAllInfo(id);
        return esProduct;
    }

    @GetMapping("/detail/sku/{id}" )
    public EsProduct productSkuInfo(@PathVariable("id") Long id){
        EsProduct esProduct = productService.productSkuInfo(id);
        return esProduct;
    }
}

