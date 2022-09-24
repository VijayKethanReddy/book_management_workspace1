package com.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.book.BookRepository;
import com.book.PaymentRepository;
import com.book.RoleRepository;
import com.book.entity.Book;
import com.book.entity.BookCategory;
import com.book.entity.ERole;
import com.book.entity.Payment;
import com.book.entity.Role;
import com.book.entity.User;
import com.book.service.impl.PaymentServiceImpl;

/**
 * 
 * @author cogjava3180
 * This is PaymentServiceImplTest which is used for testing PaymentServiceImpl methods
 *
 */

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
	
	@Mock
	PaymentRepository paymentRepository;
	
	@Mock
	RoleRepository roleRepository;
	
	@Mock
	BookRepository bookRepository;
	
	@InjectMocks
	PaymentServiceImpl paymentService;
	
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
	
	Payment getPayment() {
		Payment payment = new Payment();
		User reader = getReader();
		payment.setId(1);
		payment.setPurchasedBook(getBook());
		payment.setPurchasedDate(LocalDate.now());
		payment.setReaderUserId(reader.getId());
		payment.setReaderUserName(reader.getUserName());
		payment.setEmailId(reader.getEmailId());
		return payment;
	}
	
	Book getBook() {
		Book book = new Book();
		User author = getAuthor();
		book.setId(1);
		book.setLogo("book1.url");
		book.setTitle("book1");
		book.setCategory(BookCategory.ADVENTURE);
		book.setPrice(new BigDecimal(1.0));
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

	@Test
	void testBuyBook() {
		Payment payment = getPayment();
		when(paymentRepository.save(payment)).thenReturn(payment);
		Payment actual = paymentService.buyBook(payment);
		assertEquals(1,	actual.getId());
	}
	
	@Test
	void testGetPurchasedBooks() {
		Optional<Book> book = Optional.of(getBook());
		Integer bookId = 1;
		int readerId = 1;
		Optional<Payment> payment = Optional.of(getPayment());
		when(bookRepository.findById(bookId)).thenReturn(book);
		when(paymentRepository.findByPurchasedBookAndReaderUserId(book.get(),readerId)).thenReturn(payment);
		Book actual = paymentService.getPurchasedBook(bookId, readerId);
		assertEquals(payment.get().getPurchasedBook(),actual);
	}
	
	@Test
	void testGetPurchasedBooks1() {
		Optional<Book> book = Optional.of(getBook());
		Integer bookId = 1;
		int readerId = 1;
		Optional<Payment> payment = Optional.empty();
		Book purchasedBook = null;
		when(bookRepository.findById(bookId)).thenReturn(book);
		when(paymentRepository.findByPurchasedBookAndReaderUserId(book.get(),readerId)).thenReturn(payment);
		Book actual = paymentService.getPurchasedBook(bookId, readerId);
		assertEquals(purchasedBook,actual);
	}
	
	@Test
	void testGetPurchasedBooks2() {
		Optional<Book> book = Optional.empty();
		Integer bookId = 1;
		int readerId = 1;
		Book purchasedBook = null;
		when(bookRepository.findById(bookId)).thenReturn(book);
		Book actual = paymentService.getPurchasedBook(bookId, readerId);
		assertEquals(purchasedBook,actual);
	}
	
	@Test
	void testFindAllPurchasedBooks() {
		int readerId = 1;
		List<Payment> listOfPayment = new ArrayList<>();
		Payment payment = getPayment();
		listOfPayment.add(payment);
		Book book = payment.getPurchasedBook();
		List<Book> listOfPurchasedBooks = new ArrayList<>();
		listOfPurchasedBooks.add(book);
		Optional<List<Payment>> paymentList = Optional.of(listOfPayment);
		when(paymentRepository.findByReaderUserId(readerId)).thenReturn(paymentList);
		List<Book> actual = paymentService.findAllPurchasedBooks(readerId);
		assertEquals(listOfPurchasedBooks,actual);
	}
	
	@Test
	void testFindAllPurchasedBooks1() {
		int readerId = 1;
		List<Payment> listOfPayment = new ArrayList<>();
		List<Book> listOfPurchasedBooks = new ArrayList<>();
		Optional<List<Payment>> paymentList = Optional.of(listOfPayment);
		when(paymentRepository.findByReaderUserId(readerId)).thenReturn(paymentList);
		List<Book> actual = paymentService.findAllPurchasedBooks(readerId);
		assertEquals(listOfPurchasedBooks,actual);
	}
	
	@Test
	void testFindPurchasedBookById() {
		Optional<Payment> payment = Optional.of(getPayment());
		int pid = 1;
		int readerId = 1;
		Book purchasedBook = payment.get().getPurchasedBook();
		when(paymentRepository.findByIdAndReaderUserId(pid , readerId)).thenReturn(payment);
		Book actual = paymentService.findPurchasedBookByPaymentId(pid, readerId);
		assertEquals(purchasedBook,actual);
	}
	
	@Test
	void testFindPurchasedBookById1() {
		Optional<Payment> payment = Optional.empty();
		int pid = 1;
		int readerId = 1;
		Book purchasedBook = null;
		when(paymentRepository.findByIdAndReaderUserId(pid , readerId)).thenReturn(payment);
		Book actual = paymentService.findPurchasedBookByPaymentId(pid, readerId);
		assertEquals(purchasedBook,actual);
	}
	
	@Test
	void testGetRefund() {
		Optional<Book> book = Optional.of(getBook());
		Optional<Payment> payment = Optional.of(getPayment());
		int pid = 1;
		int readerId = 1;
		Integer bookId = 1;
		when(bookRepository.findById(bookId)).thenReturn(book);
		when(paymentRepository.findByPurchasedBookAndReaderUserId(book.get(),readerId)).thenReturn(payment);
		Integer actual = paymentService.getRefund(readerId, bookId);
		assertEquals(pid,actual);
	}
	
	@Test
	void testGetRefund1() {
		Optional<Book> book = Optional.of(getBook());
		Optional<Payment> payment = Optional.of(getPayment());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate purchasedDate = LocalDate.parse("21/09/2022", formatter);
		payment.get().setPurchasedDate(purchasedDate);
		int pid = 0;
		int readerId = 1;
		Integer bookId = 1;
		when(bookRepository.findById(bookId)).thenReturn(book);
		when(paymentRepository.findByPurchasedBookAndReaderUserId(book.get(),readerId)).thenReturn(payment);
		Integer actual = paymentService.getRefund(readerId, bookId);
		assertEquals(pid,actual);
	}
	
	@Test
	void testGetRefund2() {
		Optional<Book> book = Optional.of(getBook());
		Optional<Payment> payment = Optional.empty();
		int pid = 0;
		int readerId = 1;
		Integer bookId = 1;
		when(bookRepository.findById(bookId)).thenReturn(book);
		when(paymentRepository.findByPurchasedBookAndReaderUserId(book.get(),readerId)).thenReturn(payment);
		Integer actual = paymentService.getRefund(readerId, bookId);
		assertEquals(pid,actual);
	}
	
	@Test
	void testGetRefund3() {
		Optional<Book> book = Optional.empty();
		int pid = 0;
		int readerId = 1;
		Integer bookId = 1;
		when(bookRepository.findById(bookId)).thenReturn(book);
		Integer actual = paymentService.getRefund(readerId, bookId);
		assertEquals(pid,actual);
	}
	
}
