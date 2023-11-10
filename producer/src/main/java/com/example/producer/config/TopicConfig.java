package com.example.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("asynSend")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicToUpperReq() {
        return TopicBuilder.name("toUpper")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic getBook() {
        return TopicBuilder.name("getBook")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic addBook() {
        return TopicBuilder.name("addBook")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicRepliess() {
        return TopicBuilder.name("replies")
                .partitions(10)
                .replicas(1)
                .build();
    }

}
