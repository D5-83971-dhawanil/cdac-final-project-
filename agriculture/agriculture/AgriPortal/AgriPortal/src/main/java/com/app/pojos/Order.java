package com.app.pojos;

import javax.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;
    private int quantity;
    private double price;
    private LocalDate orderDate;
    private String status; // CURRENT, COMPLETED, CANCELLED

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Ensure the user_id column is not nullable
    private Users user; // Ensure this relationship is correctly mapped
}
