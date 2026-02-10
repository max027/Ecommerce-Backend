package com.saurabh.E_Commerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto {
    @NotNull(message = "stock quantity is required")
    private int quantity;

    @NotBlank(message = "stock action is required")
    private String action;
}
