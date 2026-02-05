package com.saurabh.E_Commerce.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {
    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;
}
