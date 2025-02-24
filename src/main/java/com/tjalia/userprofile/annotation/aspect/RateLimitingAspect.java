package com.tjalia.userprofile.annotation.aspect;

import com.tjalia.userprofile.annotation.RateLimit;
import com.tjalia.userprofile.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitingAspect {

    private final Map<String, RateLimitTracker> count = new ConcurrentHashMap<>();
    private final HttpServletRequest request;

    @Around("@annotation(rateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String clientIp = getClientIP();
        String method = joinPoint.getSignature().toShortString();

        String key = clientIp + ":" + method;
        RateLimitTracker tracker = count.computeIfAbsent(key,
                k -> new RateLimitTracker(rateLimit.limit(), rateLimit.window()));

        synchronized (tracker) {
            if(tracker.isLimitExceeded()) {
                log.warn("Rate limit exceeded: {} from IP {}", key, clientIp);
                throw new ApiException(HttpStatus.TOO_MANY_REQUESTS, "Too many request. Please try again later.");
            }
            tracker.increment();
        }

        return joinPoint.proceed();
    }


    private String getClientIP() {
        String ip = request.getHeader("X-Forwarded-For");
        return (ip == null || ip.isEmpty()) ? request.getRemoteAddr() : ip.split(",")[0];
    }

    private static class RateLimitTracker {
        private final int limit;
        private final long timeWindowMillis;
        private AtomicInteger count;
        private Instant windowStart;

        public RateLimitTracker(int limit, int timeWindowSeconds) {
            this.limit = limit;
            this.timeWindowMillis = timeWindowSeconds * 1000L;
            this.count = new AtomicInteger(0);
            this.windowStart = Instant.now();
        }

        public synchronized boolean isLimitExceeded() {
            resetIfNeeded();
            return count.get() >= limit;
        }

        public synchronized void increment() {
            resetIfNeeded();
            count.incrementAndGet();
        }

        private void resetIfNeeded() {
            if (Instant.now().isAfter(windowStart.plusMillis(timeWindowMillis))) {
                count.set(0);
                windowStart = Instant.now();
            }
        }
    }
}
