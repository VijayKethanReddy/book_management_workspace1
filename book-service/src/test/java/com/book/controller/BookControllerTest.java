package com.book.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import com.book.RoleRepository;
import com.book.UserRepository;
import com.book.constants.BookConstants;
import com.book.entity.Book;
import com.book.entity.BookAuthor;
import com.book.entity.BookCategory;
import com.book.entity.ERole;
import com.book.entity.Payment;
import com.book.entity.Role;
import com.book.entity.User;
import com.book.service.impl.BookServiceImpl;
import com.book.service.impl.PaymentServiceImpl;
import com.book.service.impl.UserServiceImpl;
import org.mockito.quality.Strictness;
import com.book.request.SignupRequest;

/**
 * 
 * @author cogjava3180
 * This is BookControllerTest which is used for testing BookController methods and register method in AuthController
 *
 */

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {
	@Mock
	BookServiceImpl bookService;
	
	@Mock
	UserServiceImpl userService;
	
	@Mock
	PaymentServiceImpl paymentService;
	
	@Mock
	UserRepository userRepository;

	@Mock
	RoleRepository roleRepository;
	
	@Mock
	PasswordEncoder encoder;
	
	@InjectMocks
	BookController controller;
	
	@InjectMocks
	AuthController authController;
	
	@Mock
	RestTemplate restTemplate;
	
	Role getAuthorRole() {
		Role role = new Role();
		role.setId(1);
		role.setName(ERole.ROLE_AUTHOR);
		return role;
	}
	
	Role getReaderRole() {
		Role role = new Role();
		role.setId(1);
		role.setName(ERole.ROLE_READER);
		return role;
	}
	
	BookAuthor getBookAuthor() {
		BookAuthor bookAuthor = new BookAuthor();
		bookAuthor.setBook(getBook());
		bookAuthor.setEmailId(getAuthor().getEmailId());
		return bookAuthor;
	}
	
	Payment getPayment(){
		Payment payment = new Payment();
		User user = getReader();
		payment.setId(1);
		payment.setPurchasedBook(getBook());
		payment.setReaderUserId(user.getId());
		payment.setReaderUserName(user.getUserName());
		payment.setEmailId(user.getEmailId());
		payment.setPurchasedDate(LocalDate.now());
		return payment;
	}
	
	User getAuthor() {
		User author = new User();
		author.setId(1);
		author.setName("David");
		author.setEmailId("david@gmail.com");
		author.setUserName("David1");
		author.setPassword("David@12345");
		Set<Role> roles = new HashSet<>();
		roles.add(getAuthorRole());
		author.setRoles(roles);
		return author;
	}
	
	User getReader() {
		User reader = new User();
		reader.setId(1);
		reader.setName("Reader1");
		reader.setEmailId("reader1@gmail.com");
		reader.setUserName("Reader1");
		reader.setPassword("Reader@12345");
		Set<Role> roles = new HashSet<>();
		roles.add(getReaderRole());
		reader.setRoles(roles);
		return reader;
	}
	
	Book getBook() {
		Book book = new Book();
		book.setId(1);
		book.setLogo("book1.url");
		book.setTitle("book1");
		book.setCategory(BookCategory.ADVENTURE);
		book.setPrice(new BigDecimal(1.0));
		User author = getAuthor();
		book.setAuthorName(author.getName());
		book.setAuthorUserName(author.getUserName());
		book.setPublisher("ABC Publisher");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate publishedDate = LocalDate.parse("01/09/2022", formatter);
		book.setPublishedDate(publishedDate );
		book.setContent("This is book1 content");
		book.setActive(true);
		return book;
	}
	
	SignupRequest getSignupRequest() {
		Set<String> role = new HashSet<>();
		role.add("author");
		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setName("David");
		signupRequest.setUserName("David1");
		signupRequest.setEmailId("david1@gmail.com");
		signupRequest.setPassword("David@12345");
		signupRequest.setRole(role);
		return signupRequest;
	}

	@Test
	void testSaveBook() {
		User author = getAuthor();
		Book book = getBook();
		Book book1 = null;
		int authorId = 1;
		String title = "book1";
		BookAuthor bookAuthor = getBookAuthor();
		ResponseEntity<String> response = new ResponseEntity<String>("email sent", HttpStatus.OK);
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		when(bookService.getBook(title)).thenReturn(book1);
		when(bookService.saveBook(book)).thenReturn(book);
		when(restTemplate.postForEntity(BookConstants.SEND_EMAIL_URL, bookAuthor, String.class)).thenReturn(response);
		ResponseEntity<Integer> savedbookId = controller.saveBook(1, book);
		assertEquals(1, savedbookId.getBody());
	}
	
	@Test
	void testSaveBook1() {
		User author = null;
		Book book = getBook();
		Book book1 = null;
		int authorId = 2;
		String title = "book1";
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		when(bookService.getBook(title)).thenReturn(book1);
		ResponseEntity<Integer> savedbookId = controller.saveBook(2, book);
		assertEquals(HttpStatus.BAD_REQUEST, savedbookId.getStatusCode());
	}
	
	@Test
	void testSaveBook2() {
		Book book = getBook();
		Book book1 = getBook();
		String title = "book1";
		when(bookService.getBook(title)).thenReturn(book1);
		ResponseEntity<Integer> savedbookId = controller.saveBook(1, book);
		assertEquals(HttpStatus.CONFLICT, savedbookId.getStatusCode());
	}

	@Test
	void testSearchBooks() {
		Book book = getBook();
		List<Book> listOfBooks = new ArrayList<>();
		listOfBooks.add(book);
		String title = "Book1";
		String category="Adventure";
		String author="David";
		BigDecimal price= new BigDecimal(1.0);
		String publisher="ABC Publisher";
		when(bookService.searchBooks(title, category, author, price, publisher)).thenReturn(listOfBooks);
		ResponseEntity<List<Book>> actual= controller.searchBooks(title, category, author, price, publisher);
		assertEquals(listOfBooks, actual.getBody());
	}
	
	@Test
	void testGetAllAuthorBooks() {
		User author = getAuthor();
		Book book = getBook();
		List<Book> listOfBooks = new ArrayList<>();
		listOfBooks.add(book);
		Integer authorId=1;
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		when(bookService.getAllAuthorBooks(author.getUserName())).thenReturn(listOfBooks);
		ResponseEntity<List<Book>> actual= controller.getAllAuthorBooks(authorId);
		assertEquals(listOfBooks, actual.getBody());
	}
	
	@Test
	void testGetAllAuthorBooks1() {
		User author = null;
		Integer authorId=2;
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		ResponseEntity<List<Book>> actual= controller.getAllAuthorBooks(authorId);
		assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
	}
	
	@Test
	void testGetAuthorBook() {
		User author = getAuthor();
		Book book = getBook();
		Integer bookId=1;
		Integer authorId=1;
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		when(bookService.getAuthorBook(bookId, author.getUserName())).thenReturn(book);
		ResponseEntity<Book> actual= controller.getAuthorBook(authorId, bookId);
		assertEquals(book, actual.getBody());
	}
	
	@Test
	void testGetAuthorBook1() {
		User author = getAuthor();
		Book book = null;
		Integer bookId=2;
		Integer authorId=1;
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		when(bookService.getAuthorBook(bookId, author.getUserName())).thenReturn(book);
		ResponseEntity<Book> actual= controller.getAuthorBook(authorId, bookId);
		assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
	}
	
	@Test
	void testGetAuthorBook2() {
		User author = null;
		Integer bookId=1;
		Integer authorId=2;
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		ResponseEntity<Book> actual= controller.getAuthorBook(authorId, bookId);
		assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
	}
	
	@Test
	void testBuyBook() {
		User user = getReader();
		Book purchasedBook = null;
		Book book = getBook();
		Integer bookId = 1;
		Integer readerId = 1;
		Payment payment = getPayment();
		Payment payment1 = getPayment();
		payment1.setId(0);
		when(paymentService.getPurchasedBook(bookId, readerId)).thenReturn(purchasedBook);
		when(bookService.getBook(bookId)).thenReturn(book);
		when(userService.getUser(readerId, ERole.ROLE_READER)).thenReturn(user);
		when(paymentService.buyBook(payment1)).thenReturn(payment);
		ResponseEntity<Integer> savedpaymentId = controller.buyBook(bookId, readerId);
		assertEquals(1, savedpaymentId.getBody());
	}
	
	@Test
	void testBuyBook1() {
		User user = null;
		Book purchasedBook = null;
		Book book = getBook();
		Integer bookId = 1;
		Integer readerId = 2;
		when(paymentService.getPurchasedBook(bookId, readerId)).thenReturn(purchasedBook);
		when(bookService.getBook(bookId)).thenReturn(book);
		when(userService.getUser(readerId, ERole.ROLE_READER)).thenReturn(user);
		ResponseEntity<Integer> savedpaymentId = controller.buyBook(bookId, readerId);
		assertEquals(HttpStatus.BAD_REQUEST, savedpaymentId.getStatusCode());
	}
	
	@Test
	void testBuyBook2() {
		Book purchasedBook = null;
		Book book = null;
		Integer bookId = 2;
		Integer readerId = 1;
		when(paymentService.getPurchasedBook(bookId, readerId)).thenReturn(purchasedBook);
		when(bookService.getBook(bookId)).thenReturn(book);
		ResponseEntity<Integer> savedpaymentId = controller.buyBook(bookId, readerId);
		assertEquals(HttpStatus.BAD_REQUEST, savedpaymentId.getStatusCode());
	}
	
	@Test
	void testBuyBook3() {
		Book purchasedBook = getBook();
		Integer bookId = 2;
		Integer readerId = 1;
		when(paymentService.getPurchasedBook(bookId, readerId)).thenReturn(purchasedBook);
		ResponseEntity<Integer> savedpaymentId = controller.buyBook(bookId, readerId);
		assertEquals(HttpStatus.CONFLICT, savedpaymentId.getStatusCode());
	}
	
	@Test
	void testFindAllPurchasedBooks() {
		Book book = getBook();
		List<Book> listOfBooks = new ArrayList<>();
		listOfBooks.add(book);
		int readerId = 1;
		when(paymentService.findAllPurchasedBooks(readerId)).thenReturn(listOfBooks);
		ResponseEntity<List<Book>> actual = controller.findAllPurchasedBooks(readerId);
		assertEquals(listOfBooks, actual.getBody());
	}
	
	@Test
	void testFindPurchasedBookByBookId() {
		Book book = getBook();
		Integer bookId = 1;
		Integer readerId = 1;
		when(paymentService.getPurchasedBook(bookId , readerId )).thenReturn(book);
		ResponseEntity<Book> actual = controller.findPurchasedBookByBookId(readerId, bookId);
		assertEquals(book, actual.getBody());
	}
	
	@Test
	void testFindPurchasedBookByBookId1() {
		Book book = null;
		Integer bookId = 1;
		Integer readerId = 1;
		when(paymentService.getPurchasedBook(bookId , readerId )).thenReturn(book);
		ResponseEntity<Book> actual = controller.findPurchasedBookByBookId(readerId, bookId);
		assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
	}
	
	@Test
	void testFindPurchasedBookByPaymentId() {
		Book book = getBook();
		Integer readerId = 1;
		int pid = 1;
		when(paymentService.findPurchasedBookByPaymentId(pid , readerId)).thenReturn(book);
		ResponseEntity<Book> actual = controller.findPurchasedBookByPaymentId(pid, readerId);
		assertEquals(book, actual.getBody());
	}
	
	@Test
	void testFindPurchasedBookByPaymentId1() {
		Book book = null;
		Integer readerId = 1;
		int pid = 1;
		when(paymentService.findPurchasedBookByPaymentId(pid , readerId)).thenReturn(book);
		ResponseEntity<Book> actual = controller.findPurchasedBookByPaymentId(pid, readerId);
		assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
	}
	
	@Test
	void testGetRefund() {
		Integer bookId = 1;
		Integer readerId = 1;
		Integer refund = 1;
		when(paymentService.getRefund(readerId, bookId)).thenReturn(refund);
		ResponseEntity<Integer> actual = controller.getRefund(readerId, bookId);
		assertEquals(refund, actual.getBody());
	}
	
	@Test
	void testGetRefund1() {
		Integer bookId = 1;
		Integer readerId = 1;
		Integer refund = 0;
		when(paymentService.getRefund(readerId, bookId)).thenReturn(refund);
		ResponseEntity<Integer> actual = controller.getRefund(readerId, bookId);
		assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
	}
	
	@Test
	void testEditBook() {
		User author = getAuthor();
		Book book = getBook();
		int authorId = 1;
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		when(bookService.saveBook(book)).thenReturn(book);
		ResponseEntity<Integer> savedbookId = controller.editBook(1, book);
		assertEquals(1, savedbookId.getBody());
	}
	
	@Test
	void testEditBook1() {
		User author = null;
		Book book = getBook();
		int authorId = 2;
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		ResponseEntity<Integer> savedbookId = controller.editBook(2, book);
		assertEquals(HttpStatus.BAD_REQUEST, savedbookId.getStatusCode());
	}
	
	@Test
	void testRegisterUser(){
		SignupRequest signUpRequest = getSignupRequest();
		String encodedPassword = "$2a$10$oBCsNlxtJ70Q.usYdcFFse6YUBVm.VYk4fcAMGxsXecLuazju9poq";
		Optional<Role> readerRole = Optional.of(getReaderRole());
		Optional<Role> authorRole = Optional.of(getAuthorRole());
		User user = getAuthor();
		when(userRepository.existsByUserName(signUpRequest .getUserName())).thenReturn(false);
		when(userRepository.existsByEmailId(signUpRequest.getEmailId())).thenReturn(false);
		when(encoder.encode(signUpRequest.getPassword())).thenReturn(encodedPassword);
		when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(readerRole);
		when(roleRepository.findByName(ERole.ROLE_AUTHOR)).thenReturn(authorRole);
		when(userRepository.save(user)).thenReturn(user);
		ResponseEntity<?> actual = authController.registerUser(signUpRequest);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}
	
	@Test
	void testRegisterUser1(){
		SignupRequest signUpRequest = getSignupRequest();
		String encodedPassword = "$2a$10$oBCsNlxtJ70Q.usYdcFFse6YUBVm.VYk4fcAMGxsXecLuazju9poq";
		Optional<Role> readerRole = Optional.of(getReaderRole());
		User user = getAuthor();
		Exception e = new RuntimeException(BookConstants.ERROR_ROLE_NOT_FOUND_MSG);
		when(userRepository.existsByUserName(signUpRequest .getUserName())).thenReturn(false);
		when(userRepository.existsByEmailId(signUpRequest.getEmailId())).thenReturn(false);
		when(encoder.encode(signUpRequest.getPassword())).thenReturn(encodedPassword);
		
		when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(readerRole);
		when(roleRepository.findByName(ERole.ROLE_AUTHOR)).thenThrow(e);
		when(userRepository.save(user)).thenReturn(user);
		assertThrows(RuntimeException.class,()->{
			authController.registerUser(signUpRequest);
		});
		Set<String> role = new HashSet<>();
		role.add("reader");
		signUpRequest.setRole(role);
		when(roleRepository.findByName(ERole.ROLE_READER)).thenThrow(e);
		assertThrows(RuntimeException.class,()->{
			authController.registerUser(signUpRequest);
		});
	}
	
	@Test
	void testRegisterUser3(){
		SignupRequest signUpRequest = getSignupRequest();
		String encodedPassword = "$2a$10$oBCsNlxtJ70Q.usYdcFFse6YUBVm.VYk4fcAMGxsXecLuazju9poq";
		Optional<Role> readerRole = Optional.of(getReaderRole());
		Optional<Role> authorRole = Optional.of(getAuthorRole());
		User user = getAuthor();
		Exception e = new RuntimeException(BookConstants.ERROR_ROLE_NOT_FOUND_MSG);
		when(userRepository.existsByUserName(signUpRequest .getUserName())).thenReturn(false);
		when(userRepository.existsByEmailId(signUpRequest.getEmailId())).thenReturn(false);
		when(encoder.encode(signUpRequest.getPassword())).thenReturn(encodedPassword);
		when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(readerRole);
		when(roleRepository.findByName(ERole.ROLE_AUTHOR)).thenReturn(authorRole);
		when(userRepository.save(user)).thenReturn(user);
		
		Set<String> role = new HashSet<>();
		role.add("reader");
		signUpRequest.setRole(role);
		when(roleRepository.findByName(ERole.ROLE_AUTHOR)).thenReturn(authorRole);
		when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(readerRole);
		ResponseEntity<?> actual = authController.registerUser(signUpRequest);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
		signUpRequest.setRole(null);
		actual = authController.registerUser(signUpRequest);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
		when(roleRepository.findByName(ERole.ROLE_READER)).thenThrow(e);
		assertThrows(RuntimeException.class,()->{
			authController.registerUser(signUpRequest);
		});
		when(userRepository.existsByUserName(signUpRequest .getUserName())).thenReturn(true);
		actual = authController.registerUser(signUpRequest);
		assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
		when(userRepository.existsByUserName(signUpRequest .getUserName())).thenReturn(false);
		when(userRepository.existsByEmailId(signUpRequest.getEmailId())).thenReturn(true);
		actual = authController.registerUser(signUpRequest);
		assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
	}
	
}
