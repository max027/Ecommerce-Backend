package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.AcceptInviteRequest;
import com.saurabh.E_Commerce.dto.InviteRequest;
import com.saurabh.E_Commerce.dto.RegisterResponse;
import com.saurabh.E_Commerce.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/invite")
    public ResponseEntity<RegisterResponse> adminSignup(@RequestBody InviteRequest request){
        adminService.inviteAdmin(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/vendor/register")
    public ResponseEntity<RegisterResponse> vendorSignup(@RequestBody InviteRequest request){
        adminService.inviteVendor(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept-invite")
    public ResponseEntity<?>acceptInvite(@RequestParam String token, @RequestBody AcceptInviteRequest request){
       adminService.acceptInvite(token,request);
       return ResponseEntity.ok("Invite accepted");
    }
}
