package com.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.pojos.Product;
import com.app.repository.ProductRepository;
import com.app.service.ProductService;

@RestController
@RequestMapping("/farmers")
@CrossOrigin
public class FarmerController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    // 1. Add a new product
    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    // 2. View sold products and remaining quantity
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. Update product quantity
    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<Product> updateProductQuantity(@PathVariable Long id, @RequestParam int quantity) {
        Optional<Product> updatedProduct = productService.updateProductQuantity(id, quantity);
        if (updatedProduct.isPresent()) {
            return ResponseEntity.ok(updatedProduct.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. Update product price
    @PutMapping("/updatePrice/{id}")
    public ResponseEntity<Product> updateProductPrice(@PathVariable Long id, @RequestParam double price) {
        Optional<Product> updatedProduct = productService.updateProductPrice(id, price);
        if (updatedProduct.isPresent()) {
            return ResponseEntity.ok(updatedProduct.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Get all products of the farmer
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @PostMapping("/purchaseProduct")
    public ResponseEntity<String> purchaseProduct(@RequestParam Long productId, @RequestParam int quantity) {
        boolean success = productService.purchaseProduct(productId, quantity, productId);
        if (success) {
            return ResponseEntity.ok("Purchase successful");
        } else {
            return ResponseEntity.badRequest().body("Purchase failed or insufficient stock");
        }
    }
}
