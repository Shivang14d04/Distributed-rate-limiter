package org.shivang.distributedratelimiter.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RedisRateLimiter {
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveState(String key, double currentTokens, long lastRefillTime){
        redisTemplate.opsForHash().put(key, "currentTokens", currentTokens);
        redisTemplate.opsForHash().put(key, "lastRefillTime", lastRefillTime);
    }

    public Map<Object,Object> getState(String key){
      return   redisTemplate.opsForHash().entries(key);
    }



}
