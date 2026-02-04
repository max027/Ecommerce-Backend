package com.saurabh.E_Commerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private long id;
    private String email;
    private String first_name;
    private String last_name;
    private String phone;
}
