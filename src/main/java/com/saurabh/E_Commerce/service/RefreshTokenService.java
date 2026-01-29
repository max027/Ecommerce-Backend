package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.RefreshToken;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.repository.RefreshTokenRepository;
import com.saurabh.E_Commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${security.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public RefreshToken generateToken(Users users){
        RefreshToken token=new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUsers(users);
        token.setExpiresAt(Instant.now().plus(2, ChronoUnit.DAYS));
        token.setRevoked(false);

        return refreshTokenRepository.save(token);
    }

    public RefreshToken verify(String token){
        RefreshToken refreshToken=refreshTokenRepository.findByToken(token).orElseThrow(
                ()->new ApiError("invalid refresh token",HttpStatus.UNAUTHORIZED.value())
        );

        if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(Instant.now())){
            throw new ApiError("refresh token expired",HttpStatus.UNAUTHORIZED.value());
        }

        return refreshToken;
    }

    @Transactional
    public void revoke(RefreshToken token){
        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }

    @Transactional
    public void logout(RefreshToken token){
       refreshTokenRepository.delete(token);
    }

}
