package com.saurabh.E_Commerce.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private long id;
    private String email;
    private String accessToken;
    private String refreshToken;
}
