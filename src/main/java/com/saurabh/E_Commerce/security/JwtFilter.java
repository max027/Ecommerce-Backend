package com.saurabh.E_Commerce.security;

import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final AuthUtils authUtils;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=null;
        if (request.getCookies()!=null){
            for (Cookie cookie: request.getCookies()){
                if ("accessToken".equals(cookie.getName())){
                    token=cookie.getValue();
                }
            }

        }
        if (token==null || !authUtils.isTokenValid(token)){
           filterChain.doFilter(request,response);
           return;
        }

        String username=authUtils.getUsername(token);

        List<String>authorities=authUtils.getAuthorities(token);
        List<? extends GrantedAuthority>grantedAuthorities=authorities.stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            Users users=userRepository.findByEmail(username).orElseThrow();
            UsernamePasswordAuthenticationToken token1=
                    new UsernamePasswordAuthenticationToken(
                            users,null,grantedAuthorities
                    );
            SecurityContextHolder.getContext().setAuthentication(token1);
        }
        filterChain.doFilter(request,response);
    }
}
