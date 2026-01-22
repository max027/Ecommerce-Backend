package com.saurabh.E_Commerce.models;

import com.saurabh.E_Commerce.models.embeddables.UserRolesId;
import jakarta.persistence.*;
import org.apache.catalina.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "user_roles")
public class UserRoles {
    @EmbeddedId
    private UserRolesId userRolesId;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Roles roles;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users users;

    @Column(name = "assigned_at")
    private Instant assignedAt=Instant.now();

}
