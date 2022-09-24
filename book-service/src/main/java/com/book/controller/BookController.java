package com.book.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.book.entity.Payment;
import com.book.entity.User;
import com.book.service.PaymentService;
import com.book.service.impl.BookServiceImpl;
import com.book.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author cogjava3180
 * This is BookController which run methods for book api
 * searchBooks method is used for fetching all books which match conditions for title, category, author, price and publisher
 * saveBook method is used for saving book with author id
 * getAllAuthorBooks method is used for fetching all author books with author id
 * getAuthorBook method is used for fetching book with author id and book id
 * buyBook method is used for purchasing book with book id and reader id
 * findAllPurchasedBooks method is used for fetching all purchased books with reader id
 * findPurchasedBookByPaymentId method is used for fetching purchased book with payment id and reader id
 * getRefund method is used for refund with reader id and book id
 * editBook method is used for editing book details with author id
 * 
 * 
 *
 */

@CrossOrigin
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
	PaymentService paymentService;
	
	@Value("${book.app.emailServiceEnabled}")
	private Boolean emailServiceEnabled;

	@GetMapping("/books/search")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<List<Book>> searchBooks(@RequestParam String title, @RequestParam String category, 
			@RequestParam String author, @RequestParam BigDecimal price, 
			@RequestParam String publisher) {
		ResponseEntity<List<Book>> response;
		List<Book> listOfBooks = bookService.searchBooks(title, category, author, price, publisher);
		response = new ResponseEntity<>(listOfBooks, HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/author/{authorId}/books")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<Integer> saveBook(@PathVariable("authorId") int authorId, @Valid @RequestBody Book book) {
		ResponseEntity<Integer> response;
		BookAuthor bookAuthor = new BookAuthor();
		Book book1 = bookService.getBook(book.getTitle());
		if(book1 == null) {
			int bookId = 0;
			User user = userService.getUser(authorId, ERole.ROLE_AUTHOR);
			book.setAuthorName(user.getName());
			book.setAuthorUserName(user.getUserName());
			book = bookService.saveBook(book);
			bookId = book.getId();
			response = new ResponseEntity<>(bookId, HttpStatus.CREATED);
			if(Boolean.TRUE.equals(emailServiceEnabled)) {
				bookAuthor.setBook(book);
				bookAuthor.setEmailId(user.getEmailId());
				ResponseEntity<String> responseFromEmailService = restTemplate.postForEntity(BookConstants.SEND_EMAIL_URL, bookAuthor, String.class);
				log.debug(responseFromEmailService.getBody());
			}
		}else {
			response = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return response;
	}
	
	@GetMapping("/author/{authorId}/allbooks")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<List<Book>> getAllAuthorBooks(@PathVariable("authorId") int authorId) {
		ResponseEntity<List<Book>> response;
		User user = userService.getUser(authorId, ERole.ROLE_AUTHOR);
		List<Book> listOfBooks = bookService.getAllAuthorBooks(user.getUserName());
		response = new ResponseEntity<>(listOfBooks, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/author/{authorId}/book/{bookId}")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<Book> getAuthorBook(@PathVariable("authorId") int authorId, @PathVariable("bookId") Integer bookId) {
		ResponseEntity<Book> response;
		User user = userService.getUser(authorId, ERole.ROLE_AUTHOR);
		Book authorBook = bookService.getAuthorBook(bookId, user.getUserName());
		if(authorBook == null) {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else {
			response = new ResponseEntity<>(authorBook, HttpStatus.OK);
		}
		return response;
	}
	
	@PostMapping("/books/{bookId}/buy/{readerId}")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<Integer> buyBook(@PathVariable("bookId") int bookId, @PathVariable("readerId") int readerId) {
		ResponseEntity<Integer> response;
		Book purchasedBook = paymentService.getPurchasedBook(bookId, readerId);
		if(purchasedBook == null) {
			Book book = bookService.getBook(bookId);
			User user = userService.getUser(readerId, ERole.ROLE_READER);
			Payment payment = new Payment();
			payment.setPurchasedBook(book);
			payment.setReaderUserId(user.getId());
			payment.setReaderUserName(user.getUserName());
			payment.setEmailId(user.getEmailId());
			payment.setPurchasedDate(LocalDate.now());
			payment = paymentService.buyBook(payment);
			int paymentId = payment.getId();
			response = new ResponseEntity<>(paymentId, HttpStatus.CREATED);
		}
		else {
			response = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return response;
	}
	
	@GetMapping("/readers/{readerId}/books")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<List<Book>> findAllPurchasedBooks(@PathVariable("readerId") int readerId) {
		ResponseEntity<List<Book>> response;
		List<Book> listOfPurchasedBooks = paymentService.findAllPurchasedBooks(readerId);
		response = new ResponseEntity<>(listOfPurchasedBooks, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/readers/{readerId}/books/{bookId}")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<Book> findPurchasedBookByBookId(@PathVariable("readerId") int readerId, @PathVariable("bookId") Integer bookId) {
		ResponseEntity<Book> response;
		Book purchasedBook = paymentService.getPurchasedBook(bookId, readerId);
		if(purchasedBook == null) {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else {
			response = new ResponseEntity<>(purchasedBook, HttpStatus.OK);
		}
		return response;
	}
	
	@GetMapping("/readers/{readerId}/books/pid")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<Book> findPurchasedBookByPaymentId(@RequestParam int pid, @PathVariable("readerId") int readerId) {
		ResponseEntity<Book> response;
		Book purchasedBook = paymentService.findPurchasedBookByPaymentId(pid, readerId);
		if(purchasedBook!=null) {
			response = new ResponseEntity<>(purchasedBook, HttpStatus.OK);
		}
		else {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return response;
	}
	
	@DeleteMapping("/readers/{readerId}/books/{bookId}/refund")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<Integer> getRefund(@PathVariable("readerId") int readerId, @PathVariable("bookId") int bookId) {
		ResponseEntity<Integer> response;
		Integer refund = paymentService.getRefund(readerId, bookId);
		if(refund!=0) {
			response = new ResponseEntity<>(refund, HttpStatus.OK);
		}
		else {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return response;
	}
	
	@PostMapping("/author/{authorId}/books/editbook")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<Integer> editBook(@PathVariable("authorId") int authorId, @Valid @RequestBody Book book) {
		ResponseEntity<Integer> response;
		BookAuthor bookAuthor = new BookAuthor();
		int bookId = 0;
		User user = userService.getUser(authorId, ERole.ROLE_AUTHOR);
		book.setAuthorName(user.getName());
		book.setAuthorUserName(user.getUserName());
		book = bookService.saveBook(book);
		if(book != null) {
			bookId = book.getId();
		}
		response = new ResponseEntity<>(bookId, HttpStatus.CREATED);
		bookAuthor.setBook(book);
		bookAuthor.setEmailId(user.getEmailId());
		return response;
	}
	
}
