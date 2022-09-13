package com.book.service;

import javax.validation.Valid;

import com.book.entity.ERole;
import com.book.entity.User;

/**
 * 
 * @author cogjava3180
 * This is UserService interface which used for defining user details methods
 *
 */

public interface UserService {

	User getUser(int userId, ERole roleUser);
	
	//public User saveAuthor(@Valid User user);
}
