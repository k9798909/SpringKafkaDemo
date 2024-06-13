package com.example.consumer.kafkaListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.service.config.TopicConfig;
import com.example.service.dto.KafkaDto;
import com.example.service.dto.SendMailDto;
import com.example.service.services.MailService;
import com.example.service.services.RedisService;

@Configuration
public class kafkaListener {
	private static final Logger logger = LoggerFactory.getLogger(kafkaListener.class);

	@Autowired
	private RedisService redisService;
	@Autowired
	private MailService mailService;

	@KafkaListener(id = TopicConfig.SEND_MAIL_TOPIC_ID, topics = TopicConfig.SEND_MAIL_TOPIC)
	public void listenMailTopic(KafkaDto dto) {
		try {
			SendMailDto sendMailDto = redisService.get(dto.getRedisKey(), SendMailDto.class)
					.orElseThrow(() -> new RuntimeException("SendMailDto Not Fund"));
			
			mailService.sendMail(sendMailDto.getEmail(), sendMailDto.getName());
		} catch (Exception e) {
			logger.error("listenMailTopic error", e);
		}
	}
}
