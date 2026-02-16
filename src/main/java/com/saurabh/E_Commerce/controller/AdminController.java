package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.AuthDtos.*;
import com.saurabh.E_Commerce.service.AdminService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<Page<UserDto>> getAllVendors(
            @RequestParam int page,
            @RequestParam int pageSize
    ){
        return ResponseEntity.ok(adminService.getAllVendors(page,pageSize));
    }

    @PostMapping("/invite")
    public ResponseEntity<RegisterResponse> adminSignup(@Valid @RequestBody InviteRequest request){
        adminService.inviteAdmin(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/vendor/invite")
    public ResponseEntity<RegisterResponse> vendorSignup(@Valid @RequestBody InviteRequest request){
        adminService.inviteVendor(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PermitAll
    @PostMapping("/accept-invite")
    public ResponseEntity<?>acceptInvite(@RequestParam String token, @Valid @RequestBody AcceptInviteRequest request){
       adminService.acceptInvite(token,request);
       return ResponseEntity.ok("Invite accepted");
    }
    @PermitAll
    @PostMapping("/vendors/accept-invite")
    public ResponseEntity<?>VendorAcceptInvite(@RequestParam String token, @Valid @RequestBody VendorAcceptInviteDto request){
        adminService.vendorAcceptInvite(token,request);
        return ResponseEntity.ok("Invite accepted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateAdmin(@PathVariable long id, @Valid @RequestBody RegisterRequest request){
        adminService.updateAdmin(id,request);
       return ResponseEntity.ok().build();
    }

    @PutMapping("/vendors/{id}")
    public ResponseEntity<?>updateVendors(@PathVariable long id, @Valid @RequestBody RegisterRequest request){
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
    public ResponseEntity<?> createRoles(@Valid @RequestBody RoleRequest request){
        adminService.createRoles(request);
       return ResponseEntity.ok("roles created");
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> updateRoles(@Valid @RequestBody RoleRequest request,@PathVariable long id){
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

    @GetMapping("/users")
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam int page,
            @RequestParam int pageSize
    ){
        return ResponseEntity.ok(adminService.getAllUsers(page, pageSize));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUsersById(@PathVariable long id){
        return ResponseEntity.ok(adminService.getUsersById(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUsers(@PathVariable long id){
        adminService.deleteUsers(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/suspend")
    public ResponseEntity<?> suspendUser(@PathVariable long id){
        adminService.suspendUsers(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/roles")
    public ResponseEntity<?> assignRoles(@PathVariable long id,@Valid @RequestBody AssignRolesDto request){
        adminService.assignRoles(id,request);
        return ResponseEntity.ok("roles assigned to userId:"+id);
    }
}
