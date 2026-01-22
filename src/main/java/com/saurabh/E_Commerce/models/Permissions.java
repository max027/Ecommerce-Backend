package com.saurabh.E_Commerce.models;

import com.saurabh.E_Commerce.models.enums.ModuleEnum;
import com.saurabh.E_Commerce.models.enums.PermissionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TenantId;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permissions extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long permissionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermissionEnum name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ModuleEnum module;

}
