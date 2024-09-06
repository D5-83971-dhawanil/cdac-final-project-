package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude ={"product","cart"})
public class CartItem  extends BaseEntity{
	
	private int quantity;
	@Column(name = "total_price")
	private double totalPrice;
	
	@ManyToOne
	@JoinColumn(name = "cart_id",nullable=false)
	private Cart cart;
	
	@OneToOne
	@JoinColumn(name="product_id",nullable=false)
	private Product product;
	
	public CartItem(int quantity, double totalPrice) {
		super();
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}	
}
