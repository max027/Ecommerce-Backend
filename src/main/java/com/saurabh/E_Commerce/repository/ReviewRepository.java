package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.models.Review;
import com.saurabh.E_Commerce.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUsersAndProducts(Users users, Products products);
}