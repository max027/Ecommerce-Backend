package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}