package com.book.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 
 * @author cogjava3180
 * Payment bean is used for declaring the details of Payment and validation of Payment details
 *
 */

@Data
@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "purchasedBook cannot be null#######")
	@ManyToOne
	private Book purchasedBook;
	
	@NotNull(message = "readerUserId cannot be null#######")
	private int readerUserId;
	
	@NotBlank(message = "readerUserName cannot be blank#######")
	private String readerUserName;
	
	@NotBlank(message = "emailId cannot be blank#######")
	private String emailId;
	
	@NotNull(message = "purchasedDate cannot be blank#######")
	private LocalDate purchasedDate;
	
}
