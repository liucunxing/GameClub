package com.base.utils.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    public void set(String key,Object value,long expireTime){
        redisTemplate.opsForValue().set(key, value, expireTime,TimeUnit.MINUTES);
    }
    public void hashSet(String key,String hKey,Object value,long expireTime){
        redisTemplate.opsForHash().put(key,hKey,value);
        redisTemplate.expire(key,expireTime,TimeUnit.MINUTES);
    }

    public Object getByHashKey(String key,String hashKey){
        return redisTemplate.opsForHash().get(key,hashKey);
    };
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
