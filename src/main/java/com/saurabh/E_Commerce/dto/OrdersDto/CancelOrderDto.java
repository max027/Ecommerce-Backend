package com.saurabh.E_Commerce.dto.OrdersDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelOrderDto {
    @NotBlank(message = "reason is required")
    private String reason;
}
