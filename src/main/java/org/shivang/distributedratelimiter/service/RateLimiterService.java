package org.shivang.distributedratelimiter.service;

import lombok.RequiredArgsConstructor;
import org.shivang.distributedratelimiter.Algorithm.TokenBucket;
import org.shivang.distributedratelimiter.RateLimiterResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimiterService {
    private final TokenBucket tokenBucket;


    public RateLimiterResult getRateLimiterResult() {
        RateLimiterResult result = new RateLimiterResult();
        result.setRequestAllowed(tokenBucket.isRequestAllowed());
        result.setLimit(tokenBucket.getCapacity());
        result.setRemainingTokens(tokenBucket.getCurrentTokens());
        return result;
    }
}
