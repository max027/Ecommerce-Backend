package com.saurabh.E_Commerce.models;

import com.saurabh.E_Commerce.models.enums.RolesEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Roles extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long roleId;

    @Enumerated(EnumType.STRING)
    @Column(unique = true,nullable = false)
    private RolesEnum name;

    private String description;

}
