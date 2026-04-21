package com.example.consumer.kafkaListener;

import com.example.service.dto.KafkaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.service.config.TopicConfig;
import com.example.service.dto.SendMailDto;
import com.example.service.services.MailService;

@Configuration
public class kafkaListener {
    private static final Logger logger = LoggerFactory.getLogger(kafkaListener.class);

    private final MailService mailService;

    public kafkaListener(MailService mailService) {
        this.mailService = mailService;
    }

    @KafkaListener(id = TopicConfig.SEND_MAIL_TOPIC_ID, topics = TopicConfig.SEND_MAIL_TOPIC)
    public void listenMailTopic(KafkaMessage<SendMailDto> dto) {
        try {
            mailService.sendMail(dto.getPayload().email(), dto.getPayload().name());
        } catch (Exception e) {
            logger.error("listenMailTopic error", e);
        }
    }
}
