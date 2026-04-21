package com.example.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class KafkaMessage<T> {
    private String id;
    private T payload;

    public KafkaMessage(T payload) {
        this.id = UUID.randomUUID().toString();
        this.payload = payload;
    }
}
