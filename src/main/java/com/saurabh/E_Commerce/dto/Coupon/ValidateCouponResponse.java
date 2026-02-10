package com.saurabh.E_Commerce.dto.Coupon;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ValidateCouponResponse {
    private boolean valid;
    private String message;

    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
}
