package com.example.service.dto;

public record SendMailDto(String name,
                          String email) {

    public SendMailDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
