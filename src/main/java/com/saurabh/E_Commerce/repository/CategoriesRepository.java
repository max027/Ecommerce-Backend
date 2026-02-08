package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    Optional<Categories> findBySlug(String slug);
}