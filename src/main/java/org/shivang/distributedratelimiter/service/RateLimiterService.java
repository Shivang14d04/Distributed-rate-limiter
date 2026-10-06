package org.shivang.distributedratelimiter.service;

import lombok.RequiredArgsConstructor;
import org.shivang.distributedratelimiter.Algorithm.RedisTokenBucket;
import org.shivang.distributedratelimiter.RateLimiterResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimiterService {
    private final RedisTokenBucket tokenBucket;


    public RateLimiterResult getRateLimiterResult(String key) {
        RateLimiterResult result = new RateLimiterResult();
        result.setRequestAllowed(tokenBucket.isRequestAllowed(key));
        result.setLimit(tokenBucket.getCapacity());
        result.setRemainingTokens(tokenBucket.getCurrentTokens(key));
        return result;
    }
}
