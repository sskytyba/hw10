package com.example.app;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final StringRedisTemplate template;

    @GetMapping("/get")
    public String get(@RequestParam("key") String key) {
        return this.template.opsForValue().get(key);
    }

    @PostMapping("/set")
    public void set(@RequestBody KeyValue keyValue) {
        this.template.opsForValue().set(keyValue.getKey(), keyValue.getValue());
    }

    @Data
    public static class KeyValue {
        private String key;
        private String value;
    }

}
