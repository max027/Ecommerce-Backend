package com.saurabh.E_Commerce.dto.OrdersDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderDto {
    private List<ItemsDto> items;
    private long shippingAddress;
    private long billingAddress;
    private String couponCode;
    private String paymentMethod;
}
