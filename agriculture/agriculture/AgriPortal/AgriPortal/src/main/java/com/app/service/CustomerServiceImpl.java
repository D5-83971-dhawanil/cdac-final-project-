package com.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ICategoryDao;
import com.app.dao.ICustomerDao;
import com.app.dao.IProductDao;
import com.app.pojos.Categories;
import com.app.pojos.Product;
import com.app.pojos.Role;
import com.app.pojos.SubCategories;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private IProductDao productDao;
    
    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public String addProductToCart(long pid) {
        logger.info("Attempting to add product with ID: {} to cart.", pid);
        // TODO: Implement logic
        logger.info("Adding product with ID: {} to cart is not yet implemented.", pid);
        return null;
    }

    @Override
    public List<Product> viewCart(long uid) {
        logger.info("Fetching cart for user with ID: {}", uid);
        // TODO: Implement logic
        logger.info("Viewing cart for user with ID: {} is not yet implemented.", uid);
        return null;
    }

    @Override
    public List<Categories> showAllCategories() {
        logger.info("Fetching all categories with type 'F'.");
        List<Categories> categories = categoryDao.findByType("F");
        logger.info("Found {} categories with type 'F'.", categories.size());
        return categories;
    }

    @Override
    public List<Product> viewProductsByCategory(Iterable<Long> cid) {
        logger.info("Fetching products for category IDs: {}", cid);
        // TODO: Implement logic
        logger.info("Viewing products by category is not yet implemented.");
        return null;
    }

    // Uncomment and update this method if needed
    /*
    @Override
    public List<SubCategories> showAllSubCategories() {
        logger.info("Fetching all subcategories.");
        List<SubCategories> subCategories = categoryDao.viewAllSubcategories();
        logger.info("Found {} subcategories.", subCategories.size());
        return subCategories;
    }
    */
}
