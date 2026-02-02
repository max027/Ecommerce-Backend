package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
    Optional<ResetToken> findByToken(String token);
    void deleteAllByExpiresAtBefore(Instant time);
}