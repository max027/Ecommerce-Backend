package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.models.Review;
import com.saurabh.E_Commerce.models.Users;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    @Query("""
            SELECT p FROM Products p WHERE p.price BETWEEN :minPrice AND :maxPrice
            """)
    Page<Products> findAllRange(Pageable pageable, @Param("minPrice")int minPrice,@Param("maxPrice") int maxPrice);

    @Query("""
        SELECT r FROM Review  r JOIN Products p 
        ON p.productId=r.products.productId 
        """)
    Page<Review> findAllReviews(Pageable pageable);

    Optional<Products> findBySlug(String slug);

    Page<Products> findByVendorsUsers(Users vendorsUsers, Pageable pageable);
}