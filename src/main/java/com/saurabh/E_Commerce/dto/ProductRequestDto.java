package com.saurabh.E_Commerce.dto;

import com.saurabh.E_Commerce.models.Categories;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.StackTrace;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequestDto {
    @NotNull
    private String name;

    @NotNull
    private String sku;

    @NotNull
    private String slug;

    private String description;

    @NotNull
    private long categoryId;

    @NotNull
    private double price;

    private double discountPrice;

    @NotNull
    @Min(1)
    private int stockQuantity;

    @NotNull
    private List<String> imageUrls;
}
