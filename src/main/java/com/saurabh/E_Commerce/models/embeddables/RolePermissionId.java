package com.saurabh.E_Commerce.models.embeddables;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionId implements Serializable {
    @Column(name = "role_id")
    private long roleId;

    @Column(name = "permission_id")
    private long permissionId;

}
