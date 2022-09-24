package com.book.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.book.UserRepository;
import com.book.constants.BookConstants;
import com.book.entity.ERole;
import com.book.entity.Role;
import com.book.entity.User;
import com.book.RoleRepository;
import com.book.security.jwt.JwtUtils;
import com.book.security.service.impl.UserDetailsImpl;
import com.book.request.LoginRequest;
import com.book.request.SignupRequest;
import com.book.response.JwtResponse;
import com.book.response.MessageResponse;

/**
 * 
 * @author cogjava3180
 * This is AuthController which run methods for book api
 * authenticateUser method is used for sign in
 * registerUser method is used for sign up
 * 
 * 
 *
 */

@CrossOrigin
@RestController
@RequestMapping("/userauth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getName(),
												 userDetails.getUsername(), 
												 userDetails.getEmailId(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (Boolean.TRUE.equals(userRepository.existsByUserName(signUpRequest.getUserName()))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse(BookConstants.ERROR_USER_NAME_EXISTS_MSG));
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmailId(signUpRequest.getEmailId()))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse(BookConstants.ERROR_EMAIL_ID_EXISTS_MSG));
		}

		// Create new user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUserName(), 
							 signUpRequest.getEmailId(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_READER)
					.orElseThrow(() -> new RuntimeException(BookConstants.ERROR_ROLE_NOT_FOUND_MSG));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				if(role.equalsIgnoreCase("author")) {
					Role authorRole = roleRepository.findByName(ERole.ROLE_AUTHOR)
							.orElseThrow(() -> new RuntimeException(BookConstants.ERROR_ROLE_NOT_FOUND_MSG));
					roles.add(authorRole);
				}
				else {
					Role readerRole = roleRepository.findByName(ERole.ROLE_READER)
							.orElseThrow(() -> new RuntimeException(BookConstants.ERROR_ROLE_NOT_FOUND_MSG));
					roles.add(readerRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse(BookConstants.USER_REGISTERED_MSG));
	}
}