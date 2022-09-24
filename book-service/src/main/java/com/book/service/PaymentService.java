package com.book.service;

import java.util.List;

import com.book.entity.Book;
import com.book.entity.Payment;

/**
 * 
 * @author cogjava3180
 * This is PaymentService interface which used for defining payment details methods
 *
 */

public interface PaymentService {

	Payment buyBook(Payment payment);

	Book getPurchasedBook(Integer bookId, Integer readerId);

	List<Book> findAllPurchasedBooks(int readerId);

	Book findPurchasedBookByPaymentId(int pid, int readerId);

	Integer getRefund(int readerId, int bookId);

}
