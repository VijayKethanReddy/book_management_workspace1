package com.book.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
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
 * searchBooks is used for fetching books which match conditions for title, category, author, price and publisher
 * getBook method is used for fetching book details for book id
 * getAllAuthorBooks method is used for fetching all author books with user name
 * getAuthorBook method is used for fetching book with user name and book id
 * getBook method is used for fetching book with title
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
		Boolean flag =false;
		
		List<Book> bookList = bookRepository.findAll();
		if(!bookList.isEmpty()) {
			if(!StringUtils.isBlank(title) && !StringUtils.isBlank(category) && !StringUtils.isBlank(author) && (price.compareTo(BigDecimal.ZERO)>0) && !StringUtils.isBlank(publisher)) {
				listOfBooks = bookList.stream().
				filter(book -> ((book.getActive() == Boolean.TRUE) && (book.getTitle().equalsIgnoreCase(title) && book.getCategory().toString().equalsIgnoreCase(category) && 
						book.getAuthorName().equalsIgnoreCase(author) && (book.getPrice().compareTo(price) == 0) 
						&& book.getPublisher().equalsIgnoreCase(publisher)))).collect(Collectors.toList());
			}
			else {
				listOfBooks = new ArrayList<>(bookList);
				if(!StringUtils.isBlank(title)) {
					flag =true;
					listOfBooks = bookList.stream().
							filter(book -> (book.getActive() == Boolean.TRUE) && (book.getTitle().equalsIgnoreCase(title))).collect(Collectors.toList());
				}
				if(!StringUtils.isBlank(author)) {
					flag =true;
					listOfBooks = listOfBooks.stream().
							filter(book -> (book.getActive() == Boolean.TRUE) && (book.getAuthorName().equalsIgnoreCase(author))).collect(Collectors.toList());
				}
				if(!StringUtils.isBlank(category)) {
					flag =true;
					listOfBooks = listOfBooks.stream().
							filter(book -> (book.getActive() == Boolean.TRUE) && (book.getCategory().toString().equalsIgnoreCase(category))).collect(Collectors.toList());
				}
				if(price.compareTo(BigDecimal.ZERO)>0) {
					flag =true;
					listOfBooks = listOfBooks.stream().
							filter(book -> (book.getActive() == Boolean.TRUE) && (book.getPrice().compareTo(price) == 0)).collect(Collectors.toList());
				}
				if(!StringUtils.isBlank(publisher)) {
					flag =true;
					listOfBooks = listOfBooks.stream().
							filter(book -> (book.getActive() == Boolean.TRUE) && (book.getPublisher().equalsIgnoreCase(publisher))).collect(Collectors.toList());
				}
				if(flag.equals(Boolean.FALSE)) {
					listOfBooks = new ArrayList<>();
				}
			}
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

	public Book getBook(String title) {
		Book book = null;
		Optional<Book> bookOptional = bookRepository.findByTitle(title);
		if(bookOptional.isPresent()) {
			book = bookOptional.get();
		}
		return book;
	}

}
