package com.saurabh.E_Commerce.dto.CartDtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartRequestDto {
    @NotNull(message = "product id is required")
    private Long productId;
    @NotNull(message = "quantity is required")
    private int quantity;
}
