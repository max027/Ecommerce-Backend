package com.saurabh.E_Commerce.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {
    private String oldPassword;
    private String newPassword;
}
