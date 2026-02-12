package com.saurabh.E_Commerce.dto.ProductDtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductRequestDto {
    @NotBlank(message = "product name is required")
    private String name;

    @NotBlank(message = "product sky name is required")
    private String sku;

    @NotBlank(message = "product slug is required")
    private String slug;

    private String description;

    @NotNull
    private long categoryId;

    @NotNull
    private BigDecimal price;

    private double discountPrice;

    @NotNull
    @Min(1)
    private int stockQuantity;

    @NotNull
    private List<String> imageUrls;
}
