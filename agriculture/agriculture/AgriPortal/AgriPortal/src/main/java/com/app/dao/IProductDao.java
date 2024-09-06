package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.dto.ProductDto;
import com.app.pojos.Product;
import com.app.pojos.Role;
import com.app.pojos.Users;

public interface IProductDao extends JpaRepository<Product, Long> {
	
//	List<Product> findAllByUsersRole(String role);

	
	List<Product> findByUser(Users user);
	
	@Query("select p from Product p")
	List<Product> viewProductsForBuying(@Param("u_userId") long userId);

	List<Product> findBySubCategoriesId(long subCatID);
	 List<Product> findByUserId(long userId);
	
}
	

