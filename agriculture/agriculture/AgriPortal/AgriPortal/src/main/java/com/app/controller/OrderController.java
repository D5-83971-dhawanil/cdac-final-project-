package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.dto.OrderDto;
import com.app.service.IOrderService;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins="*",allowedHeaders = "*")

public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/current")
    public List<OrderDto> getCurrentOrders(@RequestParam("userId") long userId) {
        return orderService.getCurrentOrders(userId);
    }

    @GetMapping("/history")
    public List<OrderDto> getOrderHistory(@RequestParam("userId") long userId) {
        return orderService.getOrderHistory(userId);
    }

    @GetMapping("/total-purchased")
    public int getTotalPurchasedItems(@RequestParam("userId") long userId) {
        return orderService.getTotalPurchasedItems(userId);
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody OrderDto orderDto) {
        String response = orderService.placeOrder(orderDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable long orderId, @RequestParam("userId") long userId) {
        try {
            String response = orderService.cancelOrder(orderId, userId);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<OrderDto>> getOrdersForFarmer(@PathVariable long farmerId) {
        List<OrderDto> orders = orderService.getOrdersForFarmer(farmerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<OrderDto>> getOrdersForVendor(@PathVariable long vendorId) {
        List<OrderDto> orders = orderService.getOrdersForVendor(vendorId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/farmer/{farmerId}/total")
    public ResponseEntity<Double> getTotalOrderValueForFarmer(@PathVariable long farmerId) {
        double totalValue = orderService.getTotalOrderValueForFarmer(farmerId);
        return ResponseEntity.ok(totalValue);
    }

    @GetMapping("/vendor/{vendorId}/total")
    public ResponseEntity<Double> getTotalOrderValueForVendor(@PathVariable long vendorId) {
        double totalValue = orderService.getTotalOrderValueForVendor(vendorId);
        return ResponseEntity.ok(totalValue);
    }

}
