package com.saurabh.E_Commerce.security;

import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final AuthUtils authUtils;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headers=response.getHeader("Authorization");
        if (headers==null || !headers.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        String token=headers.split("Bearer ")[1];
        String username=authUtils.getUsername(token);

        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            Users users=userRepository.findByEmail(username).orElseThrow();
            UsernamePasswordAuthenticationToken token1=
                    new UsernamePasswordAuthenticationToken(
                            users,null,users.getAuthorities()
                    );
            SecurityContextHolder.getContext().setAuthentication(token1);
        }
        filterChain.doFilter(request,response);
    }
}
