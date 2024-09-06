package com.app.dto;

import com.app.pojos.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(Include.NON_EMPTY)
public class CartItemDto {
	// idhar sirf quantity bhejna hai re body mai 
	private long id ;
	private int quantity;
	private double totalPrice;
	private Product product;
	public CartItemDto(int quantity, double totalPrice) {
		super();
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
	public CartItemDto(long id,int quantity, double totalPrice, Product product) {
		this.id = id ;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.product = product;
	}
	
}
