package com.joe.gmall.pms.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @program: gmail
 * @description
 * @author: Joe
 * @create: 2020-01-07
 */
@Configuration
public class RedisCacheConfig {

    /**
     *RedisTemplate与RedisCacheConfiguration 没有直接关系
     *     @Bean
     *     public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
     *             throws UnknownHostException {
     *         RedisTemplate<Object, Object> template = new RedisTemplate<>();
     *         template.setConnectionFactory(redisConnectionFactory);
     *         template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
     *         return template;
     *     }
     * @param
     * @return
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties){

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();

        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

}
