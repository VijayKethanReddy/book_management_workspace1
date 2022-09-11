package com.book.entity;

import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class BookAuthor {
	private Book book;
	private String emailId;
}
