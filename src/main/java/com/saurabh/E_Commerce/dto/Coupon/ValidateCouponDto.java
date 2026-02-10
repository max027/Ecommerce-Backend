package com.saurabh.E_Commerce.dto.Coupon;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ValidateCouponDto {
    @NotBlank(message = "cupon code is required")
    private String code;

    @NotNull
    @DecimalMin(value = "0.0",inclusive = false)
    private BigDecimal cartTotal;
}
