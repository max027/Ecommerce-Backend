package com.saurabh.E_Commerce.dto.AuthDtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest{
    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @NotNull(message = "permission are required")
    private String[] permissions;

}
