package com.app.pojos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Cart extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name="uid")
	@MapsId
	private Users cartOwner;
	
	@Column(name = "total_items")
	private int totalItems;
	
	@Column(name = "total_cart_price")
	private double totalCartPrice;
	
	@CreationTimestamp 
	@Column(name = "created_on")
	private LocalDate createdOn;
	
	@UpdateTimestamp 
	@Column(name = "last_updated_on")
	private LocalDate lastUpdatedOn;
	
	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CartItem> cartItems=new ArrayList<>();

	public Cart(Users cartOwner) {
		super();
		this.cartOwner = cartOwner;
	}
	
}