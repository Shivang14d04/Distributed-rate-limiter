package org.shivang.distributedratelimiter.Algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenBucketTest {


    @Test
    public void shouldAllowRequestsWhenTokensAvailable() {
        TokenBucket tokenBucket = new TokenBucket(4,1);
        assertTrue(tokenBucket.isRequestAllowed());
        assertTrue(tokenBucket.isRequestAllowed());
        assertTrue(tokenBucket.isRequestAllowed());
        assertTrue(tokenBucket.isRequestAllowed());
    }
    @Test
    public void shouldRejectRequestWhenTokensExhausted(){
        TokenBucket tokenBucket = new TokenBucket(4,1);
        assertTrue(tokenBucket.isRequestAllowed());
        assertTrue(tokenBucket.isRequestAllowed());
        assertTrue(tokenBucket.isRequestAllowed());
        assertTrue(tokenBucket.isRequestAllowed());
        assertFalse(tokenBucket.isRequestAllowed());
    }

    @Test
    public void shouldAllowRequestAfterTokenRefill()throws InterruptedException {
        TokenBucket tokenBucket = new TokenBucket(2,1);
        assertTrue(tokenBucket.isRequestAllowed());
        assertTrue(tokenBucket.isRequestAllowed());
        Thread.sleep(1000);
        assertTrue(tokenBucket.isRequestAllowed());

    }
    @Test
    public void shouldNotExceedBucketCapacity() throws InterruptedException {
        TokenBucket tokenBucket = new TokenBucket(2, 5);
        assertTrue(tokenBucket.isRequestAllowed());
        assertTrue(tokenBucket.isRequestAllowed());
        Thread.sleep(1000);
        assertTrue(tokenBucket.isRequestAllowed());
        assertTrue(tokenBucket.isRequestAllowed());
        assertFalse(tokenBucket.isRequestAllowed());
    }
}
