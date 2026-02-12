package com.saurabh.E_Commerce.dto.OrdersDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemsDto {
    @NotNull(message = "product id is required")
    long productId;

    @NotNull(message = "product id quantity is required")
    @Min(1)
    int quantity;
}
