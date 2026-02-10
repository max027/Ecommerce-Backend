package com.saurabh.E_Commerce.dto.Coupon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CouponRequestDto {
    @NotBlank(message = "coupon code is required")
    private String code;

    private String description;

    @NotBlank(message = "discount type is required")
    private String discountType;

    @NotNull
    private double discountValue;

    @NotNull
    private double minOrderValue;

    @NotNull
    private double maxDiscountValue;

    @NotNull
    private int usageLimit;

    @NotNull
    private int usageCoupon;

    @NotNull
    private boolean isActive;

    @NotNull
    private Instant validFrom;

    @NotNull
    private Instant validUntil;
}
