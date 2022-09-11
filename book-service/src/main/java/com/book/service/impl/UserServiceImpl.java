package com.book.service.impl;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.RoleRepository;
import com.book.UserRepository;
import com.book.constants.BookConstants;
import com.book.entity.ERole;
import com.book.entity.Role;
import com.book.entity.User;
import com.book.service.UserService;

/**
 * 
 * @author cogjava3180
 * This is UserServiceImpl which is used for running methods from controller
 * getAuthor method is used for fetching user details for user id
 * saveAuthor method is used for saving user details
 *
 */

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public User getAuthor(int authorId, ERole roleAuthor) {
		User user = null;
		Role authorRole = roleRepository.findByName(roleAuthor)
				.orElseThrow(() -> new RuntimeException(BookConstants.ERROR_ROLE_NOT_FOUND_MSG));
		Optional<User> userOptional = userRepository.findById(authorId);
		if(userOptional.isPresent() && userOptional.get().getRoles().contains(authorRole)) {
			user = userOptional.get();
		}
		return user;
	}
	
//	@Override
//	public User saveAuthor(@Valid User user) {
//		return userRepository.save(user);
//	}

}
