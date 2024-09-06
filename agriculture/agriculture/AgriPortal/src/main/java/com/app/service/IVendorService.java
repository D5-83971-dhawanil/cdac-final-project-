package com.app.service;

import com.app.pojos.Users;

public interface IVendorService {
	
	String editProfile(Users vendor);
	
	Users showProfile(long userId);
}
