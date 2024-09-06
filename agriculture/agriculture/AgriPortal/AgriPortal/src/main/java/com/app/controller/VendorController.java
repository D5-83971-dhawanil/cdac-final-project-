package com.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ProductDto;
import com.app.dto.ProductRequest;
import com.app.pojos.Product;
import com.app.pojos.Users;
import com.app.repository.UsersRepository;
import com.app.service.ProductService;

@RestController
@RequestMapping("/vendor")
//@CrossOrigin(origins="*",allowedHeaders = "*")
@CrossOrigin
public class VendorController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UsersRepository usersRepository;

    // 1. Add a new product
//    @PostMapping("/addProduct")
//    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest) {
//        Optional<Users> userOpt = usersRepository.findById(productRequest.getUserId());
//        if (!userOpt.isPresent()) {
//            return ResponseEntity.badRequest().body("Invalid User ID");
//        }
//        
//        // Convert ProductDto to Product
//        ProductDto dto = productRequest.getProduct();
//        Product product = new Product();
//        product.setProductName(dto.getProductName());
//        product.setPrice(dto.getPrice());
//        
//        // Convert expiryDate from LocalDate to String if necessary
//        LocalDate expiryDate = dto.getExpiryDate();
//        //product.setExpiryDate(expiryDate != null ? expiryDate.toString() : null);
//        
//        product.setDescription(dto.getDescription());
//        product.setQuantity(dto.getQuantity());
//        product.setUser(userOpt.get());
//
//        Product savedProduct = productService.addProduct(product);
//        return ResponseEntity.ok(savedProduct);
//    }
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("price") double price,
            @RequestParam("quantity") double quantity,
            @RequestParam("description") String description,
            @RequestParam("expiryDate") String expiryDate,
            @RequestParam("userId") Long userId,
            @RequestParam("imagePath") String imagePath) {

        Optional<Users> userOpt = usersRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Invalid User ID");
        }

        // Convert input data to Product object
        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setExpiryDate(expiryDate);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setUser(userOpt.get());
        product.setImagePath(imagePath); // Set the image path provided

        // Save the product
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    // 2. Sell a product (reduce quantity)
    @PostMapping("/sellProduct/{productId}")
    public ResponseEntity<?> sellProduct(@PathVariable Long productId, @RequestParam double quantitySold) {
        Optional<Product> productOpt = productService.getProductById(productId);
        if (!productOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        Product product = productOpt.get();
        
        if (quantitySold > product.getRemainingQuantity()) {
            return ResponseEntity.badRequest().body("Not enough quantity in stock");
        }

        productService.updateProductQuantity(productId, quantitySold);
    
        return ResponseEntity.ok("Product sold successfully. Remaining quantity: " + product.getRemainingQuantity());
    }

    // 3. Show remaining quantity of a product
    @GetMapping("/remainingQuantity/{productId}")
    public ResponseEntity<?> getRemainingQuantity(@PathVariable Long productId) {
        Optional<Product> productOpt = productService.getProductById(productId);
        if (!productOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        Product product = productOpt.get();
        return ResponseEntity.ok("Remaining quantity: " + product.getRemainingQuantity());
    }

    // 4. Get categories of uploaded products
    @GetMapping("/productCategories/{userId}")
    public ResponseEntity<?> getProductCategories(@PathVariable Long userId) {
        List<String> categories = productService.getProductCategoriesByUserId(userId);
        return ResponseEntity.ok(categories);
    }

    // 5. Get total amount of sold products
    @GetMapping("/totalSales/{userId}")
    public ResponseEntity<?> getTotalSales(@PathVariable Long userId) {
        double totalSales = productService.calculateTotalSales(userId);
        return ResponseEntity.ok("Total sales amount: $" + totalSales);
    }

    // 6. Get the price of a product
    @GetMapping("/productPrice/{productId}")
    public ResponseEntity<?> getProductPrice(@PathVariable Long productId) {
        Optional<Product> productOpt = productService.getProductById(productId);
        if (!productOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        Product product = productOpt.get();
        return ResponseEntity.ok("Price: $" + product.getPrice());
    }

    // 7. Get total quantity of sold products
    @GetMapping("/totalQuantitySold/{userId}")
    public ResponseEntity<?> getTotalQuantitySold(@PathVariable Long userId) {
        double totalQuantity = productService.calculateTotalQuantitySold(userId);
        return ResponseEntity.ok("Total quantity sold: " + totalQuantity + " units");
    }

    // Purchase a product
    @PostMapping("/purchaseProduct")
    public ResponseEntity<String> purchaseProduct(@RequestParam Long productId, @RequestParam int quantity, @RequestParam Long userId) {
        boolean success = productService.purchaseProduct(productId, quantity, userId);
        if (success) {
            return ResponseEntity.ok("Purchase successful");
        } else {
            return ResponseEntity.badRequest().body("Purchase failed or insufficient stock");
        }
    }
}
