package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Product;
import com.app.pojos.Users;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Custom query method to find all products by a specific user (farmer)
    List<Product> findByUser(Users user);

    // Custom query method to find all products by a specific name
    List<Product> findByProductNameContaining(String productName);
    
    // Additional methods can be added as per your requirements
}
