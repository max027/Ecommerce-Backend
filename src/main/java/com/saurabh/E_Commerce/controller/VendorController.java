package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.AuthDtos.UserDto;
import com.saurabh.E_Commerce.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vendors")
public class VendorController {
    private final VendorService vendorService;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<UserDto>getProfile(){
        return ResponseEntity.ok(vendorService.getProfile());
    }
}
