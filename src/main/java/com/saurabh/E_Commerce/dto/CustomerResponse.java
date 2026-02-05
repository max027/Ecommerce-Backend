package com.saurabh.E_Commerce.dto;

import com.saurabh.E_Commerce.models.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class CustomerResponse {
    private long id;
    private String email;
    private String first_name;
    private String last_name;
    private String phone;
    private Set<AddressRequest> address;
}
