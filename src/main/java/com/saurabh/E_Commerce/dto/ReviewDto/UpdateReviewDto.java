package com.saurabh.E_Commerce.dto.ReviewDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReviewDto {
    @NotNull
    private int rating;
    @NotBlank
    private String text;
}
