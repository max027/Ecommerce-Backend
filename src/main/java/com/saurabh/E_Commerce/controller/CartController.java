package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.CartDtos.CartDto;
import com.saurabh.E_Commerce.dto.CartDtos.CartRequestDto;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/")
    public ResponseEntity<CartDto>getCartItems(){
        return ResponseEntity.ok(cartService.getUserCart());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/item")
    public ResponseEntity<?>addToCart(@Valid @RequestBody CartRequestDto request){
        cartService.addToCart(request);
        return ResponseEntity.ok("product added to cart");
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/item/{itemId}")
    public ResponseEntity<?>updateCart(@RequestBody Map<String,Integer> request,long itemId){
        if (request.get("quantity")==null){
            throw new ApiError("quantity is required", HttpStatus.FORBIDDEN.value());
        }
        cartService.updateCart(request.get("quantity"),itemId);
        return ResponseEntity.ok("cart updated");
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<?>removeFromCart(long itemId){
        cartService.removeFromCart(itemId);
        return ResponseEntity.ok("item removed from cart");
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/")
    public ResponseEntity<?>clearCart(){
        cartService.clearCart();
        return ResponseEntity.ok("cart cleared");
    }

}
