package com.example.producer.controllers;

import com.example.producer.records.SendMailRequest;
import com.example.service.config.TopicConfig;
import com.example.service.dto.KafkaMessage;
import com.example.service.dto.SendMailDto;
import com.example.service.services.KafkaTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SendMailController {
    private final KafkaTemplateService kafkaTemplateService;

    public SendMailController(KafkaTemplateService kafkaTemplateService) {
        this.kafkaTemplateService = kafkaTemplateService;
    }

    @GetMapping({"/"})
    public String sendMail() {
        return "index";
    }

    @ResponseBody
    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(SendMailRequest request) {
        SendMailDto sendMailDto = new SendMailDto(request.name(), request.email());
        kafkaTemplateService.asyncSend(TopicConfig.SEND_MAIL_TOPIC, new KafkaMessage<>(sendMailDto));
        return ResponseEntity.status(HttpStatus.OK).body("已將資料傳送至KAFKA CONSUMER");
    }
}
