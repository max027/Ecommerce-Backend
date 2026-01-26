package com.saurabh.E_Commerce.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginResponse {
    private long id;
    private String email;
    private String jwt;
}
