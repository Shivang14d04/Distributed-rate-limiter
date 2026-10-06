package org.shivang.distributedratelimiter.Algorithm;

import org.junit.jupiter.api.Test;
import org.shivang.distributedratelimiter.redis.RedisRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RedisTokenBucketTest {
    @Autowired
    private RedisRateLimiter  redisRateLimiter;
    @Test
    public void testIsRequestAllowed() {

        RedisTokenBucket bucket =
                new RedisTokenBucket(50, 2, redisRateLimiter);

        boolean allowed = bucket.isRequestAllowed("test-user");
        assertTrue(allowed);
    }
}
