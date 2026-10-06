package org.shivang.distributedratelimiter.Algorithm;

import lombok.Data;
import org.shivang.distributedratelimiter.redis.RedisRateLimiter;

import java.util.Map;
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

    public boolean isRequestAllowed(String key) {

        Map<Object, Object> state = redisRateLimiter.getState(key);

        long now = System.nanoTime();

        if (state.isEmpty()) {
            double currentTokens = capacity - 1;
            redisRateLimiter.saveState(
                    key,
                    currentTokens,
                    now
            );
            return true;
        }

        double currentTokens =
                Double.parseDouble(
                        state.get("currentTokens").toString()
                );

        long lastRefillTime =
                Long.parseLong(
                        state.get("lastRefillTime").toString()
                );

        long elapsedTime = now - lastRefillTime;

        double secondsPassed =
                elapsedTime / 1_000_000_000.0;

        double tokensToAdd =
                secondsPassed * refillRate;

        currentTokens =
                Math.min(
                        capacity,
                        currentTokens + tokensToAdd
                );

        lastRefillTime = now;

        // Consume one token
        if (currentTokens >= 1) {

            currentTokens--;

            redisRateLimiter.saveState(
                    key,
                    currentTokens,
                    lastRefillTime
            );

            return true;
        }

        redisRateLimiter.saveState(
                key,
                currentTokens,
                lastRefillTime
        );

        return false;
    }

    public double getCurrentTokens(String key) {
        Map<Object, Object> state = redisRateLimiter.getState(key);

        if (state.isEmpty()) {
            return capacity;
        }

        return Double.parseDouble(
                state.get("currentTokens").toString()
        );
    }
}