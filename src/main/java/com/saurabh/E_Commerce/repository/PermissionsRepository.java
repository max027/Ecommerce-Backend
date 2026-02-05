package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
}