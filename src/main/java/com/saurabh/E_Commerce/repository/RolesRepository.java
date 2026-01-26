package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Roles;
import com.saurabh.E_Commerce.models.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findRolesByName(RolesEnum name);
}
