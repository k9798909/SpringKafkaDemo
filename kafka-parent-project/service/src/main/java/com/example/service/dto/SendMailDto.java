package com.example.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMailDto {
	private String name;
	private String email;

	public SendMailDto(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public SendMailDto() {
		super();
	}
}
