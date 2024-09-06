package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Users;

public interface IVendorDao extends JpaRepository<Users, Long> {

	

    
}
