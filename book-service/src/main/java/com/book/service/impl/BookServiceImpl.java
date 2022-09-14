package com.book.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.book.BookRepository;
import com.book.entity.Book;
import com.book.service.BookService;

/**
 * 
 * @author cogjava3180
 * This is BookServiceImpl which is used for running methods from controller
 * saveBook method is used for saving book details
 * searchBooks is used for fetching books which match conditions for category, author, price and publisher
 * getBook method is used for fetching book details for book id
 *
 */

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	BookRepository bookRepository;
	
	@Override
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}
	
	@Override
	public List<Book> searchBooks(String title, String category, String author, BigDecimal price, String publisher) {
		List<Book> listOfBooks = new ArrayList<>();
		List<Book> bookList = bookRepository.findAll();
		if(!bookList.isEmpty()) {
			listOfBooks = bookList.stream().
			filter(book -> (book.getActive() == Boolean.TRUE) && (book.getTitle().equals(title) || book.getCategory().toString().equals(category) || 
					book.getAuthorName().equalsIgnoreCase(author) || (book.getPrice() == price) 
					|| book.getPublisher().equalsIgnoreCase(publisher))).collect(Collectors.toList());
		}
		return listOfBooks;
	}
	
	@Override
	public Book getBook(Integer bookId) {
		Book book = null;
		Optional<Book> bookOptional = bookRepository.findById(bookId);
		if(bookOptional.isPresent()) {
			book = bookOptional.get();
		}
		return book;
	}

	public List<Book> getAllAuthorBooks(String userName) {
		List<Book> listOfBooks = new ArrayList<>();
		List<Book> bookList = bookRepository.findAll();
		if(!bookList.isEmpty()) {
			listOfBooks = bookList.stream().
			filter(book -> book.getAuthorUserName().equalsIgnoreCase(userName)).collect(Collectors.toList());
		}
		return listOfBooks;
	}

	public Book getAuthorBook(Integer bookId, String userName) {
		Book book = null;
		Optional<Book> bookOptional = bookRepository.findById(bookId);
		if(bookOptional.isPresent() && bookOptional.get().getAuthorUserName().equalsIgnoreCase(userName)) {
			book = bookOptional.get();
		}
		return book;
	}

}
