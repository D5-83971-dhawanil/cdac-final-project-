package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Users;

public interface IUserDao extends JpaRepository<Users, Long> {

}
