package com.example.service.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String redisKey;
	
	public KafkaDto() {
		super();
	}

	public KafkaDto(String redisKey) {
		super();
		this.redisKey = redisKey;
	}

}
