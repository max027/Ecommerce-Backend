package com.saurabh.E_Commerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDto {
    @NotNull
    private String url;
}
