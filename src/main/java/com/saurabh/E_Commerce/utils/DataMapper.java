package com.saurabh.E_Commerce.utils;

import com.saurabh.E_Commerce.dto.AddressDto;
import com.saurabh.E_Commerce.dto.AuthDtos.UserDto;
import com.saurabh.E_Commerce.dto.CartDtos.CartItemsDto;
import com.saurabh.E_Commerce.dto.CategoryDtos.CategoryDto;
import com.saurabh.E_Commerce.dto.Coupon.CouponsResponseDto;
import com.saurabh.E_Commerce.dto.OrdersDto.OrderItemsResponseDto;
import com.saurabh.E_Commerce.dto.OrdersDto.OrderResponseDto;
import com.saurabh.E_Commerce.dto.OrdersDto.OrderTimeLineDto;
import com.saurabh.E_Commerce.dto.ProductDtos.ProductDto;
import com.saurabh.E_Commerce.dto.ReviewDto.ReviewsDto;
import com.saurabh.E_Commerce.dto.Vendors.VendorsDto;
import com.saurabh.E_Commerce.dto.inventory.TransactionDto;
import com.saurabh.E_Commerce.models.*;

import java.util.ArrayList;
import java.util.List;

public class DataMapper {
    public static UserDto convertToUserDto(Users users){
        return UserDto.builder().id(users.getUserId())
                .email(users.getEmail())
                .first_name(users.getFirstName())
                .last_name(users.getLastName())
                .phone(users.getPhone())
                .build();
    }

    public static VendorsDto convertToUserDto(Vendors vendors){
        VendorsDto dto=new VendorsDto();
        dto.setUserId(vendors.getUsers().getUserId());
        dto.setEmail(vendors.getBusinessEmail());
        dto.setBusinessName(vendors.getBusinessName());
        dto.setFirstName(vendors.getUsers().getFirstName());
        dto.setLastName(vendors.getUsers().getLastName());
        dto.setGstNumber(vendors.getGstNumber());
        dto.setPhone(vendors.getUsers().getPhone());
        return dto;
    }
    public static ProductDto convertToProductDto(Products products){
        return ProductDto.builder()
                .id(products.getProductId())
                .vendorId(products.getVendors().getUsers().getUserId())
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
    public static CouponsResponseDto convertToCoupon(Coupons coupons){
        return CouponsResponseDto.builder()
                .couponsId(coupons.getCouponsId())
                .code(coupons.getCode())
                .description(coupons.getDescription())
                .usageCoupon(coupons.getUsageCoupon())
                .usageLimit(coupons.getUsageLimit())
                .discountType(coupons.getDiscountType())
                .discountValue(coupons.getDiscountValue())
                .minOrderValue(coupons.getMinOrderValue())
                .maxDiscountValue(coupons.getMaxDiscountValue())
                .validFrom(coupons.getValidFrom())
                .validUntil(coupons.getValidUntil())
                .isActive(coupons.isActive())
                .build();
    }
    public static AddressDto convertToAddressResponse(Address address){
        AddressDto addressDto=new AddressDto();
        addressDto.setAddressType(address.getAddressType());
        addressDto.setAddressLine1(address.getAddressLine1());
        addressDto.setAddressLine2(address.getAddressLine2());
        addressDto.setState(address.getState());
        addressDto.setCity(address.getCity());
        addressDto.setCountry(address.getCountry());
        addressDto.setPostalCode(address.getPostalCode());
        return addressDto;
    }
    public static OrderResponseDto convertToOrderResponse(Orders orders){
        OrderResponseDto dto=new OrderResponseDto();
        List<OrderItemsResponseDto>oidto=new ArrayList<>();
        dto.setOrdersId(orders.getOrdersId());
        dto.setOrderNumber(orders.getOrderNumber());
        dto.setBillingAddress(convertToAddressResponse(orders.getBillingAddress()));
        dto.setShippingAddress(convertToAddressResponse(orders.getShippingAddress()));
        dto.setStatus(orders.getStatus());
        dto.setSubtotal(orders.getSubtotal());
        dto.setDiscountAmount(orders.getDiscountAmount());
        dto.setTaxAmount(orders.getTaxAmount());
        dto.setTotalAmount(orders.getTotalAmount());
        dto.setShippingAmount(orders.getShippingAmount());

        for(OrderItems items:orders.getOrderItems()){
            OrderItemsResponseDto dto1=new OrderItemsResponseDto();
            dto1.setOrderItemsId(items.getOrderItemsId());
            dto1.setQuantity(items.getQuantity());
            dto1.setProductName(items.getProductName());
            dto1.setUnitPrice(items.getUnitPrice());
            dto1.setTotalPrice(items.getTotalPrice());
            oidto.add(dto1);
        }
        dto.setOrderItems(oidto);
        return dto;
    }

    public static OrderTimeLineDto convertToOrderTimeline(OrderStatusHistory history){
        OrderTimeLineDto dto=new OrderTimeLineDto();
        dto.setNote(history.getNote());
        dto.setOrderStatusHistoryId(history.getOrderStatusHistoryId());
        dto.setStatus(history.getStatus());
        dto.setOrdersId(history.getOrders().getOrdersId());
        return dto;
    }

    public static TransactionDto convertToTransactionDto(InventoryTransaction inventoryTransaction){
        TransactionDto dto=new TransactionDto();
        dto.setInventoryTransactionId(inventoryTransaction.getInventoryTransactionId());
        dto.setNotes(inventoryTransaction.getNotes());
        dto.setProductId(inventoryTransaction.getProducts().getProductId());
        dto.setQuantityChange(inventoryTransaction.getQuantityChange());
        dto.setQuantityBefore(inventoryTransaction.getQuantityBefore());
        dto.setQuantityAfter(inventoryTransaction.getQuantityAfter());
        dto.setReferenceId(inventoryTransaction.getReferenceId());
        dto.setTransactionType(inventoryTransaction.getTransactionType().toString());
        dto.setReferenceType(inventoryTransaction.getReferenceType().toString());
        return dto;
    }

    private DataMapper(){

    }
}
