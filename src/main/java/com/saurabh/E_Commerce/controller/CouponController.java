package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.Coupon.CouponRequestDto;
import com.saurabh.E_Commerce.dto.Coupon.CouponsResponseDto;
import com.saurabh.E_Commerce.dto.Coupon.ValidateCouponDto;
import com.saurabh.E_Commerce.dto.Coupon.ValidateCouponResponse;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/")
    public ResponseEntity<ValidateCouponResponse>validate(@Valid @RequestBody ValidateCouponDto request){
       return ResponseEntity.ok(couponService.validate(request));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CouponsResponseDto>>getAllCoupons(@RequestParam int page,@RequestParam int limit){
        return ResponseEntity.ok(couponService.getAllCoupons(page,limit));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CouponsResponseDto>getById(@PathVariable long id){
        return ResponseEntity.ok(couponService.getById(id));
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCoupon(@RequestBody CouponRequestDto request){
        couponService.createCoupon(request);
        return ResponseEntity.ok("coupon created");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCoupon(@RequestBody CouponRequestDto request,@PathVariable long id){
        couponService.updateCoupon(request,id);
        return ResponseEntity.ok("coupon updated");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCoupon(@PathVariable long id){
        couponService.deleteCoupon(id);
        return ResponseEntity.ok("coupon deleted");
    }
    @PutMapping("/{id}/toggle")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeActiveStatus(@PathVariable long id, @RequestBody Map<String,String>request){
        String toggle=request.get("toggle");
        if (toggle==null){
            throw new ApiError("toggle is required", HttpStatus.FORBIDDEN.value());
        }
        couponService.changeStatus(toggle,id);
        return ResponseEntity.ok("coupon deleted");
    }

}

