package com.saurabh.E_Commerce.security;

import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Users> users=userRepository.findByEmail(username);
        if (users.isEmpty()){
           throw new UsernameNotFoundException("user not found");
        }

        return  users.get();
    }
}
