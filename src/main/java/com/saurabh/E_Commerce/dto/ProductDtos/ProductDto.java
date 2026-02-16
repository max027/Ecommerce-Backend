package com.saurabh.E_Commerce.dto.ProductDtos;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
public class ProductDto {
    private long id;
    private long vendorId;
    private String name;
    private String sku;
    private String description;
    private String categoryName;
    private BigDecimal price;
}
