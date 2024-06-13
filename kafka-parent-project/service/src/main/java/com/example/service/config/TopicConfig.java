package com.example.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;

@Configuration
public class TopicConfig {
    public static final String SEND_MAIL_TOPIC = "sendMailTopic";
    public static final String SEND_MAIL_TOPIC_ID = "sendMailTopicId";

    @Bean
    public NewTopics topic() {
        return new NewTopics(
                TopicBuilder.name(SEND_MAIL_TOPIC)
                        .partitions(10)
                        .replicas(1)
                        .build());
    }

}
