package com.saurabh.E_Commerce.dto.CartDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequestDto {

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    private long parentId;

    @NotBlank(message = "slug is required")
    private String slug;

    @NotNull
    private boolean isActive;

}
