package com.joe.gmall.portal.controller;
import	java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-12
 */
@RestController
@RequestMapping("/threadPool")
public class ThreadPoolController {

    @Qualifier("mainThreadPoolExecutor")
    @Autowired
    ThreadPoolExecutor mainThreadPoolExecutor;

    @Qualifier("otherThreadPoolExecutor")
    @Autowired
    ThreadPoolExecutor otherThreadPoolExecutor;

    @GetMapping("/main/status")
    public String  getThreadPoolInfo(){
        return mainThreadPoolExecutor.toString();
    }
}
