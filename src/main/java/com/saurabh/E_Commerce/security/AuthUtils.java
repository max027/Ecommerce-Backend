package com.saurabh.E_Commerce.security;

import com.saurabh.E_Commerce.models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthUtils {
   @Value("${security.jwt.secret-key}")
   private String secretKey;

   @Value("${security.jwt.expiration-time}")
   private long expiration;

   private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
   }


   public String generateToken(Users users){
      Map<String,Object>map=new HashMap<>();
      map.put("authorities",users.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return Jwts.builder()
                .signWith(getSecretKey())
                .subject(users.getUsername())
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
}
