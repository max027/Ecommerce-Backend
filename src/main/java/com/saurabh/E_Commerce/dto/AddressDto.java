package com.saurabh.E_Commerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    @NotBlank(message = "address type is required")
    private String addressType;

    @NotBlank(message = "address line is required")
    private String addressLine1;

    @NotBlank(message = "address line is required")
    private String addressLine2;

    @NotBlank(message = " city is required")
    private String city;

    @NotBlank(message = "state is required")
    private String state;

    @NotBlank(message = "postal code is required")
    private String postalCode;

    @NotBlank(message = "country name is required")
    private String country;
}
