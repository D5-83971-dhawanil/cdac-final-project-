package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Role;
import com.app.pojos.UserRole;

public interface IRoleDao extends JpaRepository<Role, Long> {

	Role findByRoleName(UserRole userRole);

}
