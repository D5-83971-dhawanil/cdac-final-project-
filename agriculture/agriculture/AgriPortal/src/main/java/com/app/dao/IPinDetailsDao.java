package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.PinDetails;

public interface IPinDetailsDao extends JpaRepository<PinDetails, String> {

}
