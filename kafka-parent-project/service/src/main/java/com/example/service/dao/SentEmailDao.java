package com.example.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.service.model.SentEmail;

@Repository
public interface SentEmailDao extends JpaRepository<SentEmail, Long> {

}
