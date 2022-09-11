package com.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.kafka.constants.ApplicationConstants;
import com.kafka.model.BookAuthor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author cogjava3180
 * This is KafKaProducerService which sends book details to topic name send email
 *
 */

@Slf4j
@Service
public class KafKaProducerService 
{
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void sendMessage(String message) 
	{
		log.info(String.format("Message sent -> %s", message));
		this.kafkaTemplate.send(ApplicationConstants.TOPIC_NAME_TEST, message);
	}
	
	public void sendEmailForBookCreation(BookAuthor bookAuthor) 
	{
		log.info("Book: "+ bookAuthor.toString());
		this.kafkaTemplate.send(ApplicationConstants.TOPIC_NAME_SEND_EMAIL, bookAuthor);
	}
}
