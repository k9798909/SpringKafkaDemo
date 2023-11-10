package com.example.producer.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.producer.services.KafkaTemplateService;

@RestController
public class ProducerController {
    private KafkaTemplateService kafkaTemplateService;

    public ProducerController(KafkaTemplateService kafkaTemplateService) {
        this.kafkaTemplateService = kafkaTemplateService;
    }

    @GetMapping("/asynSend")
    public ResponseEntity<String> send() {
        kafkaTemplateService.asynSend("asynSend", "message send");
        return ResponseEntity.ok("非同步執行，Response已回應消費者執行的程序扔然在執行。");
    }

    @GetMapping("/toUpper/{upper}")
    public ResponseEntity<String> toUpper(@PathVariable("upper") String upper) throws Exception {
        String message = kafkaTemplateService.reply("toUpper", upper, String.class);
        return ResponseEntity.ok("生產者送出資料，等待消費者處理並回傳(處理動作:url參數轉成大寫):" + message);
    }

    @GetMapping("/book")
    public ResponseEntity<String> getBook() throws Exception {
        List<BookDto> list = this.kafkaTemplateService.replyForList("getBook", BookDto.class);
        return ResponseEntity.ok("book所有資料:" + list);
    }

    @PostMapping("/book")
    public ResponseEntity<String> addBook() throws Exception {
        String message1 = kafkaTemplateService.reply("addBook", new BookDto(null, "book1"), String.class);
        String message2 = kafkaTemplateService.reply("addBook", new BookDto(null, "book2"), String.class);
        return ResponseEntity.ok("第一本書新增:" + message1 + " 第二本書新增:" + message2);
    }

    public static record BookDto(String id, String name) {
    }
}
