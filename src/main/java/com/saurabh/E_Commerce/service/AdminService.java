package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.AcceptInviteRequest;
import com.saurabh.E_Commerce.dto.RegisterRequest;
import com.saurabh.E_Commerce.dto.RegisterResponse;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.InviteToken;
import com.saurabh.E_Commerce.models.Roles;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.models.enums.RolesEnum;
import com.saurabh.E_Commerce.repository.InviteTokenRepository;
import com.saurabh.E_Commerce.repository.RolesRepository;
import com.saurabh.E_Commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    //private final EmailService emailService;
    private final String frontendUrl="http://localhost:8080/";
    private final InviteTokenRepository inviteTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RolesRepository rolesRepository;

    public void inviteAdmin(String email){
        createAndSendInvite(email, RolesEnum.ADMIN);
    }

    public void inviteVendor(String email){
        createAndSendInvite(email, RolesEnum.ADMIN);
    }
    private void createAndSendInvite(String email, RolesEnum rolesEnum) {
        if(userRepository.existsByEmail(email)){
            throw  new ApiError("user already exist", HttpStatus.CONFLICT.value());
        }

        String token= UUID.randomUUID().toString()+UUID.randomUUID();
        InviteToken inviteToken=new InviteToken();
        inviteToken.setToken(token);
        inviteToken.setEmail(email);
        inviteToken.setRoles(rolesEnum);
        inviteToken.setExpiresAt(Instant.now().plusSeconds(43200));

        inviteTokenRepository.save(inviteToken);
        //send email
    }

    public void acceptInvite(String token, AcceptInviteRequest request){
        InviteToken inviteToken=inviteTokenRepository.findByToken(token).orElseThrow(
                ()->new ApiError("Invalid Token",HttpStatus.FORBIDDEN.value())
        );

        if (inviteToken.isUsed()){
            throw new ApiError("Invite already exists",HttpStatus.CONFLICT.value());
        }
        if (Instant.now().isBefore(inviteToken.getExpiresAt())){
            throw new ApiError("Invite expires",HttpStatus.FORBIDDEN.value());
        }

        Users users=new Users();
        users.setEmail(inviteToken.getEmail());
        users.setPassword(encoder.encode(request.getPassword()));

//      users.setRoles(Set.of(inviteToken.getRoles()));

        inviteToken.setUsed(true);
        inviteTokenRepository.save(inviteToken);

    }

}
