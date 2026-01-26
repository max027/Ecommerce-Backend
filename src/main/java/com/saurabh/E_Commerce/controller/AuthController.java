package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.LoginRequest;
import com.saurabh.E_Commerce.dto.LoginResponse;
import com.saurabh.E_Commerce.dto.RegisterRequest;
import com.saurabh.E_Commerce.dto.RegisterResponse;
import com.saurabh.E_Commerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse>signup(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>signup(@RequestBody LoginRequest request){
        return ResponseEntity.ok(service.login(request));
    }


}
