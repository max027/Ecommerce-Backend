package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.*;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    private String extractRefreshToken(HttpServletRequest request){
        String token=null;
        for(Cookie cookie: request.getCookies()){
            if ("refreshToken".equals(cookie.getName())){
                token=cookie.getValue();
            }
        }
        System.out.println("hello");
        System.out.println(token);
        if (token==null){
            throw new ApiError("No refreshToken found", HttpStatus.FORBIDDEN.value());
        }
        return token;
    }
    private String extractAccessToken(HttpServletRequest request){
        String token=null;
        for(Cookie cookie: request.getCookies()){
            if ("accessToken".equals(cookie.getName())){
                token=cookie.getValue();
            }
        }
        System.out.println("hello");
        System.out.println(token);
        if (token==null){
            throw new ApiError("No refreshToken found", HttpStatus.FORBIDDEN.value());
        }
        return token;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse>signup(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.signup(request));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<RegisterResponse>signupAdmin(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.signAdmin(request));
    }
    private ResponseEntity<?>buildResponse(ResponseCookie accessCookie,ResponseCookie refreshCookie){
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshCookie.toString())
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest request){
        LoginTokens cookies= service.login(request);
        ResponseCookie accessCookie= ResponseCookie.from("accessToken",cookies.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Lax")
                .build();

        ResponseCookie refreshCookie= ResponseCookie.from("refreshToken",cookies.getRefreshToken())
                .secure(true)
                .httpOnly(true)
                .maxAge(Duration.ofDays(2))
                .sameSite("Lax")
                .build();

        return buildResponse(accessCookie,refreshCookie);
    }
    @PostMapping("/refresh")
    public ResponseEntity<?>refresh(HttpServletRequest request){
        String token=extractRefreshToken(request);
        LoginTokens cookies= service.refresh(token);

        ResponseCookie accessCookie= ResponseCookie.from("accessToken",cookies.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Lax")
                .build();

        ResponseCookie refreshCookie= ResponseCookie.from("refreshToken",cookies.getRefreshToken())
                .secure(true)
                .httpOnly(true)
                .maxAge(Duration.ofDays(2))
                .sameSite("Lax")
                .build();


        return buildResponse(accessCookie,refreshCookie);
    }

    @PostMapping("/logout")
    public ResponseEntity<?>logout(HttpServletRequest request){
        String refreshToken=extractRefreshToken(request);
        String accessToken=extractAccessToken(request);

        ResponseCookie accessCookie= ResponseCookie.from("accessToken",accessToken)
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .sameSite("Lax")
                .build();

        ResponseCookie refreshCookie= ResponseCookie.from("refreshToken",refreshToken)
                .secure(true)
                .httpOnly(true)
                .maxAge(0)
                .sameSite("Lax")
                .build();
        service.logout(refreshToken);

        return buildResponse(accessCookie,refreshCookie);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody Map<String,String>newPassword ){
        service.forgetPassword(newPassword);
        return ResponseEntity.ok("Password Changed");
    }

}
