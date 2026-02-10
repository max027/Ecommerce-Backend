package com.saurabh.E_Commerce.dto.CartDtos;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemsDto {
    private long id;
    private long cartId;
    private long productsId;
    private String productsName;
    private int quantity;
    private double price;
}
