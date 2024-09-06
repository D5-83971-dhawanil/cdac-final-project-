package com.app.pojos;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"roles","password","cart"})
public class Users extends BaseEntity {

	
	@Column(name = "first_name", length = 15, nullable = false)
	private String firstname;
	
	
	@Column(name = "last_name", length = 15, nullable = false)
	private String lastname;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Role role;
	
	@Column(name = "mobile_no", nullable = false)
	private String mobileno;
	
	@Column(length = 25, unique = true,nullable = false)
	private String email;

	@Column(length = 20, nullable = false)
	private String password;
	
	@Embedded
	private Address address;
	
	@OneToOne
	@JoinColumn(name = "pincode")
	private PinDetails pincode;

	@JsonIgnore
	@OneToOne(mappedBy = "cartOwner",cascade = CascadeType.ALL,orphanRemoval = true)
	private Cart cart;
		
	
}
