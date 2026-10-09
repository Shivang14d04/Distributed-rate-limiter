package org.shivang.distributedratelimiter.service;

import lombok.RequiredArgsConstructor;
import org.shivang.distributedratelimiter.Algorithm.RedisTokenBucket;
import org.shivang.distributedratelimiter.RateLimiterResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RateLimiterService {
    private final RedisTokenBucket tokenBucket;


    public RateLimiterResult getRateLimiterResult(String userId) {
        String key = "rate_limit:user:"+userId;
        List<Object> result = tokenBucket.isRequestAllowed(key);
        boolean allowed = Long.parseLong(result.get(0).toString()) ==1;
        double remainingTokens = Double.parseDouble(result.get(1).toString());

        RateLimiterResult response = new RateLimiterResult();

        response.setRequestAllowed(allowed);
        response.setLimit(tokenBucket.getCapacity());
        response.setRemainingTokens(remainingTokens);

        return response;
    }
}
