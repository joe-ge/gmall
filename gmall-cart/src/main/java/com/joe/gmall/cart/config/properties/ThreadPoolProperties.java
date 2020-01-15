package com.joe.gmall.cart.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-12
 */
@Data
@ConfigurationProperties(prefix = "gmall.pool")
public class ThreadPoolProperties {

    /**
     * corePoolSize the number of threads to keep in the pool, even
     * if they are idle, unless {@code allowCoreThreadTimeOut} is set
     */
    private Integer corePoolSize;

    /**
     * maximumPoolSize the maximum number of threads to allow in the pool
     */
    private Integer maximumPoolSize;

    /**
     * keepAliveTime when the number of threads is greater than
     * the core, this is the maximum time that excess idle threads
     * will wait for new tasks before terminating.
     */
    private Duration keepAliveTime;

    /**
     * unit the time unit for the {@code keepAliveTime} argument
     */

    /**
     * workQueue the queue to use for holding tasks before they are
     * executed.  This queue will hold only the {@code Runnable}
     * tasks submitted by the {@code execute} method.
     */
    private Integer queueSize;
    /**
     * threadFactory the factory to use when the executor
     * creates a new thread
     */

    /**
     * handler the handler to use when execution is blocked
     * because the thread bounds and queue capacities are reached
     */
}
