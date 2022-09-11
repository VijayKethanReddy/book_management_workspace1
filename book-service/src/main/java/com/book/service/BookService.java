package com.book.service;

import java.math.BigDecimal;
import java.util.List;

import com.book.entity.Book;

/**
 * 
 * @author cogjava3180
 * This is BookService interface which used for defining book details methods
 *
 */

public interface BookService {

	public Book saveBook(Book book);

	public List<Book> searchBooks(String category, String author, BigDecimal price, String publisher);

	public Book getBook(Integer bookId);

}
