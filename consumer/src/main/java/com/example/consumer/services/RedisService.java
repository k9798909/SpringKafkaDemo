package com.example.consumer.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.consumer.Utils.JsonUtility;

@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
    private static final String SEQ_KEY = "seqKey";

    private RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> String saveToJson(T target) {
        String seq = getNextSequence();
        redisTemplate.opsForValue().set(seq, JsonUtility.objectToJson(target));
        return seq;
    }

    public <T> T findByKey(String key, Class<T> clazz) {
        return JsonUtility.jsonToObject(redisTemplate.opsForValue().get(key), clazz);
    }

    public <T> List<T>queryForList(String key, Class<T> clazz) {
        return JsonUtility.jsonToList(redisTemplate.opsForValue().get(key), clazz);
    }

    private String getNextSequence() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Long seq = ops.increment(SEQ_KEY, 1);
        if (seq == null) {
            logger.error("nextSequence must exist");
            throw new RuntimeException("nextSequence must exist");
        }
        return seq.toString(); // 自增 1，返回新的流水號
    }

}
