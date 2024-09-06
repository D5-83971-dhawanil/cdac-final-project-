package com.app.service;

import java.util.List;
import com.app.dto.ProductDto;
import com.app.dto.ProductRequestDto;
import com.app.dto.UpdateImageDto;
import com.app.pojos.Product;

public interface IProductService {
    Product addProduct(ProductRequestDto productRequestDto, long uid);
    Product editProductById(long productId);
    Product updateProduct(ProductRequestDto productRequestDto, long uid);
    List<Product> addedProducts(long uid);
    String deleteProduct(long productId);
    List<Product> productsBySubCategory(long subCatID);
    List<Product> getAllProducts();
    Product getProduct(long productID);
    void updateProductImagePath(UpdateImageDto updateImageDto);
    boolean purchaseProduct(Long productId, int quantity, Long userId);
    Product updateProductPrice(ProductDto productDto);
	Object showAllCategories(String userType);
}
