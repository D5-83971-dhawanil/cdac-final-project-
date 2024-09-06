package com.app.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private long id;
    private String productName;
    private int quantity;
    private double price;
    private LocalDate orderDate;
    private String status; // CURRENT, COMPLETED, CANCELLED
    private long userId; // Add this field to map the user ID
}
