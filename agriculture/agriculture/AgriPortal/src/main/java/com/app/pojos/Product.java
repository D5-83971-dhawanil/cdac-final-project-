package com.app.pojos;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Product extends BaseEntity {

    @Column(name = "product_name", length = 20, unique = true)
    private String productName;
    
    @Column(nullable = false)
    private double price;
    
    @Column(name = "expiry_date")
    private String expiryDate; // Use LocalDate
    
    @Column(length = 250)
    private String description;
    
    @Column(nullable = false)
    private double quantity;

    @Column(name = "sold_quantity", nullable = false)
    private double soldQuantity = 0; // Initialized to 0

    public double getRemainingQuantity() {
        return this.quantity - this.soldQuantity;
    }
    
    @ManyToOne
    @JoinColumn(name = "uid")
    private Users user;
    
    @ManyToOne
    @JoinColumn(name = "sbt_id")
    private SubCategories subCategories;
    public void setExpiryDate(String string) {
        this.expiryDate = string;
    }
    
    @Column(name = "image_path", length = 500)
    private String imagePath;
}
