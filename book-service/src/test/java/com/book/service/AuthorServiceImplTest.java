//package com.book.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//import java.util.Optional;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import com.book.AuthorRepository;
//import com.book.entity.Author;
//import com.book.service.impl.AuthorServiceImpl;
//
///**
// * 
// * @author cogjava3180
// * This is AuthorServiceImplTest which is used for testing AuthorServiceImpl methods
// *
// */
//
//@ExtendWith(MockitoExtension.class)
//class AuthorServiceImplTest {
//	
//	@Mock
//	AuthorRepository authorRepository;
//	
//	@InjectMocks
//	AuthorServiceImpl authorService;
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
//	@Test
//	void testGetAuthor() {
//		Optional<Author> author = Optional.of(getAuthor());
//		Integer authorId = 1;
//		when(authorRepository.findById(authorId)).thenReturn(author);
//		Author actual = authorService.getAuthor(authorId);
//		assertEquals(author.get(),actual);
//	}
//	
//	@Test
//	void testSaveAuthor() {
//		Author author = getAuthor();
//		when(authorRepository.save(author)).thenReturn(author);
//		authorService.saveAuthor(author);
//		assertEquals(1,	author.getId());
//	}
//}
