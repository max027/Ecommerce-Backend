package com.saurabh.E_Commerce.utils;

import com.saurabh.E_Commerce.dto.*;
import com.saurabh.E_Commerce.dto.AuthDtos.UserDto;
import com.saurabh.E_Commerce.dto.CartDtos.CartItemsDto;
import com.saurabh.E_Commerce.dto.CategoryDtos.CategoryDto;
import com.saurabh.E_Commerce.dto.ProductDtos.ProductDto;
import com.saurabh.E_Commerce.models.*;

public class DataMapper {
    public static UserDto convertToUserDto(Users users){
        return UserDto.builder().id(users.getUserId())
                .email(users.getEmail())
                .first_name(users.getFirstName())
                .last_name(users.getLastName())
                .phone(users.getPhone())
                .build();
    }
    public static ProductDto convertToProductDto(Products products){
        return ProductDto.builder()
                .id(products.getProductId())
                .name(products.getName())
                .description(products.getDescription())
                .categoryName(products.getCategories().getName())
                .price(products.getPrice())
                .sku(products.getSku())
                .build();
    }
    public static ReviewsDto converToReviewsDto(Review review){
        return ReviewsDto.builder()
                .reviewId(review.getReviewId())
                .username(review.getUsers().getFirstName()+" "+review.getUsers().getLastName())
                .rating(review.getRating())
                .text(review.getText())
                .isVerifiedPurchase(review.isVerifiedPurchase())
                .build();
    }
    public static CategoryDto convertToCategoryDto(Categories categories){
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setCategoryId(categories.getCategoryId());
        categoryDto.setName(categories.getName());
        categoryDto.setDescription(categories.getDescription());
        if (categories.getParentId()==null){
            categoryDto.setParentId(0);
        }else {
            categoryDto.setParentId(categories.getParentId().getCategoryId());
        }
        categoryDto.setSlug(categories.getSlug());
        categoryDto.setIsActive(categories.isActive());
        return categoryDto;
    }
    public static CartItemsDto convertToCartItemsDto(CartItems items){
        CartItemsDto dto=new CartItemsDto();
        dto.setId(items.getId());
        dto.setCartId(items.getCart().getId());
        dto.setPrice(items.getPrice());
        dto.setQuantity(items.getQuantity());
        dto.setProductsId(items.getProducts().getProductId());
        dto.setProductsName(items.getProducts().getName());
        return dto;
    }
    private DataMapper(){

    }
}
