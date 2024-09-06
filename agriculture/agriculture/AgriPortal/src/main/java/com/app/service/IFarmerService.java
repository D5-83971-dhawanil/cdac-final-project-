package com.app.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.app.pojos.Categories;
import com.app.pojos.Product;

public interface IFarmerService {
	
	List<Categories> viewCategories();

}
