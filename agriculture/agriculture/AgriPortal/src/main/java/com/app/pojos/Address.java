package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Address {

	@Column(length = 100, unique = true)
	private String apartment;
	@Column(length = 100)
	private String area;
	@Column(length = 100)
	private String landmark;
	
}
