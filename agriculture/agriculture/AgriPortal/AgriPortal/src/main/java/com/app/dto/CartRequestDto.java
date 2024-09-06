package com.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.app.pojos.Product;
import com.app.pojos.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CartRequestDto {

	@NotBlank
	private double quantity;
	
	
	private Users cartOwner;
	
	@NotBlank
	private long productID;

}
