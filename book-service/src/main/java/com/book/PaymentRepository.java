package com.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.entity.Book;
import com.book.entity.Payment;

/**
 * 
 * @author cogjava3180
 * This is BookRepository which is used for saving book details and fetching book details from db
 *
 */

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

	Optional<Payment> findByPurchasedBookAndReaderUserId(Book purchasedBook, int readerUserId);

	Optional<List<Payment>> findByReaderUserId(int readerUserId);

	Optional<Payment> findByIdAndReaderUserId(int id, int readerUserId);

}
