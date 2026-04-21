package com.example.service.services;

import com.example.service.dto.KafkaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaTemplateService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTemplateService.class);
    private final KafkaTemplate<String, KafkaMessage<?>> kafkaTemplate;

    public KafkaTemplateService(KafkaTemplate<String, KafkaMessage<?>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void asyncSend(String topic, KafkaMessage<?> kafkaMessage) {
        logger.info("start asyncSend topic:{}", topic);
        this.kafkaTemplate.send(topic, kafkaMessage);
        logger.info("end asyncSend topic:{}", topic);
    }

}
