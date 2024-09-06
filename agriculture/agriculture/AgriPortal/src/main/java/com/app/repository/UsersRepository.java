package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.pojos.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    // Additional query methods (if needed) can be added here
}
