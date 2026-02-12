package com.saurabh.E_Commerce.dto.CartDtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartDto {
    private long id;
    private long usersId;
    private List<CartItemsDto> items;
    private BigDecimal totalAmount;
}
