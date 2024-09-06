package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.dao.ICategoryDao;
import com.app.dao.IOrderDao;
import com.app.dao.IPinDetailsDao;
import com.app.dao.IProductDao;
import com.app.dao.IRoleDao;
import com.app.dao.ISubCategoryDao;
import com.app.dao.IUserDao;
import com.app.dto.OrderDto;
import com.app.pojos.Categories;
import com.app.pojos.Order;
import com.app.pojos.PinDetails;
import com.app.pojos.Product;
import com.app.pojos.Role;
import com.app.pojos.SubCategories;
import com.app.pojos.Users;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private IUserDao userDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    private ISubCategoryDao subCategoryDao;

    @Autowired
    private IPinDetailsDao pinDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IProductDao productDao;

    private RestTemplate restTemplate;

    @Value("${netbanking.post.url}")
    private String getUrl;

    @Autowired
    public AdminServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public List<Users> viewAllUsers() {
        logger.info("Fetching all users");
        return userDao.findAll();
    }

    @Override
    public String deleteUser(long userId) {
        logger.info("Attempting to delete user with ID: {}", userId);
        Users user = userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No such user exists with ID: " + userId));
        userDao.deleteById(userId);
        String message = "User " + user.getFirstname() + " " + user.getLastname() + " is deleted";
        logger.info(message);
        return message;
    }

    @Override
    public List<Categories> showCategories() {
        logger.info("Fetching all categories");
        return categoryDao.findAll();
    }

    @Override
    public List<SubCategories> showSubCategories() {
        logger.info("Fetching all sub-categories");
        return subCategoryDao.findAll();
    }

    @Override
    public Categories addCategory(Categories category) {
        logger.info("Adding new category: {}", category.getCategories());
        return categoryDao.save(category);
    }

    @Override
    public SubCategories addSubCategory(SubCategories subCategory, Long categoryID) throws Exception {
        logger.info("Adding new sub-category to category with ID: {}", categoryID);
        Categories categories = categoryDao.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("No such category exists with ID: " + categoryID));
        subCategory.setCategories(categories);
        return subCategoryDao.save(subCategory);
    }

    @Override
    public String deleteCategory(long categoryId) {
        logger.info("Attempting to delete category with ID: {}", categoryId);
        Categories category = categoryDao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("No such category exists with ID: " + categoryId));
        categoryDao.deleteById(categoryId);
        String message = "Category " + category.getCategories() + " is deleted";
        logger.info(message);
        return message;
    }

    @Override
    public String deleteSubCategory(long subCategoryId) {
        logger.info("Attempting to delete sub-category with ID: {}", subCategoryId);
        SubCategories subCategory = subCategoryDao.findById(subCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("No such sub-category exists with ID: " + subCategoryId));
        
        List<Product> products = productDao.findBySubCategoriesId(subCategoryId);
        logger.info("Deleting products associated with sub-category ID: {}", subCategoryId);
        products.forEach(product -> productDao.delete(product));
        
        Categories category = categoryDao.findBySubCategoriesCategoriesId(subCategory.getCategories().getId());
        logger.info("Removing sub-category from its parent category");
        category.getSubCategories().remove(subCategory);
        categoryDao.save(category);
        
        subCategoryDao.deleteById(subCategoryId);
        String message = "Sub-Category " + subCategory.getSubCategoryName() + " is deleted";
        logger.info(message);
        return message;
    }

    @Override
    public PinDetails addPincode(PinDetails pinDetails) {
        logger.info("Adding new pin code: {}", pinDetails.getPincode());
        return pinDao.save(pinDetails);
    }

    @Override
    public Role addRole(Role role) {
        logger.info("Adding new role: {}", role.getRoleName());
        return roleDao.save(role);
    }
    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderDao.findAll();
        return orders.stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrdersByFarmers() {
        // Assuming farmers are identified by some role or userType in the user model
        List<Order> orders = orderDao.findAll(); // Fetch all orders and filter in-memory or via query based on farmers
        return orders.stream()
                .filter(order -> order.getUser() != null && order.getUser().getRole().equals("FARMER"))
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrdersByUsers() {
        List<Order> orders = orderDao.findAll();
        return orders.stream()
                .filter(order -> order.getUser() != null && order.getUser().getRole().equals("USER"))
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllSalesOrdersByVendors() {
        List<Order> orders = orderDao.findAll();
        return orders.stream()
                .filter(order -> order.getUser() != null && order.getUser().getRole().equals("VENDOR"))
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllSalesOrdersByFarmers() {
        List<Order> orders = orderDao.findAll();
        return orders.stream()
                .filter(order -> order.getUser() != null && order.getUser().getRole().equals("FARMER"))
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }
}
