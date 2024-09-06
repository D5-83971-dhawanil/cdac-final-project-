package com.app.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class ProductDto {
    private long id;
    private String productName;
    private double price;
    private LocalDate expiryDate; // Ensure this is LocalDate
    private String description;
    private double quantity;
    private Long userId;
    private String imagePath;
}
