package com.example.consumer.kafkaListener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;

import com.example.consumer.model.Book;
import com.example.consumer.repository.BookRepository;
import com.example.consumer.services.RedisService;

@Configuration
public class kafkaListener {
    private static final Logger logger = LoggerFactory.getLogger(kafkaListener.class);

    private BookRepository bookRepository;
    private RedisService redisService;

    public kafkaListener(BookRepository bookRepository, RedisService redisService) {
        this.bookRepository = bookRepository;
        this.redisService = redisService;
    }

    @KafkaListener(id = "asynSendId", topics = "asynSend")
    public void listen(KafkaDto dto) throws InterruptedException {
        logger.info("asynSend執行中...");
        Thread.sleep(5000);
        logger.info("asynSend執行完畢");
    }

    @KafkaListener(id = "toUpperId", topics = "toUpper")
    @SendTo
    public KafkaDto toUpper(KafkaDto dto) {
        String inMessage = redisService.findByKey(dto.key(), String.class);
        String outKey = redisService.saveToJson(inMessage.toUpperCase());
        return new KafkaDto(outKey);
    }

    @KafkaListener(id = "getBookId", topics = "getBook")
    @SendTo
    public KafkaDto getBook(KafkaDto dto) {
        List<Book> allBook = bookRepository.findAll();
        String key = redisService.saveToJson(allBook);
        return new KafkaDto(key);
    }

    @KafkaListener(id = "addBookId", topics = "addBook")
    @SendTo
    public KafkaDto addBook(KafkaDto dto) {
        try {
            Book book = redisService.findByKey(dto.key(), Book.class);
            bookRepository.save(book);
            String key = redisService.saveToJson("成功");
            return new KafkaDto(key);
        } catch (Exception e) {
            String key = redisService.saveToJson("失敗：" + e.getMessage());
            return new KafkaDto(key);
        }
    }

    public static record KafkaDto(String key) {
    }

}
