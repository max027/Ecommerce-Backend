package com.saurabh.E_Commerce.config;

import com.saurabh.E_Commerce.repository.ResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TokenCleanup {
    private final ResetTokenRepository resetTokenRepository;
    @Scheduled(fixedRate = 3_600_000)
    public void clearExpiration(){
        resetTokenRepository.deleteAllByExpiresAtBefore(Instant.now());
    }
}
