package com.saurabh.E_Commerce.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "reset_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long tokenId;

    private String token;

    private Instant expiresAt;

    @ManyToOne
    private Users users;

    public boolean isExpired() {
        return expiresAt.isBefore(Instant.now());
    }
}
