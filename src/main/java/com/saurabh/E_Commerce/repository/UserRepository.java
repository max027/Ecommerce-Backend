package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    @Modifying
    @Query("""
    update Users  u
    set u.lastLogin=:time
    where u.userId=:userId
    """)
    void updateLastLogin(@Param("userId") long userId, @Param("time") Instant time);
}
