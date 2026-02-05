package com.saurabh.E_Commerce.utils;

import com.saurabh.E_Commerce.dto.UserDto;
import com.saurabh.E_Commerce.models.Users;

public class DataMapper {
    public static UserDto convertToUserDto(Users users){
        return UserDto.builder().id(users.getUserId())
                .email(users.getEmail())
                .first_name(users.getFirstName())
                .last_name(users.getLastName())
                .phone(users.getPhone())
                .build();
    }
    private DataMapper(){

    }
}
