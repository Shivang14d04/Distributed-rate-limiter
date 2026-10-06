package org.shivang.distributedratelimiter.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

@SpringBootTest
public class RedisRateLimiterTest {
    @Autowired
    private  RedisRateLimiter redisRateLimiter;



    @Test
    public void testRateLimiterWithRedis(){
        String key = "key";
        double currentTokens = 50;
        long lastRefillTime = 12345678L;
        redisRateLimiter.saveState(key, currentTokens, lastRefillTime);
        Map<Object,Object> state = redisRateLimiter.getState(key);
        assertEquals(currentTokens, state.get("currentTokens"));
        assertEquals(lastRefillTime, state.get("lastRefillTime"));
    }
}
