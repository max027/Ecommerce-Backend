package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.AddressRequest;
import com.saurabh.E_Commerce.dto.CustomerResponse;
import com.saurabh.E_Commerce.dto.UserDto;
import com.saurabh.E_Commerce.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
@RequestMapping("/api/users")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/profile")
    public ResponseEntity<CustomerResponse> getProfile(){
        return ResponseEntity.ok(customerService.getProfile());
    }
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UserDto request){
        customerService.updateProfile(request);
        return ResponseEntity.ok("profile updated");
    }

    @GetMapping("/address")
    public ResponseEntity<Set<AddressRequest>> getAddress(){
        return ResponseEntity.ok(customerService.getAddress());
    }

    @PostMapping("/address")
    public ResponseEntity<?> addAddress(@Valid @RequestBody AddressRequest request){
        customerService.addAddress(request);
        return ResponseEntity.ok("new address added");
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<?> updateAddress(@Valid @RequestBody AddressRequest request,@PathVariable  Long id){
        customerService.updateAddress(request,id);
        return ResponseEntity.ok("address updated");
    }
    @GetMapping("/address/{id}")
    public ResponseEntity<AddressRequest> getSpecificAddress(@PathVariable  Long id){
        return ResponseEntity.ok(customerService.getSpecificAddress(id));
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable  Long id){
        customerService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/address/{id}/set-default")
    public ResponseEntity<?> setDefault(@PathVariable  Long id){
        customerService.setDefault(id);
        return ResponseEntity.noContent().build();
    }
}
