package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Transactional
    public void updateLastLogin(){
        long userId=1;//change after implementing jwt
        userRepository.updateLastLogin(userId, Instant.now());
    }
}
