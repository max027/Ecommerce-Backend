package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    @Modifying
    @Query("""
    update Users  u
    set u.lastLogin=:time
    where u.userId=:userId
    """)
    void updateLastLogin(@Param("userId") long userId, @Param("time") Instant time);

    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("""
        SELECT DISTINCT u FROM Users  u 
        JOIN u.roles r WHERE r.name="ADMIN"
        """)
    List<Users> findAllAdmins();

    @Query("""
        SELECT DISTINCT u FROM Users  u 
        JOIN u.roles r WHERE r.name="VENDOR"
        """)
    Page<Users> findAllVendors(Pageable pageable);

}
