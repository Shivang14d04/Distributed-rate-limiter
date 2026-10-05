package org.shivang.distributedratelimiter.controller;

import lombok.RequiredArgsConstructor;
import org.shivang.distributedratelimiter.RateLimiterResult;
import org.shivang.distributedratelimiter.service.RateLimiterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rate-limiter")
public class RateLimiterController {
    private final RateLimiterService rateLimiterService;


    @GetMapping("/response")
    public ResponseEntity<RateLimiterResult> response(){
       RateLimiterResult result = rateLimiterService.getRateLimiterResult();
       if(result.isRequestAllowed()){
           return ResponseEntity.status(HttpStatus.OK).body(result);
       }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(result);
    }

}
