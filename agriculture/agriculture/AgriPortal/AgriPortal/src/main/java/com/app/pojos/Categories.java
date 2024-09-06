package com.app.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Categories extends BaseEntity {
	
	@NotBlank(message = "category name is required")
	@Column(length = 20, nullable = false)
	private String categories;
	
	@NotBlank(message = "enter F and V for farmers and vendors oriented categories respectively ")
	@Column(length = 1, nullable = false)
	private String type;
	
	@OneToMany(mappedBy = "categories",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
	private List<SubCategories> subCategories = new ArrayList<SubCategories>();
	
	public void removeSubcategory(SubCategories subCategory) {
		subCategories.remove(subCategory);
	}
}
