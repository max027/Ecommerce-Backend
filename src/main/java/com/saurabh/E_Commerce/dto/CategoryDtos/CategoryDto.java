package com.saurabh.E_Commerce.dto.CategoryDtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {
    @NotNull
    private long categoryId;

    @NotNull
    private String name;

    private String description;

    private long parentId;

    @NotNull
    private String slug;

    @NotNull
    private boolean isActive;

    public void setIsActive(boolean value){
        isActive=value;
    }
}
