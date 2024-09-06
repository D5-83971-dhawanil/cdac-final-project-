package com.app.dao;

import com.app.pojos.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderDao extends JpaRepository<Order, Long> {
    List<Order> findByUserIdAndStatus(long userId, String status);
    Optional<Order> findByIdAndUserId(long orderId, long userId);
    List<Order> findByProductName(String productName);

    // Correct way to get total purchased items
    default int countTotalPurchasedItems(long userId) {
        return findByUserIdAndStatus(userId, "COMPLETED").stream()
                .mapToInt(Order::getQuantity)
                .sum();
    }
}
