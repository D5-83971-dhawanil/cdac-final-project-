package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "shop_details")
public class ShopDetails extends BaseEntity {

	@Column(name = "shop_name", length = 100, nullable = false)
	private String shopName;
	
	@Column(length = 150, nullable = false)
	private String gstin;
	
	@OneToOne
	@JoinColumn(name = "uid")
	@MapsId
	private Users user;
}
