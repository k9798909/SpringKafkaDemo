package com.example.service.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service.dao.SentEmailDao;
import com.example.service.model.SentEmail;

import jakarta.transaction.Transactional;

@Service
public class MailService {
	
	@Autowired
	private MailSenderService mailSenderService;
	@Autowired
	private SentEmailDao sentEmailDao;
	
	/**
	 * 
	 * @param toMail
	 * @param name
	 */
	@Transactional
	public void sendMail(String toMail, String name) {
		SentEmail sentEmail = new SentEmail();
		sentEmail.setEmail(toMail);
		sentEmail.setName(name);
		sentEmail.setSentTime(LocalDateTime.now());
		sentEmailDao.save(sentEmail);
		
		String subject = "你好" + name + "KAFKA Producer（生產者）和 Consumer（消費者） 正常運作";
		String text = "你好" + name + " KAFKA Producer（生產者）和 Consumer（消費者） 正常運作";
		mailSenderService.sendSimpleMessage(toMail, subject, text);
	}
}
