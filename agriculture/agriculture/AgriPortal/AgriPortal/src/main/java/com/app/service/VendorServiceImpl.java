package com.app.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.IProductDao;
import com.app.dao.IVendorDao;
import com.app.pojos.Users;
import com.app.custom_exception.ResourceNotFoundException;

@Service
@Transactional
public class VendorServiceImpl implements IVendorService {
	
    private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);
	
	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private IVendorDao vendorDao;

	@Override
	public Users showProfile(long userId) {
        logger.info("Fetching profile for user with ID: {}", userId);
        Optional<Users> vendor = vendorDao.findById(userId);
        if (vendor.isPresent()) {
            logger.info("Profile found for user ID: {}", userId);
            return vendor.get();
        } else {
            logger.error("No such user exists with ID: {}", userId);
            throw new ResourceNotFoundException("No such user exists");
        }
	}

	@Override
	public String editProfile(Users vendor) {
        logger.info("Editing profile for user with ID: {}", vendor.getId());
        try {
            vendorDao.save(vendor);
            logger.info("Profile updated successfully for user ID: {}", vendor.getId());
            return "Profile edited";
        } catch (Exception e) {
            logger.error("Error occurred while editing profile for user ID: {}: {}", vendor.getId(), e.getMessage());
            throw e;
        }
	}		 
}
