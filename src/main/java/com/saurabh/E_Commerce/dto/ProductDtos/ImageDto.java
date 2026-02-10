package com.saurabh.E_Commerce.dto.ProductDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDto {
    @NotBlank(message = "image url is required")
    private String url;
}
