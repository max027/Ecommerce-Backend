package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.AuthDtos.UserDto;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.security.AuthUtils;
import com.saurabh.E_Commerce.utils.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VendorService {
    private final AuthUtils authUtils;

    public UserDto getProfile() {
        Users users=authUtils.getCurrentUser();
        return DataMapper.convertToUserDto(users);
    }

}
