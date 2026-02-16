package com.saurabh.E_Commerce.dto.Vendors;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorsDto {
    private long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    private String businessEmail;

    private String businessName;

    private boolean isApproved=false;

    private String gstNumber;
}
