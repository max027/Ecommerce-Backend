package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.AuthDtos.*;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.InviteToken;
import com.saurabh.E_Commerce.models.Permissions;
import com.saurabh.E_Commerce.models.Roles;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.models.enums.ModuleEnum;
import com.saurabh.E_Commerce.repository.InviteTokenRepository;
import com.saurabh.E_Commerce.repository.PermissionsRepository;
import com.saurabh.E_Commerce.repository.RolesRepository;
import com.saurabh.E_Commerce.repository.UserRepository;
import com.saurabh.E_Commerce.utils.DataMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
@Validated
@Transactional
public class AdminService {

    private final String frontendUrl="http://localhost:8080/api";
    private final InviteTokenRepository inviteTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RolesRepository rolesRepository;
    private final PermissionsRepository permissionsRepository;
    private final EmailService emailService;

    public void inviteAdmin(@NotNull String email){
        createAndSendInvite(email, "ADMIN");
    }

    public void inviteVendor(@NotNull String email){
        createAndSendInvite(email, "VENDOR");
    }

    private Users fetchUsers(long id){
        return userRepository.findById(id).orElseThrow(
                ()->new UsernameNotFoundException("user with id:"+id+" not found")
        );
    }

    private void createAndSendInvite(String email, String rolesEnum) {
        if(userRepository.existsByEmail(email)){
            throw  new ApiError("user already exist", HttpStatus.CONFLICT.value());
        }

        String token= UUID.randomUUID().toString()+UUID.randomUUID();
        InviteToken inviteToken=new InviteToken();
        inviteToken.setToken(token);
        inviteToken.setEmail(email);
        inviteToken.setRoles(rolesEnum);
        inviteToken.setExpiresAt(Instant.now().plusSeconds(43200));

        inviteTokenRepository.save(inviteToken);
        //send email

        String link=frontendUrl+"/accept-invite?token="+token;
        emailService.send(email,"You are invited","Click to join:"+link);
    }

    public void acceptInvite(@NotNull String token, @Valid AcceptInviteRequest request){
        InviteToken inviteToken=inviteTokenRepository.findByToken(token).orElseThrow(
                ()->new ApiError("Invalid Token",HttpStatus.FORBIDDEN.value())
        );

        if (inviteToken.getExpiresAt().isBefore(Instant.now())){
            throw new ApiError("Invite expires",HttpStatus.UNAUTHORIZED.value());
        }

        Users users=new Users();
        users.setEmail(inviteToken.getEmail());
        users.setPassword(encoder.encode(request.getPassword()));
        //fetch role
        Roles roles=rolesRepository.findRolesByName(inviteToken.getRoles()).orElseThrow(
                ()->new ApiError("Given role not found",HttpStatus.NOT_FOUND.value())
        );
        users.setRoles(Set.of(roles));
        users.setFirstName(request.getFirstName());
        users.setLastName(request.getLastName());
        users.setPhone(request.getPhone());

        userRepository.save(users);
        inviteTokenRepository.delete(inviteToken);
    }

    public List<UserDto> getAllAdmin() {
        List<Users>admins=userRepository.findAllAdmins();
        List<UserDto>response=new ArrayList<>();
        for(Users admin :admins){
            UserDto dto=DataMapper.convertToUserDto(admin);
            response.add(dto);
        }

        return response;

    }

    public Page<UserDto> getAllVendors(int page,int pageSize) {
        Pageable pageable=PageRequest.of(page,pageSize);
        return userRepository.findAllVendors(pageable).map(DataMapper::convertToUserDto);
    }

    public void updateAdmin(@NotNull long id,@Valid RegisterRequest request) {
        Users users=fetchUsers(id);
        users.setUserId(id);
        users.setPhone(request.getPhone());
        users.setFirstName(request.getFirstName());
        users.setLastName(request.getLastName());
        users.setEmail(users.getEmail());
        userRepository.save(users);

    }

