package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
}