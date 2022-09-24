package com.book.service.impl;

import java.util.Optional;
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
 * getUser method is used for fetching user with user id and role
 *
 */

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public User getUser(int userId, ERole roleUser) {
		User user = null;
		Role userRole = roleRepository.findByName(roleUser)
				.orElseThrow(() -> new RuntimeException(BookConstants.ERROR_ROLE_NOT_FOUND_MSG));
		Optional<User> userOptional = userRepository.findById(userId);
		if(userOptional.isPresent() && userOptional.get().getRoles().stream().anyMatch(r -> r.getName().equals(userRole.getName()))) {
			user = userOptional.get();
		}
		return user;
	}

}
