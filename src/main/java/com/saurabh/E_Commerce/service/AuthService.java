package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.LoginRequest;
import com.saurabh.E_Commerce.dto.LoginResponse;
import com.saurabh.E_Commerce.dto.RegisterRequest;
import com.saurabh.E_Commerce.dto.RegisterResponse;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.repository.UserRepository;
import com.saurabh.E_Commerce.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtils authUtils;

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

        userRepository.save(users);

        return new RegisterResponse(
                users.getUserId(),
                users.getEmail()
        );
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authUser=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Users users=(Users) authUser.getPrincipal();
        LoginResponse response=new LoginResponse();

        String token= authUtils.generateToken(users);

        response.setId(users.getUserId());
        response.setJwt(token);
        response.setEmail(users.getEmail());

        return response;
    }
}
