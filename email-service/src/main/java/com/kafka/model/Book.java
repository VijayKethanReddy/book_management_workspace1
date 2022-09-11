package com.kafka.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 
 * @author cogjava3180
 * Book model is used for receiving the book details from Book Controller for sending email
 *
 */

@Data
public class Book {
	
	private int id;
	
	private String logo;
	
	private String title;
	
	private BookCategory category;
	
	private BigDecimal price;
	
	private String authorUserName;
	
	private String authorName;
	
	private String publisher;
	
	@DateTimeFormat(style = "dd/MM/yyyy")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate publishedDate;
	
	private String content;
	
	private Boolean active;
}
