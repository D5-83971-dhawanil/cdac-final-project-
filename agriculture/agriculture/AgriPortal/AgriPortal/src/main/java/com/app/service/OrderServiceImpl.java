package com.app.service;

import com.app.dao.IOrderDao;
import com.app.dao.IProductDao;
import com.app.dao.IUserDao;
import com.app.dto.OrderDto;
import com.app.pojos.Order;
import com.app.pojos.Product;
import com.app.pojos.Users;
import com.app.custom_exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IProductDao productDao;

    @Autowired
    private IUserDao userDao; 

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<OrderDto> getCurrentOrders(long userId) {
        List<Order> orders = orderDao.findByUserIdAndStatus(userId, "CURRENT");
        return orders.stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrderHistory(long userId) {
        List<Order> orders = orderDao.findByUserIdAndStatus(userId, "COMPLETED");
        return orders.stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalPurchasedItems(long userId) {
        return orderDao.countTotalPurchasedItems(userId);
    }

    @Override
    public String placeOrder(OrderDto orderDto) {
        // Fetch the user by userId
        Users user = userDao.findById(orderDto.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + orderDto.getUserId()));

        // Map OrderDto to Order and set the user
        Order order = mapper.map(orderDto, Order.class);
        order.setUser(user);

        // Save the order
        orderDao.save(order);
        return "Order placed successfully!";
    }

    @Override
    public String cancelOrder(long orderId, long userId) {
        System.out.println("Attempting to cancel order with ID: " + orderId + " for user with ID: " + userId);

        Order order = orderDao.findByIdAndUserId(orderId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found for this user"));

        if (order.getStatus().equals("CURRENT")) {
            order.setStatus("CANCELLED");
            orderDao.save(order);
            return "Order cancelled successfully!";
        } else {
            return "Order cannot be cancelled as it is not in CURRENT status.";
        }
    }

    @Override
    public List<OrderDto> getOrdersForFarmer(long farmerId) {
        List<Product> products = productDao.findByUserId(farmerId);
        List<Order> orders = products.stream()
            .flatMap(product -> orderDao.findByProductName(product.getProductName()).stream())
            .collect(Collectors.toList());

        return orders.stream()
            .map(order -> {
                OrderDto dto = mapper.map(order, OrderDto.class);
                dto.setUserId(order.getUser() != null ? order.getUser().getId() : -1); // Handle null user
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersForVendor(long vendorId) {
        List<Product> products = productDao.findByUserId(vendorId);
        List<Order> orders = products.stream()
            .flatMap(product -> orderDao.findByProductName(product.getProductName()).stream())
            .collect(Collectors.toList());

        return orders.stream()
            .map(order -> {
                OrderDto dto = mapper.map(order, OrderDto.class);
                dto.setUserId(order.getUser() != null ? order.getUser().getId() : -1); // Handle null user
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    public double getTotalOrderValueForFarmer(long farmerId) {
        return getOrdersForFarmer(farmerId).stream()
            .mapToDouble(order -> order.getQuantity() * order.getPrice())
            .sum();
    }

    @Override
    public double getTotalOrderValueForVendor(long vendorId) {
        return getOrdersForVendor(vendorId).stream()
            .mapToDouble(order -> order.getQuantity() * order.getPrice())
            .sum();
    }
}
