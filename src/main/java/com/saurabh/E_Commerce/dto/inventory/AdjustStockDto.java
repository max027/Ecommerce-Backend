package com.saurabh.E_Commerce.dto.inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdjustStockDto {
    @NotNull
    private int productId;

    @NotNull
    private int quantityChange;

    @NotBlank
    private String type;

    @NotBlank
    private String notes;
}
