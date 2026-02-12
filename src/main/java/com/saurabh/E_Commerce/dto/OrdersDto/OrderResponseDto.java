package com.saurabh.E_Commerce.dto.OrdersDto;

import com.saurabh.E_Commerce.dto.AddressDto;
import com.saurabh.E_Commerce.models.Address;
import com.saurabh.E_Commerce.models.OrderItems;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.models.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDto {
    private long ordersId;

    private String orderNumber;

    private StatusEnum status;

    private BigDecimal subtotal;

    private BigDecimal discountAmount;

    private BigDecimal taxAmount;

    private BigDecimal shippingAmount;

    private BigDecimal totalAmount;

    private AddressDto shippingAddress;

    private AddressDto billingAddress;

    private List<OrderItemsResponseDto> orderItems;

}
