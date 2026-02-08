package com.saurabh.E_Commerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequestDto {

    @NotNull
    private String name;

    private String description;

    private long parentId;

    @NotNull
    private String slug;

    @NotNull
    private boolean isActive;

}
