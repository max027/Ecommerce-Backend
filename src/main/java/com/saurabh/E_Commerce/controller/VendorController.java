package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.AuthDtos.UserDto;
import com.saurabh.E_Commerce.dto.OrdersDto.OrderResponseDto;
import com.saurabh.E_Commerce.dto.ProductDtos.ProductDto;
import com.saurabh.E_Commerce.dto.ProductDtos.ProductRequestDto;
import com.saurabh.E_Commerce.dto.Vendors.UpdateVendorDto;
import com.saurabh.E_Commerce.dto.Vendors.VendorsDto;
import com.saurabh.E_Commerce.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vendors")
public class VendorController {
    private final VendorService vendorService;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<VendorsDto>getProfile(){
        return ResponseEntity.ok(vendorService.getProfile());
    }
    @PutMapping("/profile")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?>updateProfile(@RequestBody UpdateVendorDto request){
        vendorService.updateVendor(request);
        return ResponseEntity.ok("vendor updated");
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('VENDOR') or hasAuthority('VIEW_PRODUCT')")
    public ResponseEntity<Page<ProductDto>>getAllProducts(@RequestParam int page , @RequestParam int limit){
        return ResponseEntity.ok(vendorService.getAllProducts(page,limit));
    }


    @GetMapping("/products/{id}")
    @PreAuthorize("hasRole('VENDOR') or hasAuthority('VIEW_PRODUCT')")
    public ResponseEntity<ProductDto>getProducts(@PathVariable long id){
        return ResponseEntity.ok(vendorService.getProducts(id));
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('VENDOR') or hasAuthority('CREATE_PRODUCT')")
    public ResponseEntity<?>createProduct(@Valid @RequestBody ProductRequestDto request ){
        vendorService.createProduct(request);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/products/{id}")
    @PreAuthorize("hasRole('VENDOR') or hasAuthority('UPDATE_PRODUCT')")
    public ResponseEntity<?>updateProducts(@PathVariable long id, @Valid @RequestBody ProductRequestDto request){
        vendorService.updateProduct(id,request);
        return ResponseEntity.ok("product updated");
    }

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasRole('VENDOR') or hasAuthority('DELETE_PRODUCT')")
    public ResponseEntity<?>deleteProduct(@PathVariable long id){
        vendorService.deleteProduct(id);
        return ResponseEntity.ok("product deleted");
    }

    @GetMapping("/products/orders")
    @PreAuthorize("hasRole('VENDOR') or hasAuthority('VIEW_ORDERS')")
    public ResponseEntity<List<OrderResponseDto>>viewAllOrders(){
        return ResponseEntity.ok(vendorService.viewAllOrders());
    }

    @GetMapping("/products/orders/{id}")
    @PreAuthorize("hasRole('VENDOR') or hasAuthority('VIEW_ORDERS')")
    public ResponseEntity<OrderResponseDto>viewOrders(@PathVariable long id){
        return ResponseEntity.ok(vendorService.viewOrders(id));
    }
}
