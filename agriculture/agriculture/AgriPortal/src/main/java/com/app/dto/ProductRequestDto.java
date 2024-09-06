package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private String productName;
    private double price;
    private String expiryDate;
    private String description;
    private double quantity;
    private long subCatId;
    private long userId;
    private String imagePath; // Added to handle image path
}
