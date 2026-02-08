package com.saurabh.E_Commerce.utils;

import com.saurabh.E_Commerce.dto.CategoryDto;
import com.saurabh.E_Commerce.dto.ProductDto;
import com.saurabh.E_Commerce.dto.ReviewsDto;
import com.saurabh.E_Commerce.dto.UserDto;
import com.saurabh.E_Commerce.models.Categories;
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
    private DataMapper(){

    }
}
