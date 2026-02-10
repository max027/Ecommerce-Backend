package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponsRepository extends JpaRepository<Coupons, Long> {
    Optional<Coupons> findByCodeAndIsActiveTrue(String code);
    Optional<Coupons> findByCode(String code);
}