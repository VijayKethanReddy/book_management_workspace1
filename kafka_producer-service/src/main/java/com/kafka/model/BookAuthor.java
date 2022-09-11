package com.kafka.model;

import lombok.Data;

/**
 * 
 * @author cogjava3180
 * BookAuthor model is used for receiving the book and emailId details from Book Controller for sending email
 *
 */

@Data
public class BookAuthor {
	private Book book;
	private String emailId;
}
