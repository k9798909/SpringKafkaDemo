package com.example.service.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.service.common.JsonUtility;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    private JedisPool jedisPool;

    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 設定redis資料
     * 
     * @param <T>
     * @param key
     * @param target
     */
    public <T> void set(String key, T target) {
        try (Jedis jedis = getJedis()) {
            jedis.set(key, JsonUtility.objectToJson(target));
        }
    }

    /**
     * 取得redis資料
     * 
     * @param <T>
     * @param key
     * @param clazz
     * @return
     */
    public <T> Optional<T> get(String key, Class<T> clazz) {
        try (Jedis jedis = getJedis()) {
            String result = jedis.get(key);

            if (result == null) {
                return Optional.empty();
            }

            return Optional.of(JsonUtility.jsonToObject(result, clazz));
        }
    }

    private Jedis getJedis() {
        return jedisPool.getResource();
    }
}
