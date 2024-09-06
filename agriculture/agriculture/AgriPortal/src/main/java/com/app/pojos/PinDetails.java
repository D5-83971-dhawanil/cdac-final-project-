package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pin_details")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PinDetails {

	@Id
	@Column(length = 6,unique = true)
	private String pincode;
	
	@Column(length = 30,unique = true, nullable = false )
	private String city;
	public PinDetails(String pincode) {
		super();
		this.pincode = pincode;
	}
	public PinDetails(String pincode, String city) {
		super();
		this.pincode = pincode;
		this.city = city;
	}
	
	
}
