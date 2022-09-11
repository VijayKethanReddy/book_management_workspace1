//package com.book.entity;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import java.time.LocalDate;
//import java.util.Set;
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//
//@ExtendWith(MockitoExtension.class)
//class BookTest {
//	
//	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//	
//	Author getAuthor() {
//		Author author = new Author();
//		author.setId(1);
//		author.setName("");
//		author.setEmailId("");
//		author.setUserName("");
//		return author;
//	}
//	
//	Book getBook() {
//		Book book = new Book();
//		book.setId(1);
//		book.setLogo("");
//		book.setTitle("");
//		book.setCategory(null);
//		book.setPrice(null);
//		book.setAuthor(getAuthor());
//		book.setPublisher("");
//		LocalDate publishedDate = null;
//		book.setPublishedDate(publishedDate );
//		book.setContent("");
//		book.setActive(null);
//		return book;
//	}
//	
//	@Test
//	void testBook() {
//		Book book = getBook();
//		Set<ConstraintViolation<Book>> violations = validator.validate(book);
//		assertFalse(violations.isEmpty());
//	}
//	
//	@Test
//	void testAuthor() {
//		Author author = getAuthor();
//		Set<ConstraintViolation<Author>> violations = validator.validate(author);
//		assertFalse(violations.isEmpty());
//	}
//}
//
