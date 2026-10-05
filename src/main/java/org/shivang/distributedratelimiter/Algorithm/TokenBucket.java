package org.shivang.distributedratelimiter.Algorithm;

import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class TokenBucket {
    private final int capacity;
    private  double currentTokens;
    private  final double refillRate;
    private  long lastRefillTime;

    public TokenBucket( int capacity, double refillRate) {
        this.currentTokens = capacity;
        this.lastRefillTime = System.nanoTime();
        this.refillRate = refillRate;
        this.capacity = capacity;
    }

    public boolean isRequestAllowed() {
        long now = System.nanoTime();
        long elapsedTime = now - lastRefillTime;
        double secondsPassed = elapsedTime / 1_000_000_000.0;
        double tokensToAdd = secondsPassed*refillRate;
        currentTokens = Math.min(currentTokens+ tokensToAdd, capacity);
        lastRefillTime = now;
        if(currentTokens>=1){
            currentTokens--;
            return true;
        }
        return false;

    }






}