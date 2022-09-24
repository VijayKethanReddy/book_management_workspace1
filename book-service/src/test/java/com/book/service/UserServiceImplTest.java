package com.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import com.book.RoleRepository;
import com.book.UserRepository;
import com.book.constants.BookConstants;
import com.book.entity.ERole;
import com.book.entity.Role;
import com.book.entity.User;
import com.book.service.impl.UserServiceImpl;

/**
 * 
 * @author cogjava3180
 * This is UserServiceImplTest which is used for testing UserServiceImpl methods
 *
 */

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	RoleRepository roleRepository;
	
	@InjectMocks
	UserServiceImpl userService;
	
	Role getAuthorRole() {
		Role role = new Role();
		role.setId(1);
		role.setName(ERole.ROLE_AUTHOR);
		return role;
	}
	
	Role getReaderRole() {
		Role role = new Role();
		role.setId(1);
		role.setName(ERole.ROLE_READER);
		return role;
	}
	
	User getAuthor() {
		User author = new User();
		author.setId(1);
		author.setName("David");
		author.setEmailId("david@gmail.com");
		author.setUserName("David1");
		author.setPassword("David@12345");
		Set<Role> roles = new HashSet<>();
		roles.add(getAuthorRole());
		author.setRoles(roles);
		return author;
	}
	
	User getReader() {
		User reader = new User();
		reader.setId(1);
		reader.setName("Reader1");
		reader.setEmailId("reader1@gmail.com");
		reader.setUserName("Reader1");
		reader.setPassword("Reader@12345");
		Set<Role> roles = new HashSet<>();
		roles.add(getReaderRole());
		reader.setRoles(roles);
		return reader;
	}
	
	@Test
	void testGetAuthor(){
		Optional<User> author = Optional.of(getAuthor());
		Integer authorId = 1;
		ERole roleUser = ERole.ROLE_AUTHOR;
		Optional<Role> userRole = Optional.of(getAuthorRole());
		when(roleRepository.findByName(roleUser)).thenReturn(userRole);
		when(userRepository.findById(authorId)).thenReturn(author);
		User actual = userService.getUser(authorId, ERole.ROLE_AUTHOR);
		assertEquals(author.get(),actual);
	}
	
	@Test
	void testGetAuthor1(){
		Optional<User> author = Optional.empty();
		User user = null;
		Integer authorId = 1;
		ERole roleUser = ERole.ROLE_AUTHOR;
		Optional<Role> userRole = Optional.of(getAuthorRole());
		when(roleRepository.findByName(roleUser)).thenReturn(userRole);
		when(userRepository.findById(authorId)).thenReturn(author);
		User actual = userService.getUser(authorId, ERole.ROLE_AUTHOR);
		assertEquals(user,actual);
	}
	
	@Test
	void testGetAuthor2(){
		Optional<User> reader = Optional.of(getReader());
		User user = null;
		Integer authorId = 1;
		ERole roleUser = ERole.ROLE_AUTHOR;
		Optional<Role> userRole = Optional.of(getAuthorRole());
		when(roleRepository.findByName(roleUser)).thenReturn(userRole);
		when(userRepository.findById(authorId)).thenReturn(reader);
		User actual = userService.getUser(authorId, ERole.ROLE_AUTHOR);
		assertEquals(user,actual);
	}
	
	@Test
	void testGetAuthor3(){
		Integer authorId = 1;
		ERole roleUser = ERole.ROLE_READER;
		Exception e = new RuntimeException(BookConstants.ERROR_ROLE_NOT_FOUND_MSG);
		when(roleRepository.findByName(roleUser)).thenThrow(e);
		assertThrows(RuntimeException.class,()->{
			userService.getUser(authorId, ERole.ROLE_AUTHOR);
		});
	}
	
}
