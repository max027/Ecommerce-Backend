package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.ProductImage;
import com.saurabh.E_Commerce.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findByProductImageIdAndProducts(long productImageId, Products products);
}