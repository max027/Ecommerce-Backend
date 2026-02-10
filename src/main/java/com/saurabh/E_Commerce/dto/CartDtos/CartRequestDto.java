package com.saurabh.E_Commerce.dto.CartDtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartRequestDto {
    @NotNull
    private Long productId;
    @NotNull
    private int quantity;
}
