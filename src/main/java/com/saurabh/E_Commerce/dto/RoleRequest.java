package com.saurabh.E_Commerce.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest{
    @NotNull(message = "name is required")
    private String name;

    private String description;

    @NotNull(message = "permission are required")
    private String[] permissions;

}
