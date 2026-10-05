package org.shivang.distributedratelimiter;

import lombok.Data;

@Data
public class RateLimiterResult {
    private double remainingTokens;
    private int limit;
    private boolean isRequestAllowed;

}
