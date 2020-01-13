package com.joe.gmall.portal.config;

import com.joe.gmall.portal.config.properties.ThreadPoolProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-12
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolConfig {
    private final Integer corePoolSize;
    private final Integer maximumPoolSize;
    private final Duration keepAliveTime;
    private final Integer queueSize;

    public ThreadPoolConfig(ThreadPoolProperties threadPoolProperties) {
        this.corePoolSize = threadPoolProperties.getCorePoolSize();
        this.maximumPoolSize = threadPoolProperties.getMaximumPoolSize();
        this.keepAliveTime = threadPoolProperties.getKeepAliveTime();
        this.queueSize = threadPoolProperties.getQueueSize();
    }


    @Bean
    public ThreadPoolExecutor mainThreadPoolExecutor(){
        LinkedBlockingDeque<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>(queueSize);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,
                10,TimeUnit.MINUTES,linkedBlockingDeque);
        return threadPoolExecutor;
    }

    @Bean
    public ThreadPoolExecutor otherThreadPoolExecutor(){
        LinkedBlockingDeque<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>(queueSize);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,
                10,TimeUnit.MINUTES,linkedBlockingDeque);
        return threadPoolExecutor;
    }

}
