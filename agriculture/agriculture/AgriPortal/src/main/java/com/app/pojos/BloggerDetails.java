package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "blogger_details")
public class BloggerDetails extends BaseEntity {

	@Column(length = 100, nullable = false)
	private String qualifications;
	
	@OneToOne
	@JoinColumn(name = "uid")
	@MapsId
	private Users user;
	
}
