package com.app.service;

import java.util.List;

import com.app.dto.OrderDto;
import com.app.pojos.Categories;
import com.app.pojos.PinDetails;
import com.app.pojos.Role;
import com.app.pojos.SubCategories;
import com.app.pojos.Users;


public interface IAdminService {

	List<Users> viewAllUsers();
	
	String deleteUser(long userId);
	
	List<Categories> showCategories();
	
	List<SubCategories> showSubCategories();
	
	Categories addCategory(Categories category);
	
	SubCategories addSubCategory(SubCategories subCategory, Long categoryID) throws Exception;
	
	String deleteCategory(long categoryId);
	
	String deleteSubCategory(long subCategoryId);
	
	PinDetails addPincode(PinDetails pinDetails);
	
	Role addRole(Role role);
	List<OrderDto> getAllOrders();
    List<OrderDto> getAllOrdersByFarmers();
    List<OrderDto> getAllOrdersByUsers();
    List<OrderDto> getAllSalesOrdersByVendors();
    List<OrderDto> getAllSalesOrdersByFarmers();
	//void UpdateSubCategoryImagePath(UpdateImageDto updateImageDto);
}
