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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import com.book.BookRepository;
import com.book.entity.Book;
import com.book.entity.BookCategory;
import com.book.entity.ERole;
import com.book.entity.Role;
import com.book.entity.User;
import com.book.service.impl.BookServiceImpl;

/**
 * 
 * @author cogjava3180
 * This is BookServiceImplTest which is used for testing BookServiceImpl methods
 *
 */

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
	
	@Mock
	BookRepository bookRepository;
	
	@InjectMocks
	BookServiceImpl bookService;
	
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
		LocalDate publishedDate = LocalDate.parse("23/09/2022", formatter);
		book.setPublishedDate(publishedDate);
		book.setContent("This is book1 content");
		book.setActive(true);
		return book;
	}
	
	@Test
	void testSaveBook() {
		Book book = getBook();
		when(bookRepository.save(book)).thenReturn(book);
		book = bookService.saveBook(book);
		assertEquals(1,	book.getId());
	}
	
	@Test
	void testGetBook() {
		Integer bookId = 1;
		Optional<Book> book = Optional.of(getBook());
		when(bookRepository.findById(bookId)).thenReturn(book);
		Book actual = bookService.getBook(bookId);
		assertEquals(bookId, actual.getId());
	}
	
	@Test
	void testGetBook1() {
		Integer bookId = 2;
		Optional<Book> book = Optional.empty();
		when(bookRepository.findById(bookId)).thenReturn(book);
		Book actual = bookService.getBook(bookId);
		assertEquals(null, actual);
	}
	
	@Test
	void testSearchBooks() {
		List<Book> books = new ArrayList<>();
		Book book = getBook();
		String title = "book1";
		String category="Adventure";
		String author="David";
		BigDecimal price=new BigDecimal(1.0);
		String publisher="ABC Publisher";
		books.add(book);
		when(bookRepository.findAll()).thenReturn(books);
		Iterable<Book> actual = bookService.searchBooks(title, category, author, price, publisher);
		assertEquals(books,	actual);
	}
	
	@Test
	void testSearchBooks1() {
		Book book = getBook();
		List<Book> listOfBooks = new ArrayList<>();
		listOfBooks.add(book);
		List<Book> listOfBooks1 = new ArrayList<>();
		String title = "book1";
		String category="Adventure";
		String author="David";
		BigDecimal price=new BigDecimal(1.0);
		String publisher="ABC Publisher";
		when(bookRepository.findAll()).thenReturn(listOfBooks);
		List<Book> actual = bookService.searchBooks(title, category, author, price, publisher);
		List<Book> actual1 = bookService.searchBooks("", category, author, price, publisher);
		List<Book> actual2 = bookService.searchBooks(title,"", author, price, publisher);
		List<Book> actual3 = bookService.searchBooks(title,category, "", price, publisher);
		List<Book> actual4 = bookService.searchBooks(title,category, author, new BigDecimal(0.0), publisher);
		List<Book> actual5 = bookService.searchBooks(title,category, author, price, "");
		List<Book> actual6 = bookService.searchBooks("","", "", new BigDecimal(0.0), "");
		List<Book> actual7 = bookService.searchBooks("NA","", author, price, publisher);
		List<Book> actual8 = bookService.searchBooks("", "NA", author, price, publisher);
		List<Book> actual9 = bookService.searchBooks(title, "", "NA", price, publisher);
		List<Book> actual10 = bookService.searchBooks(title,category, "", new BigDecimal(2.0), publisher);
		List<Book> actual11 = bookService.searchBooks(title,category, author, new BigDecimal(0.0), "NA");
		assertEquals(listOfBooks,	actual);
		assertEquals(listOfBooks,	actual1);
		assertEquals(listOfBooks,	actual2);
		assertEquals(listOfBooks,	actual3);
		assertEquals(listOfBooks,	actual4);
		assertEquals(listOfBooks,	actual5);
		assertEquals(listOfBooks1,	actual6);
		assertEquals(listOfBooks1,	actual7);
		assertEquals(listOfBooks1,	actual8);
		assertEquals(listOfBooks1,	actual9);
		assertEquals(listOfBooks1,	actual10);
		assertEquals(listOfBooks1,	actual11);
		when(bookRepository.findAll()).thenReturn(listOfBooks1);
		actual = bookService.searchBooks(title, category, author, price, publisher);
		assertEquals(listOfBooks1,	actual);
		when(bookRepository.findAll()).thenReturn(listOfBooks);
		actual = bookService.searchBooks("NA", category, author, price, publisher);
		actual1 = bookService.searchBooks(title, "NA", author, price, publisher);
		actual2 = bookService.searchBooks(title, category, "NA", price, publisher);
		actual3 = bookService.searchBooks(title,category, author, new BigDecimal(2.0), publisher);
		actual4 = bookService.searchBooks(title,category, author, price, "NA");
		assertEquals(listOfBooks1,	actual);
		assertEquals(listOfBooks1,	actual1);
		assertEquals(listOfBooks1,	actual2);
		assertEquals(listOfBooks1,	actual3);
		assertEquals(listOfBooks1,	actual4);
		listOfBooks = new ArrayList<>();
		book = getBook();
		book.setActive(false);
		listOfBooks.add(book);
		when(bookRepository.findAll()).thenReturn(listOfBooks);
		actual = bookService.searchBooks(title, category, author, price, publisher);
		actual1 = bookService.searchBooks("", category, author, price, publisher);
		actual2 = bookService.searchBooks(title, "", author, price, publisher);
		actual3 = bookService.searchBooks(title, category, "", price, publisher);
		actual4 = bookService.searchBooks(title, category, author, new BigDecimal(0.0), publisher);
		actual5 = bookService.searchBooks(title, category, author, price, "");
		actual6 = bookService.searchBooks("","", "", new BigDecimal(0.0), "");
		assertEquals(listOfBooks1,	actual);
		assertEquals(listOfBooks1,	actual1);
		assertEquals(listOfBooks1,	actual2);
		assertEquals(listOfBooks1,	actual3);
		assertEquals(listOfBooks1,	actual4);
		assertEquals(listOfBooks1,	actual5);
		assertEquals(listOfBooks1,	actual6);
	}
	
	@Test
	void testGetAllAuthorBooks() {
		Book book = getBook();
		List<Book> listOfBooks = new ArrayList<>();
		listOfBooks.add(book);
		String userName = "David1";
		when(bookRepository.findAll()).thenReturn(listOfBooks);
		List<Book> actual = bookService.getAllAuthorBooks(userName);
		assertEquals(listOfBooks, actual);
	}
	
	@Test
	void testGetAllAuthorBooks1() {
		Book book = getBook();
		List<Book> listOfBooks = new ArrayList<>();
		listOfBooks.add(book);
		List<Book> listOfBooks1 = new ArrayList<>();
		String userName = "James1";
		when(bookRepository.findAll()).thenReturn(listOfBooks);
		List<Book> actual = bookService.getAllAuthorBooks(userName);
		assertEquals(listOfBooks1, actual);
	}
	
	@Test
	void testGetAllAuthorBooks2() {
		List<Book> listOfBooks = new ArrayList<>();
		String userName = "David1";
		when(bookRepository.findAll()).thenReturn(listOfBooks);
		List<Book> actual = bookService.getAllAuthorBooks(userName);
		assertEquals(listOfBooks, actual);
	}
	
	@Test
	void testGetAuthorBook() {
		Optional<Book> book = Optional.of(getBook());
		String userName = "David1";
		Integer bookId = 1;
		when(bookRepository.findById(bookId )).thenReturn(book);
		Book actual = bookService.getAuthorBook(bookId, userName);
		assertEquals(book.get(), actual);
	}
	
	@Test
	void testGetAuthorBook1() {
		Optional<Book> book = Optional.of(getBook());
		Book book1 = null;
		String userName = "James1";
		Integer bookId = 1;
		when(bookRepository.findById(bookId )).thenReturn(book);
		Book actual = bookService.getAuthorBook(bookId, userName);
		assertEquals(book1, actual);
	}
	
	@Test
	void testGetAuthorBook2() {
		Optional<Book> book = Optional.empty();
		Book book1 = null;
		String userName = "David1";
		Integer bookId = 1;
		when(bookRepository.findById(bookId )).thenReturn(book);
		Book actual = bookService.getAuthorBook(bookId, userName);
		assertEquals(book1, actual);
	}
	
	@Test
	void testGetBook2() {
		Optional<Book> book = Optional.of(getBook());
		String title = "book1";
		when(bookRepository.findByTitle(title)).thenReturn(book);
		Book actual = bookService.getBook(title);
		assertEquals(book.get(), actual);
	}
	
	@Test
	void testGetBook3() {
		Optional<Book> book = Optional.empty();
		Book book1 = null;
		String title = "book1";
		when(bookRepository.findByTitle(title)).thenReturn(book);
		Book actual = bookService.getBook(title);
		assertEquals(book1, actual);
	}

}
