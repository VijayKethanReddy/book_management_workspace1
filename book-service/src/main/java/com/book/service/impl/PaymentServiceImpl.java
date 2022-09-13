package com.book.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.book.BookRepository;
import com.book.PaymentRepository;
import com.book.entity.Book;
import com.book.entity.Payment;
import com.book.service.PaymentService;

/**
 * 
 * @author cogjava3180
 * This is PaymentServiceImpl which is used for running methods from controller
 * getAuthor method is used for fetching user details for user id
 * saveAuthor method is used for saving user details
 *
 */

@Transactional
@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Override
	public Payment buyBook(Payment payment) {
		return paymentRepository.save(payment);
	}
	
	@Override
	public Book getPurchasedBook(Integer bookId, Integer readerId) {
		Book book = null;
		Book purchasedBook = null;
		Optional<Book> bookOptional = bookRepository.findById(bookId);
		if(bookOptional.isPresent()) {
			book = bookOptional.get();
			Optional<Payment> paymentOptional = paymentRepository.findByPurchasedBookAndReaderUserId(book,readerId);
			if(paymentOptional.isPresent()) {
				purchasedBook = paymentOptional.get().getPurchasedBook();
			}
		}
		return purchasedBook;
	}

	@Override
	public List<Book> findAllPurchasedBooks(int readerId) {
		List<Book> listOfPurchasedBooks = new ArrayList<>();
		Optional<List<Payment>> paymentListOptional = paymentRepository.findByReaderUserId(readerId);
		if(paymentListOptional.isPresent()) {
			//listOfPurchasedBooks = purchasedBookListOptional.get();
			listOfPurchasedBooks = paymentListOptional.get().stream().map(p -> p.getPurchasedBook()).collect(Collectors.toList());
		}
		return listOfPurchasedBooks;
	}

	@Override
	public Book findPurchasedBookByPaymentId(int pid, int readerId) {
		Book purchasedBook = null;
		Optional<Payment> paymentOptional = paymentRepository.findByIdAndReaderUserId(pid, readerId);
		if(paymentOptional.isPresent()) {
			purchasedBook = paymentOptional.get().getPurchasedBook();
		}
		return purchasedBook;
	}

	@Override
	public Integer getRefund(int readerId, int bookId) {
		Book book = null;
		Integer pid = 0;
		Payment payment = null;
		Optional<Book> bookOptional = bookRepository.findById(bookId);
		if(bookOptional.isPresent()) {
			book = bookOptional.get();
			Optional<Payment> paymentOptional = paymentRepository.findByPurchasedBookAndReaderUserId(book,readerId);
			if(paymentOptional.isPresent()) {
				payment = paymentOptional.get();
				long noOfDays = payment.getPurchasedDate().until(LocalDate.now(), ChronoUnit.DAYS);
				if(noOfDays==0) {
					pid = payment.getId();
					paymentRepository.deleteById(pid);
				}
			}
		}
		return pid;
	}
	
}
