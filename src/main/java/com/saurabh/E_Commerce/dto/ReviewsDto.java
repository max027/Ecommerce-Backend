package com.saurabh.E_Commerce.dto;

import com.saurabh.E_Commerce.models.Orders;
import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.models.Users;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Getter
@Setter
@Builder
public class ReviewsDto {

    private long reviewId;

    private String username;

    private int rating;

    private String text;

    private boolean isVerifiedPurchase;

}
