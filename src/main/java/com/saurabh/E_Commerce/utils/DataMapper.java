package com.saurabh.E_Commerce.utils;

import com.saurabh.E_Commerce.dto.ProductDto;
import com.saurabh.E_Commerce.dto.ReviewsDto;
import com.saurabh.E_Commerce.dto.UserDto;
import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.models.Review;
import com.saurabh.E_Commerce.models.Users;

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
    private DataMapper(){

    }
}
