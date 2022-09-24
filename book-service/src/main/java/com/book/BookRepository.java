package com.book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.book.entity.Book;
import com.book.entity.BookCategory;

/**
 * 
 * @author cogjava3180
 * This is BookRepository which is used for saving book details and fetching book details from db
 *
 */

public interface BookRepository extends JpaRepository<Book, Integer>{

	Optional<Book> findByTitle(String title);
	
	Optional<List<Book>> findByCategory(BookCategory category);
	
	Optional<List<Book>> findByAuthorUserName(String authorUserName);
	
	Optional<List<Book>> findByPrice(BigDecimal price);
	
	Optional<List<Book>> findByPublisher(String publisher);
	
}
