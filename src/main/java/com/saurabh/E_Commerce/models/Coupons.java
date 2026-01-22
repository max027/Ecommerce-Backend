package com.saurabh.E_Commerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupons extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long couponsId;

    @Column(nullable = false,unique = true)
    private String code;

    private String description;

    @Column(nullable = false)
    private String discount_type;

    @Column(nullable = false)
    private double discount_value;

    @Column(name = "min_order_value", nullable = false)
    private double minOrderValue;

    @Column(name = "max_discount_value", nullable = false)
    private double maxDiscountValue;

    @Column(name = "usage_limit")
    private int usageLimit;

    @Column(name = "usage_coupon")
    private int usageCoupon=0;

    @Column(name = "is_active")
    private boolean isActive=true;

    @Column(name = "valid_from",nullable = false)
    private Date validFrom;

    @Column(name = "valid_until",nullable = false)
    private Date validUntil;
}
