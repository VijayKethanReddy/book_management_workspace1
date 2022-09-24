package com.book.entity;

import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * 
 * @author cogjava3180
 * BookAuthor bean is used for sending book details and email id for email service
 *
 */

@Data
@Component
public class BookAuthor {
	private Book book;
	private String emailId;
}
