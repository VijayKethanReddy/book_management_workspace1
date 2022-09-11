package com.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kafka.model.BookAuthor;
import com.kafka.service.KafKaProducerService;


/**
 * 
 * @author cogjava3180
 * This is KafkaProducerController which run methods for email api
 * sendEmail method is used for sending email when book is created
 *
 */

@RestController
@RequestMapping(value = "/kafka")
public class KafkaProducerController {
	private final KafKaProducerService producerService;

	@Autowired
	public KafkaProducerController(KafKaProducerService producerService) {
		this.producerService = producerService;
	}

	@PostMapping(value = "/publish")
	public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
		this.producerService.sendMessage(message);
	}
	
	@PostMapping(value = "/sendEmail")
	public String sendMessageToKafkaTopic(
			@RequestBody BookAuthor bookAuthor) {
		this.producerService.sendEmailForBookCreation(bookAuthor);
		return "email sent";
	}
}