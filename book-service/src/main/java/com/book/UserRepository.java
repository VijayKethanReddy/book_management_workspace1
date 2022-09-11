package com.book;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.book.entity.User;

/**
 * 
 * @author cogjava3180
 * This is UserRepository which is used for saving user details and fetching user details from db
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);

	Boolean existsByUserName(String userName);

	Boolean existsByEmailId(String emailId);
}
