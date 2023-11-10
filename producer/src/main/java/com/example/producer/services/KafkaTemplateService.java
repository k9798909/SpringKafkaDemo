package com.example.producer.services;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import com.example.producer.Utils.JsonUtility;

@Service
public class KafkaTemplateService {
    private ReplyingKafkaTemplate<String, Object, Object> kafkaTemplate;
    private RedisService redisService;

    public KafkaTemplateService(ReplyingKafkaTemplate<String, Object, Object> kafkaTemplate,
            RedisService redisService) {
        this.kafkaTemplate = kafkaTemplate;
        this.redisService = redisService;
    }

    /**
     * 透過kafkaTemplate傳送資料給消費者，不等待消費者處理完畢
     * @param topic
     * @param key
     */
    public void asynSend(String topic, String key) {
        this.kafkaTemplate.send(topic, new KafkaDto(key));
    }

    /**
     * 透過kafkaTemplate傳送資料給消費者，並等待消費者處理完畢後回傳資料
     * @param <T>
     * @param topic
     * @param outClazz
     * @return
     * @throws Exception
     */
    public <T> T reply(String topic, Class<T> outClazz) throws Exception {
        return reply(topic, "", outClazz);
    }

    /**
     * 透過kafkaTemplate傳送資料給消費者，並等待消費者處理完畢後回傳資料
     * @param <T>
     * @param topic
     * @param in
     * @param outClazz
     * @return
     * @throws Exception
     */
    public <T> T reply(String topic, Object in, Class<T> outClazz) throws Exception {
        // 將傳給消費者的資料存入redis後取得key
        String inKey = redisService.saveToJson(in);
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, new KafkaDto(inKey));

        // 將key傳給消費者，等待消費者處理完畢
        RequestReplyFuture<String, Object, Object> replyFuture = this.kafkaTemplate.sendAndReceive(producerRecord);
        ConsumerRecord<String, Object> consumerRecord = replyFuture.get();

        // 取得回傳的key，在用key取得回傳資料
        String jsonString = consumerRecord.value().toString();
        String outKey = JsonUtility.jsonToObject(jsonString, KafkaDto.class).key();
        return redisService.findByKey(outKey, outClazz);
    }

    /**
     * 透過kafkaTemplate傳送資料給消費者，並等待消費者處理完畢後回傳資料
     * @param <T>
     * @param topic
     * @param outClazz
     * @return
     * @throws Exception
     */
    public <T> List<T> replyForList(String topic, Class<T> outClazz) throws Exception {
        return replyForList(topic, "", outClazz);
    }

    /**
     * 透過kafkaTemplate傳送資料給消費者，並等待消費者處理完畢後回傳資料
     * @param <T>
     * @param topic
     * @param in
     * @param outClazz
     * @return
     * @throws Exception
     */
    public <T> List<T> replyForList(String topic, Object in, Class<T> outClazz) throws Exception {
        // 將傳給消費者的資料存入redis後取得key
        String inKey = redisService.saveToJson(in);
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, new KafkaDto(inKey));

        // 將key傳給消費者，等待消費者處理完畢
        RequestReplyFuture<String, Object, Object> replyFuture = this.kafkaTemplate.sendAndReceive(producerRecord);
        ConsumerRecord<String, Object> consumerRecord = replyFuture.get();

        // 取得回傳的key，在用key取得回傳資料
        String jsonString = consumerRecord.value().toString();
        String outKey = JsonUtility.jsonToObject(jsonString, KafkaDto.class).key();
        return redisService.queryForList(outKey, outClazz);
    }

    public static record KafkaDto(String key) {
    }
}
