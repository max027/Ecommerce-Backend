package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.*;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.RefreshToken;
import com.saurabh.E_Commerce.models.Roles;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.models.enums.RolesEnum;
import com.saurabh.E_Commerce.repository.RolesRepository;
import com.saurabh.E_Commerce.repository.UserRepository;
import com.saurabh.E_Commerce.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtils authUtils;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public RegisterResponse signup(RegisterRequest request){
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

        Roles userRoles=rolesRepository.findRolesByName(RolesEnum.CUSTOMER).orElseThrow(
                ()->new ApiError("Default role for user not found",HttpStatus.NOT_FOUND.value())
        );

        users.setRoles(Set.of(userRoles));
        userRepository.save(users);

        return new RegisterResponse(
                users.getUserId(),
                users.getEmail()
        );
    }

    public LoginTokens login(LoginRequest request) {
        Authentication authUser=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Users users=(Users) authUser.getPrincipal();

        String token= authUtils.generateToken(users);
        RefreshToken refreshToken=refreshTokenService.generateToken(users);

        LoginTokens tokens=new LoginTokens();
        tokens.setAccessToken(token);
        tokens.setRefreshToken(refreshToken.getToken());

        return tokens;
    }
    public LoginTokens refresh(String refreshToken){
        RefreshToken token=refreshTokenService.verify(refreshToken);
        refreshTokenService.revoke(token);

        Users users=token.getUsers();

        String newAccessToken= authUtils.generateToken(users);
        RefreshToken newRefreshToken=refreshTokenService.generateToken(users);

        LoginTokens tokens=new LoginTokens();
        tokens.setAccessToken(newAccessToken);
        tokens.setRefreshToken(newRefreshToken.getToken());

        return tokens;

    }
    public void logout(String token){
        RefreshToken refreshToken=refreshTokenService.verify(token);
        refreshTokenService.logout(refreshToken);
    }

    public void forgetPassword(Map<String, String> newPassword) {
        Users users=authUtils.getCurrentUser();


    }
}

