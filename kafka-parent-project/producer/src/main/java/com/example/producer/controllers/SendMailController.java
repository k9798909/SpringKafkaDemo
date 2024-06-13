package com.example.producer.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.producer.records.SendMailRequest;
import com.example.service.config.TopicConfig;
import com.example.service.dto.SendMailDto;
import com.example.service.services.KafkaTemplateService;
import com.example.service.services.RedisService;


@Controller
public class SendMailController {
	@Autowired
    private RedisService redisService;
	@Autowired
    private KafkaTemplateService kafkaTemplateService;
	
	@GetMapping({"/"})
	public String sendMail(Model model) {
		return "index";
	}
	
	@ResponseBody
    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(SendMailRequest request) {
    	String redisKey = UUID.randomUUID().toString();
    	redisService.set(redisKey, new SendMailDto(request.name(),request.email()));
    	kafkaTemplateService.asynSend(TopicConfig.SEND_MAIL_TOPIC, redisKey);
        return ResponseEntity.status(HttpStatus.OK).body("已將資料傳送至KAFKA CONSUMER");
    }
}
