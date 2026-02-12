package com.saurabh.E_Commerce.dto.OrdersDto;

import com.saurabh.E_Commerce.models.Orders;
import com.saurabh.E_Commerce.models.Products;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemsResponseDto {
    private long orderItemsId;

    private String productName;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

}
