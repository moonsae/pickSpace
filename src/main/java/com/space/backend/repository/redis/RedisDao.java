package com.space.backend.repository.redis;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisDao {
    private final RedisTemplate<String, Object> redisTemplate;


    public void setValues(String key, String value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(timeoutSeconds));
        log.info("Set refreshToken with TTL = {} seconds", timeoutSeconds);
    }
    public String getValues(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }
}
