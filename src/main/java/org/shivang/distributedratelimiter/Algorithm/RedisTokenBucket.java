package org.shivang.distributedratelimiter.Algorithm;

import lombok.Data;
import org.shivang.distributedratelimiter.redis.RedisRateLimiter;

import java.util.List;
@Data
public class RedisTokenBucket {

    private final int capacity;
    private final double refillRate;
    private final RedisRateLimiter redisRateLimiter;

    public RedisTokenBucket(
            int capacity,
            double refillRate,
            RedisRateLimiter redisRateLimiter) {

        this.capacity = capacity;
        this.refillRate = refillRate;
        this.redisRateLimiter = redisRateLimiter;
    }

    public List<Object> isRequestAllowed(String key) {


        return redisRateLimiter.executeRateLimit(
                key,
                capacity,
                refillRate
        );
    }
}