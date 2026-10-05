package org.shivang.distributedratelimiter.configuration;

import org.shivang.distributedratelimiter.Algorithm.TokenBucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

    @Bean
    public TokenBucket getTokenBucket() {
        return new TokenBucket(50,2);
    }
}
