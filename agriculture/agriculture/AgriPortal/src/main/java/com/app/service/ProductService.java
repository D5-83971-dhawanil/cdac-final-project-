package com.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pojos.Product;
import com.app.pojos.Users;
import com.app.repository.ProductRepository;
import com.app.repository.UsersRepository;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UsersRepository usersRepository;

    // Add a new product
    public Product addProduct(Product product) {
        logger.info("Adding new product with name: {}", product.getProductName());
        return productRepository.save(product);
    }

    // Get a product by ID
    public Optional<Product> getProductById(Long id) {
        logger.info("Fetching product with ID: {}", id);
        return productRepository.findById(id);
    }

    // Update product quantity
    public Optional<Product> updateProductQuantity(Long id, double newQuantity) {
        logger.info("Updating product quantity for ID: {} to {}", id, newQuantity);
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setQuantity(newQuantity);
            productRepository.save(product);
            logger.info("Product quantity updated successfully for ID: {}", id);
            return Optional.of(product);
        } else {
            logger.warn("Product with ID: {} not found for quantity update", id);
            return Optional.empty();
        }
    }

    // Update product price
    public Optional<Product> updateProductPrice(Long id, double price) {
        logger.info("Updating product price for ID: {} to {}", id, price);
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setPrice(price);
            productRepository.save(product);
            logger.info("Product price updated successfully for ID: {}", id);
            return Optional.of(product);
        } else {
            logger.warn("Product with ID: {} not found for price update", id);
            return Optional.empty();
        }
    }

    // Check if the product belongs to the given user
    public boolean isProductOwnedByUser(Long productId, Long userId) {
        logger.info("Checking if product ID: {} is owned by user ID: {}", productId, userId);
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            boolean ownedByUser = userId.equals(product.getUser().getId());
            logger.info("Product ID: {} ownership check result: {}", productId, ownedByUser);
            return ownedByUser;
        }
        logger.warn("Product with ID: {} not found for ownership check", productId);
        return false;
    }

    // Check if the product belongs to a vendor (not the current user)
    public boolean isProductFromVendor(Long productId, Long userId) {
        boolean fromVendor = !isProductOwnedByUser(productId, userId);
        logger.info("Product ID: {} is from vendor: {}", productId, fromVendor);
        return fromVendor;
    }

    // Purchase a product
    public boolean purchaseProduct(Long productId, int quantity, Long userId) {
        logger.info("Attempting to purchase product ID: {} with quantity: {} for user ID: {}", productId, quantity, userId);
        if (!isProductFromVendor(productId, userId)) {
            logger.warn("Cannot purchase your own product ID: {}", productId);
            return false;
        }

        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            logger.warn("Product ID: {} not found", productId);
            return false;
        }

        Product product = productOpt.get();
        if (product.getQuantity() < quantity) {
            logger.warn("Insufficient stock for product ID: {}. Available quantity: {}", productId, product.getQuantity());
            return false;
        }

        product.setQuantity(product.getQuantity() - quantity);
        product.setSoldQuantity(product.getSoldQuantity() + quantity);
        productRepository.save(product);
        logger.info("Purchase successful for product ID: {}. Remaining quantity: {}", productId, product.getQuantity());
        return true;
    }

    // Get all products
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }

    // Calculate total sales for a user
    public double calculateTotalSales(Long userId) {
        logger.info("Calculating total sales for user ID: {}", userId);
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            List<Product> products = productRepository.findByUser(user);
            double totalSales = products.stream()
                    .mapToDouble(p -> p.getPrice() * p.getSoldQuantity())
                    .sum();
            logger.info("Total sales for user ID: {} is {}", userId, totalSales);
            return totalSales;
        } else {
            logger.warn("User ID: {} not found for sales calculation", userId);
            return 0;
        }
    }

    // Calculate total quantity sold for a user
    public double calculateTotalQuantitySold(Long userId) {
        logger.info("Calculating total quantity sold for user ID: {}", userId);
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            List<Product> products = productRepository.findByUser(user);
            double totalQuantitySold = products.stream()
                    .mapToDouble(Product::getSoldQuantity)
                    .sum();
            logger.info("Total quantity sold for user ID: {} is {}", userId, totalQuantitySold);
            return totalQuantitySold;
        } else {
            logger.warn("User ID: {} not found for quantity sold calculation", userId);
            return 0;
        }
    }

    // Get categories of products uploaded by a user
    public List<String> getProductCategoriesByUserId(Long userId) {
        logger.info("Fetching product categories for user ID: {}", userId);
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            List<Product> products = productRepository.findByUser(user);

            Set<String> categoryNames = new HashSet<>();
            for (Product product : products) {
                if (product.getSubCategories() != null) {
                    categoryNames.add(product.getSubCategories().getSubCategoryName());
                } else {
                    categoryNames.add("Unknown");
                }
            }

            List<String> categories = new ArrayList<>(categoryNames);
            logger.info("Categories for user ID: {} are: {}", userId, categories);
            return categories;
        } else {
            logger.warn("User ID: {} not found for fetching product categories", userId);
            return List.of();  // Return an empty list if the user is not found
        }
    }
}
