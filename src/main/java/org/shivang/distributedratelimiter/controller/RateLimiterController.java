package org.shivang.distributedratelimiter.controller;

import lombok.RequiredArgsConstructor;
import org.shivang.distributedratelimiter.RateLimiterResult;
import org.shivang.distributedratelimiter.service.RateLimiterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rate-limiter")
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    @GetMapping("/response")
    public ResponseEntity<RateLimiterResult> response() {
        String key = "test-user";

        RateLimiterResult result =
                rateLimiterService.getRateLimiterResult(key);

        if (result.isRequestAllowed()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(result);
        }
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(result);
    }
}