package com.book.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.book.constants.BookConstants;
import com.book.entity.Book;
import com.book.entity.BookAuthor;
import com.book.entity.ERole;
import com.book.entity.User;
import com.book.service.impl.BookServiceImpl;
import com.book.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author cogjava3180
 * This is BookController which run methods for book api
 * getBook method is used for fetching book details for book id
 * searchBooks method is used for fetching all books which match conditions for category, author, price and publisher
 * saveBook method is used for saving book with author id
 * saveAuthor method is used for saving author details
 * 
 * 
 *
 */

@Slf4j
@RestController
@RequestMapping("/digitalbooks")
public class BookController extends BaseController {

	@Autowired
	BookServiceImpl bookService;
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	BookAuthor bookAuthor;

	@GetMapping("/readers/{emailId}/books/{bookId}")
	@PreAuthorize("hasRole('READER') or hasRole('AUTHOR')")
	public ResponseEntity<Book> getBook(@PathVariable String emailId, @PathVariable Integer bookId) {
		log.debug("Inside getBook method");
		ResponseEntity<Book> response;
		Book book = bookService.getBook(bookId);
		if(book == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			response = new ResponseEntity<>(book, HttpStatus.OK);
		}
		return response;
	}

	@GetMapping("/books/search")
	@PreAuthorize("hasRole('READER') or hasRole('AUTHOR')")
	public ResponseEntity<List<Book>> searchBooks(@RequestParam String category, 
			@RequestParam String author, @RequestParam BigDecimal price, 
			@RequestParam String publisher) {
		ResponseEntity<List<Book>> response;
		List<Book> listOfBooks = bookService.searchBooks(category, author, price, publisher);
		response = new ResponseEntity<>(listOfBooks, HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/author/{authorId}/books")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<Integer> saveBook(@PathVariable("authorId") int authorId, @Valid @RequestBody Book book) {
		ResponseEntity<Integer> response;
		User user = userService.getAuthor(authorId, ERole.ROLE_AUTHOR);
		if(user!=null) {
			book.setAuthorName(user.getName());
			book.setAuthorUserName(user.getUserName());
			bookService.saveBook(book);
			int bookId = book.getId();
			response = new ResponseEntity<>(bookId, HttpStatus.CREATED);
			bookAuthor.setBook(book);
			bookAuthor.setEmailId(user.getEmailId());
			ResponseEntity<String> responseFromEmailService = restTemplate.postForEntity(BookConstants.SEND_EMAIL_URL, bookAuthor, String.class);
			log.debug(responseFromEmailService.getBody());
		}
		else {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
//	@PostMapping("/author/signup")
//	public ResponseEntity<Integer> saveAuthor(@Valid @RequestBody User user) {
//		ResponseEntity<Integer> response;
//		User user1 = userService.saveAuthor(user);
//		if(user1!=null) {
//			int authorId = user1.getId();
//			response = new ResponseEntity<>(authorId, HttpStatus.CREATED);
//		}
//		else {
//			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		return response;
//	}
}
