package com.saurabh.E_Commerce.dto.Coupon;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class CouponsResponseDto {
    private long couponsId;
    private String code;
    private String description;
    private String discountType;
    private double discountValue;
    private double minOrderValue;
    private double maxDiscountValue;
    private int usageLimit;
    private int usageCoupon;
    private boolean isActive=true;
    private Instant validFrom;
    private Instant validUntil;
}
