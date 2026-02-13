package com.saurabh.E_Commerce.dto.ReviewDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {

    @NotNull
    private long productId;

    @NotNull
    private long orderId;

    @NotNull
    @Min(1)
    private int rating;

    private String text;

}
