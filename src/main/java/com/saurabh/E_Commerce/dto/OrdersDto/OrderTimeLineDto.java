package com.saurabh.E_Commerce.dto.OrdersDto;

import com.saurabh.E_Commerce.models.Orders;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Getter
@Setter
public class OrderTimeLineDto {
    private long OrderStatusHistoryId;

    private long ordersId;

    private String status;

    private String note;
}
