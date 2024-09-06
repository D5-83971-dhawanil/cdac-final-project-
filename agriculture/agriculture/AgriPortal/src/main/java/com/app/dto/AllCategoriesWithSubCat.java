package com.app.dto;

import java.util.ArrayList;

import com.app.pojos.SubCategories;


public class AllCategoriesWithSubCat {
	
	private Long id;
	
	private String categories;
	
	private String type;
	
	private java.util.List<SubCategories> subCategories = new ArrayList<SubCategories>();
}
