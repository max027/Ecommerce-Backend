package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
}