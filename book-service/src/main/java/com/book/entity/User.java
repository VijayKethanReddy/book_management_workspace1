package com.book.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * @author cogjava3180
 * User bean is used for declaring the details of user with roles author and reader and validation of user details
 *
 */

@Data
@Entity
@Table(	name = "users", 
uniqueConstraints = { 
	@UniqueConstraint(columnNames = "userName"),
	@UniqueConstraint(columnNames = "emailId") 
})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "name1 cannot be blank#######")
	@Size(max = 50)
	private String name;
	
	@Email
	@NotBlank(message = "emailId cannot be blank#######")
	@Size(max = 50)
	private String emailId;
	
	@NotBlank(message = "username cannot be blank#######")
	@Size(max = 20)
	private String userName;
	
	@NotBlank(message = "password cannot be blank#######")
	@Size(max = 120)
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	public User() {
	}

	public User(String name, String userName, String emailId, String password) {
		this.name = name;
		this.userName = userName;
		this.emailId = emailId;
		this.password = password;
	}

}