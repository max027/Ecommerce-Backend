package com.saurabh.E_Commerce.dto.ReviewDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
