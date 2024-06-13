package com.example.service.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.service.dto.KafkaDto;

@Service
public class KafkaTemplateService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTemplateService.class);
    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaTemplateService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 透過kafkaTemplate傳送至訊息佇列
     * 
     * @param topic
     * @param redisKey
     */
    public void asynSend(String topic, String redisKey) {
    	logger.info("start asynSend redisKey:" + redisKey);
        this.kafkaTemplate.send(topic, new KafkaDto(redisKey));
        logger.info("end asynSend redisKey:" + redisKey);
    }

}
