package com.app.service;

import java.util.List;

import com.app.pojos.Cart;
import com.app.pojos.Categories;
import com.app.pojos.Product;
import com.app.pojos.Role;
import com.app.pojos.SubCategories;

public interface ICustomerService {
	
//	//view all products
//	List<Product> viewAllProducts(Role role);
	
	//view products by category cid = category id
	List<Product> viewProductsByCategory(Iterable<Long> cid);

	
	//add product to cart and subtract from quantity of products in product table pid = product id
	String addProductToCart(long pid);

	//view cart uid = user id
	List<Product> viewCart(long uid);

	
	//get all categories
	List<Categories> showAllCategories();
	
	//get all subcategories
//	List<SubCategories> showAllSubCategories();
	
	
	
}
