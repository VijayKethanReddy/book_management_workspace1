package com.book;

import org.springframework.data.jpa.repository.JpaRepository;
import com.book.entity.Book;

/**
 * 
 * @author cogjava3180
 * This is BookRepository which is used for saving book details and fetching book details from db
 *
 */

public interface BookRepository extends JpaRepository<Book, Integer>{

}
