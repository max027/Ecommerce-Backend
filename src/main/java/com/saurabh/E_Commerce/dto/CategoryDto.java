package com.saurabh.E_Commerce.dto;

import com.saurabh.E_Commerce.models.Categories;
import com.saurabh.E_Commerce.models.Products;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

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