    public void updateVendors(@NotNull long id,@Valid RegisterRequest request) {
        Users users=fetchUsers(id);

        users.setUserId(id);
        users.setPhone(request.getPhone());
        users.setFirstName(request.getFirstName());
        users.setLastName(request.getLastName());
        users.setEmail(users.getEmail());
        userRepository.save(users);

    }

    public void deleteStaff(@NotNull long id) {
        Users users=fetchUsers(id);
        userRepository.delete(users);
    }

    public void suspendVendors(@NotNull long id) {
        Users users=fetchUsers(id);
        users.setIsEnabled(false);
        userRepository.save(users);
    }

    public void createRoles(@Valid RoleRequest request) {
        Roles roles=rolesRepository.findRolesByName(request.getName()).orElse(null);
        if (roles!=null){
            throw new ApiError("Roles "+request.getName()+" already exists",HttpStatus.CONFLICT.value());
        }

        roles=new Roles();
        roles.setName(request.getName());
        roles.setDescription(request.getDescription());
        Set<Permissions>permissionsSet=new HashSet<>();
        for(String permission:request.getPermissions()){
            Permissions permissions= new Permissions();
            permissions.setName(permission);
            permissions.setDescription(request.getDescription());
            permissions.setModule(ModuleEnum.ROLES);
            permissionsSet.add(permissions);
            permissionsRepository.save(permissions);
        }
        roles.setPermissions(permissionsSet);

        rolesRepository.save(roles);
    }

    public void updateRoles(@Valid RoleRequest request,long id) {
       Roles roles=rolesRepository.findById(id).orElseThrow();
       roles.setRoleId(id);
       roles.setName(request.getName());
       roles.setDescription(request.getDescription());

        Set<Permissions>permissionsSet=new HashSet<>();
        for(String permission:request.getPermissions()){
            Permissions permissions= new Permissions();
            permissions.setName(permission);
            permissions.setDescription(request.getDescription());
            permissions.setModule(ModuleEnum.ROLES);
            permissionsSet.add(permissions);
            permissionsRepository.save(permissions);
        }
        roles.setPermissions(permissionsSet);

        rolesRepository.save(roles);
    }

    public void deleteRoles(@NotNull long id) {
        Roles roles=rolesRepository.findById(id).orElseThrow(()->new ApiError("Roles "+id+" not found",HttpStatus.NOT_FOUND.value()));
        rolesRepository.delete(roles);

    }

    public List<PermissionResponse> getAllPermissions() {
        List<Permissions>permissions=permissionsRepository.findAll();
        List<PermissionResponse>permissionResponses=new ArrayList<>();
        for(Permissions permission:permissions){
            PermissionResponse response=PermissionResponse.builder()
                    .permissionId(permission.getPermissionId())
                    .name(permission.getName())
                    .description(permission.getDescription())
                    .module(permission.getModule())
                    .build();
            permissionResponses.add(response);
        }
        return permissionResponses;
    }

    public Page<UserDto> getAllUsers(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        return userRepository.findAll(pageable).map(DataMapper::convertToUserDto);
    }

    public UserDto getUsersById(long id) {
        Users users=fetchUsers(id);
        return DataMapper.convertToUserDto(users);
    }

    public void deleteUsers(@NotNull long id) {
        Users users=fetchUsers(id);
        userRepository.delete(users);
    }

    public void suspendUsers(@NotNull long id) {
        Users users=fetchUsers(id);
        users.setIsEnabled(false);
        userRepository.save(users);
    }

    public void assignRoles(long id, AssignRolesDto request) {
        Users users=fetchUsers(id);
        Set<Roles>roles=new HashSet<>();
        for (long i:request.getRolesId()){
            Roles assignRole=rolesRepository.findById(i).orElseThrow();
            roles.add(assignRole);
        }
        users.setRoles(roles);
        userRepository.save(users);
    }
}
