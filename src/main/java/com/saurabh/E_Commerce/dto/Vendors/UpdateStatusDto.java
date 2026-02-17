package com.saurabh.E_Commerce.dto.Vendors;

import com.saurabh.E_Commerce.models.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusDto {
    @NotBlank
    private String status;
    @NotBlank
    private String notes;
}
