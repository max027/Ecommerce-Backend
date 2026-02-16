package com.saurabh.E_Commerce.dto.AuthDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendorAcceptInviteDto {
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=.,?])(?=\\S+$).{8,20}$",
            message = "Password must be 8-20 characters long and include at least one digit, one lowercase letter, one uppercase letter, and one special character (!@#$%^&+=.,?)")
    private String password;

    @NotBlank(message = "first name is required")
    private String firstName;

    @NotBlank(message = "last name is required")
    private String lastName;

    @Range(message = "invalid phone")
    private String phone;

    @NotBlank(message = "gst number is required")
    private String gstNumber;

    @NotBlank(message = "businessName is required")
    private String businessName;
}
