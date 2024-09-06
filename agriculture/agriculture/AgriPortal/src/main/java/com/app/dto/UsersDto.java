package com.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.app.pojos.Address;
import com.app.pojos.PinDetails;
import com.app.pojos.Role;
import com.app.pojos.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class UsersDto {

	//@NotBlank(message = "first name is required")
	//@Length(min = 4,max=20,message = "Invalid length of firstName!")
	private String firstname;
	
	//@NotBlank(message = "last name is required")
	private String lastname;
	
	//@NotNull(message = "Role is required and should be in Uppercase")
	private UserRole role;
	
	//@Pattern(regexp = "(^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$)")
	//@NotNull(message = "mobile number must be supplied!")
	private String mobileno;
	
	@NotBlank(message = "Email required")
	@Email(message = "Invalid email format!")
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)                       // to skip the field during marshalling or serialisation
//	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[#@$*]).{5,20})",message = "Invalid Password !")
	private String password;
	
	
	private Address address;
	
	
	private PinDetails pincode;
	
}
