package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.*;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.*;
import com.saurabh.E_Commerce.models.enums.RolesEnum;
import com.saurabh.E_Commerce.repository.AddressRepository;
import com.saurabh.E_Commerce.repository.ResetTokenRepository;
import com.saurabh.E_Commerce.repository.RolesRepository;
import com.saurabh.E_Commerce.repository.UserRepository;
import com.saurabh.E_Commerce.security.AuthUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtils authUtils;
    private final RefreshTokenService refreshTokenService;
    private final String frontendUrl="http://localhost:8080/api/auth";
    private final ResetTokenRepository resetTokenRepository;
    private final EmailService emailService;
    private final AddressRepository addressRepository;

    public RegisterResponse signup(CustomerRequest request){
        Users users=userRepository.findByEmail(request.getEmail()).orElse(null);
        if (users!=null){
            throw new ApiError("User already exist", HttpStatus.CONFLICT.value());
        }
        users=new Users();
        users.setEmail(request.getEmail());
        users.setFirstName(request.getFirstName());
        users.setLastName(request.getLastName());
        users.setPhone(request.getPhone());
        users.setPassword(encoder.encode(request.getPassword()));

        Roles userRoles=rolesRepository.findRolesByName("CUSTOMER").orElseThrow(
                ()->new ApiError("Default role for user not found",HttpStatus.NOT_FOUND.value())
        );

        users.setRoles(Set.of(userRoles));
        userRepository.save(users);

        Address address=new Address();
        address.setState(request.getAddress().getState());
        address.setAddressType(request.getAddress().getAddressType());
        address.setCity(request.getAddress().getCity());
        address.setAddressLine2(request.getAddress().getAddressLine2());
        address.setAddressLine1(request.getAddress().getAddressLine1());
        address.setPostalCode(request.getAddress().getPostalCode());
        address.setUsers(users);
        address.setCountry(request.getAddress().getCountry());
        addressRepository.save(address);

        users.setAddresses(Set.of(address));

        return new RegisterResponse(
                users.getUserId(),
                users.getEmail()
        );
    }
    public RegisterResponse signAdmin(RegisterRequest request){
        Users users=userRepository.findByEmail(request.getEmail()).orElse(null);

        if (users!=null){
            throw new ApiError("User already exist", HttpStatus.CONFLICT.value());
        }
        users=new Users();
        users.setEmail(request.getEmail());
        users.setFirstName(request.getFirstName());
        users.setLastName(request.getLastName());
        users.setPhone(request.getPhone());
        users.setPassword(encoder.encode(request.getPassword()));

        Roles userRoles=rolesRepository.findRolesByName("ADMIN").orElseThrow(
                ()->new ApiError("Default role for user not found",HttpStatus.NOT_FOUND.value())
        );

        users.setRoles(Set.of(userRoles));
        userRepository.save(users);

        return new RegisterResponse(
                users.getUserId(),
                users.getEmail()
        );
    }

    public Map<String,String> login(LoginRequest request) {
        Authentication authUser=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Users users=(Users) authUser.getPrincipal();

        users.setLastLogin(Instant.now());
        userRepository.save(users);

        String token= authUtils.generateToken(users);
        RefreshToken refreshToken=refreshTokenService.generateToken(users);

        Map<String,String>map=new HashMap<>();
        map.put("accessToken",token);
        map.put("refreshToken",refreshToken.getToken());

        return map;
    }
    public Map<String,String> refresh(String refreshToken){
        RefreshToken token=refreshTokenService.verify(refreshToken);
        refreshTokenService.revoke(token);

        Users users=token.getUsers();

        String newAccessToken= authUtils.generateToken(users);
        RefreshToken newRefreshToken=refreshTokenService.generateToken(users);


        Map<String,String>map=new HashMap<>();
        map.put("accessToken",newAccessToken);
        map.put("refreshToken",newRefreshToken.getToken());

        return map;
    }
    public void logout(String token){
        RefreshToken refreshToken=refreshTokenService.verify(token);
        refreshTokenService.logout(refreshToken);
    }

    public void forgetPassword(String email) {
        Users users=userRepository.findByEmail(email).orElseThrow(
                ()->new UsernameNotFoundException("user:"+email+" not found")
        );

        String token= UUID.randomUUID().toString()+UUID.randomUUID();
        ResetToken resetToken=new ResetToken();
        resetToken.setUsers(users);
        resetToken.setToken(token);
        resetToken.setExpiresAt(Instant.now().plusSeconds(3000));

        resetTokenRepository.save(resetToken);

        String link=frontendUrl+"/reset-password?token="+token;
        emailService.send(email,"password reset","Click to reset-password:"+link);
    }
    public void resetPassword(ResetPasswordDto resetPasswordDto, String token){
        ResetToken resetToken=resetTokenRepository.findByToken(token).orElseThrow(
                ()->new ApiError("no token found",HttpStatus.FORBIDDEN.value())
        );
        if (resetToken.isExpired()){
           throw new ApiError("token expired",HttpStatus.UNAUTHORIZED.value());
        }

        Users users=resetToken.getUsers();

        if (!encoder.matches(resetPasswordDto.getOldPassword(), users.getPassword())){
           throw new ApiError("password dont match",HttpStatus.FORBIDDEN.value());
        }

        users.setPassword(encoder.encode(resetPasswordDto.getNewPassword()));
        userRepository.save(users);

        resetTokenRepository.delete(resetToken);
    }

    public void sendVerification(){
        Users users=authUtils.getCurrentUser();
        if (users.isVerified()){
            throw new ApiError("user is already verified",HttpStatus.CONFLICT.value());
        }

        String token= authUtils.generateEmailVerificationToken(users.getEmail(),users.getUserId());
        String url=frontendUrl+"/verify?token="+token;
        emailService.send(users.getEmail(),"Email Verification","Click to Verify email"+url);
    }

    public void verifyEmail(String token) {
        authUtils.handelEmailVerification(token);
    }

    public UserDto getUserInformation() {
        Users users=authUtils.getCurrentUser();
        return UserDto.builder()
                .id(users.getUserId())
                .email(users.getEmail())
                .first_name(users.getFirstName())
                .last_name(users.getLastName())
                .phone(users.getPhone())
                .build();

    }
}

