package com.app.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.dao.ICartDao;
import com.app.dao.ICategoryDao;
import com.app.dao.IProductDao;
import com.app.dao.ISubCategoryDao;
import com.app.dao.IUserDao;
import com.app.dto.ProductDto;
import com.app.dto.ProductRequestDto;
import com.app.dto.UpdateImageDto;
import com.app.pojos.Product;
import com.app.pojos.Users;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private ISubCategoryDao subCatDao;

    @Autowired
    private IProductDao productDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public Product addProduct(ProductRequestDto productRequestDto, long uid) {
        logger.info("Adding product with details: {}", productRequestDto);
        Product product = mapper.map(productRequestDto, Product.class);
        product.setUser(userDao.findById(uid)
            .orElseThrow(() -> new ResourceNotFoundException("No such user exists")));
        product.setSubCategories(subCatDao.findById(productRequestDto.getSubCatId())
            .orElseThrow(() -> new ResourceNotFoundException("No such category exists")));
        
        if (product.getImagePath() == null || product.getImagePath().isEmpty()) {
            product.setImagePath("default-image-path.jpg");
        }
        
        Product savedProduct = productDao.save(product);
        logger.info("Product added with ID: {}", savedProduct.getId());
        return savedProduct;
    }

    @Override
    public Product editProductById(long productId) {
        logger.info("Fetching product with ID: {}", productId);
        return productDao.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("No such product exists"));
    }

    @Override
    public Product updateProduct(ProductRequestDto productRequestDto, long id) {
        logger.info("Updating product with ID: {} using details: {}", id, productRequestDto);
        Product existingProduct = productDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No such product exists"));

        existingProduct.setProductName(productRequestDto.getProductName());
        existingProduct.setPrice(productRequestDto.getPrice());
        existingProduct.setExpiryDate(productRequestDto.getExpiryDate());
        existingProduct.setDescription(productRequestDto.getDescription());
        existingProduct.setQuantity(productRequestDto.getQuantity());

        if (productRequestDto.getImagePath() != null && !productRequestDto.getImagePath().isEmpty()) {
            existingProduct.setImagePath(productRequestDto.getImagePath());
        }

        if (productRequestDto.getSubCatId() != 0) {
            existingProduct.setSubCategories(subCatDao.findById(productRequestDto.getSubCatId())
                .orElseThrow(() -> new ResourceNotFoundException("No such subcategory exists")));
        }

        if (productRequestDto.getUserId() != 0) {
            existingProduct.setUser(userDao.findById(productRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("No such user exists")));
        }

        Product updatedProduct = productDao.save(existingProduct);
        logger.info("Product updated with ID: {}", updatedProduct.getId());
        return updatedProduct;
    }

    @Override
    public List<Product> addedProducts(long uid) {
        logger.info("Fetching products added by user with ID: {}", uid);
        Users user = userDao.findById(uid)
            .orElseThrow(() -> new ResourceNotFoundException("No such User exists"));
        return productDao.findByUser(user);
    }

    @Override
    public String deleteProduct(long productId) {
        logger.info("Deleting product with ID: {}", productId);
        productDao.deleteById(productId);
        logger.info("Product deleted with ID: {}", productId);
        return "Product is deleted";
    }

    @Override
    public Object showAllCategories(String userType) {
        logger.info("Fetching categories for user type: {}", userType);
        if ("farmer".equals(userType)) {
            return categoryDao.findByType("F");
        } else {
            return categoryDao.findAll();
        }
    }

    @Override
    public List<Product> productsBySubCategory(long subCatID) {
        logger.info("Fetching products by subcategory ID: {}", subCatID);
        return productDao.findBySubCategoriesId(subCatID);
    }

    @Override
    public Product getProduct(long productID) {
        logger.info("Fetching product with ID: {}", productID);
        return productDao.findById(productID)
            .orElseThrow(() -> new ResourceNotFoundException("No such product exists"));
    }

    @Override
    public void updateProductImagePath(UpdateImageDto updateImageDto) {
        logger.info("Updating image path for product ID: {} to {}", updateImageDto.getId(), updateImageDto.getFileName());
        Product product = productDao.findById(updateImageDto.getId())
            .orElseThrow(() -> new ResourceNotFoundException("No such product exists"));
        product.setImagePath(updateImageDto.getFileName());
        productDao.save(product);
    }

    @Override
    public Product updateProductPrice(ProductDto productDto) {
        logger.info("Updating price for product ID: {} to {}", productDto.getId(), productDto.getPrice());
        Product existingProduct = productDao.findById(productDto.getId())
            .orElseThrow(() -> new ResourceNotFoundException("No such product exists"));

        existingProduct.setPrice(productDto.getPrice());
        Product updatedProduct = productDao.save(existingProduct);
        logger.info("Product price updated for ID: {}", updatedProduct.getId());
        return updatedProduct;
    }

    @Override
    public boolean purchaseProduct(Long productId, int quantity, Long userId) {
        logger.info("Attempting to purchase product ID: {} with quantity: {} for user ID: {}", productId, quantity, userId);
        Optional<Product> productOpt = productDao.findById(productId);
        if (!productOpt.isPresent()) {
            logger.warn("Product ID: {} not found for purchase", productId);
            return false; // Product not found
        }

        Product product = productOpt.get();
        if (product.getQuantity() < quantity) {
            logger.warn("Insufficient stock for product ID: {}. Available quantity: {}", productId, product.getQuantity());
            return false; // Insufficient stock
        }

        if (userId.equals(product.getUser().getId())) {
            logger.warn("Cannot purchase your own product ID: {}", productId);
            return false; // Cannot purchase own product
        }

        product.setQuantity(product.getQuantity() - quantity);
        product.setSoldQuantity(product.getSoldQuantity() + quantity); // Update sold quantity
        productDao.save(product);
        logger.info("Purchase successful for product ID: {}. Remaining quantity: {}", productId, product.getQuantity());
        return true;
    }

    @Override
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productDao.findAll();
    }
}
