package com.app.service;

import com.app.dto.UserSiginResponse;
import com.app.dto.UsersDto;
import com.app.pojos.Users;

public interface IRegisterService {

	String registerUser(UsersDto userDto);
	
	Users validateUser(String mob, String pwd);

	UserSiginResponse getBloggerUserdetails(long buid);
}
