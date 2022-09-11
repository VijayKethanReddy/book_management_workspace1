//package com.book.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//import com.book.BookRepository;
//import com.book.entity.Author;
//import com.book.entity.Book;
//import com.book.entity.BookCategory;
//import com.book.service.impl.BookServiceImpl;
//
///**
// * 
// * @author cogjava3180
// * This is BookServiceImplTest which is used for testing BookServiceImpl methods
// *
// */
//
//@MockitoSettings(strictness = Strictness.LENIENT)
//@ExtendWith(MockitoExtension.class)
//class BookServiceImplTest {
//	
//	@Mock
//	BookRepository bookRepository;
//	
//	@InjectMocks
//	BookServiceImpl bookService;
//	
//	Author getAuthor() {
//		Author author = new Author();
//		author.setId(1);
//		author.setName("David");
//		author.setEmailId("david@gmail.com");
//		author.setUserName("David1");
//		return author;
//	}
//	
//	Book getBook() {
//		Book book = new Book();
//		book.setId(1);
//		book.setLogo("book1.url");
//		book.setTitle("book1");
//		book.setCategory(BookCategory.ADVENTURE);
//		book.setPrice(new BigDecimal(1.0));
//		book.setAuthor(getAuthor());
//		book.setPublisher("ABC Publisher");
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		LocalDate publishedDate = LocalDate.parse("01/09/2022", formatter);
//		book.setPublishedDate(publishedDate );
//		book.setContent("This is book1 content");
//		book.setActive(true);
//		return book;
//	}
//	
//	@Test
//	void testSaveBook() {
//		Book book = getBook();
//		when(bookRepository.save(book)).thenReturn(book);
//		bookService.saveBook(book);
//		assertEquals(1,	book.getId());
//	}
//	
//	@Test
//	void testGetBook() {
//		Integer bookId = 1;
//		Optional<Book> book = Optional.of(getBook());
//		when(bookRepository.findById(bookId)).thenReturn(book);
//		Book actual = bookService.getBook(bookId);
//		assertEquals(bookId, actual.getId());
//	}
//	
//	@Test
//	void testGetBook1() {
//		Integer bookId = 2;
//		Optional<Book> book = Optional.empty();
//		when(bookRepository.findById(bookId)).thenReturn(book);
//		Book actual = bookService.getBook(bookId);
//		assertEquals(null, actual);
//	}
//	
//	@Test
//	void testSearchBooks() {
//		List<Book> books = new ArrayList<>();
//		Book book = getBook();
//		String category="Adventure";
//		String author="David";
//		BigDecimal price=new BigDecimal(1.0);
//		String publisher="ABC Publisher";
//		books.add(book);
//		when(bookRepository.findAll()).thenReturn(books);
//		Iterable<Book> actual = bookService.searchBooks(category, author, price, publisher);
//		assertEquals(books,	actual);
//	}
//	
//	@Test
//	void testSearchBooks1() {
//		Book book = getBook();
//		List<Book> listOfBooks = new ArrayList<>();
//		listOfBooks.add(book);
//		List<Book> listOfBooks1 = new ArrayList<>();
//		String category="Adventure";
//		String author="David";
//		BigDecimal price=new BigDecimal(1.0);
//		String publisher="ABC Publisher";
//		when(bookRepository.findAll()).thenReturn(listOfBooks);
//		List<Book> actual = bookService.searchBooks(category, author, price, publisher);
//		List<Book> actual1 = bookService.searchBooks("NA", author, price, publisher);
//		List<Book> actual2 = bookService.searchBooks("NA", "NA", price, publisher);
//		List<Book> actual3 = bookService.searchBooks("NA", "NA", new BigDecimal(2.0), publisher);
//		List<Book> actual4 = bookService.searchBooks("NA", "NA", new BigDecimal(2.0), "DEF");
//		assertEquals(listOfBooks,	actual);
//		assertEquals(listOfBooks,	actual1);
//		assertEquals(listOfBooks,	actual2);
//		assertEquals(listOfBooks,	actual3);
//		assertEquals(listOfBooks1,	actual4);
//	}
//
//}
