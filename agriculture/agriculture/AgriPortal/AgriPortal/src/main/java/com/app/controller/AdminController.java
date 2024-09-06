package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.OrderDto;
import com.app.pojos.Categories;
import com.app.pojos.SubCategories;
import com.app.service.IAdminService;
@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

	@Autowired
	private IAdminService adminService;
	
	// api to get list of all Users registered on application;
	@GetMapping("/allUsers")
	
	public ResponseEntity<?> showAllUsers()     //-------------------------------------------
	{
		return ResponseEntity.ok(adminService.viewAllUsers());
	}
	
	// api to get list of all categories with associated subCategories ;
	@GetMapping("/allCategories")
	public ResponseEntity<?> showAllCategories()      //-----------------------------
	{
		return ResponseEntity.ok(adminService.showCategories());
	}
	
	// api to get list of all subCategories with list of all associated products;
	@GetMapping("/allSubCategories")
	public ResponseEntity<?> showAllSubCategories()    //--------------------------------------
	{
		return ResponseEntity.ok(adminService.showSubCategories());
	}
	
	// api to delete category from categories table
	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable long categoryId)       //--------------------------------
	{
		return ResponseEntity.ok(adminService.deleteCategory(categoryId));
		
	} 
	
	// ERROR :- in it subCategories child products are getting deleted but sub category is not deleted;
	@DeleteMapping("/subCategory/{subCategoryId}")
	public ResponseEntity<?> deleteSubCategory(@PathVariable long subCategoryId)    //----------------------------------
	{
		return ResponseEntity.ok(adminService.deleteSubCategory(subCategoryId));
		
	} 
	
	// api to delete user from database;
	@DeleteMapping("/user/{userId}")         //------------------------------------------
	public ResponseEntity<?> deleteUsers(@PathVariable long userId)
	{
		return ResponseEntity.ok(adminService.deleteUser(userId));
		
	} 
	
	// api to add category in database;
	@PostMapping("/add/category")
	public ResponseEntity<?> addCategory(@RequestBody @Valid Categories category)    //---------------------------------
	{
		return ResponseEntity.ok(adminService.addCategory(category));
		
	} 
	
	// api to add sub category in the database;
	@PostMapping("/add/{categoryID}/subCategory")
	public ResponseEntity<?> addSubCategory(@RequestBody SubCategories subCategories, @PathVariable Long categoryID )  throws Exception //--------------------------------
	{
		return ResponseEntity.ok(adminService.addSubCategory(subCategories,categoryID));
		
	}
	
	 @GetMapping("/allOrders")
	    public ResponseEntity<List<OrderDto>> showAllOrders() {
	        List<OrderDto> orders = adminService.getAllOrders();
	        return ResponseEntity.ok(orders);
	    }

	    // New endpoint to get all orders by farmers
	    @GetMapping("/ordersByFarmers")
	    public ResponseEntity<List<OrderDto>> showAllOrdersByFarmers() {
	        List<OrderDto> orders = adminService.getAllOrdersByFarmers();
	        return ResponseEntity.ok(orders);
	    }

	    // New endpoint to get all orders placed by users
	    @GetMapping("/ordersByUsers")
	    public ResponseEntity<List<OrderDto>> showAllOrdersByUsers() {
	        List<OrderDto> orders = adminService.getAllOrdersByUsers();
	        return ResponseEntity.ok(orders);
	    }

	    // New endpoint to get all sales orders by vendors
	    @GetMapping("/salesOrdersByVendors")
	    public ResponseEntity<List<OrderDto>> showAllSalesOrdersByVendors() {
	        List<OrderDto> orders = adminService.getAllSalesOrdersByVendors();
	        return ResponseEntity.ok(orders);
	    }

	    // New endpoint to get all sales orders by farmers
	    @GetMapping("/salesOrdersByFarmers")
	    public ResponseEntity<List<OrderDto>> showAllSalesOrdersByFarmers() {
	        List<OrderDto> orders = adminService.getAllSalesOrdersByFarmers();
	        return ResponseEntity.ok(orders);
	    }
//	@PostMapping("/subCategory/image")
//	public void updateSubcategoryImage(@RequestBody UpdateImageDto updateImageDto) {
//		adminService.UpdateSubCategoryImagePath(updateImageDto);
//	}

}
