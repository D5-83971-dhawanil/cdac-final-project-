package com.app.service;

import com.app.dto.OrderDto;

import java.util.List;

public interface IOrderService {
    List<OrderDto> getCurrentOrders(long userId);
    List<OrderDto> getOrderHistory(long userId);
    int getTotalPurchasedItems(long userId);
    String placeOrder(OrderDto orderDto);
    String cancelOrder(long orderId, long userId);
    List<OrderDto> getOrdersForFarmer(long farmerId);
    List<OrderDto> getOrdersForVendor(long vendorId);
    double getTotalOrderValueForFarmer(long farmerId);
    double getTotalOrderValueForVendor(long vendorId);
}
