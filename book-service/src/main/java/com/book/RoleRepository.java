package com.book;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.book.entity.ERole;
import com.book.entity.Role;

/**
 * 
 * @author cogjava3180
 * This is RoleRepository which is used for saving role details and fetching role details from db
 *
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(ERole name);
}
