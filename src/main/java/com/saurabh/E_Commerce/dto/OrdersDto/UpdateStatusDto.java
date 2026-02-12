package com.saurabh.E_Commerce.dto.OrdersDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusDto {
    @NotBlank(message = "status is required")
    private String status;

    @NotBlank(message = "note is required")
    private String note;
}
