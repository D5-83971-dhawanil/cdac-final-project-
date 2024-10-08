package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Users;

public interface IRegisterDao extends JpaRepository<Users, Long> {
	
	Users findByMobilenoAndPassword(String mob, String pwd);

}
