package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.*;
import com.saurabh.E_Commerce.models.Permissions;
import com.saurabh.E_Commerce.service.AdminService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllAdmin(){
        return ResponseEntity.ok(adminService.getAllAdmin());
    }

    @GetMapping("/vendors")
    public ResponseEntity<List<UserDto>> getAllVendors(){
        return ResponseEntity.ok(adminService.getAllVendors());
    }

    @PostMapping("/invite")
    public ResponseEntity<RegisterResponse> adminSignup(@RequestBody InviteRequest request){
        adminService.inviteAdmin(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/vendor/register")
    public ResponseEntity<RegisterResponse> vendorSignup(@RequestBody InviteRequest request){
        adminService.inviteVendor(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PermitAll
    @PostMapping("/accept-invite")
    public ResponseEntity<?>acceptInvite(@RequestParam String token, @RequestBody AcceptInviteRequest request){
       adminService.acceptInvite(token,request);
       return ResponseEntity.ok("Invite accepted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateAdmin(@PathVariable long id, @RequestBody RegisterRequest request){
        adminService.updateAdmin(id,request);
       return ResponseEntity.ok().build();
    }

    @PutMapping("/vendors/{id}")
    public ResponseEntity<?>updateVendors(@PathVariable long id, @RequestBody RegisterRequest request){
        adminService.updateVendors(id,request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteStaff(@PathVariable long id){
        adminService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/vendors/{id}/suspend")
    public ResponseEntity<?> suspendVendors(@PathVariable long id){
        adminService.suspendVendors(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/roles")
    public ResponseEntity<?> createRoles(@RequestBody RoleRequest request){
        adminService.createRoles(request);
       return ResponseEntity.ok("roles created");
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> updateRoles(@RequestBody RoleRequest request,@PathVariable long id){
        adminService.updateRoles(request,id);
        return ResponseEntity.ok("roles updated");
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteRoles(@PathVariable long id){
        adminService.deleteRoles(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionResponse>> getPermissons(){
        return ResponseEntity.ok(adminService.getAllPermissions());
    }
}
