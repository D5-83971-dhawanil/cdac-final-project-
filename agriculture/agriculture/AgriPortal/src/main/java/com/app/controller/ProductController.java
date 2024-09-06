package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ProductDto;
import com.app.dto.ProductRequestDto;
import com.app.dto.UpdateImageDto;
import com.app.pojos.Product;
import com.app.service.IProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {
    
    @Autowired
    private IProductService productService;

    // API to show a particular product by product ID
    @GetMapping("/{productID}")
    public ResponseEntity<?> showProduct(@PathVariable long productID) {
        return ResponseEntity.ok(productService.getProduct(productID));
    }
    
    // API to add a product with userID
    @PostMapping("/add/{uid}")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequestDto productRequestDto, @PathVariable long uid) {
        Product product = productService.addProduct(productRequestDto, uid);
        return ResponseEntity.ok(product);
    }
    
    // API to update a product using product ID
    @PutMapping("/edit/{pid}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequestDto productRequestDto, @PathVariable long pid) {
        return ResponseEntity.ok(productService.updateProduct(productRequestDto, pid));
    }
    
    // API to get product details for editing
    @GetMapping("/edit/{productId}")
    public ResponseEntity<?> editProduct(@PathVariable long productId) {
        return ResponseEntity.ok(productService.editProductById(productId));
    }
    
    // API to delete a product
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }
    
    // API to show products related to a particular user
    @GetMapping("/list/{userid}")
    public ResponseEntity<?> showUsersProducts(@PathVariable long userid) {
        return ResponseEntity.ok(productService.addedProducts(userid));
    }
    
    // API to view categories as per user type
    @GetMapping("/viewCategories/{userType}")
    public ResponseEntity<?> viewCategories(@PathVariable String userType) {
        return ResponseEntity.ok(productService.showAllCategories(userType));
    }
    
    // API to get products associated with a particular subCategory
    @GetMapping("/viewProducts/{subCatID}")
    public ResponseEntity<?> getProductsFromSubCategoryID(@PathVariable long subCatID) {
        return ResponseEntity.ok(productService.productsBySubCategory(subCatID));
    }
    
    // API to update image path of a product
    @PostMapping("/image")
    public ResponseEntity<?> updateProductImage(@RequestBody UpdateImageDto updateImageDto) {
        productService.updateProductImagePath(updateImageDto);
        return ResponseEntity.ok("Image path updated successfully.");
    }
    
    // API to get all products
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
  //  @Autowired
   // private IProductService productService;

    // Other endpoints...

    // API to update the price of a product
    @PutMapping("/updatePrice")
    public ResponseEntity<?> updateProductPrice(@RequestBody ProductDto productDto) {
        Product updatedProduct = productService.updateProductPrice(productDto);
        return ResponseEntity.ok(updatedProduct);
    }
}
