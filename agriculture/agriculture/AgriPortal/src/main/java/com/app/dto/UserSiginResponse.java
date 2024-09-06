package com.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSiginResponse {
	

//	@NotBlank(message = "first name is required")
//	@Length(min = 4,max=20,message = "Invalid length of firstName!")
	private String firstname;
	
//	@NotBlank(message = "last name is required")
	private String lastname;
	
//	@NotBlank(message = "Email required")
//	@Email(message = "Invalid email format!")
	private String email;
	
	
}
