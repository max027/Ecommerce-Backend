package com.saurabh.E_Commerce.security;

import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Component
@RequiredArgsConstructor
public class AuthUtils {
   private final UserRepository userRepository;
   @Value("${security.jwt.secret-key}")
   private String secretKey;

   @Value("${security.jwt.expiration-time}")
   private long expiration;

   private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
   }

   public boolean isTokenValid(String token){
      try{
         Claims claims=extractClaims(token);
         Instant expirationTime=claims.getExpiration().toInstant();
         if (expirationTime.isBefore(Instant.now())){
            return false;
         }
         return true;
      }catch (JwtException  | IllegalArgumentException ex){
         return false;
      }
   }

   public String generateEmailVerificationToken(String email,long userId){
      Users users=userRepository.findById(userId).orElseThrow(
              ()->new ApiError("User not found",HttpStatus.NOT_FOUND.value())
      );
      return Jwts.builder()
              .subject(String.valueOf(users.getUserId()))
              .claim("email",users.getEmail())
              .issuedAt(new Date())
              .expiration(Date.from(Instant.now().plus(Duration.ofMinutes(15))))
              .signWith(getSecretKey())
              .compact();
   }
   public String generateToken(Users users){
      Map<String,Object>map=new HashMap<>();
      map.put("authorities",users.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return Jwts.builder()
                .signWith(getSecretKey())
                .subject(users.getUsername())
                .claim("id",users.getUserId())
                .claims(map)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .compact();
   }
   private Claims extractClaims(String token){
       return Jwts.parser()
               .verifyWith(getSecretKey())
               .build()
               .parseSignedClaims(token)
               .getPayload();
   }
   public String getUsername(String token){
      return extractClaims(token).getSubject();
   }
   public List<String> getAuthorities(String token){
      Claims claims=extractClaims(token);
      return (List<String>)claims.get("authorities");
   }

   public Users getCurrentUser(){
      Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
      if (authentication==null){
         throw new ApiError("user not authenticated", HttpStatus.UNAUTHORIZED.value());
      }
      return (Users) authentication.getPrincipal();
   }

   @Transactional
   public void handelEmailVerification(String token){
      if (!isTokenValid(token)){
         throw new ApiError("Invalid Token",HttpStatus.FORBIDDEN.value());
      }

      Claims claims=extractClaims(token);

      long userId=Long.parseLong(claims.getSubject());
      Users users=userRepository.findById(userId).orElseThrow(
              ()->new ApiError("User not found",HttpStatus.NOT_FOUND.value())
      );

      users.setVerified(true);
      userRepository.save(users);
   }
}
