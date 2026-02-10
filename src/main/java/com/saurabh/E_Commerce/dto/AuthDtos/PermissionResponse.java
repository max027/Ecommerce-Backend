package com.saurabh.E_Commerce.dto.AuthDtos;

import com.saurabh.E_Commerce.models.enums.ModuleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class PermissionResponse {
    private long permissionId;
    private String name;
    private String description;
    private ModuleEnum module;
}
