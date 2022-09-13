package com.book.entity;


import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @author cogjava3180
 * Book bean is used for declaring the details of book and validation of book details
 *
 */

@Data
@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "logo cannot be blank#######")
	private String logo;
	
	@NotBlank(message = "title cannot be blank#######")
	private String title;
	
	@NotNull(message = "category cannot be blank#######")
	@Enumerated(EnumType.STRING)
	private BookCategory category;
	
	@NotNull(message = "price cannot be null#######")
	@DecimalMin(value = "1.0", message = "price cannot be less than 1")
	@Digits(integer = 3, fraction = 2)
	private BigDecimal price;
	
	private String authorUserName;
	
	private String authorName;
	
	@NotBlank(message = "publisher cannot be blank#######")
	private String publisher;
	
	@NotNull(message = "publishedDate cannot be blank#######")
	@DateTimeFormat(style = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate publishedDate;
	
	@NotBlank(message = "content cannot be blank#######")
	private String content;
	
	@NotNull(message = "active cannot be null#######")
	private Boolean active;
}
