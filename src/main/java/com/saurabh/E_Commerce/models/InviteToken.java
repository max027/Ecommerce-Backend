package com.saurabh.E_Commerce.models;

import com.saurabh.E_Commerce.models.enums.RolesEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "invite_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InviteToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long inviteTokenId;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private RolesEnum roles;

    @Column(nullable = false,unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiresAt;

    private boolean used=false;

}
