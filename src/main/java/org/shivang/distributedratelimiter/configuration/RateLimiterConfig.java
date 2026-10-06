package org.shivang.distributedratelimiter.configuration;

import lombok.RequiredArgsConstructor;
import org.shivang.distributedratelimiter.Algorithm.RedisTokenBucket;
import org.shivang.distributedratelimiter.redis.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
public class RateLimiterConfig {


    @Bean
    public RedisTokenBucket getRedisTokenBucket(RedisRateLimiter redisRateLimiter) {
        return new RedisTokenBucket(50,2,redisRateLimiter);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        return template;
    }
}
