package com.saurabh.E_Commerce.dto.OrdersDto;

import com.saurabh.E_Commerce.models.Orders;
import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.models.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Getter
@Setter
public class ReviewDto {
    private long productsId;
    @Min(value = 1,message = "rating should be between 1 to 5")
    @Max(value = 5,message = "rating should be between 1 to 5")
    private int rating;
    private String comment;
}
