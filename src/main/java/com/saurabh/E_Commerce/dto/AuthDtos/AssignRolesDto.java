package com.saurabh.E_Commerce.dto.AuthDtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRolesDto {
    @NotNull
   private Long[] rolesId;
}
