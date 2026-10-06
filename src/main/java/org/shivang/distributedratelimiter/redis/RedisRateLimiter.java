package org.shivang.distributedratelimiter.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RedisRateLimiter {
    private final StringRedisTemplate redisTemplate;
    private static final String LUA_SCRIPT = """
        local currentTokens =
            tonumber(redis.call('HGET', KEYS[1], 'currentTokens'))

        local lastRefillTime =
            tonumber(redis.call('HGET', KEYS[1], 'lastRefillTime'))

        local time = redis.call('TIME')
        local now =
            tonumber(time[1]) * 1000000 + tonumber(time[2])

        local capacity = tonumber(ARGV[1])
        local refillRate = tonumber(ARGV[2])

        if currentTokens == nil then
            currentTokens = capacity
            lastRefillTime = now
        end

        local elapsedTime = now - lastRefillTime
        local secondsPassed = elapsedTime / 1000000

        local tokensToAdd =
            secondsPassed * refillRate

        currentTokens = math.min(
            capacity,
            currentTokens + tokensToAdd
        )

        local allowed = 0

        if currentTokens >= 1 then
            currentTokens = currentTokens - 1
            allowed = 1
        end

        redis.call(
            'HSET',
            KEYS[1],
            'currentTokens',
            currentTokens
        )

        redis.call(
            'HSET',
            KEYS[1],
            'lastRefillTime',
            now
        )

        return {allowed, currentTokens}
        """;

    public void saveState(String key, double currentTokens, long lastRefillTime){
        redisTemplate.opsForHash().put(key, "currentTokens", currentTokens);
        redisTemplate.opsForHash().put(key, "lastRefillTime", lastRefillTime);
    }

    public Map<Object,Object> getState(String key){
      return   redisTemplate.opsForHash().entries(key);
    }

    public List<Object> executeRateLimit(
            String key,
            int capacity,
            double refillRate) {

        DefaultRedisScript<List> script =
                new DefaultRedisScript<>();

        script.setScriptText(LUA_SCRIPT);
        script.setResultType(List.class);
        System.out.println("capacity = " + capacity);
        System.out.println("refillRate = " + refillRate);

       return redisTemplate.execute(
                script,
                List.of(key),
                String.valueOf(capacity),
                String.valueOf(refillRate)
        );
    }



}
